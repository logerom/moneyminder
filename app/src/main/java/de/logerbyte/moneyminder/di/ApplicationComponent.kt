package de.logerbyte.moneyminder.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.logerbyte.moneyminder.App
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