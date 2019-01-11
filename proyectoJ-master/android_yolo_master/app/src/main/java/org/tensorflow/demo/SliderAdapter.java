package org.tensorflow.demo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by jj on 12/06/18.
 */

public class SliderAdapter extends PagerAdapter {
    Context conte;
    LayoutInflater infla;
    public SliderAdapter(Context context){
        this.conte=context;

    }
    public  int slide_imagenes[]={R.drawable.tierra,R.drawable.ayuda};
    public String texto1 []={"Dato Curioso", "Objetivo"};
    public String texto2 []={"En el mundo hay unas 45 millones de personas con discapacidad visual y la cifra va en aumento Sin embargo, en el 80% de los casos, la pérdida de visión se puede prevenir o curar, según la Organización Mundial de la Salud (OMS).","Nuestro Objetivo es ayudar a estas personas para mejorar sus seguridad y poder realizar tareas "};
    @Override
    public int getCount() {
        return texto1.length;
    }

    @Override
    public boolean isViewFromObject(View view1, Object object) {
        return view1==(RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        infla=(LayoutInflater) conte.getSystemService(conte.LAYOUT_INFLATER_SERVICE);
        View view=infla.inflate(R.layout.slide_layout, container,false);
        ImageView slide_image=view.findViewById(R.id.imageView);
        TextView text=view.findViewById(R.id.textView);
        TextView text2=view.findViewById(R.id.textView3);
        slide_image.setImageResource(slide_imagenes[position]);
        text.setText(texto1[position]);
        text2.setText(texto2[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
