package org.tensorflow.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jj on 19/08/18.
 */

public class Puntaje extends SQLiteOpenHelper {
    String tabla="CREATE TABLE Puntaje (PuntuacionMax int, PuntuacionMax2 int, PuntuacionMax3 int, PuntuacionMax4 int, PuntuacionMax5 int, PuntuacionMax6 int, PuntuacionMax7 int)";
    public Puntaje(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Puntaje");
        db.execSQL(tabla);
    }
}