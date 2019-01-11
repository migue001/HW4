package org.tensorflow.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class Lector_Traductor2 extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    ImageButton btnOK;
    String text="";
    boolean SoloUnaVez;
    private View mFloatingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector__traductor2);
         mFloatingView=LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        textView = (TextView) findViewById(R.id.tv_traductor);
        SoloUnaVez=getIntent().getBooleanExtra("SoloUnaVez",true);
        //--Textospeech

        btnOK = findViewById(R.id.btnOKtraductor);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=text.replace("\n"," ");
                FloatingViewService.txtTraducir.setText(""+text);
                expandedView.setVisibility(View.VISIBLE);
                collapsedView.setVisibility(View.GONE);
                //finish();
                /*Intent i = new Intent(getApplicationContext(),Traductor.class);
                i.putExtra("tra",text);
                startActivity(i);
                Log.i("AQUI",textView.getText().toString());*/
            }
        });
        //----------
        try{
            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            if (!textRecognizer.isOperational()) {
                Log.w("MainActivity", "Detector dependencies are not yet available");
            } else {
                try{
                    cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                            .setFacing(CameraSource.CAMERA_FACING_BACK)
                            .setRequestedPreviewSize(1280, 1024)
                            .setRequestedFps(2.0f)
                            .setAutoFocusEnabled(true)
                            .build();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder surfaceHolder) {

                        try {
                            if (ActivityCompat.checkSelfPermission(Lector_Traductor2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(Lector_Traductor2.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        RequestCameraPermissionID);
                                return;
                            }
                            cameraSource.start(cameraView.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                        cameraSource.stop();
                    }
                });

                textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<TextBlock> detections) {

                        final SparseArray<TextBlock> items = detections.getDetectedItems();
                        if (items.size() != 0) {
                            textView.post(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        for (int i = 0; i < items.size(); ++i) {
                                            TextBlock item = items.valueAt(i);
                                            stringBuilder.append(item.getValue());
                                            //  stringBuilder.append("\n");
                                        }
                                        textView.setText(stringBuilder.toString());
                                        text=        stringBuilder.toString();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }

                });

            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
}
