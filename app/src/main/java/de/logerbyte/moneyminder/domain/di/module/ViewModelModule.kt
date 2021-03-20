package de.logerbyte.moneyminder.domain.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.logerbyte.moneyminder.presentation.activityCashSummary.CashSummaryViewModel
import de.logerbyte.moneyminder.presentation.dialog.dialogFilter.FilterDialogViewModel
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogViewModel
import de.logerbyte.moneyminder.domain.di.ViewModelKey
import de.logerbyte.moneyminder.presentation.dialog.dialogAddCash.AddCashViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CashSummaryViewModel::class)
    abstract fun intoMapCashSummaryViewModel(cashSummaryViewModel: CashSummaryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditDialogViewModel::class)
    abstract fun intoMapCashViewModel(editDialogViewModel: EditDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterDialogViewModel::class)
    abstract fun intoMapFilterDialogVM(filterDialogViewModel: FilterDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCashViewModel::class)
    abstract fun intoMapAddCashViewModel(addCashViewModel: AddCashViewModel): ViewModel
}