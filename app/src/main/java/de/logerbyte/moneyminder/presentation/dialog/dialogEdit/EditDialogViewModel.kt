package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.NAMED_DATE
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import javax.inject.Inject
import javax.inject.Named

class EditDialogViewModel @Inject constructor(@Named(NAMED_DATE) date: String) : ViewModel() {

    var cashViewItem: DayExpenseViewItem = DayExpenseViewItem(date,"","","","")

}

