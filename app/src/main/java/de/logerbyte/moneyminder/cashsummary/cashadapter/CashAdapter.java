package de.logerbyte.moneyminder.cashsummary.cashadapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
        loadExpenseList();
        setUpDayList();
        calendar = Calendar.getInstance();
        createViewTypeList();
    }

    private void setUpDayList() {
        for (int i = 0; i < list.size(); i++) {
            Calendar parsedDate = parseDate(i);
            Date day = parsedDate.getTime();
            dateMap.put(day, list.get(i));
        }
    }

    // FIXME: 23.09.18 2 same loadExpense functions. base it.
    public void loadExpenseList() {
        appDatabaseManager.getAllExpense().subscribeOn(Schedulers.io()).map(
                expenses -> sortExpenses(expenses)).observeOn(AndroidSchedulers.mainThread()).subscribe(expenses -> {
            list = ConvertUtil.expensesToCashItems(expenses);
            mAdapterListener.onLoadedExpenses(expenses);
            notifyDataSetChanged();
        });
    }

    private List<Expense> sortExpenses(List<Expense> expenses) {
        // FIXME: 22.09.18 sort as util class
        Collections.sort(expenses, (o1, o2) -> {
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
        Collections.reverse(expenses);
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
                viewHolder = sameWeek(parent);
                break;
            case SUMMARY_LINE:
                viewHolder = newWeek(parent);
                break;
        }

        return viewHolder;
    }

    private RecyclerView.ViewHolder newWeek(ViewGroup parent) {
        AdapterEntryPlusSummaryBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.adapter_entry_plus_summary, parent, false);
        return new ViewHolderSummary(binding);
    }

    private RecyclerView.ViewHolder sameWeek(ViewGroup parent) {
        AdapterEntryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_entry, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SAME_WEEK:
                initDayWeek(position, holder);
                break;
            case SUMMARY_LINE:
                initCashSummaryLine(position, holder);
        }
    }

    private void createWeekDates() {
        calendar.clear();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Date actualDate = null;

        for (int i = 0; i < 52; i++) {
            int week = i + 1;

            for (CashAdapterItemViewModel itemViewModel : list) {
                String dateString = itemViewModel.getCashDate().get();
                try {
                    actualDate = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar.setTime(actualDate);
                int weekYear = calendar.getWeekYear();

                // add date item to week
                if (week == weekYear) {
                    weekList.get(i).add(itemViewModel);
                } else {
                    return;
                }
            }
        }
    }

    private void initCashSummaryLine(int position, RecyclerView.ViewHolder holder) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, position + 1);
        Date time = calendar.getTime();
        //        getCostForLast7Days(time);
        // TODO: 12.12.18 init new week with listener and view model
    }

    private void initDayWeek(int position, RecyclerView.ViewHolder holder) {
        // TODO: 18.12.18 init dates
        CashAdapterItemViewModel cashAdapterItemViewModel = list.get(position);
        cashAdapterItemViewModel.setAdapterListener(this);
        cashAdapterItemViewModel.setDialogViewModelListener(this);
        cashAdapterItemViewModel.setActivityListener(cashSummaryActivity);
        ((ViewHolder) holder).binding.setVmCashItem(cashAdapterItemViewModel);
    }

    @Override
    public int getItemViewType(int position) {
        return viewtypeList.get(position).ordinal();
    }

    private int bla(int position) {
        Iterator<LinkedList<CashAdapterItemViewModel>> iteratorWeek = weekList.iterator();

        if (iteratorWeek.hasNext()) {
            Iterator<CashAdapterItemViewModel> iteratorDay = iteratorWeek.next().iterator();
            viewtypeList.add(ViewType.SAME_WEEK);
        } else {
            viewtypeList.add(ViewType.SUMMARY_LINE);
        }

        //        CashAdapterItemViewModel firstDate = ((LinkedList<CashAdapterItemViewModel>) firstWeek).getFirst();
        //        if (firstDate != null) {
        //
        //        }

        // cost in that week.
        int[] summaryLinesIndex = {7, 14, 21, 28, 35, 42, 49, 56, 63, 70, 77, 84, 91, 98, 105, 112, 119, 126, 133, 140,
                147, 154, 161, 168, 175, 182, 189, 196, 203, 210, 217, 224, 231, 238, 245, 252, 259, 266};
        List days = Arrays.asList(summaryLinesIndex);
        if (days.contains(position)) {
            return SUMMARY_LINE;
        } else {
            return SAME_WEEK;
        }
    }

    private void createViewTypeList() {
        for (List<CashAdapterItemViewModel> dayList : weekList) {
            for (CashAdapterItemViewModel day : dayList) {
                viewtypeList.add(ViewType.SAME_WEEK);
            }
            viewtypeList.add(ViewType.SUMMARY_LINE);
        }
    }

    @NonNull
    private Calendar parseDate(int position) {
        CashAdapterItemViewModel cashAdapterItemViewModel = list.get(position);
        String dateString = cashAdapterItemViewModel.getCashDate().get();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        Date actualDate = null;

        try {
            actualDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actualDate);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar;
    }

    @Override
    public int getItemCount() {
        return viewtypeList.size();
    }

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
