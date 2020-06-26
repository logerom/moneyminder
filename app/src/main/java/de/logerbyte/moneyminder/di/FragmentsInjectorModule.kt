package de.logerbyte.moneyminder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.deleteDialog.DeleteDialogFragment
import de.logerbyte.moneyminder.di.fragment.AddCashDialogModule
import de.logerbyte.moneyminder.editDialog.EditDialogFragment

@Module(includes = [AddCashDialogModule::class])
abstract class FragmentsInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributesDeleteDialogFragment(): DeleteDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesEditDialogFragment(): EditDialogFragment

}
