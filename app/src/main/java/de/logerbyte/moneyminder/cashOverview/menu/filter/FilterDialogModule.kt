package de.logerbyte.moneyminder.cashOverview.menu.filter

import dagger.Module

@Module
class FilterDialogModule {
    abstract class FilterBindsModule {
        abstract fun bindsViewModel(filterDialogVM: FilterDialogVM): FilterDialogVMListener
    }
}