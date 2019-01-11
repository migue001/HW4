package org.tensorflow.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jj on 11/07/18.
 */

public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder>implements View.OnClickListener {

    private View.OnClickListener listener2;
    @Override
    public void onClick(View view) {
        if(listener2!=null){
            listener2.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagensena);


        }
    }
    public List<Recyclers3> listadieta2;
    public Adapter3(List<Recyclers3>listadieta2){
        this.listadieta2=listadieta2;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler3,parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.fondoazul);



    }

    @Override
    public int getItemCount() {
        return listadieta2.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener2=listener;
    }
}
