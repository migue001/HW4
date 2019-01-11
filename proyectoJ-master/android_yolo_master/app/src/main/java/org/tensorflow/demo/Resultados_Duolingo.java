package org.tensorflow.demo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.tensorflow.demo.SQLite.Puntuacion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Resultados_Duolingo extends AppCompatActivity {

    public static int total,aciertos;
    ImageView img;
    Button btnRegresar,btnIntentarlo;
    TextView txtRes,txtRestxt;
    boolean update=false;
    String PPa[],PAc[];

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados__duolingo);
        img=findViewById(R.id.IMGResultado);
        txtRes=findViewById(R.id.txtResultado);
        txtRestxt=findViewById(R.id.txtResultadotxt);
        btnRegresar=findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
                Ventana_cursos_ejer.aciertos=0;
                Ventana_Cursos_Ejer2.aciertos2=0;
                finish();
                startActivity(new Intent(getApplicationContext(),Menu_Cursos.class));
            }
        });
        setAciertos();
        total=getIntent().getIntExtra("total",9);
        Settear();
    }

    private void insertar() {
        switch (Menu_Cursos.ventana){
            case "1":
                Puntaje puntuacion=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase=puntuacion.getWritableDatabase();
                String guardar="INSERT INTO Puntaje(PuntuacionMax)VALUES('"+aciertos+"')";
                sqLiteDatabase.execSQL(guardar);
                Toast.makeText(this, "Se ha Registreado en 1", Toast.LENGTH_SHORT).show();
                break;
            case "2":
                Puntaje puntuacion2=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase2=puntuacion2.getWritableDatabase();
                String guardar2="INSERT INTO Puntaje(PuntuacionMax2)VALUES('"+aciertos+"')";
                sqLiteDatabase2.execSQL(guardar2);
                Toast.makeText(this, "Se ha Registreado en 2", Toast.LENGTH_SHORT).show();
                break;
            case "3":
                Puntaje puntuacion3=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase3=puntuacion3.getWritableDatabase();
                String guardar3="INSERT INTO Puntaje(PuntuacionMax3)VALUES('"+aciertos+"')";
                sqLiteDatabase3.execSQL(guardar3);
                Toast.makeText(this, "Se ha Registreado en 3", Toast.LENGTH_SHORT).show();
                break;
            case "4":
                Puntaje puntuacion4=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase4=puntuacion4.getWritableDatabase();
                String guardar4="INSERT INTO Puntaje(PuntuacionMax4)VALUES('"+aciertos+"')";
                sqLiteDatabase4.execSQL(guardar4);
                Toast.makeText(this, "Se ha Registreado en 4", Toast.LENGTH_SHORT).show();
                break;
            case "5":
                Puntaje puntuacion5=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase5=puntuacion5.getWritableDatabase();
                String guardar5="INSERT INTO Puntaje(PuntuacionMax5)VALUES('"+aciertos+"')";
                sqLiteDatabase5.execSQL(guardar5);
                Toast.makeText(this, "Se ha Registreado en 5", Toast.LENGTH_SHORT).show();
                break;
            case "6":
                Puntaje puntuacion6=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase6=puntuacion6.getWritableDatabase();
                String guardar6="INSERT INTO Puntaje(PuntuacionMax6)VALUES('"+aciertos+"')";
                sqLiteDatabase6.execSQL(guardar6);
                Toast.makeText(this, "Se ha Registreado en 6", Toast.LENGTH_SHORT).show();
                break;
            case "7":
                Puntaje puntuacion7=new Puntaje(this,"Puntaje",null,2);
                SQLiteDatabase sqLiteDatabase7=puntuacion7.getWritableDatabase();
                String guardar7="INSERT INTO Puntaje(PuntuacionMax7)VALUES('"+aciertos+"')";
                sqLiteDatabase7.execSQL(guardar7);
                Toast.makeText(this, "Se ha Registreado en 7", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    Cursor cursor;
    private void setAciertos(){
        aciertos=Ventana_Cursos_Ejer2.aciertos2;
    }
    private void Settear(){
        /*double rs=aciertos/total;
        rs=rs*100;
        txtRes.setText(rs+"%");
        if(rs==0.0){
            txtRes.setText(aciertos+"/"+total);
        }*/
        txtRes.setText(aciertos+"/"+total);
        int imagen=0;
        String txt="";
        if(total==aciertos) {
            imagen = (R.drawable.resultadotodas);
            txt="Perfecto, has acertado el 100% de las respuestas.\nAprendes muy Rapido";
        }else if(aciertos>(total/2)){
            imagen=(R.drawable.resultadocasitodas);
            txt="Casi, has acertado la mayoria de las respuestas.\nPuedes hacerlo mejor.";
        }
        else if(aciertos==(total/2)){
            imagen=(R.drawable.resultadomitad);
            txt="Puedes mejorar, has acertado la mitad de las respuestas.\nEstudia mas.";
        }else if(aciertos==0){
            imagen=(R.drawable.resultadocasininguna);
            txt = "¡Muy mal!, no has acertado ninguna de las respuestas.\n¡A repasar todo!";
        }else{
            imagen = (R.drawable.resultadocasininguna);
            txt = "¡Estudia!, has acertado menos de la mitad de las respuestas.\nNecesitas repasar más la lección.";
        }
        img.setImageResource(imagen);
        txtRestxt.setText(""+txt);
        //txtRestxt.append(txt);
    }
}