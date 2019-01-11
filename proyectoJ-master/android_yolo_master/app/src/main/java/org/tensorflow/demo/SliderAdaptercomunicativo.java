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

public class SliderAdaptercomunicativo extends PagerAdapter {
    Context conte;
    LayoutInflater infla;
    public SliderAdaptercomunicativo(Context context){
        this.conte=context;

    }
    public  int slide_imagenes[]={R.drawable.tierra,R.drawable.ayuda};
    public String texto1 []={"Dato Curioso", "Objetivo"};
    public String texto2 []={"La OMS estima que 360 millones de personas sufren pérdida comunicativa, 328 millones de adultos y 32 millones de niños, más del 5% de la población mundial.","Nuestro objetivo es ayudar a aquellas personas con estos problemas tanto en el aprendizaje y su vida diaria teniendo como meta estar en centros de ayuda para estas personas."};
    @Override
    public int getCount() {
        return texto1.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        infla=(LayoutInflater) conte.getSystemService(conte.LAYOUT_INFLATER_SERVICE);
        View view=infla.inflate(R.layout.slidecomuni, container,false);
        ImageView slide_image=view.findViewById(R.id.imageView3);
        TextView text=view.findViewById(R.id.textView3);
        TextView text2=view.findViewById(R.id.textView33);
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
