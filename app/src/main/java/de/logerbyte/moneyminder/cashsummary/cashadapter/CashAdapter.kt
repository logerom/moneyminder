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

        createWeekDates(list)
        createViewTypeList(list)
        notifyDataSetChanged()
    }

    private fun sortExpenses(expenses: List<Expense>): List<Expense> {
        // FIXME: 22.09.18 sort as util class
        return expenses.sortedBy { expense -> expense.cashDate }

        /*(compareByDescending {  }) {it.
            val sdf = SimpleDateFormat("dd.MM.yy")
            var d1: Date? = null
            var d2: Date? = null

            try {
                d1 = sdf.parse(o1.cashDate)
                d2 = sdf.parse(o2.cashDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            d1!!.compareTo(d2)
        }*/
        // return expenses
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
                itemPosition = itemPosition + 1

                if (itemPosition == position) {
                    // Here is the searched item
                    initDayWeek(weekList[i][j], viewHolder)
                    return
                }
            }

            // plus 1 for summary line
            itemPosition = itemPosition + 1
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
            cashSummary = cashSummary + java.lang.Double.parseDouble(DigitUtil.commaToDot(vm.cashInEuro.get()))
        }

        (holder as ViewHolderSummary).binding.vmSummary = ItemSummaryViewModel(cashSummary.toString())
    }

    private fun initDayWeek(cashAdapterItemViewModel: CashAdapterItemViewModel, holder: RecyclerView.ViewHolder) {
        cashAdapterItemViewModel.setAdapterListener(this)
        cashAdapterItemViewModel.setDialogViewModelListener(this)
        cashAdapterItemViewModel.setActivityListener(cashSummaryActivity)
        (holder as ViewHolder).binding.vmCashItem = cashAdapterItemViewModel
    }

    private fun isSameWeek() {}

    private fun createWeekDates(list: ArrayList<CashAdapterItemViewModel>) {
        var weekInCurrentDate: Int
        var actualDate: Date? = null
        calendar.clear()
        weekList.clear()


        weekList.add(LinkedList())
        var currentWeek = weekList.last

        // FIXME: 20.01.19 could be efficienter when we delete this item from the root item list which is already
        // signed to a week
        for (itemViewModel0 in list) {
            actualDate = getDate(actualDate, itemViewModel0)
            calendar.time = actualDate
            weekInCurrentDate = calendar.get(Calendar.WEEK_OF_YEAR)

            for (itemViewModel in list) {
                val dateToCompare = getDate(actualDate, itemViewModel)
                calendar.time = dateToCompare
                val weekToCompare = calendar.get(Calendar.WEEK_OF_YEAR)

                // TODO: 20.01.19
                //      1. take iterator and delete item from list.
                //      2. when week to compare is lower then actual week, its a new year
                if (weekInCurrentDate == weekToCompare) {
                    currentWeek.add(itemViewModel)
                }
                if (weekInCurrentDate < weekToCompare) {
                    weekList.add(LinkedList())
                    currentWeek = weekList.last
                    break
                }
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
    //        }
    //            return SAME_WEEK;
    //        } else {
    //            return SUMMARY_LINE;
    //        if (days.contains(position)) {
    //        List days = Arrays.asList(summaryLinesIndex);
    //                147, 154, 161, 168, 175, 182, 189, 196, 203, 210, 217, 224, 231, 238, 245, 252, 259, 266};
    //        int[] summaryLinesIndex = {7, 14, 21, 28, 35, 42, 49, 56, 63, 70, 77, 84, 91, 98, 105, 112, 119, 126, 133, 140,
    //        // cost in that week.
    //
    //        //        }
    //        //
    //        //        if (firstDate != null) {
    //        //        CashAdapterItemViewModel firstDate = ((LinkedList<CashAdapterItemViewModel>) firstWeek).getFirst();
    //
    //        }
    //            viewtypeList.add(ViewType.SUMMARY_LINE);
    //        } else {
    //            viewtypeList.add(ViewType.SAME_WEEK);
    //            Iterator<CashAdapterItemViewModel> iteratorDay = iteratorWeek.next().iterator();
    //        if (iteratorWeek.hasNext()) {
    //
    //        Iterator<LinkedList<CashAdapterItemViewModel>> iteratorWeek = weekList.iterator();
    //    private int bla(int position) {

    //    }

    fun createViewTypeList(list: ArrayList<CashAdapterItemViewModel>) {
        viewtypeList.clear()
        for (dayList in weekList) {
            for (day in dayList) {
                viewtypeList.add(ViewType.SAME_WEEK)
            }
            viewtypeList.add(ViewType.SUMMARY_LINE)
        }
    }
    //        return calendar;
    //        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    //        calendar.setTime(actualDate);
    //        Calendar calendar = Calendar.getInstance();
    //
    //        }
    //            e.printStackTrace();
    //        } catch (ParseException e) {
    //            actualDate = sdf.parse(dateString);
    //        try {
    //
    //        Date actualDate = null;
    //        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
    //        String dateString = cashAdapterItemViewModel.getCashDate().get();
    //        CashAdapterItemViewModel cashAdapterItemViewModel = list.get(position);
    //    private Calendar parseDate(int position) {
    //    @NonNull

    //    }

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
