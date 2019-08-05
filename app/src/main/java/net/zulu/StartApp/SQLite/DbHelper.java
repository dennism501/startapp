package net.zulu.StartApp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "budget_db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Budget.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Budget.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }

    public long insertBudget(int amount) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Budget.COLUMN_AMOUNT, amount);
        long id = db.insert(Budget.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public List<Budget> getAmount() {

        List<Budget> amount = new ArrayList<>();


        String query =
                "SELECT * FROM " + Budget.TABLE_NAME +
                        " ORDER BY " + Budget.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Budget budget = new Budget();
                budget.setId(cursor.getInt(cursor.getColumnIndex(Budget.COLUMN_ID)));
               budget.setAmount(cursor.getInt(cursor.getColumnIndex(Budget.COLUMN_AMOUNT)));

               amount.add(budget);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return amount;

    }

    public int updateBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Budget.COLUMN_AMOUNT, budget.getAmount());

        // updating row
        return db.update(Budget.TABLE_NAME, values, Budget.COLUMN_ID + " = ?",
                new String[]{String.valueOf(budget.getId())});
    }



}
