package de.logerbyte.moneyminder.entities

import de.logerbyte.moneyminder.BuildConfig

object FeatureFlag {
    val CASH_ITEM_FILLED = BuildConfig.DEBUG
    val BUDGET = BuildConfig.DEBUG
    val EDIT_CASH = BuildConfig.DEBUG
}
