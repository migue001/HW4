package org.tensorflow.demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class Opciones extends AppCompatActivity {
    public static String disca="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
    }
    private static final long DURATION_TRANSITION=1000;
    private Transition transitio;
    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void iniciarActividad(){
        transitio.setDuration(DURATION_TRANSITION);
        transitio.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transitio);
        Intent inte=new Intent(Opciones.this, Visual.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void iniciarActividad2(){
        transitio.setDuration(DURATION_TRANSITION);
        transitio.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transitio);
        Intent inte=new Intent(Opciones.this, Auditivo.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void iniciarActividad3(){
        transitio.setDuration(DURATION_TRANSITION);
        transitio.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transitio);
        Intent inte=new Intent(Opciones.this, Comunicativo.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void card1(View view) {
        transitio=new Slide(Gravity.START);
        iniciarActividad();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void card2(View view) {
        transitio=new Fade(Fade.OUT);
        iniciarActividad2();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void card3(View view) {
        transitio=new Explode();
        iniciarActividad3();
    }
}
