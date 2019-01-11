package org.tensorflow.demo;

/**
 * Created by jj on 26/07/18.
 */

public class Recyclerm {
    private String nombree;
    private String contentestado;
    private int imagen;
    public void setNombre(String nombre){
        this.nombree=nombre;

    }
    public void setImagen(int imagen){
        this.imagen=imagen;
    }
    public int getImagen(){
        return imagen;
    }
    public void setContentestado(String contentestado){
this.contentestado=contentestado;
    }

    public String getNombree(){
        return nombree;
    }
    public String getContentestado(){
        return contentestado;
    }
}
