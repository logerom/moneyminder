package de.logerbyte.moneyminder.domain.di.module.feature

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.presentation.dialog.dialogAddCash.AddCashDialogFragment
import de.logerbyte.moneyminder.domain.di.FragmentScope
import de.logerbyte.moneyminder.presentation.dialog.dialogAddCash.AddCashViewModel
import javax.inject.Named

@Module
abstract class AddCashDialogModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun addCashDialogFragmet(): AddCashDialogFragment

    @Module
    class InjectViewModel {
        @Provides
        @FragmentScope
        @Named("ANDROID_VIEWMODEL")
        fun provideAddCashDialogViewModel(
                factory: ViewModelProvider.Factory,
                injector: AddCashDialogFragment
        ) = ViewModelProviders.of(injector, factory).get(AddCashViewModel::class.java)
    }
}
