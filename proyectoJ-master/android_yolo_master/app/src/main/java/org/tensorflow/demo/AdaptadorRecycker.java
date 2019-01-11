package org.tensorflow.demo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
 * Created by jj on 25/06/18.
 */

public class AdaptadorRecycker extends RecyclerView.Adapter<AdaptadorRecycker.ViewHolder>implements View.OnClickListener {

    private View.OnClickListener listener;
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
private ImageView linear;

        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.texto);

linear=itemView.findViewById(R.id.image);
        }
    }
    public List<Reciclers>listadieta;
    public AdaptadorRecycker(List<Reciclers>listadieta){
        this.listadieta=listadieta;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listadieta.get(position).getTexto());

        holder.linear.setImageResource(listadieta.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return listadieta.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
}