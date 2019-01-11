package org.tensorflow.demo;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Visual2 extends Fragment {
JsonObjectRequest jsonObjectRequestt;
RequestQueue requestQueue;
RecyclerView recyclerView;
ArrayList<Recyclerm>listaestados;
    ImageView imageButton;
    ProgressDialog progressDialog;
    String nombre;
    public Fragment_Visual2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment__visual2, container, false);
           cargar();

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

        requestQueue= Volley.newRequestQueue(getContext());
        listaestados=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerEstados2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        return view;

    }

    private void cargar() {
        try {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Cargando...");
            progressDialog.show();
            String url = "https://atencionaclientes19.000webhostapp.com/BasedeDatos/ConsultarEstados2.php/";
            jsonObjectRequestt = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Recyclerm recyclerm = null;
                    JSONArray json = response.optJSONArray("Est");
                    try {
                        for (int i = 0; i < json.length(); i++) {
                            recyclerm = new Recyclerm();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            recyclerm.setNombre(jsonObject.optString("Nombre"));
                            recyclerm.setContentestado(jsonObject.optString("Texto"));
                            listaestados.add(recyclerm);
                        }
                        progressDialog.hide();
                        Adaptador_mostrar adaptador_mostrar = new Adaptador_mostrar(listaestados);

                        recyclerView.setAdapter(adaptador_mostrar);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "No se ha podido Conectar" + response.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Errorresponse", response.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequestt);
        }catch (Exception e){
            //Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
