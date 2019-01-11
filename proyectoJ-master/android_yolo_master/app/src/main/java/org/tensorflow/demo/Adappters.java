package org.tensorflow.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jj on 19/12/18.
 */

public class Adappters extends RecyclerView.Adapter<Adappters.ViewHolder>implements View.OnClickListener {
    private View.OnClickListener listener;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.temas);
        }

    }
    public ArrayList<Classes> list;
    public Adappters(ArrayList<Classes> list){
        this.list=list;
    }
    @NonNull
    @Override
    public Adappters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,null,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adappters.ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTexto());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnClickListener2(View.OnClickListener listenerr){
        this.listener=listenerr;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }
}

