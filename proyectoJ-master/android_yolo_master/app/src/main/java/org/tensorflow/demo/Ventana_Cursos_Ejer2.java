package org.tensorflow.demo;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.demo.Clases.VariablesYDatos;
import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageView;

public class Ventana_Cursos_Ejer2 extends AppCompatActivity {
    MediaPlayer mp1,mp2;
GifImageView imagen1, imagen2,imagen3,imagen4;
TextView textView, textView2,textView3,numero1,numero2,numero3,numero4, respuestapopup;
CardView cardView, cardView2,cardView3,cardView4;
public static int filas=0, bloquecito=0, aciertos2=0, total=0;
Dialog epicdialogCorrect, epicDialogincorrect;
Button boton;
boolean SoloUno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana__cursos__ejer2);
        mp1=MediaPlayer.create(this,R.raw.correctosonido);
        mp2=MediaPlayer.create(this,R.raw.incorrectosonido);
        epicdialogCorrect=new Dialog(this);
        epicDialogincorrect=new Dialog(this);
        imagen1=findViewById(R.id.imagengif1);
        imagen2=findViewById(R.id.imagengif2);
        imagen3=findViewById(R.id.imagengif3);
        imagen4=findViewById(R.id.imagengif4);
        textView=findViewById(R.id.texto1);
        textView2=findViewById(R.id.texto2);
        textView3=findViewById(R.id.tema);
        numero1=findViewById(R.id.numero1);
        numero2=findViewById(R.id.numero2);
        numero3=findViewById(R.id.numero3);
        numero4=findViewById(R.id.numero4);
        cardView=findViewById(R.id.card1);
        cardView2=findViewById(R.id.card2);
        cardView3=findViewById(R.id.card3);
        cardView4=findViewById(R.id.card4);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                verificar(numero1.getText().toString());
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(numero2.getText().toString());
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(numero3.getText().toString());
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(numero4.getText().toString());
            }
        });
        Cargar();
    }

    private void verificar(String s) {
        if(s.equalsIgnoreCase(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][10].toString())){
        showepicdialogCorrrect();
        aciertos2+=1;
        mp1.start();
        }
        else{
            mp2.start();
            showepicdialogIncorrrect();
        }
    }

    private void showepicdialogIncorrrect() {
        epicDialogincorrect.setContentView(R.layout.popup_incorrecto);
        boton=epicDialogincorrect.findViewById(R.id.siguiente_incorrecto);
        respuestapopup=epicDialogincorrect.findViewById(R.id.respuesta);
        respuestapopup.setText("La Respuesta es:\n"+VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][10].toString());
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hilo2();
                epicDialogincorrect.dismiss();
            }
        });
        epicDialogincorrect.show();
    }

    private void hilo2() {
        total+=1;
        Intent intent=new Intent(this,Ventana_Cursos_Ejer2.class);
        if(filas==3){
            nuevaActividad();
            return;
        }
        else{
            filas+=1;
        }
        intent.putExtra("bloquesito",bloquecito);
        intent.putExtra("fila",filas);
        intent.putExtra("total",total);
        intent.putExtra("aciertos",aciertos2);
        intent.putExtra("SoloUno",SoloUno);
        finish();
        startActivity(intent);
    }

    private void nuevaActividad() {
        aciertos2+=Ventana_cursos_ejer.aciertos;
        filas=0;
       Intent intent=new Intent(this,Resultados_Duolingo.class);
       startActivity(intent);
       finish();
    }

    private void showepicdialogCorrrect() {
        epicdialogCorrect.setContentView(R.layout.popup_correcto);
        boton=epicdialogCorrect.findViewById(R.id.correcto);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hilo2();
                epicdialogCorrect.dismiss();
            }
        });
        epicdialogCorrect.show();
    }

    private void Cargar(){
        if(Menu_Cursos.ventana.equalsIgnoreCase("1")){
            bloquecito=0;
        }
        if (Menu_Cursos.ventana.equalsIgnoreCase("2")){
           bloquecito=1;
        }
        if (Menu_Cursos.ventana.equalsIgnoreCase("3")){
            bloquecito=2;
        }
        if (Menu_Cursos.ventana.equalsIgnoreCase("4")){
            bloquecito=3;
        }
        if(Menu_Cursos.ventana.equalsIgnoreCase("5")){
            bloquecito=4;
        }
        if (Menu_Cursos.ventana.equalsIgnoreCase("6")){
            bloquecito=5;
        }
        if (Menu_Cursos.ventana.equalsIgnoreCase("7")){
            bloquecito=6;
        }
imagen1.setImageResource((int)VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][0]);
imagen2.setImageResource((int)VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][1]);
        imagen3.setImageResource((int)VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][2]);
        imagen4.setImageResource((int)VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][3]);
        numero1.setText(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][4].toString());
        numero2.setText(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][5].toString());
        numero3.setText(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][6].toString());
        numero4.setText(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][7].toString());
        textView.setText(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][8].toString());
        textView2.setText(VariablesYDatos.Opciones_Duolingo2[bloquecito][filas][9].toString());
        textView3.setText(VariablesYDatos.DuolingoNombres[bloquecito].toString());
    }


}
