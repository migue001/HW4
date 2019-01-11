package org.tensorflow.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class Menu_Cursos extends AppCompatActivity {
    public static ImageButton imageButton, imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7, filal;
    public static String ventana="";
    ProgressBar progressBar, progressBar2, progressBar3,progressBar4,progressBar5,progressBar6,progressBar7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__cursos);
       imageButton=findViewById(R.id.imageButton4);
        imageButton2=findViewById(R.id.imageButton9);
        imageButton3=findViewById(R.id.imageButton10);
        imageButton4=findViewById(R.id.imageButton11);
        imageButton5=findViewById(R.id.imageButton12);
        imageButton6=findViewById(R.id.imageButton13);
        imageButton7=findViewById(R.id.imageButton14);
        progressBar=findViewById(R.id.progressBar1);
        progressBar2=findViewById(R.id.progressBar2);
        progressBar3=findViewById(R.id.progressBar3);
        progressBar4=findViewById(R.id.progressBar4);
        progressBar5=findViewById(R.id.progressBar5);
        progressBar6=findViewById(R.id.progressBar6);
        progressBar7=findViewById(R.id.progressBar7);
        filal=findViewById(R.id.imageButton3);
        mostrar();
       imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventana="1";
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
                ventana="2";
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
                ventana="3";
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
                ventana="4";
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
                ventana="5";
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
                ventana="6";
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Menu_Cursos.this,Ventana_cursos_ejer.class);
                startActivity(inet);
                ventana="7";
            }
        });
filal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Menu_Cursos.this,Inicio_auditivo.class);
        startActivity(intent);
    }
});
    }

    private void mostrar() {
        //int progreso1;
        Puntaje conex=new Puntaje(this,"DBPuntaje",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT PuntuacionMax FROM Puntaje",null);
        while(cursor.moveToNext()){
            //progreso1=cursor.getInt(0);
            progressBar.setProgress(cursor.getInt(0));
        }
    }
}
