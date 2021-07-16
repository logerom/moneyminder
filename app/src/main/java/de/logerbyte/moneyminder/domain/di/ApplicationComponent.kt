package de.logerbyte.moneyminder.domain.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.logerbyte.moneyminder.presentation.App
import de.logerbyte.moneyminder.domain.di.module.FragmentModule
import de.logerbyte.moneyminder.domain.di.module.ApplicationModule
import de.logerbyte.moneyminder.domain.di.module.feature.CashSummaryActivityModule
import de.logerbyte.moneyminder.domain.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ViewModelModule::class,
            CashSummaryActivityModule::class,
            ApplicationModule::class,
            AndroidSupportInjectionModule::class,
            FragmentModule::class
        ])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
