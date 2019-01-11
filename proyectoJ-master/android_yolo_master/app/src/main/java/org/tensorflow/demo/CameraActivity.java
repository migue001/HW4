/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*  This file has been modified by Nataniel Ruiz affiliated with Wall Lab
 *  at the Georgia Institute of Technology School of Interactive Computing
 */

package org.tensorflow.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CameraActivity extends Activity {
  private static final int PERMISSIONS_REQUEST = 1;
  public static String Objeto = "";
  private String Ultimo_Objeto = "j";
  public static double ObjetoProb = 0.1;
  public static double Ul_ObjetoProb = 0.0;
  private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
  private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
  public static TextToSpeech toSpeech;
  int resultx;
  boolean SoloUno=false;
  boolean Tarea_CursarCalle=false, Tarea_Medicina=false,MedicinaEncontrada=false;
  String LadoDerecho="",LadoIzquierdo="";
  //
//

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    setContentView(R.layout.activity_camera);
   try {
    SoloUno = getIntent().getBooleanExtra("SoloUno", false);
     Tarea_CursarCalle = getIntent().getBooleanExtra("CruzarCalle", false);
     Tarea_Medicina = getIntent().getBooleanExtra("Medicina", false);
   }catch (Exception e){
     e.printStackTrace();
   }
    // cameraView = (SurfaceView) findViewById(R.id.surfaceView);
    // textView = (TextView) findViewById(R.id.tv);

    if (hasPermission()) {
      if (null == savedInstanceState) {
        setFragment();
      }
    } else {
      requestPermission();
    }

    try {
      toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
          if (status == TextToSpeech.SUCCESS) {
            resultx = toSpeech.setLanguage(Locale.getDefault());
          } else {
          }
        }
      });
      checkPermission();
    } catch (Exception e) {
      e.printStackTrace();
    }
if(Tarea_CursarCalle) {
  CruzarCalle();
  return;
}
if(Tarea_Medicina) {
      Medicina();
      return;
    }
    final Handler handler = new Handler();
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          public void run() {
            try {
              //if (!Ultimo_Objeto.equals(Objeto) && ObjetoProb>Ul_ObjetoProb) {
              if (!Ultimo_Objeto.equals(Objeto)) {
                String txt = "";
                if (ObjetoProb > 8) {
                  if (Objeto.equalsIgnoreCase("persona") &&ObjetoProb<30) {
                    return;
                  }
                    String articulo="un";
                  if(Objeto.equalsIgnoreCase("Persona")||Objeto.equalsIgnoreCase("Planta en maceta")||
                          Objeto.equalsIgnoreCase("bicicleta")||Objeto.equalsIgnoreCase("silla")
                          ||Objeto.equalsIgnoreCase("Motocicleta")||Objeto.equalsIgnoreCase("bicicleta")||
                          Objeto.equalsIgnoreCase("Bolleta")){
                    articulo="una";
                  }
                  if (ObjetoProb < 9) {
                    txt = "Creo que es "+articulo+" " + Objeto + " pero no estoy seguro";
                  } else if (ObjetoProb < 10) {
                    txt = "Parece "+ articulo+" " + Objeto;
                  } else {
                    txt = "Veo "+articulo+" " + Objeto;
                  }
                  if (Objeto.equalsIgnoreCase("Automovil")) {
                    txt = "¡Cuidado! detecto un carro enfrente.";
                  } else if (Objeto.equalsIgnoreCase("Autobus")) {
                    txt = "¡Cuidado! detecto un autobus enfrente.";
                  } else if (Objeto.equalsIgnoreCase("Bicicleta")) {
                    txt = "¡Cuidado! detecto una bicicleta enfrente.";
                  }

                }
                toSpeech.speak(txt, TextToSpeech.QUEUE_FLUSH, null);

                while (toSpeech.isSpeaking()){
                }
                if(SoloUno){
                  finish();
                  startActivity(new Intent(getApplicationContext(),Inicio_Visual.class));
                }

               /* if(Objeto.equalsIgnoreCase("Monitor")||Objeto.equalsIgnoreCase("Botella")){
                  finish();
                  startActivity(new Intent(getApplicationContext(), Lector.class));
                }*/
              }
              Ultimo_Objeto = Objeto;
              Ul_ObjetoProb=ObjetoProb;

              Log.i("AQUI", Objeto + " ultimo: " + Ultimo_Objeto);
            } catch (Exception e) {
              Log.e("error", e.getMessage());
            }
          }
        });
      }
    };
    //Cambia de fragments cada tiempo
    timer.schedule(task, 0, 2000);

    //FrameLayout cn = (FrameLayout) findViewById(R.id.container);
    //cn.setVisibility(View.INVISIBLE);
    //Intentar Poner ambos layout a la mitad de la pantalla o cambiar su visibilidad para ver que p
    //
    //
  }

  private void Medicina() {
    final Handler handler = new Handler();
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          public void run() {
            try {
              //if (!Ultimo_Objeto.equals(Objeto) && ObjetoProb>Ul_ObjetoProb) {

                if (ObjetoProb > 9) {
                  if(Objeto.equalsIgnoreCase("Botella")){
                    if(!MedicinaEncontrada) {
                      toSpeech.speak("Encontré una botella", TextToSpeech.QUEUE_ADD, null);
                      MedicinaEncontrada=true;
                      Intent i=new Intent(getApplicationContext(),Lector.class);
                      i.putExtra("SoloUnaVez",true);
                      startActivity(i);
                      finish();
                    }
                  }
                }

                while (toSpeech.isSpeaking()){
                }

              Ultimo_Objeto = Objeto;
              Ul_ObjetoProb=ObjetoProb;

              Log.i("AQUI", Objeto + " ultimo: " + Ultimo_Objeto);
            } catch (Exception e) {
              Log.e("error", e.getMessage());
            }
          }
        });
      }
    };
    //Cambia de fragments cada tiempo
    timer.schedule(task, 0, 5000);

  }

  private void CruzarCalle() {
    final Handler handler = new Handler();
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          public void run() {
            try {
              //if (!Ultimo_Objeto.equals(Objeto) && ObjetoProb>Ul_ObjetoProb) {

                if(LadoDerecho.equals("")) {
                  toSpeech.speak("Te ayudaré a cruzar la calle, recuerda que mi sistema es una ayuda y solo usálo cuando no haya nadie más cerca. Comecemos Voltea al lado derecho y dame un momento para reconocer:",TextToSpeech.QUEUE_ADD,null);
                }
               else if(LadoIzquierdo.equals("")) {
                  toSpeech.speak("Ahora, Voltea al lado izquierdo. Espera",TextToSpeech.QUEUE_ADD,null);
                }
                if (ObjetoProb > 9) {
                  if(LadoDerecho.equals("")){
                    LadoDerecho=Objeto;
                  }else if(LadoIzquierdo.equals("")){
                    LadoIzquierdo=Objeto;
                  }else{
                    if(!LadoIzquierdo.equalsIgnoreCase("Automovil")
                            &&!LadoDerecho.equalsIgnoreCase("Automovil")) {
                      toSpeech.speak("Puedes pasar.",TextToSpeech.QUEUE_ADD,null);
                    }else{
                      toSpeech.speak("Detecto Automoviles, Esperemos 5 segundos.",TextToSpeech.QUEUE_ADD,null);
                      try{
                        Thread.sleep(5000);
                      }catch (Exception e){}
                      LadoDerecho="";LadoIzquierdo="";
                      CruzarCalle();
                      return;
                    }
                  }
                }

                while (toSpeech.isSpeaking()){
                }

              Ultimo_Objeto = Objeto;
              Ul_ObjetoProb=ObjetoProb;

              Log.i("AQUI", Objeto + " ultimo: " + Ultimo_Objeto);
            } catch (Exception e) {
              Log.e("error", e.getMessage());
            }
          }
        });
      }
    };
    //Cambia de fragments cada tiempo
    timer.schedule(task, 0, 5000);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSIONS_REQUEST: {

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
          setFragment();
          //

          //
        } else {
          requestPermission();
        }
      }
    }
  }

  private boolean hasPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED;
    } else {
      return true;
    }
  }

  private void requestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA) || shouldShowRequestPermissionRationale(PERMISSION_STORAGE)) {
        Toast.makeText(CameraActivity.this, "Camera AND storage permission are required for this demo", Toast.LENGTH_LONG).show();
      }
      requestPermissions(new String[] {PERMISSION_CAMERA, PERMISSION_STORAGE}, PERMISSIONS_REQUEST);
    }
  }

  private void setFragment() {
    try {
      CameraConnectionFragment cn = CameraConnectionFragment.newInstance();
      getFragmentManager()
              .beginTransaction()
              .replace(R.id.container, cn)
              .addToBackStack(null)
              .commitAllowingStateLoss();
    }catch(Exception e){
      Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
    }
  }
  private void checkPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivity(intent);
      }
    }
  }


}
