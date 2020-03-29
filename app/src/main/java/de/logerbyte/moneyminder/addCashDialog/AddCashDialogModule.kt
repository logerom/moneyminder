package de.logerbyte.moneyminder.addCashDialog

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddCashDialogModule {
    @ContributesAndroidInjector
    abstract fun contributesAddCashDialogFragment(): AddCashDialogFragment
}
