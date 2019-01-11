package org.tensorflow.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
public class Carga_Inicio extends AppCompatActivity {
    public final int duracion = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga__inicio);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String dele="DELETE FROM Usuario";
              //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                //setContentView(R.layout.activity_main);

                Datos_Usuario conex = new Datos_Usuario(getApplicationContext(), "DBUsuario", null, 2);
                SQLiteDatabase db = conex.getReadableDatabase();

                 db.execSQL(dele);
                final Cursor cursor = db.rawQuery("SELECT Discapacidad FROM Usuario", null);
                boolean sino=true;
                while (cursor.moveToNext()) {

                    if (cursor.getString(0).equals("visual")) {
                        Intent inte = new Intent(Carga_Inicio.this, Inicio_Visual.class);
                        startActivity(inte);
                        finish();
                    }
                    if (cursor.getString(0).equals("auditivo")) {
                        Intent inte = new Intent(Carga_Inicio.this, Inicio_auditivo.class);
                        startActivity(inte);
                        finish();
                    }
                    if (cursor.getString(0).equals("comunicativo")) {
                        Intent inte = new Intent(Carga_Inicio.this, Inicio_Comunicativo.class);
                        startActivity(inte);
                        finish();
                    }
                    sino=false;
                }

if(sino) {
    Intent inte = new Intent(Carga_Inicio.this, Opciones.class);
    startActivity(inte);
    finish();
}
            }


        }, duracion);
    }

}