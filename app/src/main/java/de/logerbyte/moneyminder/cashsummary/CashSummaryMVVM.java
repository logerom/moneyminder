package de.logerbyte.moneyminder.cashsummary;

/**
 * Created by logerom on 15.08.18.
 */

public interface CashSummaryMVVM {
    public interface Listener {
        public void onCashItemDeleteClicked(Long cashItemId);
    }
}
