package de.logerbyte.moneyminder.menu

import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.base.EditTextVM
import javax.inject.Inject


class MenuVm @Inject constructor() : EditTextVM {
    override var text = ObservableField<String>()
}