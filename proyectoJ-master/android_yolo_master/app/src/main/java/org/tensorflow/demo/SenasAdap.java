package org.tensorflow.demo;

/**
 * Created by jj on 19/12/18.
 */

public class SenasAdap {
    private int img;
    private String text;

    public SenasAdap(String text, int  img){

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SenasAdap(int img, String text) {
        this.img = img;
        this.text = text;
    }
}
