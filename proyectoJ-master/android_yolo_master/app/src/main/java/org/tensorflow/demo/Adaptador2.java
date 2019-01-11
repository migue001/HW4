package org.tensorflow.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jj on 28/06/18.
 */

public class Adaptador2 extends RecyclerView.Adapter<Adaptador2.ViewHolder>implements View.OnClickListener {

    private View.OnClickListener listener2;
    @Override
    public void onClick(View view) {
        if(listener2!=null){
            listener2.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.titulos);


        }
    }
    public List<Reciclers2> listadieta2;
    public Adaptador2(List<Reciclers2> listadieta2){
        this.listadieta2=listadieta2;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler2,parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listadieta2.get(position).getTitulo());



    }

    @Override
    public int getItemCount() {
        return listadieta2.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener2=listener;
    }
}
