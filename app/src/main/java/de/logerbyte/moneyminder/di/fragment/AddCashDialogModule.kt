package de.logerbyte.moneyminder.di.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment
import de.logerbyte.moneyminder.viewModels.CashViewModel

@Module
abstract class AddCashDialogModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun addCashDialogFragmet(): AddCashDialogFragment

    @Module
    class InjectViewModel {
        @Provides
        fun provideAddCashDialogViewModel(
                factory: ViewModelProvider.Factory,
                injector: AddCashDialogFragment
        ) = ViewModelProviders.of(injector, factory).get(CashViewModel::class.java)
    }
}
