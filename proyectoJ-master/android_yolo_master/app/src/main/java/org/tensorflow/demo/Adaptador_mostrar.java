package org.tensorflow.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by jj on 26/07/18.
 */

public class Adaptador_mostrar extends RecyclerView.Adapter<Adaptador_mostrar.ViewHolder> {
List<Recyclerm>listacontenido;
    //private Context context;
    TextToSpeech tts=null;
public Adaptador_mostrar(List<Recyclerm>listacontenido){
    this.listacontenido=listacontenido;
}
    @Override
    public Adaptador_mostrar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermostrar,parent,false);
    RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    view.setLayoutParams(layoutParams);
   // this.context=context;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_mostrar.ViewHolder holder, int position) {
holder.txtcontent.setText(listacontenido.get(position).getContentestado().toString());
holder.nomb.setText(listacontenido.get(position).getNombree().toString());
//holder.imageButton.setText(listacontenido.get(position).getImagen());
//holder.imageButton.setBackgroundResource(R.drawable.volumen);


    }

    @Override
    public int getItemCount() {
        return listacontenido.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
TextView nomb, txtcontent;
ImageButton imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nomb=itemView.findViewById(R.id.nombreusuario);
            txtcontent=itemView.findViewById(R.id.estadotext);
            //imageButton=itemView.findViewById(R.id.escuchar);
           /* imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre="";
                    TextToSpeech toSpeech = null;
                    String Contenido =txtcontent.getText().toString();
                    Log.i("Estado",Contenido);
/*tts=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());

        } else {
           Log.i("rrrr","Feature not supported in your device");
        }
    }

});*/
                   /* Datos_Usuario conex=new Datos_Usuario(context,"DBUsuario",null,2);
                    SQLiteDatabase db=conex.getReadableDatabase();
                    Cursor cursor=db.rawQuery("SELECT Nombre FROM Usuario",null);
                    try{
                        if(cursor.moveToFirst()){
                            nombre=cursor.getString(0);
                        }
                    }catch (Exception e){
                        Log.i("Error Nombre","BD Nombre");
                    }
                    Bundle params=new Bundle();
                    tts.speak("De "+nombre+" "+Contenido, TextToSpeech.QUEUE_FLUSH, null);*/
              //  }
            //});

        }
    }
}
