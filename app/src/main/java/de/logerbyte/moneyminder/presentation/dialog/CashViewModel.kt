package de.logerbyte.moneyminder.presentation.dialog

import android.text.Editable
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.data.viewItem.CashViewItem

open class CashViewModel: ViewModel() {
    var cashViewItem = CashViewItem()
}
