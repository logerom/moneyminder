package de.logerbyte.moneyminder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.cashOverview.deleteDialog.DeleteDialogFragment
import de.logerbyte.moneyminder.cashOverview.editDialog.EditDialogFragment
import de.logerbyte.moneyminder.cashOverview.menu.filter.FilterDialog
import de.logerbyte.moneyminder.cashOverview.menu.filter.FilterDialogModule
import de.logerbyte.moneyminder.di.fragment.AddCashDialogModule

@Module(includes = [AddCashDialogModule::class])
abstract class FragmentsInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributesDeleteDialogFragment(): DeleteDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesEditDialogFragment(): EditDialogFragment

    @ContributesAndroidInjector(modules = [FilterDialogModule::class])
    abstract fun contributeFilterDialog(): FilterDialog
}
