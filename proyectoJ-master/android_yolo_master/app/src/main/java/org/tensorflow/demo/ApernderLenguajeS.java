package org.tensorflow.demo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ApernderLenguajeS extends AppCompatActivity {
public Transition transitio;
public long DURATION_TRANSITION=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apernder_lenguaje_s);
    }

    public void click1(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void click2(View view) {
        transitio=new Fade(Fade.OUT);
        iniciarActividad2();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void iniciarActividad2() {
        transitio.setDuration(DURATION_TRANSITION);
        transitio.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transitio);
        Intent inte=new Intent(ApernderLenguajeS.this, CursoAnimales.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void click3(View view) {
        transitio=new Fade(Fade.OUT);
        iniciarActividad3();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void iniciarActividad3() {
        transitio.setDuration(DURATION_TRANSITION);
        transitio.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transitio);
        Intent inte=new Intent(ApernderLenguajeS.this, CursoSaludos.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void click4(View view) {
        transitio=new Fade(Fade.OUT);
        iniciarActividad4();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
private void iniciarActividad4(){
    transitio.setDuration(DURATION_TRANSITION);
    transitio.setInterpolator(new DecelerateInterpolator());
    getWindow().setExitTransition(transitio);
    Intent inte=new Intent(ApernderLenguajeS.this, CursoSaludos.class);
    startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
}
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void click5(View view) {
        transitio=new Fade(Fade.OUT);
        iniciarActividad5();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void iniciarActividad5() {
        transitio.setDuration(DURATION_TRANSITION);
        transitio.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transitio);
        Intent inte=new Intent(ApernderLenguajeS.this, CursoSemana.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }
}
