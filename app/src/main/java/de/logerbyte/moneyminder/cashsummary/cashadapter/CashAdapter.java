package de.logerbyte.moneyminder.cashsummary.cashadapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.cashsummary.editdialog.DialogViewModel;
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding;
import de.logerbyte.moneyminder.databinding.AdapterEntryPlusSummaryBinding;
import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.util.ConvertUtil;
import de.logerbyte.moneyminder.util.DigitUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by logerom on 28.07.18.
 */

public class CashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        CashAdapterItemViewModel.AdapterListener, DialogViewModel.ViewInterface {

    private static final int SUMMARY_LINE = 0;
    private static final int SAME_WEEK = 1;
    private List<ViewType> viewtypeList = new ArrayList<>();

    private enum ViewType {
        SUMMARY_LINE,
        SAME_WEEK
    }

    // TODO: 13.12.18 set year as filter in ui
    private static final int YEAR = 2018;
    private final Calendar calendar;
    private ArrayList<CashAdapterItemViewModel> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private AppDatabaseManager appDatabaseManager;
    private Listener mAdapterListener;
    private CashAdapterItemViewModel.ActivityListener cashSummaryActivity;
    private Map<Date, CashAdapterItemViewModel> dateMap = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
    private LinkedList<LinkedList<CashAdapterItemViewModel>> weekList = new LinkedList<>();

    public CashAdapter(AppDatabaseManager appDatabaseManager) {
        this.appDatabaseManager = appDatabaseManager;
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        loadExpenseList();
    }

    //        for (int i = 0; i < list.size(); i++) {
    //            Calendar parsedDate = parseDate(i);
    //    private void setUpDayList() {
    //            Date day = parsedDate.getTime();
    //            dateMap.put(day, list.get(i));
    //        }
    //    }

    // FIXME: 23.09.18 2 same loadExpense functions. base it.
    public void loadExpenseList() {
        appDatabaseManager.getAllExpense().subscribeOn(Schedulers.io()).map(
                expenses -> sortExpenses(expenses)).observeOn(AndroidSchedulers.mainThread()).subscribe(expenses -> {
            list = ConvertUtil.expensesToCashItems(expenses);
            mAdapterListener.onLoadedExpenses(expenses);
            createWeekDates(list);
            createViewTypeList(list);
            notifyDataSetChanged();
        });
    }

    private List<Expense> sortExpenses(List<Expense> expenses) {
        // FIXME: 22.09.18 sort as util class
        expenses.sort((o1, o2) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            Date d1 = null, d2 = null;

            try {
                d1 = sdf.parse(o1.cashDate);
                d2 = sdf.parse(o2.cashDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d1.compareTo(d2);
        });
        return expenses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        switch (viewType) {
            case SAME_WEEK:
                viewHolder = layoutDateItem(parent);
                break;
            case SUMMARY_LINE:
                viewHolder = layoutSummaryLine(parent);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initItemAtPosition(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return viewtypeList.get(position).ordinal();
    }

    @Override
    public int getItemCount() {
        return viewtypeList.size();
    }

    private void initItemAtPosition(RecyclerView.ViewHolder viewHolder, int position) {
        int itemPosition = -1;
        // TODO: 17.01.19 Silvester is first item cause of first week. It should be as first item in new year not as
        // first item in old year.
        for (int i = 0; i < weekList.size(); i++) {

            for (int j = 0; j < weekList.get(i).size(); j++) {
                itemPosition = itemPosition + 1;

                if (itemPosition == position) {
                    // Here is the searched item
                    initDayWeek(weekList.get(i).get(j), viewHolder);
                    return;
                }
            }

            // plus 1 for summary line
            itemPosition = itemPosition + 1;
            if (itemPosition == position) {
                // Summary line
                initCashSummaryLine(weekList.get(i), viewHolder);
                return;
            }
        }
    }

    private RecyclerView.ViewHolder layoutSummaryLine(ViewGroup parent) {
        AdapterEntryPlusSummaryBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.adapter_entry_plus_summary, parent, false);
        return new ViewHolderSummary(binding);
    }

    private RecyclerView.ViewHolder layoutDateItem(ViewGroup parent) {
        AdapterEntryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_entry, parent, false);
        return new ViewHolder(binding);
    }

    private void initCashSummaryLine(LinkedList<CashAdapterItemViewModel> week, RecyclerView.ViewHolder holder) {
        // TODO: 15.01.19  week-list has all 52 weeks, but should only has these weeks where entries are.
        double cashSummary = 0.0;
        for (CashAdapterItemViewModel vm : week) {
            cashSummary = cashSummary + Double.parseDouble(DigitUtil.commaToDot(vm.getCashInEuro().get()));
        }

        ((ViewHolderSummary) holder).binding.setVmSummary(new ItemSummaryViewModel(String.valueOf(cashSummary)));
    }

    private void initDayWeek(CashAdapterItemViewModel cashAdapterItemViewModel, RecyclerView.ViewHolder holder) {
        cashAdapterItemViewModel.setAdapterListener(this);
        cashAdapterItemViewModel.setDialogViewModelListener(this);
        cashAdapterItemViewModel.setActivityListener(cashSummaryActivity);
        ((ViewHolder) holder).binding.setVmCashItem(cashAdapterItemViewModel);
    }

    private void isSameWeek() {
    }

    private void createWeekDates(ArrayList<CashAdapterItemViewModel> list) {
        calendar.clear();
        weekList.clear();

        Date actualDate = null;

        for (int i = 0; i < 52; i++) {
            // todo: 17.01.19 This would be only work for a period of one year
            int week = i + 1;
            weekList.add(new LinkedList<>());
            for (CashAdapterItemViewModel itemViewModel : list) {
                String dateString = itemViewModel.getCashDate().get();

                try {
                    actualDate = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                calendar.setTime(actualDate);
                // add date item to week
                if (week == calendar.get(Calendar.WEEK_OF_YEAR)) {
                    weekList.get(i).add(itemViewModel);
                }
            }
        }
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

    public void createViewTypeList(ArrayList<CashAdapterItemViewModel> list) {
        createWeekDates(list);
        viewtypeList.clear();
        for (List<CashAdapterItemViewModel> dayList : weekList) {
            for (CashAdapterItemViewModel day : dayList) {
                viewtypeList.add(ViewType.SAME_WEEK);
            }
            viewtypeList.add(ViewType.SUMMARY_LINE);
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

    public void setList(ArrayList<CashAdapterItemViewModel> list) {
        this.list = list;
    }

    public void setLisener(CashAdapter.Listener adapterListener) {
        mAdapterListener = adapterListener;
    }

    @Override
    public void onItemDeleteClicked(Long cashItemId) {
        appDatabaseManager.deleteCashItem(cashItemId).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(aBoolean -> loadExpenseList());
    }

    @Override
    public void onItemDeleted() {
        loadExpenseList();
    }

    @Override
    public void onUpdateItem() {
        loadExpenseList();
    }

    public void setActivityListener(CashAdapterItemViewModel.ActivityListener cashSummaryActivity) {
        this.cashSummaryActivity = cashSummaryActivity;
    }

    public interface Listener {

        void onLoadedExpenses(List<Expense> expenses);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        AdapterEntryBinding binding;

        public ViewHolder(AdapterEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    protected static class ViewHolderSummary extends RecyclerView.ViewHolder {

        AdapterEntryPlusSummaryBinding binding;

        public ViewHolderSummary(AdapterEntryPlusSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
