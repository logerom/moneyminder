package de.logerbyte.moneyminder.dialogs.editDialog

import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.viewModels.CashViewModel

class EditDialogViewModel1(baseDialog1ViewListener: BaseDialog1ViewListener) : BaseDialogViewModel1
(baseDialog1ViewListener) {
    lateinit var cashViewmodel: CashViewModel
}
