package org.tensorflow.demo;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tensorflow.demo.SQLite.Notas;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Visual extends Fragment {
    private Context context=this.getActivity();
public static final long duracion=1000;
public static Transition transition;
    ImageButton btnMicro;
    CountDownTimer countDownTimer;
    String[] dias = {"domingo","lunes","martes","miércoles","jueves","viernes","sábado"};
    String input="";
    int result;
    String text;
    public TextToSpeech toSpeech;
    time t;
    MediaPlayer mp;
    String palabraauxilio="",numeroauxilio="";
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    SpeechRecognizer mSpeechRecognizer;
    boolean Esccuchando=false;
    public Fragment_Visual() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int permiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);


        View view = inflater.inflate(R.layout.fragment_fragment__visual, container, false);
requestQueue= Volley.newRequestQueue(this.getActivity());
progressDialog=new ProgressDialog(getActivity());
        FloatingActionButton fab = view.findViewById(R.id.floatt);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                iniciaractividad();
            }
        });


            ConsultarAuxilio();
        mp = MediaPlayer.create(getActivity(), R.raw.thunderunderground);
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //Ejecuta tu AsyncTask!
                            alarms myTask = new alarms();
                            myTask.execute();
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 10000);
        try {
            toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        result = toSpeech.setLanguage(Locale.getDefault());
                    } else {
                        Toast.makeText(getActivity(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkPermission();


         mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());


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
                try {
                    if (Esccuchando && !toSpeech.isSpeaking() && !Lector.toSpeech.isSpeaking() &&
                            !CameraActivity.toSpeech.isSpeaking()) {
                        toSpeech.speak("Lo siento, no escuche nada.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                Esccuchando=false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {

                    Log.i("AQUI", "results");
                    Log.i("AQUI", input);

                    String input = matches.get(0);
                    if ((input.contains("qué") && input.contains("puedes") || input.contains("hacer"))
                            || (input.contains("qué") && input.contains("puedes") || input.contains("ayudar"))
                            || (input.contains("cómo") && input.contains("puedes") && input.contains("ayudar"))
                            || (input.contains("qué") && input.contains("haces") || input.contains("ayudas"))
                            || (input.contains("cuáles") && input.contains("funcionalidades") && input.contains("labores"))) {
                        Funcionalidades();
                        return;
                    }
                    if (input.contains("alarma") && (input.contains("pausar") || input.contains("detener"))) {
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        return;
                    }

                    if (input.contains("ayúdame a leer") || input.contains("que dice") ||input.contains("leer")) {
                        startActivity(new Intent(getContext(), Lector.class));
                        return;
                    }
                    if (input.contains("cruzar la calle") || input.contains("hay carros")) {
                        Intent i =new Intent(getContext(), CameraActivity.class);
                        i.putExtra("SoloUno",false);
                        i.putExtra("CruzarCalle",true);
                        startActivity(i);
                        return;
                    }
                    if (input.contains("medicina") || input.contains("encontrar medicina")) {
                        Intent i =new Intent(getContext(), CameraActivity.class);
                        i.putExtra("SoloUno",false);
                        i.putExtra("CruzarCalle",false);
                        i.putExtra("Medicina",true);
                        startActivity(i);
                        return;
                    }
                    if (input.contains("estoy viendo") || input.contains("ayúdame a ver")
                            ||input.contains("hay enfrente")) {
                        Intent i =new Intent(getContext(), CameraActivity.class);
                        i.putExtra("SoloUno",true);
                        startActivity(i);
                        return;
                    }
                    if (input.contains("vamos a salir") || input.contains("encender cámara")
                            ||input.contains("dar un paseo")
                            ||input.contains("se mis ojos")) {
                        Intent i =new Intent(getContext(), CameraActivity.class);
                        i.putExtra("SoloUno",false);
                        startActivity(i);
                        return;
                    }
                    if (input.contains("Hey") || input.contains("Oye")) {
                        Toast.makeText(getActivity(), "Te escucho, ¿que necesitas?", Toast.LENGTH_SHORT).show();
                        text = "Te escucho, ¿que necesitas?";
                        toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        while(toSpeech.isSpeaking()){

                        }
                        Esccuchando=true;
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    }

                    String[] parts = input.split(" ");
                    for (int i = 0; i < parts.length; i++) {
                        Log.i("AQUI", parts[i] + "\n");
                        //contestar--

                        //-------Llamadas
                        if (parts[i].equalsIgnoreCase("Llama") || parts[i].equalsIgnoreCase("Llamar") ||
                                parts[i].equalsIgnoreCase("Contacta") || parts[i].equalsIgnoreCase("Contactar")
                                || parts[i].equalsIgnoreCase("Marca") || parts[i].equalsIgnoreCase("Marcar") ||
                                parts[i].equalsIgnoreCase("Háblale")
                                || parts[i].equalsIgnoreCase("Márcale")) {
                            RealizarLlamada(parts);
                            break;
                        }
                        //Alarmas-------------------
                        if (parts[i].equalsIgnoreCase("Pon") || parts[i].equalsIgnoreCase("Poner")
                                || parts[i].equalsIgnoreCase("Ponme") || parts[i].equals("Programa")) {
                            for (int j = 0; j < parts.length; j++) {
                                if (parts[j].equalsIgnoreCase("alarma")) {
                                    PonerAlarma(parts);
                                }
                            }
                            break;
                        }
                        if (parts[i].equalsIgnoreCase("Muéstrame") || parts[i].equalsIgnoreCase("Ver")
                                || parts[i].equalsIgnoreCase("Mostrar") || parts[i].equalsIgnoreCase("Dime")) {
                            for (int j = 0; j < parts.length; j++) {
                                if (parts[j].equalsIgnoreCase("alarma") || parts[j].equalsIgnoreCase("alarmas")) {
                                    VerAlarmas(parts);
                                }
                            }
                            break;
                        }
                        if (parts[i].equalsIgnoreCase("Elimina") || parts[i].equalsIgnoreCase("Quita")
                                || parts[i].equalsIgnoreCase("Quitar") || parts[i].equalsIgnoreCase("Eliminar")) {
                            for (int j = 0; j < parts.length; j++) {
                                if (parts[j].equalsIgnoreCase("alarma") || parts[j].equalsIgnoreCase("alarmas")) {
                                    EliminarAlarmas(parts);
                                }
                            }
                            break;
                        }
                        //---------Recordatorios
                        if (parts[i].equalsIgnoreCase("Recuerda") || parts[i].equalsIgnoreCase("recuérdame") || parts[i].equalsIgnoreCase("Recordar")) {
                            AñadirRecordatorio(parts);
                        }
                        //----------Mensaje
                        if (parts[i].equalsIgnoreCase("Envía") || parts[i].equalsIgnoreCase("enviar") || parts[i].equalsIgnoreCase("mandar") || parts[i].equalsIgnoreCase("mándale")
                                || parts[i].equalsIgnoreCase("envíale")) {
                            for (int j = 0; j < parts.length; j++) {
                                if (parts[j].equalsIgnoreCase("mensaje")) {
                                    EnviarMensaje("", parts);
                                }
                            }
                        }
                        if (parts[i].equalsIgnoreCase(palabraauxilio)) {
                            Auxilio();
                        }
                        if(parts[i].equalsIgnoreCase("toma")){
                            for(int j = 0;j<parts.length;j++){
                                if(parts[j].equalsIgnoreCase("nota")){
                                    TomarNota(parts);
                                    break;
                                }
                            }
                        }

                        if(parts[i].equalsIgnoreCase("leer")||parts[i].equalsIgnoreCase("léeme")||
                                parts[i].equalsIgnoreCase("dime")||parts[i].equalsIgnoreCase("ver")
                                ||parts[i].equalsIgnoreCase(
                                "muéstrame")){
                            for(int j = 0;j<parts.length;j++){
                                if(parts[j].equalsIgnoreCase("nota")||parts[j].equalsIgnoreCase("notas")){
                                    LeerNota(parts);
                                    break;
                                }
                            }
                        }

                        if(parts[i].equalsIgnoreCase("eliminar")||parts[i].equalsIgnoreCase("elimina")||
                                parts[i].equalsIgnoreCase("borrar")||parts[i].equalsIgnoreCase("borra")){
                            for(int j = 0;j<parts.length;j++){
                                if(parts[j].equalsIgnoreCase("nota")||parts[j].equalsIgnoreCase("notas")){
                                    BorrarNota(parts);
                                    break;
                                }
                            }
                        }
                        if(parts[i].equalsIgnoreCase("Leer")|| parts[i].equalsIgnoreCase("léeme")||
                                parts[i].equalsIgnoreCase("lee")){
                            for(int j=0;j<parts.length;j++){
                                if(parts[j].equalsIgnoreCase("Estado")|| parts[j].equalsIgnoreCase("Estados")){
                                    Leerestados();
                                    break;
                                }
                            }
                        }
                        if(parts[i].equalsIgnoreCase("Di")|| parts[i].equalsIgnoreCase("Mi")||parts[i].equalsIgnoreCase("Como")|| parts[i].equalsIgnoreCase("Cuál")){
                            for(int j=0;j<parts.length;j++){
                                if(parts[j].equalsIgnoreCase("Nombre")|| parts[j].equalsIgnoreCase("LLamó")){
                                    Nombre();
                                }
                            }

                        }
                        if(parts[i].equalsIgnoreCase("Agrega")|| parts[i].equalsIgnoreCase("pon")|| parts[i].equalsIgnoreCase("sube")){
                            for(int j=0;j<parts.length;j++){
                                if(parts[j].equalsIgnoreCase("Estado")){
                                    agregaestado(parts);
                                }
                            }
                            break;
                        }

                    }
                    //----Que puede hacer?
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

        btnMicro = view.findViewById(R.id.btnMicro);
        btnMicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                if (mp.isPlaying()) {
                    mp.stop();
                }

            }
        });




        final Handler handlerSpeech = new Handler();
        Timer timerSpeech = new Timer();
        TimerTask taskSpeech = new TimerTask() {
            @Override
            public void run() {
                handlerSpeech.post(new Runnable() {
                    public void run() {
                        try {
                            //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                });
            }
        };
        timerSpeech.schedule(taskSpeech, 0, 8000);

        return view;
    }

    private void agregaestado(String [] parts) {
        progressDialog.setMessage("Subiento estado");
        progressDialog.show();
        String nombre="";
        Datos_Usuario conex=new Datos_Usuario(getActivity(),"DBUsuario",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT Nombre FROM Usuario",null);
        try{
            if(cursor.moveToFirst()){
                nombre=cursor.getString(0);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "Error BD", Toast.LENGTH_SHORT).show();
        }
        String estado="";
        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equalsIgnoreCase("diciendo")||parts[i].equalsIgnoreCase("diga")){
                for (int j = i+1; j < parts.length; j++) {
                    estado+=parts[j]+" ";
                }
                String url="https://atencionaclientes19.000webhostapp.com/BasedeDatos/RegistroEstado.php?Estado="+estado+"&Nombre="+nombre;
                url=url.replace(" ","%20");
                jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        toSpeech.speak("Se ha subido el estado", TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getActivity(), "Se ha subido", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        toSpeech.speak("ha ocurrido un error al subir el estado intenta de nuevo", TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getActivity(), "ha ocurrido un error al subir el estado", Toast.LENGTH_SHORT).show();
                        Log.i("Estado", error.toString());
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        }

    }


    private void Nombre() {
        Datos_Usuario conex=new Datos_Usuario(getActivity(),"DBUsuario",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT Nombre FROM Usuario",null);
        try{
            if(cursor.moveToFirst()){
                String nombre=cursor.getString(0);
                toSpeech.speak("Tu nombre es "+nombre+" o eso me has dicho", TextToSpeech.QUEUE_FLUSH, null);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "Error BD", Toast.LENGTH_SHORT).show();
        }
    }

    private void Leerestados() {
        progressDialog.setMessage("Cargando Estados...");
        progressDialog.show();
        String url="https://atencionaclientes19.000webhostapp.com/BasedeDatos/ConsultarEstados2.php";
        url=url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray js=response.optJSONArray("Est");
               // JSONObject jsonObject=null;
                //jsonObject=js.getJSONObject())
                try {
                    MatrixCursor mc=new MatrixCursor(new String []{"Nombre", "Texto"});
                    for(int i=0;i<js.length();i++) {
                        JSONObject jsonObject = null;
                        jsonObject = js.getJSONObject(i);
                       mc.newRow().add("Nombre",jsonObject.optString("Nombre")).add("Estado",jsonObject.optString("Estado"));
while (mc.moveToNext()){
}
                        toSpeech.speak("De "+mc.getString(0)+"  "+mc.getString(1), TextToSpeech.QUEUE_FLUSH, null);
                        // mc.addRow(new Object[]{jsonObject.optString("Nombre"),jsonObject.optString("Estado")});


                    }

                }catch(Exception e){


                    }

/*try{
    MatrixCursor mc=new MatrixCursor(new String []{"Nombre", "Estado"});

 for(int i=0;i<js.length();i++){
      JSONObject jsonObject=null;
      jsonObject=js.getJSONObject(i);
      //toSpeech.speak("De "+jsonObject.optString("Nombre")+"  "+jsonObject.optString("Estado"), TextToSpeech.QUEUE_FLUSH, null);
mc.addRow(new Object[]{jsonObject.optString("Nombre"),jsonObject.optString("Estado")});
while (mc.moveToNext()){
    toSpeech.speak("De "+jsonObject.optString("Nombre")+"  "+jsonObject.optString("Estado"), TextToSpeech.QUEUE_FLUSH, null);

    }

}catch(Exception e){

}*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void iniciaractividad(){
        Intent inte=new Intent(getContext(), CameraActivity.class);
        startActivity(inte);
}
    private void ConsultarAuxilio() {
        Datos_Usuario conex=new Datos_Usuario(getActivity(), "DBUsuario",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        final Cursor cursor=db.rawQuery("SELECT Numero, Frase FROM Usuario",null);
        if(cursor.moveToFirst()){
            try{
                palabraauxilio=cursor.getString(1);
                numeroauxilio=cursor.getString(0);

            }catch (Exception e){
                Toast.makeText(getActivity(),"error", Toast.LENGTH_SHORT).show();
            }
        }
    //palabraauxilio="auxilio";
      //  numeroauxilio="7151213534";
    }

    private void Auxilio() {
    /*    String mess="Me encuentro en peligro, por favor lleguen lo antes posible a: "+"";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                Uri.parse("tel:"+numeroauxilio).toString(),
                null,
                mess, //mensaje
                null,
                null);
                */
        Intent i=new Intent(getActivity(),BuscarUbicacion.class);
        i.putExtra("numero",numeroauxilio);
        startActivity(i);
    }

    private void Funcionalidades() {
        toSpeech.speak("Te puedo ayudar en varias cosas, por ejemplo: \nPuedo ayudarte con tus alarmas.\n" +
                        "Puedo llamar a algún contacto.\nEnviar mensajes.\nAyudarte en caso de auxilio.\n" +
                        "Ser tus ojos.\nAyudarte a leer\n y más."
                , TextToSpeech.QUEUE_FLUSH, null);
        while(toSpeech.isSpeaking()){
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void AñadirRecordatorio(String[] parts) {
        DataBase cnx = new DataBase(getActivity(), "DataBase", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        String dia="",hora="",mañanatarde="",nombre="",recorda="";
        String[] Atemporal={"hoy","mañana","diario"};
        for (int i = 0; i < parts.length; i++) {
            //----dia atemporal
            for (int j = 0; j < Atemporal.length; j++) {
                if (parts[i].equalsIgnoreCase(Atemporal[j])) {
                    if (parts[i].equalsIgnoreCase("mañana") && parts[i - 1].equalsIgnoreCase("la")) {
                        continue;
                    }
                    dia = Atemporal[j];
                }
            }
            //----
            if (parts[i].equalsIgnoreCase("horas")) {
                hora = parts[i - 1];
            }
            if (parts[i].equalsIgnoreCase("las") || ((parts[i].equalsIgnoreCase("la")) && !parts[i + 1].equalsIgnoreCase("mañana") && !parts[i + 1].equalsIgnoreCase("tarde")&& !parts[i + 1].equalsIgnoreCase("noche"))) {
                hora = parts[i + 1];
            }

            if (parts[i].equalsIgnoreCase("llamada") || parts[i].equalsIgnoreCase("llamar") ||
                    parts[i].equalsIgnoreCase("llamará")) {

                nombre = parts[i + 1];
            }
            if (parts[i].equalsIgnoreCase("am")) {
                mañanatarde = "am";
            }
            if (parts[i].equalsIgnoreCase("mañana") && parts[i - 1].equalsIgnoreCase("la")) {
                mañanatarde = "am";
            }
            if (parts[i].equalsIgnoreCase("tarde") || parts[i].equalsIgnoreCase("pm") || parts[i].equalsIgnoreCase("noche")) {
                mañanatarde = "pm";
            }

        }
        Log.i("AQUI", "repeticion: " + dia + "\n hora: " + hora + "\n nombre: " + nombre);

        //
        String fecha = dia, diano = "";
        Calendar calendar = Calendar.getInstance();
        if (dia.equalsIgnoreCase("hoy")) {
            diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        } else if (dia.equalsIgnoreCase("mañana")) {
            diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK) - 1);
            int a = Integer.parseInt(diano) + 1;
            if (a == 7) {
                a = 0;
            }
            diano = a + "";
        } else {
            for (int i = 0; i < dias.length; i++) {
                if (dias[i].equalsIgnoreCase(dia)) {
                    diano = i + "";
                    break;
                }
            }
        }

        if(diano.equalsIgnoreCase("")){diano="diario";}
        Log.i("AQUI","DIA "+diano);

        if(isNumber(hora)){
            hora+=":00";
        }
        if(mañanatarde.equalsIgnoreCase("")){
            String b="";
            mañanatarde="am";
            if (hora.length() == 5) {
                b = hora.substring(2);
                hora = hora.substring(0, 2);
                if(!isNumber(hora)){
                    toSpeech.speak("Proporciona una hora válida, inténtalo de nuevo", TextToSpeech.QUEUE_FLUSH, null);
                    while(toSpeech.isSpeaking()){
                    }
                    return;
                }
                int h = Integer.parseInt(hora);
                if(h>12){
                    h -=12;
                    mañanatarde="pm";
                }
                hora=h+b;
                Log.i("AQUI","Hora parseada: "+ hora);
            }
        }

        for(int i = 0;i<parts.length;i++){
            if(parts[i].equalsIgnoreCase("recuérdame")
                    ||parts[i].equalsIgnoreCase("recuerdar")){
                for(int j=i;j<parts.length;j++){
                    nombre+=parts[j];
                    if(parts[i].equalsIgnoreCase("el")||parts[i].equalsIgnoreCase("mañana")
                            ||parts[i].equalsIgnoreCase("el")){
                        break;
                    }
                }
            }
        }

        Log.i("AQUI.-.",nombre+"\ndia "+dia+"\nhora: "+hora);
        if(nombre.equalsIgnoreCase("")){
            toSpeech.speak("Debes especificar un nombre, inténtalo de nuevo", TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
            return;
        }
        if(true){
            return;
        }
        ContentValues datos1 = new ContentValues();
        datos1.put("nombre", nombre);
        datos1.put("fecha", diano);
        if(dia.equalsIgnoreCase("hoy")||dia.equalsIgnoreCase("mañana")) {
            datos1.put("repetir", "no");
        }else{
            datos1.put("repetir", "si");
        }
        datos1.put("hora", hora+" "+mañanatarde);

        Cursor c = db.rawQuery(
                "SELECT * FROM alarmas",null,null
        );

        while (c.moveToNext()){
            Log.i("AQUI","Alarma: "+c.getString(0)+"\n "+c.getString(1)
                    +"\n "+c.getString(2)+"\n "+c.getString(3));
            if(c.getString(3).equalsIgnoreCase(nombre)){
                toSpeech.speak("Ya hay una alarma registrada con ese nombre, intenta otra vez", TextToSpeech.QUEUE_FLUSH, null);
                while(toSpeech.isSpeaking()){
                }
                return;
            }
        }
        db.insert("Alarmas", null, datos1);

//
        String tex="Lista tu alarma llamada: "+nombre+", que sonará: "+dia+" a las "+hora+" horas";
        if(!mañanatarde.equalsIgnoreCase("")){
            String mañana="mañana";
            if(mañanatarde.equalsIgnoreCase("pm")){
                mañana="tarde";
            }
            tex="Lista tu alarma llamada: "+nombre+", que sonará: "+dia+" a las "+hora+" de la "+mañana;
        }
        toSpeech.speak(tex, TextToSpeech.QUEUE_FLUSH, null);
        while(toSpeech.isSpeaking()){
        }
    }
    private void BorrarNota(String[] parts) {
        Notas cnx = new Notas(getActivity(), "TomarNota", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        String nombre="";
        boolean BorrarTodas=false;
        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equalsIgnoreCase("todas")){
                BorrarTodas=true;
            }
            if(parts[i].equalsIgnoreCase("llamada")){
                for (int j = i+1; j < parts.length; j++) {
                    nombre+=parts[j]+" ";
                    BorrarTodas=false;
                }
            }
        }
        int borrados=0;
        if(BorrarTodas){
            borrados = db.delete("Notas","",null);
        }else{
            borrados = db.delete("Notas"," titulo = '"+nombre+"'",null);
        }

        if(borrados!=0){
            toSpeech.speak("Eliminado."
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }else{
            toSpeech.speak("No he eliminado nada."
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }


    }
    private void LeerNota(String[] parts) {
        Notas cnx = new Notas(getActivity(), "TomarNota", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        String nombre="";
        boolean BPNombre=false;
        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equalsIgnoreCase("llamada")||parts[i].equalsIgnoreCase("llama")){
                for (int j = i+1; j < parts.length; j++) {
                    nombre+=parts[j]+" ";
                }
                BPNombre=true;
            }
            if(parts[i].equalsIgnoreCase("todas")||parts[i].equalsIgnoreCase("totalidad")){
                BPNombre=false;
            }
        }

        Cursor c = null;
        if(BPNombre){
            c = db.rawQuery("Select * from Notas WHERE titulo = '"+nombre+"'",null);
        }else{
            c = db.rawQuery("Select * from Notas",null);
        }
        String pal="";
        int time = 0,entrar=0;
        while(c.moveToNext()){
            pal="Título: "+c.getString(0)+", Contenido: "+c.getString(1);

            toSpeech.speak(pal
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }

            String [] pal2=pal.split(" ");

            time = (int) ((pal2.length/2.5)*1000)+1000;
            Log.i("AQUI",time+" milisecond");
            Log.i("AQUI-",pal);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            entrar=1;
        }
        if(entrar!=0) {
            toSpeech.speak("Eso es todo."
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }else{
            toSpeech.speak("No he encontrado ninguna nota"
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }

        }
    }
    private void TomarNota(String[] parts) {

        Notas cnx = new Notas(getActivity(), "TomarNota", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();

        String cuerpo="",Titulo="";
        boolean salir=false;
        for (int i = 0; i < parts.length; i++) {

            if(parts[i].equalsIgnoreCase("título")){
                for (int j = i+1; j < parts.length; j++) {


                    if(parts[j].equalsIgnoreCase("inicio")||parts[j].equalsIgnoreCase("cuerpo")
                            ||parts[j].equalsIgnoreCase("inicia")){

                        for (int k = j+1; k < parts.length; k++) {


                            if(parts[k].equalsIgnoreCase("fin")||parts[k].equalsIgnoreCase("finaliza")
                                    ||parts[k].equalsIgnoreCase("punto")){
                                salir=true;
                                break;
                            }
                            cuerpo+=parts[k]+" ";
                        }
                    }//for inicio
                    if(salir){
                        break;
                    }
                    Titulo+=parts[j]+" ";
                }//for titulo

            }//titulo
        }//for grande

        ContentValues cv = new ContentValues();
        cv.put("titulo",Titulo);
        cv.put("cuerpo",cuerpo);
        db.insert("Notas",null,cv);
        if (!Titulo.isEmpty() && !cuerpo.isEmpty()) {
            toSpeech.speak("He tomado tu nota."
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }else{
            toSpeech.speak("No te he entendido."
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }

        }
        Log.i("AQUINOTA","Mess: "+cuerpo+"\ntitulo: "+Titulo);
        Cursor c = db.rawQuery("Select * from Notas",null);
        while(c.moveToNext()){
            Log.i("AQUINOTA","Mess: "+c.getString(1)+"\ntitulo: "+c.getString(0));
        }


    }
    private void EliminarAlarmas(String[] parts) {
        DataBase cnx = new DataBase(getActivity(), "DataBase", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        String sql ="",nombre="";
        boolean sin=true;
        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equalsIgnoreCase("llamada")||parts[i].equalsIgnoreCase("llamaba") || parts[i].equalsIgnoreCase("nombrada") || parts[i].equalsIgnoreCase(" ")){
                nombre=parts[i+1];
            }
            for(int j=0;j<dias.length;j++) {
                if (parts[i].equalsIgnoreCase(dias[j])) {
                    sql="fecha = '"+j+"'";
                    sin=false;
                }
            }
        }
        if(sin) {
            sql = "nombre = '" + nombre + "'";
            if (nombre.equalsIgnoreCase("")) {
                sql = "";
            }
        }
        int a=db.delete(
                "alarmas",sql,null
        );
        if(a!=0) {
            if(sql.equalsIgnoreCase("")||!sin){
                toSpeech.speak("He eliminado las alarmas"
                        , TextToSpeech.QUEUE_FLUSH, null);
                while(toSpeech.isSpeaking()){
                }
            }else {
                toSpeech.speak("He eliminado la alarma llamada: " + nombre
                        , TextToSpeech.QUEUE_FLUSH, null);
                while(toSpeech.isSpeaking()){
                }
            }
        }else{
            toSpeech.speak("No he eliminado nada porque no existe." + nombre
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void VerAlarmas(String[] parts) {
        DataBase cnx = new DataBase(getActivity(), "DataBase", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        String mensajeprevio="",sql="SELECT * FROM alarmas";
        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equalsIgnoreCase("todas")){
                sql="SELECT * FROM alarmas";
                mensajeprevio="Todas las alarmas que tienes son: ";
            }else
            if(parts[i].equalsIgnoreCase("mañana")){
                String diano="";
                Calendar calendar = Calendar.getInstance();
                diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)-1);
                int a = Integer.parseInt(diano)+1;
                sql="SELECT * FROM alarmas WHERE fecha= 'diario' or fecha= '"+a+"'";
                mensajeprevio="Todas las alarmas que tienes para mañana son: ";
                break;
            }else
            if(parts[i].equalsIgnoreCase("hoy")){
                String diano="";
                Calendar calendar = Calendar.getInstance();
                diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)-1);
                sql="SELECT * FROM alarmas WHERE fecha= 'diario' or fecha= '"+diano+"'";
                mensajeprevio="Todas las alarmas que tienes para hoy son: ";
                break;

            }
            else{
                for(int j=0;j<dias.length;j++){
                    if(parts[i].equalsIgnoreCase(dias[j])){
                        sql="SELECT * FROM alarmas WHERE fecha= 'diario' or fecha= '"+j+"'";
                        mensajeprevio="Todas las alarmas que tienes para el "+dias[j]+" son: ";
                        break;
                    }
                }
            }
        }
        Cursor c = db.rawQuery(
                sql,null,null
        );
        toSpeech.speak(mensajeprevio
                , TextToSpeech.QUEUE_FLUSH, null);
        while(toSpeech.isSpeaking()){
        }
        boolean sino=false;
        String dia;
        while (c.moveToNext()){
            sino=true;
            Log.i("AQUI--","Alarma: "+c.getString(0)+"\n "+c.getString(1)
                    +"\n "+c.getString(2)+"\n "+c.getString(3));
            if(isNumber(c.getString(1))){
                dia=dias[Integer.parseInt(c.getString(1))];
            }else{
                dia=c.getString(1);
            }

            toSpeech.speak("\nLa alarma llamada: "+c.getString(3)+" sonará: "+dia+" a las: "+
                            c.getString(0)
                    , TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
            try {
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(sino==false){
            toSpeech.speak("No encontré ninguna alarma.",TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }else{
            toSpeech.speak("Esas son todas.",TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void PonerAlarma(String[] parts) {

        String dia="",hora="",mañanatarde="",nombre="";
        String[] Atemporal={"hoy","mañana","diario"};
        try {
            for (int i = 0; i < parts.length; i++) {
                //----dia
                for (int j = 0; j < dias.length; j++) {
                    if (parts[i].equalsIgnoreCase(dias[j])) {
                        dia = dias[j];
                    }
                }
                //----dia atemporal
                for (int j = 0; j < Atemporal.length; j++) {
                    if (parts[i].equalsIgnoreCase(Atemporal[j])) {
                        if (parts[i].equalsIgnoreCase("mañana") && parts[i - 1].equalsIgnoreCase("la")) {
                            continue;
                        }
                        dia = Atemporal[j];
                    }
                }
                //----
                if (parts[i].equalsIgnoreCase("horas")) {
                    hora = parts[i - 1];
                }
                if (parts[i].equalsIgnoreCase("las") || ((parts[i].equalsIgnoreCase("la")) && !parts[i + 1].equalsIgnoreCase("mañana") && !parts[i + 1].equalsIgnoreCase("tarde")&& !parts[i + 1].equalsIgnoreCase("noche"))) {
                    hora = parts[i + 1];
                }

                if (parts[i].equalsIgnoreCase("llamada") || parts[i].equalsIgnoreCase("llamar") ||
                        parts[i].equalsIgnoreCase("llamará")) {

                    nombre = parts[i + 1];
                }
                if (parts[i].equalsIgnoreCase("am")) {
                    mañanatarde = "am";
                }
                if (parts[i].equalsIgnoreCase("mañana") && parts[i - 1].equalsIgnoreCase("la")) {
                    mañanatarde = "am";
                }
                if (parts[i].equalsIgnoreCase("tarde") || parts[i].equalsIgnoreCase("pm") || parts[i].equalsIgnoreCase("noche")) {
                    mañanatarde = "pm";
                }

            }
            Log.i("AQUI", "repeticion: " + dia + "\n hora: " + hora + "\n nombre: " + nombre);

            //
            String fecha = dia, diano = "";
            Calendar calendar = Calendar.getInstance();
            if (dia.equalsIgnoreCase("hoy")) {
                diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK) - 1);
            } else if (dia.equalsIgnoreCase("mañana")) {
                diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK) - 1);
                int a = Integer.parseInt(diano) + 1;
                if (a == 7) {
                    a = 0;
                }
                diano = a + "";
            } else {
                for (int i = 0; i < dias.length; i++) {
                    if (dias[i].equalsIgnoreCase(dia)) {
                        diano = i + "";
                        break;
                    }
                }
            }

            if(diano.equalsIgnoreCase("")){diano="diario";}
            Log.i("AQUI","DIA "+diano);

            if(isNumber(hora)){
                hora+=":00";
            }
            if(mañanatarde.equalsIgnoreCase("")){
                String b="";
                mañanatarde="am";
                if (hora.length() == 5) {
                    b = hora.substring(2);
                    hora = hora.substring(0, 2);
                    if(!isNumber(hora)){
                        toSpeech.speak("Proporciona una hora válida, inténtalo de nuevo", TextToSpeech.QUEUE_FLUSH, null);
                        while(toSpeech.isSpeaking()){
                        }
                        return;
                    }
                    int h = Integer.parseInt(hora);
                    if(h>12){
                        h -=12;
                        mañanatarde="pm";
                    }
                    hora=h+b;
                    Log.i("AQUI","Hora parseada: "+ hora);
                }
            }
            if(nombre.equalsIgnoreCase("")){
                toSpeech.speak("Debes especificar un nombre, inténtalo de nuevo", TextToSpeech.QUEUE_FLUSH, null);
                while(toSpeech.isSpeaking()){
                }
                return;
            }
            DataBase cnx = new DataBase(getActivity(), "DataBase", null, 1);
            SQLiteDatabase db = cnx.getWritableDatabase();
            ContentValues datos1 = new ContentValues();
            datos1.put("nombre", nombre);
            datos1.put("fecha", diano);
            if(dia.equalsIgnoreCase("hoy")||dia.equalsIgnoreCase("mañana")) {
                datos1.put("repetir", "no");
            }else{
                datos1.put("repetir", "si");
            }
            datos1.put("hora", hora+" "+mañanatarde);


            Cursor c = db.rawQuery(
                    "SELECT * FROM alarmas",null,null
            );

            while (c.moveToNext()){
                Log.i("AQUI","Alarma: "+c.getString(0)+"\n "+c.getString(1)
                        +"\n "+c.getString(2)+"\n "+c.getString(3));
                if(c.getString(3).equalsIgnoreCase(nombre)){
                    toSpeech.speak("Ya hay una alarma registrada con ese nombre, intenta otra vez", TextToSpeech.QUEUE_FLUSH, null);
                    while(toSpeech.isSpeaking()){
                    }
                    return;
                }
            }
            db.insert("Alarmas", null, datos1);


            String tex="Lista tu alarma llamada: "+nombre+", que sonará: "+dia+" a las "+hora+" horas";
            if(!mañanatarde.equalsIgnoreCase("")){
                String mañana="mañana";
                if(mañanatarde.equalsIgnoreCase("pm")){
                    mañana="tarde";
                }
                tex="Lista tu alarma llamada: "+nombre+", que sonará: "+dia+" a las "+hora+" de la "+mañana;
            }
            toSpeech.speak(tex, TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }catch (Exception e){
            toSpeech.speak("Algo ha salido mal, vuelve a intentarlo", TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
            e.printStackTrace();
        }
    }

    boolean isNumber(String a){
        boolean sino = true;
        try{
            int b = Integer.parseInt(a);
        }catch (NumberFormatException e){
            sino=false;
        }
        return sino;
    }

    void EnviarMensaje(String numero,String [] parts){
        String mess="";
        boolean si = false;
        String nomb="";
        for(int i = 0;i<parts.length;i++){
            if(parts[i].equalsIgnoreCase("dile")){
                for(int j=i+1;j<parts.length;j++){
                    mess+=parts[j]+" ";
                }
                break;
            }
            if(parts[i].equalsIgnoreCase("a")&&si==false){
                for(int j = i+1;j<parts.length;j++){
                    if(parts[j].equalsIgnoreCase("y")||
                            parts[j].equalsIgnoreCase("dile")){
                        break;
                    }
                    nomb+=parts[j]+" ";
                }
                if(nomb.charAt(nomb.length()-1)==' '){
                    nomb=nomb.substring(0,nomb.length()-1);
                }
                numero=ObtenerDatos(nomb);
                si=true;
            }
        }
        if(numero.equalsIgnoreCase("")||numero==null){
            toSpeech.speak("No encontré el contacto llamado "+ nomb+ " en tu agenda", TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
            return;
        }
        Log.i("AQUIMSj","numero: "+numero+"\nmensaje : "+mess+"\nNombre: "+nomb);

        //
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero.toString(), null, mess,null,null);
        toSpeech.speak("He envíado tu mensaje a: "+ nomb+ ".", TextToSpeech.QUEUE_FLUSH, null);
        while(toSpeech.isSpeaking()){
        }
        Toast.makeText(getActivity(), "Mensaje Enviado", Toast.LENGTH_LONG).show();


    }

    private void RealizarLlamada(String[] parts) {
        String nom ="";
        for(int j = 2;j<parts.length;j++){
            nom+=parts[j];
        }

        String numero = ObtenerDatos(nom);
        if(numero.equalsIgnoreCase("")||numero==null){
            toSpeech.speak("No encontré el contacto llamado "+ nom+ " en tu agenda", TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
            return;
        }
        Log.i("AQUI",numero);
        Log.i("AQUI",nom);

        Toast.makeText(getActivity(), "Este es su numero "+ numero, Toast.LENGTH_SHORT).show();
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getActivity(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
        } else {
            toSpeech.speak("Llamando a "+nom, TextToSpeech.QUEUE_FLUSH, null);
            while(toSpeech.isSpeaking()){
            }
        }
        Intent llamar = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+numero));
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED){
            return;

        }else{
            startActivity(llamar);
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);

            }
        }
    }

    public String ObtenerDatos(String nombre) {
        String numero="";
        String[] projeccion = new String[]{ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";
        Cursor c = getActivity().getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projeccion,
                selectionClause,
                null,
                sortOrder);

        while (c.moveToNext()) {
            if(c.getString(1).equalsIgnoreCase(nombre)){
                numero =  c.getString(2);
                Log.i("AQUI","Entré");
                Log.i("AQUI",numero);
            }
            //    tv.append("Identificador: " + c.getString(0) + " Nombre: " + c.getString(1) +
            // " Número: " + c.getString(2) + " Tipo: " + c.getString(3) + "\n");

        }
        c.close();
        return  numero;
    }

    //Metodos on pause y on resume
    /*@Override
    *protected void onPause() {
        super.onPause();
        //    t = new time();
        //      t.execute();
        Log.i("AQUI","En pausa");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  t = new time();
        // t.cancel(true);
        Log.i("AQUI","En resumido");
    }*/
    public void hilo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public  void hiloAlarma(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void ListenerSegundoPlano(){
        t = new time();
        t.execute();

    }
    public  void ListenerAlarma(){
        alarms a = new alarms();
        a.execute();
    }

    public String ParsearHoraAF24(String hora){
        String h="",mins="";
        if(hora.contains("am")){
            h=hora.replace("am","");
        }else {
            hora = hora.replace("pm", "");
            hora = hora.replace(" ", "");
            for (int i = 0; i < hora.length(); i++) {
                if (hora.charAt(i) == ':') {
                    mins = hora.substring(i);
                    hora = hora.substring(0, i);
                }
            }
            int a = Integer.parseInt(hora);
            a+= 12;
            h=a+mins;
        }
        h = h.replace(" ", "");
        return h;
    }

    private class time extends AsyncTask<Void,Integer,Boolean> {
        TextToSpeech toSpeech;
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ListenerSegundoPlano();
            try {
                toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            result = toSpeech.setLanguage(Locale.getDefault());
                        } else {
                            Toast.makeText(getActivity(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            //
            final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());


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
                    //   Toast.makeText(getApplicationContext(),"onrmschanged",1).show();
                    //   Log.i("AQUI","changed"+v);
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
                @Override
                public void onResults(Bundle bundle) {
                    //getting all the matches
                    ArrayList<String> matches = bundle
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    //displaying the first match
                    if (matches != null) {

                        Log.i("AQUI", "results");
                        Log.i("AQUI", input);
                        Toast.makeText(getActivity(), ""+matches.get(0), Toast.LENGTH_SHORT).show();
                        String input=matches.get(0);
                        String[] parts = input.split(" ");
                        for (int i = 0; i < parts.length; i++) {
                            Log.i("AQUI", parts[i] + "\n");
                            if (parts[i].equalsIgnoreCase("Hey") || parts[i].equalsIgnoreCase("Oye")) {
                                Toast.makeText(getActivity(), "Te escucho, ¿que necesitas?", Toast.LENGTH_SHORT).show();

                                //startActivity(new Intent(getApplicationContext(),Asistente.class));
                                mSpeechRecognizer.stopListening();
                                //
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    //    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                } else {

                                    toSpeech.speak("Te escucho, ¿que necesitas?", TextToSpeech.QUEUE_FLUSH, null);
                                    while(toSpeech.isSpeaking()){
                                    }


                                }
                                //
                                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                            }

                        }

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
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
//
            Log.i("AQUI","poste");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i = 1;i<20;i++){
                hilo();
            }
            return true;
        }
    }

    private class alarms extends  AsyncTask<Void,Integer,Boolean>{
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            String diano,sql;
            Calendar calendar = Calendar.getInstance();
            int hora, minutos;
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);
            String horactual = String.valueOf(hora + ":" + minutos);
//
            if(context!=null) {
                DataBase cnx = new DataBase(getContext(), "DataBase", null, 1);
                SQLiteDatabase db2 = cnx.getReadableDatabase();
                diano = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK) - 1);
                sql = "SELECT * FROM alarmas WHERE fecha= 'diario' or fecha= '" + diano + "'";
                Cursor c = db2.rawQuery(
                        sql, null, null);
                if (c.moveToFirst()) {
                    while (c.moveToNext()) {
                        String h = ParsearHoraAF24(c.getString(0));
                        Log.i("AQUIentre", "hora: " + h + "\nFecha: " + c.getString(1) + "\nHora actual: " + horactual);
                        if (h.equalsIgnoreCase(horactual)) {

                            mp.start();
                            Toast.makeText(getActivity(), "ALARMA!!!", Toast.LENGTH_SHORT).show();
                            if (c.getString(2).equalsIgnoreCase("no")) {
                                db2.delete("alarmas", "nombre = '" + c.getString(3) + "'", null);
                            }
                        }
                    }
                }
            }
            //
            //   ListenerAlarma();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //    hiloAlarma();
            return true;
        }
    }
}
