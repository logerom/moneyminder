package de.logerbyte.moneyminder.data.db.expense;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by logerom on 08.08.18.
 */

@Entity(tableName = "Expense")
public class Expense {

    @PrimaryKey
    public Long id;
    public String cashName;
    public String category;
    public String cashDate;
    public double cashInEuro;

    public Expense(Long id, String cashName, String category, String cashDate, double cashInEuro) {
        this.id = id;
        this.cashName = cashName;
        this.cashDate = cashDate;
        this.cashInEuro = cashInEuro;
        this.category = category;
    }
}
