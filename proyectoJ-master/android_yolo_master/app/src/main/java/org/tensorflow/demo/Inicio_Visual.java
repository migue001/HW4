package org.tensorflow.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Inicio_Visual extends AppCompatActivity {
    public static Fragment select=null;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;
    BottomNavigationView bview;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__visual);
        bview=findViewById(R.id.main_nav);
        bview.setOnNavigationItemSelectedListener(navlis);
validarpermisos();
ini();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void validarpermisos() {
        //requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navlis=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.opcion1:
                        select = new Fragment_Visual();
                        break;
                    case R.id.opcion3:
                        select = new Fragment_Visual3();
                }
            getSupportFragmentManager().beginTransaction().replace(R.id.frag,select).commit();
            return true;
        }
    };
    private void ini(){
        select=new Fragment_Visual();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,select).commit();
    }
}
