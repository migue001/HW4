package org.tensorflow.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by azulm on 08/06/2018.
 */

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Recordatorios(nombre varchar(80) primary key  ,hora varchar(20),fecha varchar(30))"
        );
        db.execSQL(
                "create table Alarmas(hora varchar(20),fecha varchar(30), repetir varchar(30),nombre varchar(80) primary key)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
