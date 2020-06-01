package de.logerbyte.moneyminder.di

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun getViewModelFactory(map: Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>) = ViewModelFactory(map)
}
