package de.logerbyte.moneyminder.presentation

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import de.logerbyte.moneyminder.framework.di.DaggerApplicationComponent

open class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}