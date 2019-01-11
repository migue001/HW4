package org.tensorflow.demo;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class Fragment_Auditivo33 extends Fragment {
    EditText texto;
    ImageButton enviarestado;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    JsonObjectRequest jsonObjectRequest, jsonObjectRequest2;
    RequestQueue requestQueue, requestQueue2;
    ProgressDialog progressDialog;
    String nombre;
    ArrayList<Recyclerimg> listaimagenes;
    ArrayList<Recyclerm> listaestados;
    ImageView imageButton;

    public Fragment_Auditivo33() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__auditivo3, container, false);
        enviarestado = view.findViewById(R.id.subir_estado);
        recyclerView = view.findViewById(R.id.bdrecyclerview);
        floatingActionButton = view.findViewById(R.id.agalerias);

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setHasFixedSize(true);
            listaimagenes = new ArrayList<>();
            listaestados = new ArrayList<>();
            texto = view.findViewById(R.id.textoEstado);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EnviarImagen.class);
                    getActivity().startActivity(intent);
                }
            });
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue2 = Volley.newRequestQueue(getContext());
            Datos_Usuario conex = new Datos_Usuario(getActivity(), "DBUsuario", null, 2);
            SQLiteDatabase db = conex.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT Nombre FROM Usuario", null);
            try {
                if (cursor.moveToFirst()) {
                    nombre = cursor.getString(0);
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error BD", Toast.LENGTH_SHORT).show();
            }
            enviarestado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        enviarestados();

                }
            });

cargarPublicaciones();
            return view;
        }
    private void cargarPublicaciones() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
    String url="https://atencionaclientes19.000webhostapp.com/BasedeDatos/ConsultarEstados.php/";
    jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
Recyclerimg recyclerimg=null;
            JSONArray json=response.optJSONArray("Contenido");
            try{
                for (int i=0;i<json.length();i++){
                    recyclerimg=new Recyclerimg();
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);
                    recyclerimg.setNombre(jsonObject.optString("Nombre"));

                    recyclerimg.setDatoo(jsonObject.optString("Imagen"));
                    if (jsonObject.optString("Comentario").equalsIgnoreCase("")){
                        recyclerimg.setComentario(jsonObject.optString("Estado"));
                    }
                    else{
                        recyclerimg.setComentario(jsonObject.optString("Comentario"));
                    }

                    listaimagenes.add(recyclerimg);

                }
                progressDialog.hide();
                Adaptadorimagen adaptadorimagen=new Adaptadorimagen(listaimagenes);
                recyclerView.setAdapter(adaptadorimagen);
            }catch (JSONException e2){
progressDialog.hide();
                Toast.makeText(getActivity(), "No se ha Podido Conectar con el Servidor: "+response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Servidor:",response.toString());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
progressDialog.hide();
            Toast.makeText(getActivity(), "No Conecta con WS", Toast.LENGTH_SHORT).show();
        }
    });

requestQueue.add(jsonObjectRequest);
    }

    private void enviarestados() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Subiendo Estado...");
        progressDialog.show();
        String url="https://atencionaclientes19.000webhostapp.com/BasedeDatos/RegistroEstado.php?Estado="+texto.getText().toString()+"&Nombre="+nombre;
        url=url.replace(" ","%20");
        jsonObjectRequest2=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Se ha publicado", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error al subir Estado: "+ error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("SUBIR",error.toString());
            }
        });
       requestQueue2.add(jsonObjectRequest2);
    }

}
