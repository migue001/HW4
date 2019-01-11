package org.tensorflow.demo;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.tensorflow.demo.Clases.VariablesYDatos;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Classes> listaa;
    public static TextView textView;
    public static String tema="Pronombres";
    String texto="";
    TextToSpeech textToSpeech;
    ImageButton imageButton, imageButton2;
    int bloque;
    int contador;
    int result;
    public static android.support.v4.app.Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.recycleropciones);
        listaa=new ArrayList<>();
        Adappters adappters=new Adappters(listaa);
        adappters.setOnClickListener2(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema=listaa.get(recyclerView.getChildAdapterPosition(v)).getTexto();
//VentanaSenas.llenarRecycler();
                //Toast.makeText(Main2Activity.this, ""+tema, Toast.LENGTH_SHORT).show();
                inicio();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adappters);
        llenarRecycler();

        textView=findViewById(R.id.texttt);

        textToSpeech= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.getDefault());
                } else {

                }
            }
        });
        imageButton2=findViewById(R.id.hablar);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frase=textView.getText().toString();
                textToSpeech.speak(frase, TextToSpeech.QUEUE_FLUSH,null);

//finish();
            }
        });
        imageButton=findViewById(R.id.borrar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentanaSenas.texto="";
                textView.setText("");
            }
        });

        inicio();
    }
    private void llenarRecycler() {
        for(int xx = 0; xx< VariablesYDatos.temas.length; xx++){
            listaa.add(new Classes(""+VariablesYDatos.temas[xx]));
        }
    }
    private void inicio(){
        fragment= new VentanaSenas();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,fragment).commit();
    }
}
