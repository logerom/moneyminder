package de.logerbyte.moneyminder.domain.di.module.feature

import dagger.Module
import de.logerbyte.moneyminder.presentation.dialog.dialogFilter.FilterDialogViewModel
import de.logerbyte.moneyminder.presentation.dialog.dialogFilter.FilterDialogVMListener

@Module
class FilterDialogModule {
    abstract class FilterBindsModule {
        abstract fun bindsViewModel(filterDialogViewModel: FilterDialogViewModel): FilterDialogVMListener
    }
}