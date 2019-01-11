package org.tensorflow.demo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import static android.Manifest.permission.*;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EnviarImagen extends AppCompatActivity {
FloatingActionButton galerias, camara;
ImageView imagen;
ImageButton imageButton;
    String path2;
    EditText comenta;
    String nombre="";
    Button publica;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Bitmap bitmap;
private final String carpeta_raiz="misImagenesPrueba/";
private final String Ruta=carpeta_raiz+"Mis Fotos";
StringRequest stringrequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_imagen);
        imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        publica=findViewById(R.id.publica);
        Datos_Usuario conex=new Datos_Usuario(this,"DBUsuario",null,2);
        SQLiteDatabase db=conex.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT Nombre FROM Usuario",null);
        try{
            if(cursor.moveToFirst()){
                nombre=cursor.getString(0);
            }
        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        validarpermisos();
        comenta=findViewById(R.id.comentario);
        requestQueue= Volley.newRequestQueue(this);
        publica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cargarwebService();
            }
        });
        //-------------------------------------------------------------------
        galerias=findViewById(R.id.agalerias);
        camara=findViewById(R.id.acamara);
        imagen=findViewById(R.id.imagen2);
        galerias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarImagen();
            }
        });
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tomarfoto();
            }
        });
    }
    private void cargarwebService(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url="https://atencionaclientes19.000webhostapp.com/BasedeDatos/RegistroPublicacion.php?";
        url=url.replace(" ","%20");
        stringrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
progressDialog.hide();
if(response.trim().equalsIgnoreCase("registra")){
    Toast.makeText(EnviarImagen.this, "Se ha Publicado", Toast.LENGTH_SHORT).show();
    finish();
}
else{
    Toast.makeText(EnviarImagen.this, "No se ha Publicado", Toast.LENGTH_SHORT).show();
    comenta.setText("");
}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EnviarImagen.this, "No se ha podido Conectar"+error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String com=comenta.getText().toString();
String imagen=convertirimagen(bitmap);
Map<String,String>parametros=new HashMap<>();
parametros.put("Comentario",com);
parametros.put("Nombre",nombre);
parametros.put("Imagen",imagen);
                return parametros;

            }
        };
requestQueue.add(stringrequest);
    }

    private String convertirimagen(Bitmap bitmap) {
        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[]image=array.toByteArray();
String imagenstring= Base64.encodeToString(image,Base64.DEFAULT);
        return imagenstring;
    }

    private boolean validarpermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
               return true;
        }
        if(shouldShowRequestPermissionRationale(CAMERA)||shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)){
         cargardialogodeRecomendacion();
        }
        else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==2&& grantResults[0]==PackageManager.PERMISSION_GRANTED&& grantResults[1]==PackageManager.PERMISSION_GRANTED){
         galerias.setEnabled(true);
         camara.setEnabled(true);
        }else {
            solicitarpermiso();
        }
    }

    private void solicitarpermiso() {

     final AlertDialog.Builder alert=new AlertDialog.Builder(EnviarImagen.this);
     alert.setTitle("Desea Configurar los permiso de forma Manual?");
     alert.setPositiveButton("si", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             Intent intent=new Intent();
             intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
             Uri uri=Uri.fromParts("package",getPackageName(),null);
             intent.setData(uri);
             startActivity(intent);
         }
     }) ;
          alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  Toast.makeText(EnviarImagen.this, "Los Permisos Fueron Rechazados", Toast.LENGTH_SHORT).show();
                  dialog.dismiss();
              }
          });
    }

    private void cargardialogodeRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(EnviarImagen.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permiso para que la aplicacion pueda funcionar correctamente");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
               requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
               
            }
        });
        dialogo.show();
    }

    private void Tomarfoto() {
        File fileimagen=new File(Environment.getExternalStorageDirectory(),Ruta);
        boolean iscreada=fileimagen.exists();
        String Nombre="";
        if(iscreada==false){
            iscreada=fileimagen.mkdirs();
        }
        else{
             Nombre=(System.currentTimeMillis()/1000)+".jpg";

        }
 path2=Environment.getExternalStorageDirectory()+File.separator+Ruta+File.separator+Nombre;
        File imagen=new File(path2);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent, 20);
    }

    private void CargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
switch (requestCode){
    case 10:
        Uri path=data.getData();
        imagen.setImageURI(path);
        try {
            bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
            imagen.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;
    case 20:
        MediaScannerConnection.scanFile(this, new String[]{path2}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.i("Ruta de Almacenamiento","Path: "+path2);
            }
        });
         bitmap= BitmapFactory.decodeFile(path2);
        imagen.setImageBitmap(bitmap);
        break;
}


        }
    }



}
