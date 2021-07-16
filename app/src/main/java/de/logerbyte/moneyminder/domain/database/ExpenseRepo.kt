package de.logerbyte.moneyminder.domain.database

import androidx.lifecycle.LiveData
import de.logerbyte.moneyminder.entities.data.database.ExpenseAPI
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by logerom on 08.08.18.
 */
@Singleton
class ExpenseRepo @Inject constructor(private val mDatabase: Database) : ExpenseAPI {

    override fun getAllExpense(): LiveData<List<ExpenseEntity>> {
        return mDatabase.expenseDao().selectAll()
    }

    override fun insertCashItemIntoDB(expenseEntity: ExpenseEntity): Observable<Boolean> {
        return Observable.fromCallable {
            mDatabase.expenseDao().insert(expenseEntity)
            true
        }
    }

    override fun deleteCashItem(id: Long): Observable<Boolean> {
        return Observable.fromCallable {
            mDatabase.expenseDao().delete(id)
            true
        }
    }

    override fun deleteCashItem(expenseEntity: ExpenseEntity): Observable<Boolean> {
        return Observable.fromCallable { mDatabase.expenseDao().delete(expenseEntity); true}
    }

    override fun updateCashItem(expenseEntity: ExpenseEntity): Observable<Boolean> {
        return Observable.fromCallable {
            mDatabase.expenseDao().update(expenseEntity)
            true
        }
    }

    override fun getCategories(): Observable<List<String>> {
        return Observable.fromCallable { mDatabase.expenseDao().selectDistinctCategory() }
    }

    override fun expensesWithCategories(checkedCategories: Set<String>): Observable<MutableList<ExpenseEntity>>? {
        return Observable.fromCallable { mDatabase.expenseDao().expensesWithCategories(checkedCategories.toTypedArray()) }
    }
}
