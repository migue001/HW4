package org.tensorflow.demo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Auditivo4 extends Fragment {


    public Fragment_Auditivo4() {
        // Required empty public constructor
    }
    TextView telJairo,telMigue,correoApp,correoMigue,correoJairo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment__auditivo4, container, false);
        TextView text = view.findViewById(R.id.texto);
        text.setText("Para cualquier aclaración, duda o error contactenos a:");
        ImageView mail = view.findViewById(R.id.mail);
        ImageView cel = view.findViewById(R.id.cel);
        telJairo=view.findViewById(R.id.textView10);
        telMigue=view.findViewById(R.id.textView11);
        correoApp=view.findViewById(R.id.textView8);
        correoJairo=view.findViewById(R.id.textView13);
        correoMigue=view.findViewById(R.id.textView14);
        correoJairo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {correoJairo.getText().toString()}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email a "+correoJairo.getText().toString()));
                    // finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(),
                            "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        correoMigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {correoMigue.getText().toString()}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email a "+correoMigue.getText().toString()));
                    // finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(),
                            "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        correoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {correoApp.getText().toString()}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email a "+correoApp.getText().toString()));
                    // finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(),
                            "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        telJairo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + telJairo.getText().toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });
        telMigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + telMigue.getText().toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        return view;
    }

}
