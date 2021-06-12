package de.logerbyte.moneyminder

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.logerbyte.moneyminder.framework.database.DatabaseMigration.MIGRATION_2_3
import de.logerbyte.moneyminder.framework.database.Database
import de.logerbyte.moneyminder.framework.database.ExpenseEntity
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseEntityRepoTest {

    private lateinit var database: Database
    private lateinit var expenseRepo: ExpenseRepo
    private val testObserver = TestObserver<MutableList<ExpenseEntity>>()

    private val expenseFake = ExpenseEntity(null, "burger", "essen", "11.11.11", 2.4, "0")

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.databaseBuilder(context,
                                        Database::class.java, DB_NAME)
            .addMigrations(MIGRATION_2_3)
            .build()

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
        val testObserver = TestObserver<List<ExpenseEntity>>()

        expenseRepo.allExpense.observe(testObserver)
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
