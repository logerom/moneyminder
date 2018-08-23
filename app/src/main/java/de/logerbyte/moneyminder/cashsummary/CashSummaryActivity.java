package de.logerbyte.moneyminder.cashsummary;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.ActivityMainBinding;

public class CashSummaryActivity extends FragmentActivity {

    private CashSummaryViewModel cashSummaryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
        bindView();
    }

    private void bindViewModel() {
        cashSummaryViewModel = ViewModelProviders.of(this).get(CashSummaryViewModel.class);
    }

    private void bindView() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(cashSummaryViewModel);

        binding.etDate.addTextChangedListener(new TextWatcher() {

            EditText editText = binding.etDate;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newString = s.toString();
                int stringLength = newString.length();

                if (stringLength == 2 || stringLength == 5) {
                    newString = newString.concat(".");
                    editText.setText(newString);
                }

                editText.setSelection(newString.length());
            }
        });
    }
}
