package com.example.a04_persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarSQLiteHelper extends SQLiteOpenHelper {

    private String sqlCrear = "CREATE TABLE car (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " nombre TEXT, color TEXT)";

    public CarSQLiteHelper(Context context, String nombreBD,
                           SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombreBD, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea una nueva versión de la tabla
        db.execSQL(sqlCrear);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS car");

        //Crea una nueva versión de la tabla
        db.execSQL(sqlCrear);
    }
}
