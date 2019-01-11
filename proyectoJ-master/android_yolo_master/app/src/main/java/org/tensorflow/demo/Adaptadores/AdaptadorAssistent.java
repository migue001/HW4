package org.tensorflow.demo.Adaptadores;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.tensorflow.demo.Clases.Elemen;
import org.tensorflow.demo.R;
import java.util.List;


/**
 * Created by azulm on 21/04/2018.
 */

public class AdaptadorAssistent extends RecyclerView.Adapter<AdaptadorAssistent.adaptadorHolder>{


    List<Elemen> ListaElemen;

    public AdaptadorAssistent(List<Elemen> listaElemen) {
        this.ListaElemen = listaElemen;
    }

    @Override
    public adaptadorHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
    /*    View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);*/
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento,parent,false);
        final adaptadorHolder holder = new adaptadorHolder(v);

holder.contenedor.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


    }
});

        return holder;
    }

    @Override
    public void onBindViewHolder(AdaptadorAssistent.adaptadorHolder holder, int position) {
        try {
            holder.txtMensaje.setText(ListaElemen.get(position).getMensaje().toString());
            holder.txtNombre.setText(ListaElemen.get(position).getNombre().toString());

           if(ListaElemen.get(position).isLado()){
               //si si es, es el usuario
               holder.contenedor.setBackgroundColor(Color.WHITE);
               holder.txtNombre.setTextColor(Color.RED);
           }else{
               holder.content.setGravity(Gravity.RIGHT);
               holder.txtNombre.setTextColor(Color.BLUE);

           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return ListaElemen.size();
    }
        public class adaptadorHolder extends RecyclerView.ViewHolder{
            TextView txtMensaje,txtNombre;
            LinearLayout content;
            CardView contenedor;
        public adaptadorHolder(View itemView) {
            super(itemView);
            txtMensaje=itemView.findViewById(R.id.txtMensaje);
            txtNombre=itemView.findViewById(R.id.txtNombre);
            contenedor=itemView.findViewById(R.id.contenedor);
            content=itemView.findViewById(R.id.content);

        }
    }
}
