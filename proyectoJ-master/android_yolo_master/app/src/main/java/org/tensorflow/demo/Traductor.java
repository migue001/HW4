package org.tensorflow.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static org.tensorflow.demo.Clases.VariablesYDatos.etiquetas;

import java.util.ArrayList;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class Traductor extends AppCompatActivity {
    GifImageView gf;
    public static EditText txtTraducir;
    TextView txtProgreso;
    ImageButton btnTraducir,btnLimpiar,btnMicroTra,btnCameraTra;
    ImageView PlayPause;
    int mayor=0,contador=1;
    String palMayor="";
    ArrayList<Integer> lista;
    int VideoPSettear=0;
    SpeechRecognizer mSpeechRecognizer;
    CountDownTimer countDownTimer;
    Switch swvelocidad;
    int milliseconds=2700;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traductor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }
        lista=new ArrayList<>();
        swvelocidad = findViewById(R.id.swvelocidad);
        swvelocidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swvelocidad.setText("Rápido");
                    milliseconds=1100;
                }else{
                    swvelocidad.setText("Lento");
                    milliseconds=2500;
                }
            }
        });
        //---------------------
        txtProgreso=findViewById(R.id.txtProgreso);
        PlayPause=findViewById(R.id.btnPausePlay);
        //----------------------
        gf=findViewById(R.id.gif);
        gf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    countDownTimer.cancel();
                    countDownTimer.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        txtTraducir=findViewById(R.id.txtTraducir);
        try{
                txtTraducir.setText(getIntent().getStringExtra("tra"));

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        if(!txtTraducir.getText().toString().equals("")){
            lista.clear();
            contador=1;
            comparar(remplazarComunes(txtTraducir.getText().toString()).split(" "));
        }
        btnLimpiar=findViewById(R.id.btnLimpiar);
        btnTraducir=findViewById(R.id.btnTraducir);
        btnTraducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTraducir.getText().toString().equalsIgnoreCase("")){
                    show("Proporciona algo para traducir.");
                    return;
                }
                lista.clear();
                contador=1;

                comparar(remplazarComunes(txtTraducir.getText().toString()).split(" "));
            }
        });

        //-----------------------------

        checkPermission();


        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

                Log.i("AQUI", "ready");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.i("AQUI", "begin");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.i("AQUI", "buffer");
            }

            @Override
            public void onEndOfSpeech() {

                Log.i("AQUI", "end");

            }

            @Override
            public void onError(int i) {
                Log.i("AQUI", "error" + i);

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    String input = matches.get(0);
                    txtTraducir.setText(input.toString());
                    if(txtTraducir.getText().toString().equalsIgnoreCase("")){
                        show("Proporciona algo para traducir.");
                        return;
                    }
                    lista.clear();
                    contador=1;

                    comparar(remplazarComunes(txtTraducir.getText().toString()).split(" "));
                }
            }
            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.i("AQUI", "partial");
                //displaying the first match
            }
            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });
        //-----------------------------
        btnCameraTra=findViewById(R.id.btncameratraducir);
        btnCameraTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTraducir.setText("");
                startActivity(new Intent(getApplicationContext(), Lector_Traductor.class));
            }
        });
        btnMicroTra=findViewById(R.id.btnmicrotraducir);
        btnMicroTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTraducir.setText("");
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTraducir.setText("");
            }
        });
    }

    private void initializeView() {
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(Traductor.this, FloatingViewService.class));
                finish();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void show(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    void comparar(String partes []){
        String palabraAC="",palabra="";
        boolean sino=false;
        String [] partesnew=BuscarNumeros(partes);
        for(int i = 0;i<partesnew.length;i++) { //repasar el input
            for (int j = 0; j < etiquetas.length; j++) {

                palabraAC = LimpiarCadenas(partesnew[i]);
                palabraAC=palabraAC.replace(" ","");
                palabra = etiquetas[j][0].toString();
                //  System.err.println(palabraAC);
                compararLetras(palabraAC, palabra);
            }
            if (mayor != 0) {
                //-------------si no esta-----------------------------------------------
                if(mayor<=3 && !palabraAC.equalsIgnoreCase("ir")&&!palabraAC.equalsIgnoreCase("tu")&&
                !palabraAC.equalsIgnoreCase("yo") && !EsNumero(palabraAC)){

                    String[] palregresada =SepararLetras(palabraAC);
                    int pos=0;
                    for(int m = 0;m<palregresada.length;m++){
                        for (int k = 0; k < etiquetas.length; k++) {
                            if (palregresada[m].equalsIgnoreCase(etiquetas[k][0].toString())) {
                                pos=k;
                            }
                        }
                        VideoPSettear=((int)(etiquetas[pos][1]));
                        lista.add(VideoPSettear);
                        pos=0;
                    }

                    continue;
                }

                //-----------_______---------------------------------------------------------
                int pos=0;
                // System.out.println(palabraAC + " se parece más a: " + palMayor);
                for (int k = 0; k < etiquetas.length; k++) {
                    if (palMayor.equalsIgnoreCase(etiquetas[k][0].toString())) {
                        pos=k;
                    }
                }
                VideoPSettear=((int)(etiquetas[pos][1]));

                lista.add(VideoPSettear);

            }
            palMayor="";
            mayor=0;
            if(sino){
                i++;
                sino=false;
            }
        }
        gf.setImageResource(lista.get(0));
        txtProgreso.setText("1/"+lista.size());
        video();
    }


    String remplazarComunes(String frase){

        String [] c= frase.split(" ");
        for(int i = 0;i<c.length;i++){
            //pasado
            if(c[i].endsWith("í")){
                c[i]=c[i] +" ayer";
            }
            //futuro
            if((c[i].endsWith("é")||c[i].endsWith("ría")||c[i].endsWith("ás"))){
            if(!c[i].equalsIgnoreCase("qué")&&!c[i].equalsIgnoreCase("sé")
                    &&!c[i].equalsIgnoreCase("estás")&&!c[i].equalsIgnoreCase("bebé")){
                c[i]=c[i] +" mañana";
            }
            }
        }
        frase ="";
        for(int i = 0;i<c.length;i++){
            frase+= c[i]+ " ";
        }

        frase=LimpiarCadenas(frase);
        frase=frase.replace("fui","ir ayer");
        frase=frase.replace("ire","ir");
        frase=frase.replace(":"," ");
        frase=frase.replace("hicieron","ustedes hacer ayer");
        frase=frase.replace("hiciste","tu hacer ayer");
        frase=frase.replace("haran","ustedes hacer manana");
        frase=frase.replace("novia","novio mujer");
        frase=frase.replace("mucho gusto","gustoenconocerte");
        frase=frase.replace("dame","dar");



        frase=frase.replace("buenas noches","buenasnoches");
        frase=frase.replace("buena noche","buenasnoches");
        frase=frase.replace("buenas taredes","buenastardes");
        frase=frase.replace("buena tarde","buenastardes");
        frase=frase.replace("buenos dias","buenosdias");
        frase=frase.replace("buen dia","buenosdias");
        frase=frase.replace("como estas","comoestas");
        frase=frase.replace("como estan","comoestas");
        frase=frase.replace("como esta","comoestas");
        frase=frase.replace("cual es tu nombre","tnombre");
        frase=frase.replace("cuál es tu nombre","tnombre");
        frase=frase.replace("como te llamas","tnombre");
        frase=frase.replace("cómo te llamas","tnombre");
        frase=frase.replace("dime tu nombre","tnombre");
        frase=frase.replace("de nada","denada");
        frase=frase.replace("gusto en conocerte","gustoenconocerte");
        frase=frase.replace("gusto en verte","gustoenconocerte");
        frase=frase.replace("hasta luego","nosvemos");
        frase=frase.replace("nos vemos","nosvemos");
        frase=frase.replace("adios","nosvemos");
        frase=frase.replace("a dios","nosvemos");
        frase=frase.replace("medio","masomenos");
        frase=frase.replace("por que","porque");
        frase=frase.replace("para que","paraque");
        frase=frase.replace("mas o menos","masomenos");

        frase=frase.replace("en adelante","enadelante");
        frase=frase.replace("para delante","enadelante");
        frase=frase.replace("adelante","enadelante");
        frase=frase.replace("delante","enadelante");
        frase=frase.replace("hacia delante","enadelante");
        frase=frase.replace("posterior","despues");
        frase=frase.replace("posteriormente","despues");
        frase=frase.replace("medio dia","mediodia");
        frase=frase.replace("otra vez","otravez");
        frase=frase.replace("todavia no","todaviano");
        frase=frase.replace("aun no","todaviano");
        frase=frase.replace("una vez","unavez");
        frase=frase.replace("primera vez","primeravez");
        frase=frase.replace("por primera vez","primeravez");
        frase=frase.replace("mi primera vez","primeravez");

        frase=frase.replace("no poder","nopoder");
        frase=frase.replace("no puedo","nopoder");
        frase=frase.replace("puedo","poder");
        frase=frase.replace("pude","poder");
        frase=frase.replace("podre","poder");
        frase=frase.replace("no pude","nopoder");
        frase=frase.replace("no podre","nopoder");

        frase=frase.replace("voy","yo ir");
        frase=frase.replace("dime","tu decir");

        frase=frase.replace("comi","comer");
        frase=frase.replace("comere","comer");

        frase=frase.replace("te "," tu ");

        frase=frase.replace("mio","yo");
        frase=frase.replace(" mi ","yo");
        frase=frase.replace("usted","tu");
        frase=frase.replace("nuestro","nosotros");
        frase=frase.replace("tuyo","tu");

        frase=frase.replace("ve","tu ir");
        frase=frase.replace("vete","tu ir");

        frase=frase.replace("suyo","ellos");

        frase=frase.replace("ahora","ahorita");

        frase=frase.replace(" a ","");
        frase=frase.replace(" las ","");
        frase=frase.replace(" la "," l ");
        frase=frase.replace("roja","rojo");

        frase=frase.replace(" uno ","1");
        frase=frase.replace(" dos ","2");
        frase=frase.replace("roja","3");
        frase=frase.replace("roja","4");
        frase=frase.replace("roja","5");
        frase=frase.replace("roja","6");
        frase=frase.replace("roja","7");
        frase=frase.replace("roja","8");
        frase=frase.replace("roja","9");
        frase=frase.replace("roja","10");
        frase=frase.replace("roja","11");
        frase=frase.replace("roja","12");
        frase=frase.replace("roja","13");
        frase=frase.replace("roja","14");
        frase=frase.replace("roja","15");
        frase=frase.replace("roja","16");
        frase=frase.replace("roja","17");
        frase=frase.replace("roja","18");
        frase=frase.replace("roja","19");
        frase=frase.replace("roja","20");

        //--------numeros


        return frase;
    }

    String LimpiarCadenas(String palabraAC){
        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
        for (int k=0; k<original.length(); k++) {
            palabraAC = palabraAC.replace(original.charAt(k), ascii.charAt(k));
        }
        palabraAC=palabraAC.toLowerCase();
        palabraAC = palabraAC.replace("?", "");palabraAC = palabraAC.replace("!", "");palabraAC = palabraAC.replace("¿", "");
        palabraAC = palabraAC.replace("¡", "");palabraAC = palabraAC.replace(",", "");palabraAC = palabraAC.replace(".", "");
        //palabraAC = palabraAC.replace(" ", "");
        return palabraAC;
    }

    void compararLetras(String palabraAC,String palabra){

        int total = palabra.length();
        int totalAC = palabraAC.length();
        int aciertos=0,menor=0;
        if(total<totalAC){
            menor=total;
        }else if(total==totalAC){
            menor=total;
        }else{
            menor=totalAC;
        }
        for(int i = 0;i<menor;i++){
            if(palabra.charAt(i)==palabraAC.charAt(i)){
                aciertos++;
            }
        }
        int res = (int) (menor/2);
        //System.out.println("res: "+res+"\nAciertos: "+aciertos+"\n------------------------");
        if(aciertos>mayor && aciertos>res){
            mayor=aciertos;
            palMayor=palabra;
        }

//        return aciertos>=res;
    }
    void video (){
        countDownTimer = new CountDownTimer(milliseconds, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(contador==lista.size()){
                    contador=0;
                    PlayPause.setImageResource(R.drawable.ic_action_reload_video);
                    return;
                }
                gf.setImageResource(lista.get(contador));
                txtProgreso.setText((contador+1)+"/"+lista.size());
                contador++;
                video();
            }
        }.start();
    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + this.getPackageName()));
                startActivity(intent);

            }
        }
    }

    String[] SepararLetras(String palabra){
        String[] res=new String[palabra.length()];
        for(int i = 0;i<palabra.length();i++){
         res[i]=palabra.charAt(i)+"";
        }
        return res;
    }

    String [] BuscarNumeros(String[] frase){
        ArrayList<String> pal =new ArrayList<>();

        for(int i = 0;i<frase.length;i++){
            if(EsNumero(frase[i])){
                if(frase[i].substring(0,1).equals("0")){
                    frase[i]=frase[i].substring(1);
                }
                int lent=frase[i].length();
                if(lent==3){
                    pal.add(frase[i].substring(0,1)+"00");
                    if(!frase[i].substring(1,2).equalsIgnoreCase("1")){
                        if(frase[i].substring(1,2).equalsIgnoreCase("0")){
                            pal.add(frase[i].substring(2,3));
                        }else{
                            pal.add(frase[i].substring(1,2)+"0");
                            if(!frase[i].substring(2,3).equals("0")){
                                pal.add(frase[i].substring(2,3));
                            }
                        }
                    }else{
                        pal.add(frase[i].substring(1,3));
                    }
                }
                else if(lent==2){
                    if(!frase[i].substring(0,1).equalsIgnoreCase("1")){
                        if(frase[i].substring(0,1).equalsIgnoreCase("0")){
                            pal.add(frase[i].substring(1,2));
                        }else{
                            pal.add(frase[i].substring(0,1)+"0");
                            if(!frase[i].substring(1,2).equals("0")){
                                pal.add(frase[i].substring(1,2));
                            }

                        }
                    }else{
                        pal.add(frase[i].substring(0,2));
                    }
                }

            }else{
                pal.add(frase[i].toString());
            }
        }
        String[] reg = new String[pal.size()];
        for(int i = 0;i<pal.size();i++){
            reg[i] = pal.get(i);
        }
        //no debe regresar frase
        return reg;
    }

    boolean EsNumero(String num){
        try{
            int no = Integer.parseInt(num);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
