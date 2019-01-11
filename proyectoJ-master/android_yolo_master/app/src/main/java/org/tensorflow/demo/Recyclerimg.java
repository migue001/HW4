package org.tensorflow.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

/**
 * Created by jj on 26/07/18.
 */

public class Recyclerimg {
    private String nombre;
    private String comentario;
    private Bitmap imagenn;
    private String rutaimagen;
    private String datoo;
    public void setImagenn(Bitmap imagenn){
        this.imagenn=imagenn;
    }
    public Bitmap getImagenn(){
        return imagenn;
    }
    public void setRutaimagen(String rutaimagen){
        this.rutaimagen=rutaimagen;
    }

    public String getRutaimagen() {
        return rutaimagen;
    }
    public void setDatoo(String datoo){
        try{
byte[]bytecode= Base64.decode(datoo,Base64.DEFAULT);
int alto=500;
int ancho=600;
            this.imagenn=BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);

        }catch (Exception e){
            e.printStackTrace();
        }
        this.datoo=datoo;
    }
    public String getDatoo(){
        return datoo;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getNombre(){
        return nombre;
    }
    public void setComentario(String comentario){
        this.comentario=comentario;
    }
    public String getComentario(){
        return comentario;
    }
}
