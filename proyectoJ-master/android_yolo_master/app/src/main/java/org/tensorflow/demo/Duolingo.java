package org.tensorflow.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.tensorflow.demo.Clases.VariablesYDatos;

import pl.droidsonroids.gif.GifImageView;

public class Duolingo extends AppCompatActivity {

    GifImageView IMGgif;
    CardView btnOpcion1,btnOpcion2,btnOpcion3,btnOpcion4;
    TextView txtOpcion1,txtOpcion2,txtOpcion3,txtOpcion4,txtPregunta,txtTema;
    int bloquecito,fila,total=0,aciertos=0;
    boolean SoloUno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duolingo);
        btnOpcion1=findViewById(R.id.btnOpcion1);
        btnOpcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Revisar(txtOpcion1.getText().toString());
            }
        });
        btnOpcion2=findViewById(R.id.btnOpcion2);
        btnOpcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Revisar(txtOpcion2.getText().toString());
            }
        });
        btnOpcion3=findViewById(R.id.btnOpcion3);
        btnOpcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Revisar(txtOpcion3.getText().toString());
            }
        });
        btnOpcion4=findViewById(R.id.btnOpcion4);
        btnOpcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Revisar(txtOpcion4.getText().toString());
            }
        });

        txtOpcion1=findViewById(R.id.txtOpcion1);
        txtOpcion2=findViewById(R.id.txtOpcion2);
        txtOpcion3=findViewById(R.id.txtOpcion3);
        txtOpcion4=findViewById(R.id.txtOpcion4);
        txtTema=findViewById(R.id.txtTema);
        txtPregunta=findViewById(R.id.txtPregunta);

        IMGgif=findViewById(R.id.IMGgif);

        SoloUno=getIntent().getBooleanExtra("SoloUno",false);

        bloquecito=getIntent().getIntExtra("bloquecito",0);
        fila=getIntent().getIntExtra("fila",0);
        total=getIntent().getIntExtra("total",0);
        aciertos=getIntent().getIntExtra("aciertos",0);

        Log.i("AQUI","Bloquecito "+ bloquecito+" \nFila "+fila+" \nAciertos "+aciertos+" \nTotal "+total);
        Log.i("AQUI", VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());
        Settear();
    }
void NuevaActivity(){
    Intent i=new Intent(this,Resultados_Duolingo.class);
    i.putExtra("total",total);
    i.putExtra("aciertos",aciertos);
    startActivity(i);
}
    void Revisar(String respuesta){

    //
        String title="",msg="Sigue así";int img=0;
        //
        if(respuesta.equalsIgnoreCase(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][5].toString())){
            //Toast.makeText(this,"Correcto!",Toast.LENGTH_SHORT).show();
            aciertos=aciertos+1;
            title="Respuesta correcta";
            img=R.drawable.ic_action_correcta;
        }else{
          //  Toast.makeText(this,"InCorrecto!",Toast.LENGTH_SHORT).show();
            title="Respuesta Incorrecta";
            img=R.drawable.ic_action_name;
            msg="La correcta era: "+VariablesYDatos.OpcionesDuolingo[bloquecito][fila][5].toString();
        }
        //
        String a="Siguiente";
        final CharSequence[] opciones={a};
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(img);
        builder.setMessage(msg);
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Siguiente")) {
                    Hilo();
                }
            }
        });
        builder.show();
        //

    }
    void Hilo(){
        total=total+1;
        if(bloquecito==VariablesYDatos.OpcionesDuolingo.length-1 && fila==4){
            NuevaActivity();
            return;
        }
        Intent i=new Intent(this,Duolingo.class);

        if(fila==4) {
            if(SoloUno){
                NuevaActivity();
                return;
            }

            bloquecito=bloquecito+1;
            fila=0;
        }else{
            fila=fila+1;
        }
        i.putExtra("bloquecito", bloquecito);
        i.putExtra("fila",fila);
        i.putExtra("total", total);
        i.putExtra("aciertos",aciertos);
        i.putExtra("SoloUno",SoloUno);
        Log.i("AQUI","Bloquecito "+ bloquecito+" \nFila "+fila);
        finish();
        startActivity(i);
    }

    void Settear(){

        txtPregunta.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][4].toString());

        txtOpcion1.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][0].toString());
        txtOpcion2.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][1].toString());
        txtOpcion3.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][2].toString());
        txtOpcion4.setText(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][3].toString());

        txtTema.setText("Tema: "+VariablesYDatos.DuolingoNombres[bloquecito]);

        IMGgif.setImageResource((int)(VariablesYDatos.OpcionesDuolingo[bloquecito][fila][6]));
        //opcion1,opcion2,opcion3,opcion4,pregunta,buena,gif
        //fila++;
    }
}
