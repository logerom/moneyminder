package de.logerbyte.moneyminder.entities.data.database;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Set;
import de.logerbyte.moneyminder.domain.database.ExpenseEntity;
import io.reactivex.Observable;

/**
 * Created by logerom on 09.08.18.
 */

public interface ExpenseAPI {
    LiveData<List<ExpenseEntity>> getAllExpense();

    Observable<Boolean> insertCashItemIntoDB(ExpenseEntity expenseEntity);

    Observable<Boolean> deleteCashItem(long id);

    Observable<Boolean> deleteCashItem(ExpenseEntity expenseEntity);

    Observable<Boolean> updateCashItem(ExpenseEntity expenseEntity);

    Observable<List<String>> getCategories();

    Observable<List<ExpenseEntity>> expensesWithCategories(@NotNull Set<String> checkedCategories);
}
