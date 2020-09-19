package de.logerbyte.moneyminder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.deleteDialog.DeleteDialogFragment
import de.logerbyte.moneyminder.di.fragment.AddCashDialogModule
import de.logerbyte.moneyminder.editDialog.EditDialogFragment
import de.logerbyte.moneyminder.menu.filter.FilterDialog
import de.logerbyte.moneyminder.menu.filter.FilterDialogModule

@Module(includes = [AddCashDialogModule::class])
abstract class FragmentsInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributesDeleteDialogFragment(): DeleteDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesEditDialogFragment(): EditDialogFragment

    @ContributesAndroidInjector(modules = [FilterDialogModule::class])
    abstract fun contributeFilterDialog(): FilterDialog
}
