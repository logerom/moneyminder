package de.logerbyte.moneyminder.db.expense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by logerom on 08.08.18.
 */

@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM Expense")
    List<Expense> selectAll();

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insert(Expense expense);

    @Query("DELETE FROM Expense where id = :entryId")
    void delete(long entryId);
}
