package de.logerbyte.moneyminder

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.logerbyte.moneyminder.data.db.ExpenseDatabase
import de.logerbyte.moneyminder.data.db.expense.Expense
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseRepoTest {

    private lateinit var database: ExpenseDatabase
    private lateinit var expenseRepo: ExpenseRepo
    private val testObserver = TestObserver<MutableList<Expense>>()

    private val expenseFake = Expense(null, "burger", "essen", "11.11.11", 2.4)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.databaseBuilder(
                context,
                ExpenseDatabase::class.java, DB_NAME
        ).build()

        expenseRepo = ExpenseRepo(database)
        initDB()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }


    @Test
    fun tesgetCategories() {
        val categories = setOf(expenseFake.category)

        expenseRepo.expensesWithCategories(categories)
                ?.subscribe(testObserver)
        testObserver.assertValue { t -> if (t[0].category == expenseFake.category) return@assertValue true else false }
    }

    @Test
    fun testgetAllEpenses() {
        val testObserver = TestObserver<List<Expense>>()

        expenseRepo.allExpense.subscribe(testObserver)
        testObserver.assertValueCount(1)
        testObserver.assertValue { t -> if (t[0].cashName == expenseFake.cashName) return@assertValue true else false }
    }

    @Test
    @Throws(IOException::class)
    fun initDB() {
        val testObserverBoolean = TestObserver<Boolean>()

        expenseRepo.insertCashItemIntoDB(expenseFake).subscribe(testObserverBoolean)
        testObserverBoolean.assertResult(true)
    }

    private fun insertFakeExpenses(count: Int) {

    }

}
