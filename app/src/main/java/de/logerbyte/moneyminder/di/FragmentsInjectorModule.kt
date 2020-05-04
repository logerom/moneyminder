package de.logerbyte.moneyminder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment

@Module
abstract class FragmentsInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributesAddCashDialogFragment(): AddCashDialogFragment


}
