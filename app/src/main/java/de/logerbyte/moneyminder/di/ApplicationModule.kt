package de.logerbyte.moneyminder.di

import dagger.Module

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {}

@Module
abstract class ApplicationModuleBinds {}
