package de.logerbyte.moneyminder.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment

@Module(includes = [AddCashDialogModule.AddCashDialogBinds::class])
class AddCashDialogModule {
//    @Provides
//    fun provideAddCashDialogViewModel(
//            factory: ViewModelProvider.Factory,
//            injector: AddCashDialogFragment
//    ) = ViewModelProviders.of(injector, factory).get(CashViewModel::class.java)
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(CashViewModel::class)
//    fun provideCashSummaryViewModel(@Named("Date") date: String): ViewModel = CashViewModel(date)
//
    // TODO-SW: sw factory for cashviewmodel

    @Module
    abstract class AddCashDialogBinds {
        //    // TODO-SW: Scoping dagger android subcomponents
        @ContributesAndroidInjector()
        abstract fun addCashDialogFragmet(): AddCashDialogFragment
    }
}
