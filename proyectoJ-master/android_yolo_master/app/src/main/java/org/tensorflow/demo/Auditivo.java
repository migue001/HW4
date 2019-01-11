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
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Auditivo extends AppCompatActivity {
    private ViewPager pager;
    private LinearLayout linear;
    private SliderAdapterauditivo sliderAdapter;
    private TextView[] nn;
    public static  final long tiempo=1000;
    public Transition transition2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditivo);
        pager=findViewById(R.id.sliderrr);
        linear=findViewById(R.id.line);
        sliderAdapter=new SliderAdapterauditivo(this);
        pager.setAdapter(sliderAdapter);
        indicador();
    }

    private void indicador() {
        nn=new TextView[2];
        for(int y=0;y<nn.length;y++){
            nn[y]=new TextView(this);
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
        transition2.setDuration(tiempo);
        transition2.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition2);
        Intent inte=new Intent(Auditivo.this, MainActivity.class);
        startActivity(inte, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sigui2(View view) {
      try {
            transition2 = new Fade(Fade.OUT);
            iniciarActividad();
            Opciones.disca = "auditivo";
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        Intent inte=new Intent(Auditivo.this, MainActivity.class);
        startActivity(inte);
    }
}
