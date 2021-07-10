package de.logerbyte.moneyminder.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by logerom on 08.08.18.
 */
@Entity(tableName = "Expense")
data class ExpenseEntity(// TODO: 13.03.21 Person as Int
    @field:PrimaryKey var id: Long?,
    var cashName: String,
    var category: String,
    var cashDate: String,
    var cashInEuro: Double,
    var person: Int
)
