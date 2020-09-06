package de.logerbyte.moneyminder.menu.filter

import androidx.databinding.ObservableField

class FilterItemVM {
    val isChecked = ObservableField<Boolean>()
    val name = ObservableField<String>()
}