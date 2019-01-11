package org.tensorflow.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jj on 26/07/18.
 */

public class Adaptadorimagen extends RecyclerView.Adapter<Adaptadorimagen.ImagenHolder> implements View.OnClickListener{
    List<Recyclerimg>listaimagen;
    private View.OnClickListener listener2;
    public Adaptadorimagen(List<Recyclerimg>listaimagen){
        this.listaimagen=listaimagen;
    }
    @Override
    public Adaptadorimagen.ImagenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermostrar2,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ImagenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptadorimagen.ImagenHolder holder, int position) {
holder.textView.setText(listaimagen.get(position).getNombre().toString());
holder.textView2.setText(listaimagen.get(position).getComentario().toString());

if(listaimagen.get(position).getImagenn()!=null){
    holder.img.setImageBitmap(listaimagen.get(position).getImagenn());
}
else{
    int alto=0;
    int ancho=0;
    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ancho,alto);
    holder.img.setLayoutParams(params);
    //holder.img.setImageResource(R.drawable.camera);
}
    }

    @Override
    public int getItemCount() {
        return listaimagen.size();
    }

    @Override
    public void onClick(View v) {
        if(listener2!=null){
            listener2.onClick(v);
        }
    }

    public class ImagenHolder extends RecyclerView.ViewHolder{
TextView textView, textView2;
ImageView img;
        public ImagenHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.nombres);
            textView2=itemView.findViewById(R.id.comentarios);
            img=itemView.findViewById(R.id.imagenes);
        }
    }
    public void setOnClickListener2(View.OnClickListener listenerr){
        this.listener2=listenerr;
    }
}
