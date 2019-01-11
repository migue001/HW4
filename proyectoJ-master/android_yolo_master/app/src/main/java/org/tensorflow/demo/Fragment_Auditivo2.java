package org.tensorflow.demo;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import org.tensorflow.demo.Clases.VariablesYDatos;
import org.tensorflow.demo.Clases.Elemen;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import org.tensorflow.demo.SQLite.Notas;
import org.tensorflow.demo.Adaptadores.AdaptadorAssistent;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Auditivo2 extends Fragment {

    RecyclerView rv;
    ArrayList<Elemen> lista;
    SearchView sv;
    String[] dias = {"domingo","lunes","martes","miércoles","jueves","viernes","sábado"};
    int result;
    String NombreAsistente="Asistente";


    String NombreUsuario="";

    public Fragment_Auditivo2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment__auditivo2, container, false);
        Datos_Usuario conex=new Datos_Usuario(getActivity(),"DBUsuario",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT Nombre FROM Usuario",null);
        try{
            if(cursor.moveToFirst()){
                NombreUsuario=cursor.getString(0);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
        lista = new ArrayList<>();
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        //LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //y esto en el xml
        //android:scrollbars="horizontal"
        rv.setLayoutManager(llm);
        sv = view.findViewById(R.id.searchview);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Elemen mess=new Elemen();
                mess.setLado(true);
                mess.setNombre(NombreUsuario);
                mess.setMensaje(query);

                lista.add(mess);
                AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                rv.setAdapter(adapter);

                verificar(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);
        mess.setMensaje("¡Hola! ¿en qué puedo ayudarte?");
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);
        return view;
    }

    void verificar(String input){

        if((input.contains("qué")&&input.contains("puedes")||input.contains("hacer"))
                ||(input.contains("qué")&&input.contains("puedes")||input.contains("ayudar"))
                ||(input.contains("cómo")&&input.contains("puedes")&&input.contains("ayudar"))
                ||(input.contains("qué")&&input.contains("haces")||input.contains("ayudas"))
                ||(input.contains("cuáles")&&input.contains("funcionalidades")&&input.contains("labores"))) {
            Funcionalidades();
            return;
        }

        if(  (input.contains("dime")&&input.contains("dato")||input.contains("ceguera"))
                ||(input.contains("dime")&&input.contains("dato")||input.contains("visual"))
                ||((input.contains("cuentame")||input.contains("cuéntame"))||
                input.contains("visual"))
                ||((input.contains("cuentame")||input.contains("cuéntame"))||input.contains("ceguera"))) {
            Datos();
            return;
        }

        String[] parts = input.split(" ");
        for (int i = 0; i < parts.length; i++) {
            Log.i("AQUI", parts[i] + "\n");
            //contestar--
            if (parts[i].equalsIgnoreCase("Hey") || parts[i].equalsIgnoreCase("Oye")) {
                Toast.makeText(getActivity(), "Te escucho, ¿que necesitas?", Toast.LENGTH_SHORT).show();
                Elemen mess=new Elemen();
                mess.setLado(false);
                mess.setNombre(NombreAsistente);
                mess.setMensaje("¿Qué necesitas?");
                lista.add(mess);
                AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                rv.setAdapter(adapter);
            }
            //-------Llamadas
            if(parts[i].equalsIgnoreCase("Llama")||parts[i].equalsIgnoreCase("Llamar")||
                    parts[i].equalsIgnoreCase("Contacta")||parts[i].equalsIgnoreCase("Contactar")
                    ||parts[i].equalsIgnoreCase("Marca")||parts[i].equalsIgnoreCase("Marcar")||
                    parts[i].equalsIgnoreCase("Háblale")
                    ||parts[i].equalsIgnoreCase("Márcale")) {
                RealizarLlamada(parts);
                break;
            }
            //Alarmas-------------------
            if(parts[i].equalsIgnoreCase("Pon")||parts[i].equalsIgnoreCase("Poner")
                    ||parts[i].equalsIgnoreCase("Ponme")) {
                for (int j = 0; j < parts.length; j++) {
                    if(parts[j].equalsIgnoreCase("alarma")){
                        PonerAlarma(parts);
                        break;
                    }
                }

            }
            if(parts[i].equalsIgnoreCase("Ir")||parts[i].equalsIgnoreCase("Ve")||parts[i].equalsIgnoreCase("Entra")){
                for(int j=0;j<parts.length;j++){
                    if(parts[j].equalsIgnoreCase("Traductor")){
                        Intent intent=new Intent(getActivity(),Traductor.class);
                        startActivity(intent);
                    }
                }

            }
            if(parts[i].equalsIgnoreCase("Muéstrame")||parts[i].equalsIgnoreCase("Ver")
                    ||parts[i].equalsIgnoreCase("Mostrar")||parts[i].equalsIgnoreCase("Dime")) {
                for (int j = 0; j < parts.length; j++) {
                    if(parts[j].equalsIgnoreCase("alarma")||parts[j].equalsIgnoreCase("alarmas")){
                        VerAlarmas(parts);
                        break;
                    }
                }

            }
            if(parts[i].equalsIgnoreCase("Elimina")||parts[i].equalsIgnoreCase("Quita")
                    ||parts[i].equalsIgnoreCase("Quitar")||parts[i].equalsIgnoreCase("Eliminar")){
                for (int j = 0; j < parts.length; j++) {
                    if(parts[j].equalsIgnoreCase("alarma")||parts[j].equalsIgnoreCase("alarmas")){
                        EliminarAlarmas(parts);
                        break;
                    }
                }

            }
            //---------Recordatorios
            if(parts[i].equalsIgnoreCase("Recuerda")||parts[i].equalsIgnoreCase("recuérdame")||parts[i].equalsIgnoreCase("Recordar")){
                AñadirRecordatorio(parts);
            }
            //----------Mensaje
            if(parts[i].equalsIgnoreCase("Envía")||parts[i].equalsIgnoreCase("enviar")||parts[i].equalsIgnoreCase("mandar")||parts[i].equalsIgnoreCase("mándale")
                    ||parts[i].equalsIgnoreCase("envíale")){
                for(int j = 0;j<parts.length;j++){
                    if(parts[j].equalsIgnoreCase("mensaje")){
                        EnviarMensaje("",parts);
                        break;
                    }
                }
            }
            //----auxilio

            //-----Tomar nota

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
        }
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);
        mess.setMensaje("No entendiendo lo que me tratas de decir.");
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);

    }

    private void Datos() {
        Random aleatorio = new Random();
// Producir nuevo int aleatorio entre 0 y 99
        int intAletorio = aleatorio.nextInt(VariablesYDatos.cifras.length);
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);
        mess.setMensaje(VariablesYDatos.cifras[intAletorio]);
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);

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
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);


        if(borrados!=0){
            mess.setMensaje("Eliminado");
        }else{
            mess.setMensaje("No he eliminado nada.");
        }
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);

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
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);

        while(c.moveToNext()){
            pal="Título: "+c.getString(0)+"\nContenido: "+c.getString(1)+"\n";

            mess.setMensaje(pal);
            lista.add(mess);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
            entrar=1;
        }
        if(entrar==0) {
            mess.setMensaje("No he encontrado ninguna nota.");
        }
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);
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
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);


        db.insert("Notas",null,cv);
        if (!Titulo.isEmpty() && !cuerpo.isEmpty()) {
            mess.setMensaje("He tomado tu nota.");

        }else{
            mess.setMensaje("No he entendido.");
        }

        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);

        Log.i("AQUINOTA","Mess: "+cuerpo+"\ntitulo: "+Titulo);
        Cursor c = db.rawQuery("Select * from Notas",null);
        while(c.moveToNext()){
            Log.i("AQUINOTA","Mess: "+c.getString(1)+"\ntitulo: "+c.getString(0));
        }


    }//metodo

    private void Funcionalidades() {

        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);
        mess.setMensaje("Te puedo ayudar en varias cosas, por ejemplo:\nPuedo ayudarte con tus alarmas.\n" +
                "Puedo llamar a algún contacto.\nEnviar mensajes.\n y más.");
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);
    }

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

                    Elemen mess=new Elemen();
                    mess.setLado(false);
                    mess.setNombre(NombreAsistente);
                    mess.setMensaje("Proporciona una hora válida, inténtalo de nuevo");
                    lista.add(mess);
                    AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                    rv.setAdapter(adapter);
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
            Elemen mess=new Elemen();
            mess.setLado(false);
            mess.setNombre(NombreAsistente);
            mess.setMensaje("Debes especificar un nombre para la alarma.");
            lista.add(mess);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
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
                Elemen mess=new Elemen();
                mess.setLado(false);
                mess.setNombre(NombreAsistente);
                mess.setMensaje("Ya hay una alarma registrada con ese nombre");
                lista.add(mess);
                AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                rv.setAdapter(adapter);
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
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);
        mess.setMensaje(tex);
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);
    }

    private void EliminarAlarmas(String[] parts) {
        DataBase cnx = new DataBase(getActivity(), "DataBase", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        String sql ="",nombre="";
        boolean sin=true;
        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equalsIgnoreCase("llamada")||parts[i].equalsIgnoreCase("llamaba")){
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
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);


        if(a!=0) {
            if(sql.equalsIgnoreCase("")||!sin){
                mess.setMensaje("He eliminado las alarmas");
            }else {
                mess.setMensaje("He eliminado la alarma llamada: " + nombre);
            }
        }else{
            mess.setMensaje("No he eliminado nada porque no existe." + nombre);
        }
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);
    }

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
        Elemen mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);
        mess.setMensaje(mensajeprevio);
        lista.add(mess);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);

        boolean sino=false;
        String dia;
        mess=new Elemen();
        mess.setLado(false);
        mess.setNombre(NombreAsistente);

        rv.setAdapter(adapter);
        while (c.moveToNext()){
            sino=true;
            Log.i("AQUI--","Alarma: "+c.getString(0)+"\n "+c.getString(1)
                    +"\n "+c.getString(2)+"\n "+c.getString(3));
            if(isNumber(c.getString(1))){
                dia=dias[Integer.parseInt(c.getString(1))];
            }else{
                dia=c.getString(1);
            }

            mess.setMensaje("La alarma llamada: "+c.getString(3)+" sonará: "+dia+" a las: "+
                    c.getString(0)+"\n");
        }



        if(sino==false){

            mess.setMensaje("No encontré ninguna alarma.");
        }

        lista.add(mess);
        adapter=new AdaptadorAssistent(lista);

    }

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
                        Elemen mess=new Elemen();
                        mess.setLado(false);
                        mess.setNombre(NombreAsistente);
                        mess.setMensaje("Proporciona una hora válida.");
                        lista.add(mess);
                        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                        rv.setAdapter(adapter);
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
                Elemen mess=new Elemen();
                mess.setLado(false);
                mess.setNombre(NombreAsistente);
                mess.setMensaje("Debes especificar un nombre, inténtalo de nuevo.");
                lista.add(mess);
                AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                rv.setAdapter(adapter);
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
                    Elemen mess=new Elemen();
                    mess.setLado(false);
                    mess.setNombre(NombreAsistente);
                    mess.setMensaje("Ya hay una alarma registrada con ese nombre.");
                    lista.add(mess);
                    AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
                    rv.setAdapter(adapter);
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
            Elemen mess=new Elemen();
            mess.setLado(false);
            mess.setNombre(NombreAsistente);
            mess.setMensaje(tex);
            lista.add(mess);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
        }catch (Exception e){
            Elemen mess=new Elemen();
            mess.setLado(false);
            mess.setNombre(NombreAsistente);
            mess.setMensaje("Algo ha salido mal :(.\nInténtalo de nuevo.");
            lista.add(mess);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
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

            Elemen messs=new Elemen();
            messs.setLado(false);
            messs.setNombre(NombreAsistente);
            messs.setMensaje("No encontré el contacto llamado "+ nomb+ " en tu agenda");
            lista.add(messs);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
            return;
        }
        Log.i("AQUIMSj","numero: "+numero+"\nmensaje : "+mess+"\nNombre: "+nomb);

        //
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                Uri.parse("tel:"+numero).toString(),
                null,
                mess, //mensaje
                null,
                null);


        Elemen messs=new Elemen();
        messs.setLado(false);
        messs.setNombre(NombreAsistente);
        messs.setMensaje("He envíado tu mensaje a: "+ nomb+ ".");
        lista.add(messs);
        AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
        rv.setAdapter(adapter);

        Toast.makeText(getActivity(), "Mensaje Enviado", Toast.LENGTH_LONG).show();


    }

    private void RealizarLlamada(String[] parts) {
        String nom ="";
        for(int j = 2;j<parts.length;j++){
            nom+=parts[j];
        }

        String numero = ObtenerDatos(nom);
        if(numero.equalsIgnoreCase("")||numero==null){
            Elemen mess=new Elemen();
            mess.setLado(false);
            mess.setNombre(NombreAsistente);
            mess.setMensaje("No encontré el contacto llamado "+ nom+ " en tu agenda");
            lista.add(mess);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
            return;
        }
        Log.i("AQUI",numero);
        Log.i("AQUI",nom);

        Toast.makeText(getActivity(), "Este es su numero "+ numero, Toast.LENGTH_SHORT).show();
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getActivity(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
        } else {
            Elemen mess=new Elemen();
            mess.setLado(false);
            mess.setNombre(NombreAsistente);
            mess.setMensaje("Llamando a "+nom);
            lista.add(mess);
            AdaptadorAssistent adapter=new AdaptadorAssistent(lista);
            rv.setAdapter(adapter);
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
                //finish();
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
        Cursor c = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.Data.CONTENT_URI, projeccion, selectionClause, null, sortOrder);
//Cursor c=getActivity().getApplicationContext().getContentResolver().query()
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
}
