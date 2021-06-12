package de.logerbyte.moneyminder.framework.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by logerom on 08.08.18.
 */

@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM Expense")
    LiveData<List<ExpenseEntity>> selectAll();


    // TODO-SW: select all where cashDate is in range
    @Query("SELECT * FROM Expense where cashDate")
    List<ExpenseEntity> selectAllBetween();

    @Query("SELECT DISTINCT category FROM Expense")
    List<String> selectDistinctCategory();

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insert(ExpenseEntity expenseEntity);

    @Query("DELETE FROM Expense where id = :entryId")
    void delete(long entryId);

    @Update
    void update(ExpenseEntity expenseEntity);

    @Delete
    void delete(ExpenseEntity expenseEntity);

    @Query("SELECT * FROM Expense WHERE category IN (:checkedCategories)")
    List<ExpenseEntity> expensesWithCategories(String[] checkedCategories);
}
