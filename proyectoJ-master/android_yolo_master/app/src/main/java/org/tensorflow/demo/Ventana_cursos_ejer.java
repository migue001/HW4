package org.tensorflow.demo;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.demo.Clases.VariablesYDatos;

import pl.droidsonroids.gif.GifImageView;

public class Ventana_cursos_ejer extends AppCompatActivity {
    MediaPlayer mp1, mp2;
    GifImageView IMGgif;
    CardView btnOpcion1,btnOpcion2,btnOpcion3,btnOpcion4;
    TextView txtOpcion1,txtOpcion2,txtOpcion3,txtOpcion4,txtPregunta,txtTema, textepicdialog;
   public static int bloquecito,fila,total=0,aciertos=0;
    boolean SoloUno;
    Dialog epicdialog, epicdilog2;
    Button aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_cursos_ejer);
        mp1=MediaPlayer.create(this,R.raw.correctosonido);
        mp2=MediaPlayer.create(this,R.raw.incorrectosonido);
        epicdialog=new Dialog(this);
        epicdilog2=new Dialog(this);
        IMGgif=findViewById(R.id.IMGgif2_2);
        txtTema=findViewById(R.id.txtTema2_2);
        txtPregunta=findViewById(R.id.txtPregunta2_2);
        txtOpcion1=findViewById(R.id.txtOpcion1_2);
        txtOpcion2=findViewById(R.id.txtOpcion2_2);
        txtOpcion3=findViewById(R.id.txtOpcion3_2);
        txtOpcion4=findViewById(R.id.txtOpcion4_2);
        SoloUno=getIntent().getBooleanExtra("SoloUno",false);


        fila=getIntent().getIntExtra("fila",0);
        total=getIntent().getIntExtra("total",0);
        //aciertos=getIntent().getIntExtra("aciertos",0);
        btnOpcion1=findViewById(R.id.btnOpcion1_2);
        btnOpcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(txtOpcion1.getText().toString());
            }
        });
        btnOpcion2=findViewById(R.id.btnOpcion2_2);
        btnOpcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(txtOpcion2.getText().toString());
            }
        });
        btnOpcion3=findViewById(R.id.btnOpcion3_2);
        btnOpcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(txtOpcion3.getText().toString());
            }
        });
        btnOpcion4=findViewById(R.id.btnOpcion4_2);
        btnOpcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(txtOpcion4.getText().toString());
            }
        });
        Settear();
    }
    private void Settear() {
        if (Menu_Cursos.ventana.equalsIgnoreCase("1")){
            bloquecito=getIntent().getIntExtra("bloquecito",0);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("2")){
            bloquecito=getIntent().getIntExtra("bloquecito",1);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("3")){
            bloquecito=getIntent().getIntExtra("bloquecito",2);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("4")){
            bloquecito=getIntent().getIntExtra("bloquecito",3);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("5")){
            bloquecito=getIntent().getIntExtra("bloquecito",4);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("6")){
            bloquecito=getIntent().getIntExtra("bloquecito",5);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("7")){
            bloquecito=getIntent().getIntExtra("bloquecito",6);
            txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
            txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
            txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
            txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
            txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());
            txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);
            IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        }
    }
    void ventana_nueva(){

        Intent intent=new Intent(this,Ventana_Cursos_Ejer2.class);
        startActivity(intent);
        /*switch (Menu_Cursos.ventana){
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
        Intent intent=new Intent(this,Resultados_Duolingo.class);
        startActivity(intent);
        //aciertos=0;
        // finish();*/
    }
    private void verificar(String respuesta) {
        String title="",msg="Sigue Asi";
        if (respuesta.equalsIgnoreCase(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][5].toString())) {
            showepicdialogcorrect();
            mp1.start();
            //Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
           aciertos += 1;

        } else {
            showepicdialogIncorrect();
            mp2.start();
            //Toast.makeText(this, "Incorrecto!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showepicdialogIncorrect() {
        epicdilog2.setContentView(R.layout.popup_incorrecto);
        aceptar=epicdilog2.findViewById(R.id.siguiente_incorrecto);
        textepicdialog=epicdilog2.findViewById(R.id.respuesta);
        textepicdialog.setText("La Respuestas es\n"+VariablesYDatos.OpcionesDuolingo[bloquecito][fila][5].toString());
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hilo();
                epicdilog2.dismiss();
            }
        });
        epicdilog2.show();
    }

    private void showepicdialogcorrect() {
        epicdialog.setContentView(R.layout.popup_correcto);
        aceptar=epicdialog.findViewById(R.id.correcto);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hilo();
                epicdilog2.dismiss();

            }
        });
        epicdialog.show();
    }

    private void hilo(){
        total+=1;
        Intent in=new Intent(this,Ventana_cursos_ejer.class);
        if (fila==4) {

            ventana_nueva();
            //fila=0;
            //finish();

            //Toast.makeText(this, "Has acabado", Toast.LENGTH_SHORT).show();
            //ventana_nueva();
            return;
        } else {
            fila += 1;
        }



        in.putExtra("bloquesito",bloquecito);
        in.putExtra("fila",fila);
        in.putExtra("total",total);
        in.putExtra("aciertos",aciertos);
        in.putExtra("SoloUno",SoloUno);
        finish();
        startActivity(in);
    }
}
