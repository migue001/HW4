package org.tensorflow.demo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_VerImagenes extends Fragment {
/*ImageButton button;
    JsonObjectRequest jsonObjectRequest, jsonObjectRequest2;
    RequestQueue requestQueue,requestQueue2;
    RecyclerView recyclerimagen;
    ArrayList<Recyclerm> listaestados;
    ArrayList<Recyclerimg>listaimagenes;
    String nombre;
    ProgressDialog progressDialog;
    public Fragment_VerImagenes() */{
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_fragment__ver_imagenes, container, false);
       /*button=view.findViewById(R.id.agalerias);
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue2=Volley.newRequestQueue(getActivity());
        recyclerimagen=view.findViewById(R.id.bdrecyclerview2);
        recyclerimagen.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerimagen.setHasFixedSize(true);
        listaimagenes=new ArrayList<>();
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent inte=new Intent(getActivity(),EnviarImagen.class);
               getActivity().startActivity(inte);
           }
       });
       cargarWebServer2();*/
       return  view;
    }
    /*private void cargarWebServer2(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url="http://192.168.64.2/BasedeDatos/ConsultarEstados.php/";
        jsonObjectRequest2=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recyclerm rec = null;
                Recyclerimg recyclerimg=null;
                JSONArray jsonArray = response.optJSONArray("Contenido");
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        recyclerimg=new Recyclerimg();
                        JSONObject jsonObject = null;
                        jsonObject=jsonArray.getJSONObject(i);
                        recyclerimg.setNombre(jsonObject.optString("Nombre"));
                        recyclerimg.setComentario(jsonObject.optString("Comentario"));
                        recyclerimg.setDatoo(jsonObject.optString("Imagen"));
                        listaimagenes.add(recyclerimg);
                    }
                    progressDialog.hide();
                    Adaptadorimagen adaptadorimagen=new Adaptadorimagen(listaimagenes);
                    recyclerimagen.setAdapter(adaptadorimagen);
                }catch (Exception e){
                    Toast.makeText(getContext(), "no se pudo establecer la conexion"+response, Toast.LENGTH_SHORT).show();
                    Log.i("ERRORS",response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                progressDialog.hide();
                Toast.makeText(getActivity(), "No se Pudo conectar "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue2.add(jsonObjectRequest2);
/*jsonObjectRequest2=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
    @Override
    public void onResponse(JSONObject response) {
        Recyclerm rec = null;
        JSONArray jsonArray = response.optJSONArray("Contenido");
        try{
        for (int i = 0; i < jsonArray.length();i++) {
            rec=new Recyclerm();
            JSONObject jsonObject = null;
jsonObject=jsonArray.getJSONObject(i);
rec.setNombre(jsonObject.optString("Nombre"));
rec.setContentestado(jsonObject.optString("Estado"));
listaestados.add(rec);
        }
            progressDialog.hide();
            Adaptador_mostrar adaptador_mostrar=new Adaptador_mostrar(listaestados);
            recyclerestado.setAdapter(adaptador_mostrar);
    }catch (Exception e){
            Toast.makeText(getContext(), "no se pudo establecer la conexion"+response, Toast.LENGTH_SHORT).show();
        }

    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
progressDialog.hide();
        Toast.makeText(getActivity(), "No se ha podido Actualizar: "+error.toString(), Toast.LENGTH_SHORT).show();
        progressDialog.hide();
    }
});
requestQueue2.add(jsonObjectRequest2);*/
    }
