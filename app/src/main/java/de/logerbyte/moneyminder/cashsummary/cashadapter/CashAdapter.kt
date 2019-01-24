package de.logerbyte.moneyminder.cashsummary.cashadapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.cashsummary.editdialog.DialogViewModel
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding
import de.logerbyte.moneyminder.databinding.AdapterEntryPlusSummaryBinding
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.db.expense.Expense
import de.logerbyte.moneyminder.util.ConvertUtil
import de.logerbyte.moneyminder.util.DigitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by logerom on 28.07.18.
 */

class CashAdapter(private val appDatabaseManager: AppDatabaseManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), CashAdapterItemViewModel.AdapterListener, DialogViewModel.ViewInterface {
    private val viewtypeList = ArrayList<ViewType>()
    private val calendar: Calendar
    private var list = ArrayList<CashAdapterItemViewModel>()
    private var layoutInflater: LayoutInflater? = null
    private var mAdapterListener: Listener? = null
    private var cashSummaryActivity: CashAdapterItemViewModel.ActivityListener? = null
    private val dateMap = HashMap<Date, CashAdapterItemViewModel>()
    internal var sdf = SimpleDateFormat("dd.MM.yy")
    private val weekList = LinkedList<LinkedList<CashAdapterItemViewModel>>()

    private enum class ViewType {
        SUMMARY_LINE,
        SAME_WEEK
    }

    init {
        calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        loadExpenseList()
    }

    //        for (int i = 0; i < list.size(); i++) {
    //            Calendar parsedDate = parseDate(i);
    //    private void setUpDayList() {
    //            Date day = parsedDate.getTime();
    //            dateMap.put(day, list.get(i));
    //        }
    //    }

    // FIXME: 23.09.18 2 same loadExpense functions. base it.
    fun loadExpenseList() {
        appDatabaseManager.allExpense.subscribeOn(Schedulers.io()).map { expenses -> sortExpenses(expenses) }.observeOn(AndroidSchedulers.mainThread()).subscribe { expenses -> initList(expenses) }
    }

    private fun initList(expenses: List<Expense>) {
        list = ConvertUtil.expensesToCashItems(expenses)
        mAdapterListener!!.onLoadedExpenses(expenses)

        createWeeDateList(list)
        createViewTypeList(list)
        notifyDataSetChanged()
    }

    private fun sortExpenses(expenses: List<Expense>): List<Expense> {
        // FIXME: 22.09.18 sort as util class
        return expenses.sortedBy { expense ->
            val sdf = SimpleDateFormat("dd.MM.yy")
            sdf.parse(expense.cashDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return when (viewType) {
            SAME_WEEK -> layoutDateItem(parent)
            else -> layoutSummaryLine(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        initItemAtPosition(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return viewtypeList[position].ordinal
    }

    override fun getItemCount(): Int {
        return viewtypeList.size
    }

    private fun initItemAtPosition(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var itemPosition = -1
        // first item in old year.
        for (i in weekList.indices) {

            for (j in 0 until weekList[i].size) {
                itemPosition += 1

                if (itemPosition == position) {
                    // Here is the searched item
                    initDayWeek(weekList[i][j], viewHolder)
                    return
                }
            }

            // plus 1 for summary line
            itemPosition += 1
            if (itemPosition == position) {
                // Summary line
                initCashSummaryLine(weekList[i], viewHolder)
                return
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

    private fun initCashSummaryLine(week: LinkedList<CashAdapterItemViewModel>, holder: RecyclerView.ViewHolder) {
        var cashSummary = 0.0
        for (vm in week) {
            cashSummary += java.lang.Double.parseDouble(DigitUtil.commaToDot(vm.cashInEuro.get()))
        }

        (holder as ViewHolderSummary).binding.vmSummary = ItemSummaryViewModel(cashSummary.toString())
    }

    private fun initDayWeek(cashAdapterItemViewModel: CashAdapterItemViewModel, holder: RecyclerView.ViewHolder) {
        cashAdapterItemViewModel.setAdapterListener(this)
        cashAdapterItemViewModel.setDialogViewModelListener(this)
        cashAdapterItemViewModel.setActivityListener(cashSummaryActivity)
        (holder as ViewHolder).binding.vmCashItem = cashAdapterItemViewModel
    }

    private fun createWeeDateList(list: ArrayList<CashAdapterItemViewModel>) {
        var again = true
        var actualDate: Date? = null
        var actualWeek: Int? = null
        val datesToDelete = ArrayList<CashAdapterItemViewModel>()

        calendar.clear()
        weekList.clear()

        while (again) {

            weekList.add(LinkedList())
            for (item: CashAdapterItemViewModel in list) {
                // actual date and week
                if (list.indexOf(item) == 0) {
                    actualDate = getDate(actualDate, item)
                    calendar.time = actualDate
                    actualWeek = calendar.get(Calendar.WEEK_OF_YEAR)
                }
                // date and week to compare with actual date
                val dateToCompare = getDate(actualDate, item)
                calendar.time = dateToCompare
                val weekToCompare = calendar.get(Calendar.WEEK_OF_YEAR)

                if (weekToCompare == actualWeek) {
                    weekList.last.add(item)
                    datesToDelete.add(item)
                } else {
                    break
                }
            }
            list.removeAll(datesToDelete)

            if (list.isEmpty()) {
                again = false
            }
        }
    }

    private fun getDate(actualDate: Date?, itemViewModel: CashAdapterItemViewModel): Date? {
        var actualDate = actualDate
        val dateString = itemViewModel.cashDate.get()

        try {
            actualDate = sdf.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return actualDate
    }

    fun createViewTypeList(list: ArrayList<CashAdapterItemViewModel>) {
        viewtypeList.clear()
        for (dayList in weekList) {
            for (day in dayList) {
                viewtypeList.add(ViewType.SAME_WEEK)
            }
            viewtypeList.add(ViewType.SUMMARY_LINE)
        }
    }

    fun setList(list: ArrayList<CashAdapterItemViewModel>) {
        this.list = list
    }

    fun setLisener(adapterListener: CashAdapter.Listener) {
        mAdapterListener = adapterListener
    }

    override fun onItemDeleteClicked(cashItemId: Long?) {
        appDatabaseManager.deleteCashItem(cashItemId!!).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe { aBoolean -> loadExpenseList() }
    }

    override fun onItemDeleted() {
        loadExpenseList()
    }

    override fun onUpdateItem() {
        loadExpenseList()
    }

    fun setActivityListener(cashSummaryActivity: CashAdapterItemViewModel.ActivityListener) {
        this.cashSummaryActivity = cashSummaryActivity
    }

    interface Listener {

        fun onLoadedExpenses(expenses: List<Expense>)
    }

    protected class ViewHolder(internal var binding: AdapterEntryBinding) : RecyclerView.ViewHolder(binding.root)

    protected class ViewHolderSummary(internal var binding: AdapterEntryPlusSummaryBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {

        private val SUMMARY_LINE = 0
        private val SAME_WEEK = 1

        private val YEAR = 2018
    }
}
