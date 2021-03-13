package de.logerbyte.moneyminder.cashOverview.viewModels

import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.NAMED_DATE
import de.logerbyte.moneyminder.cashOverview.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.cashOverview.viewItems.CashViewItem
import javax.inject.Inject
import javax.inject.Named

class CashViewModel @Inject constructor(@Named(NAMED_DATE) date: String) : ViewModel() {

    var cashViewItem: CashViewItem = CashViewItem().apply { cashDate.set(date)}

}

