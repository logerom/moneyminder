package de.logerbyte.moneyminder.cashsummary;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.ActivityMainBinding;

public class CashSummaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new CashSummaryViewModel());
    }
}
