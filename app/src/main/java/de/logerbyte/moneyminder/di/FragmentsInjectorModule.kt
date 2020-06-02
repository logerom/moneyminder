package de.logerbyte.moneyminder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment
import de.logerbyte.moneyminder.deleteDialog.DeleteDialogFragment

@Module
abstract class FragmentsInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributesAddCashDialogFragment(): AddCashDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesDeleteDialogFragment(): DeleteDialogFragment

}
