package org.tensorflow.demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Visual extends AppCompatActivity {
    private ViewPager pager;
    private LinearLayout linear;
    private SliderAdapter sliderAdapter;
    private TextView[] nn;
    private static  final long tiempo=1000;
    public Transition transition3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);
        pager=findViewById(R.id.sliderrr);
        linear=findViewById(R.id.line);
        sliderAdapter=new SliderAdapter(this);
        pager.setAdapter(sliderAdapter);
        indicador();
    }

    private void indicador() {
        nn = new TextView[2];
        for (int y = 0; y < nn.length; y++) {
            nn[y] = new TextView(this);
            nn[y].setText(Html.fromHtml("&#8226;"));
            nn[y].setTextSize(35);
            nn[y].setTextColor(getResources().getColor(R.color.white));
            linear.addView(nn[y]);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void volv(View view) {
        finishAfterTransition();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void iniciarActividad(){
        transition3.setDuration(tiempo);
        transition3.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition3);
        Intent inte=new Intent(Visual.this, MainActivity.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sigui(View view) {
        try {
            transition3 = new Slide(Gravity.START);
            iniciarActividad();
            Opciones.disca = "visual";
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        Intent inte=new Intent(Visual.this, MainActivity.class);
        startActivity(inte);

    }
}
