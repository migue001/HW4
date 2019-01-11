package org.tensorflow.demo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class CursoAnimales extends AppCompatActivity {
    public static Fragment select=null;
    public static int progreso=0;
    public static  ProgressBar prs;
    public static int x=0;
    public static int contadorporgreso=+10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_animales);
        prs=findViewById(R.id.progresso);




        }

    public static void progreso() {
prs.setProgress(contadorporgreso);
    }




}
