package de.logerbyte.moneyminder.menu.filter

import dagger.Module

@Module
class FilterDialogModule {
    abstract class FilterBindsModule {
        abstract fun bindsViewModel(filterDialogVM: FilterDialogVM): FilterDialogVMListener
    }
}