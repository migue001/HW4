package org.tensorflow.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Videos extends AppCompatActivity {
VideoView vv;
int posicionActual=0;
TextView tv,tvDes;

ImageButton btnRegresar,btnSiguiente,btnRecargar,btnQuiz;
    int videos[]={R.raw.videounoabecedario,R.raw.videodospronombres,R.raw.videotresnormascortesia,R.raw.videocuatrodiassemana,
            R.raw.videocincomesesano,R.raw.videoseistiempo,R.raw.videosieteprguntas,R.raw.videoochoverboscomunes,R.raw.videonueverespuestascortas,
            R.raw.videodiezfamilia,R.raw.videocomida,R.raw.videocuatorcetransporte,R.raw.videodocehogar,R.raw.videonumeros,
            R.raw.videooncecolores,R.raw.videoquinceprofesiones,R.raw.videosalud,R.raw.videotrececuerpo};
    String nombreVideo[]={"Abecedario","Pronombres","Normas de cortesía","Días de la semana", "Meses del año","Tiempo","Preguntas",
            "Verbos comunes","Respuestas cortas","Familia","Comida","Transporte","Hogar","Numeros","Colores","Profesiones","Salud","Cuerpo"};


    String nombreDescripcion[]={"Abecedario","Pronombres","Normas de cortesía","Días de la semana", "Meses del año","Tiempo","Preguntas",
            "Verbos comunes","Respuestas cortas","Familia","Comida","Transporte","Hogar","Numeros","Colores","Profesiones","Salud","Cuerpo"};//cambiar por la de afuera

    LinearLayout contenedorControles,contenedorVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        tv=findViewById(R.id.txtTitulo);
        tvDes=findViewById(R.id.txtDescripción);

        btnQuiz = findViewById(R.id.btnQuiz);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Duolingo.class);
                i.putExtra("bloquecito",(posicionActual-2));
                i.putExtra("fila",0);
                i.putExtra("SoloUno",true);
                startActivity(i);
            }
        });
        btnQuiz.setVisibility(View.GONE);
        vv=findViewById(R.id.video);
        contenedorControles = findViewById(R.id.contenedorcontroles);
        contenedorVideo = findViewById(R.id.contenedorVideo);

        contenedorVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contenedorControles.setVisibility(View.VISIBLE);

            }
        });
        contenedorControles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contenedorControles.setVisibility(View.GONE);
            }
        });


        btnRecargar=findViewById(R.id.btnRecargar);
        btnRecargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              vv.seekTo(0);
            }
        });
        btnSiguiente=findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(posicionActual==videos.length-1){
                    finish();
                    return;
                }
                else if(posicionActual==videos.length-2){
                    btnSiguiente.setImageResource(R.drawable.ic_action_next);
                }
                if(posicionActual!=1){
                    btnRegresar.setImageResource(R.drawable.ic_action_back_video);
                }

                posicionActual++;
                tv.setText(nombreVideo[posicionActual]);
                if(posicionActual==videos.length-1){
                    tvDes.setText((posicionActual+1)+"/"+(nombreDescripcion.length)+"\nDescripción:\n"+nombreDescripcion[posicionActual]+"\nSiguiente: Salir");
                }else {
                    tvDes.setText((posicionActual+1)+"/"+(nombreDescripcion.length)+"\nDescripción:\n" + nombreDescripcion[posicionActual] + "\nSiguiente: " + nombreVideo[posicionActual + 1]);
                }
                if(posicionActual==0||posicionActual==1||posicionActual>10){
                    btnQuiz.setVisibility(View.GONE);
                }else{
                    btnQuiz.setVisibility(View.VISIBLE);
                }
                SettearVideo();
            }
        });
        btnRegresar=findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicionActual!=videos.length-2){
                    btnSiguiente.setImageResource(R.drawable.ic_action_next_video);
                }
                 if(posicionActual==1){
                    btnRegresar.setImageResource(R.drawable.ic_action_back);
                }else if(posicionActual==0){
                finish();
                return;
                 }else{
                     btnRegresar.setImageResource(R.drawable.ic_action_back_video);
                 }

                posicionActual--;
                tv.setText(nombreVideo[posicionActual]);

                    tvDes.setText((posicionActual+1)+"/"+nombreDescripcion.length+"\nDescripción:\n" + nombreDescripcion[posicionActual] + "\nSiguiente: " + nombreVideo[posicionActual + 1]);

                if(posicionActual==0||posicionActual==1){
                    btnQuiz.setVisibility(View.GONE);
                }else{
                    btnQuiz.setVisibility(View.VISIBLE);
                }
                SettearVideo();
            }
        });
        tv.setText(nombreVideo[posicionActual]);
        tvDes.setText((posicionActual+1)+"/"+nombreDescripcion.length+"\nDescripción:\n" + nombreDescripcion[posicionActual] + "\nSiguiente: " + nombreVideo[posicionActual + 1]);
        SettearVideo();
    }
    void SettearVideo(){
        try {
            String path = "android.resource://org.tensorflow.tensorflowdemo/" + videos[posicionActual];
            Uri uri = Uri.parse(path);
            vv.setVideoURI(uri);
            vv.setMediaController(new MediaController(this));
            vv.start();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
