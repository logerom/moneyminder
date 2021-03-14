package de.logerbyte.moneyminder.domain.database.expense;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by logerom on 08.08.18.
 */

@Entity(tableName = "Expense")
public class Expense {

    // TODO: 13.03.21 Person as Int
    @PrimaryKey
    public Long id;
    public String cashName;
    public String category;
    public String cashDate;
    public double cashInEuro;
    public String person;

    public Expense(Long id, String cashName, String category, String cashDate, double cashInEuro, String person) {
        this.id = id;
        this.cashName = cashName;
        this.cashDate = cashDate;
        this.cashInEuro = cashInEuro;
        this.category = category;
        this.person = person;
    }
}
