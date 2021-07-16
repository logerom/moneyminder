package de.logerbyte.moneyminder.presentation.dialog

import android.text.Editable

interface CashViewAction {
    fun onDateTextChanged(s: Editable)
}