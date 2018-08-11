package de.logerbyte.moneyminder.db.expense;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by logerom on 08.08.18.
 */

@Entity(tableName = "Expense")
public class Expense {

    @PrimaryKey
    public Long id;
    public String cashName;
    public String cashDate;
    public double cashInEuro;
}
