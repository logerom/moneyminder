package de.logerbyte.moneyminder.domain.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.logerbyte.moneyminder.App
import de.logerbyte.moneyminder.domain.di.module.FragmentsInjectorModule
import de.logerbyte.moneyminder.domain.di.module.feature.ApplicationModule
import de.logerbyte.moneyminder.domain.di.module.feature.CashSummaryActivityModule
import de.logerbyte.moneyminder.domain.di.module.ViewModelMapModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ViewModelMapModule::class,
            CashSummaryActivityModule::class,
            ApplicationModule::class,
            AndroidSupportInjectionModule::class,
            FragmentsInjectorModule::class
        ])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}