package de.logerbyte.moneyminder.screens.cashsummary.cashadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.db.AppDatabaseManager
import de.logerbyte.moneyminder.data.db.expense.Expense
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding
import de.logerbyte.moneyminder.databinding.AdapterEntryPlusSummaryBinding
import de.logerbyte.moneyminder.deleteDialog.DeleteDialogFragment
import de.logerbyte.moneyminder.util.ConvertUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by logerom on 28.07.18.
 */

const val BUNDLE_CASHITEM_ID = "cash_item_id"

class CashAdapter @Inject constructor(
        private val appDatabaseManager: AppDatabaseManager,
        private val expenseManager: ExpenseManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), DayExpenseViewModel.AdapterListener, AdapterCallBack {

    var dependencyView: View? = null
    var floatingDepedencyViewID = 0
    lateinit var recView: RecyclerView
    var floating = false
    lateinit var attechedActivity: FragmentActivity
    private val viewtypeList = ArrayList<ViewType>()
    private val calendar: Calendar
    private var viewModelCashItems = ArrayList<DayExpenseViewModel>()
    private var layoutInflater: LayoutInflater? = null
    private var mAdapterListener: Listener? = null
    private var cashSummaryActivity: DayExpenseViewModel.ActivityListener? = null
    private val dateMap = HashMap<Date, DayExpenseViewModel>()
    var sdf = SimpleDateFormat("dd.MM.yy")
    private val weeksAndDaysWithExpenses = ArrayList<ArrayList<DayExpenseViewModel>>()
    private lateinit var daysWithWeekSummaryViewModelList: ArrayList<WeekSummaryViewModel>

    private enum class ViewType {
        SUMMARY_LINE,
        SAME_WEEK
    }

    init {
        calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        loadExpenseList()
    }

    // FIXME: 23.09.18 2 same loadExpense functions. base it.
    fun loadExpenseList() {
        appDatabaseManager.allExpense
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { expenses -> initList(expenses) }
    }

    fun initList(expenses: List<Expense>) {
        val sortedExpenses = expenses.sortedBy { sdf.parse(it.cashDate) }
        viewModelCashItems = ConvertUtil.expensesToViewModelCashItems(sortedExpenses)
        recreateList()

        mAdapterListener!!.onLoadedExpenses(expenses, expenseManager.getOverAllBudget())
        createViewTypeList(daysWithWeekSummaryViewModelList)
        notifyDataSetChanged()
    }

    private fun recreateList() {
        daysWithWeekSummaryViewModelList = expenseManager.createWeeksAndDaysExpense(viewModelCashItems)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        attechedActivity = parent.context as FragmentActivity
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return chooseViewHolder(viewType, parent)
    }

    private fun chooseViewHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        return when (viewType) {
            SAME_WEEK -> layoutDateItem(parent)
            else -> layoutSummaryLine(parent)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recView = recyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItemAtPosition(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return viewtypeList[position].ordinal
    }

    override fun getItemCount(): Int {
        return viewtypeList.size
    }

    private fun getItemAtPosition(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var itemPosition = -1
        for (weekSummary in daysWithWeekSummaryViewModelList) {
            val dayList = weekSummary.dayExpense

            // days
            for (dayExpense in dayList) {
                itemPosition++
                if (itemPosition == position) {
                    initDayItem(dayExpense, viewHolder)
                }
            }
            // weeks
            itemPosition++
            if (itemPosition == position) {
                initCashSummaryItem(weekSummary, viewHolder)
            }
        }
    }

    private fun layoutSummaryLine(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<AdapterEntryPlusSummaryBinding>(layoutInflater!!,
                R.layout.adapter_entry_plus_summary, parent, false)
        return ViewHolderSummary(binding)
    }

    private fun layoutDateItem(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<AdapterEntryBinding>(layoutInflater!!, R.layout.adapter_entry, parent, false)
        return ViewHolder(binding)
    }

    private fun initCashSummaryItem(week: WeekSummaryViewModel, holder: RecyclerView.ViewHolder) {
        (holder as ViewHolderSummary).binding.vmSummary = week
    }

    private fun initDayItem(dayExpenseViewModel: DayExpenseViewModel, holder: RecyclerView.ViewHolder) {
        dayExpenseViewModel.setItemListener(this)
        dayExpenseViewModel.setDialogListener(this)
        dayExpenseViewModel.setActivityListener(cashSummaryActivity)
        (holder as ViewHolder).binding.vmCashItem = dayExpenseViewModel
    }

    fun createViewTypeList(list: ArrayList<WeekSummaryViewModel>) {
        viewtypeList.clear()
        for (weekSummary in list) {
            for (expense in weekSummary.dayExpense) {
                viewtypeList.add(ViewType.SAME_WEEK)
            }
            viewtypeList.add(ViewType.SUMMARY_LINE)
        }
    }

    fun setLisener(adapterListener: CashAdapter.Listener) {
        mAdapterListener = adapterListener
    }

    override fun onItemDeleteClicked(cashItemId: Long?) {
        DeleteDialogFragment().let {
            it.arguments = Bundle().apply { this.putLong(BUNDLE_CASHITEM_ID, cashItemId!!) }
            it.setAdapterCallback(this)
            it.show(attechedActivity.supportFragmentManager, "delete_tag")
        }
    }

    override fun onItemDeleted() {
        loadExpenseList()
    }

    override fun onUpdateItem() {
        loadExpenseList()
    }

    override fun onAddClicked() {
        loadExpenseList()
    }

    fun setActivityListener(cashSummaryActivity: DayExpenseViewModel.ActivityListener) {
        this.cashSummaryActivity = cashSummaryActivity
    }

    fun onBudgetUpdated() {
        expenseManager.loadBudgetFromSharedPref()
        recreateList()
        this.notifyDataSetChanged()
    }

    interface Listener {

        fun onLoadedExpenses(expenses: List<Expense>, allBudget: Int)
    }

    protected class ViewHolder(internal var binding: AdapterEntryBinding) : RecyclerView.ViewHolder(binding.root)

    protected class ViewHolderSummary(internal var binding: AdapterEntryPlusSummaryBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {

        private val SUMMARY_LINE = 0
        private val SAME_WEEK = 1

        private val YEAR = 2018
    }
}
