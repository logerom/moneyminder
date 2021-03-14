package de.logerbyte.moneyminder.domain.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.presentation.dialog.dialogDelete.DeleteDialogFragment
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogFragment
import de.logerbyte.moneyminder.cashOverview.menu.filter.FilterDialog
import de.logerbyte.moneyminder.cashOverview.menu.filter.FilterDialogModule
import de.logerbyte.moneyminder.domain.di.module.feature.AddCashDialogModule

@Module(includes = [AddCashDialogModule::class])
abstract class FragmentsInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributesDeleteDialogFragment(): DeleteDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesEditDialogFragment(): EditDialogFragment

    @ContributesAndroidInjector(modules = [FilterDialogModule::class])
    abstract fun contributeFilterDialog(): FilterDialog
}
