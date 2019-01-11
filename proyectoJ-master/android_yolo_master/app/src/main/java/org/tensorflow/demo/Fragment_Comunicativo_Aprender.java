package org.tensorflow.demo;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Comunicativo_Aprender extends Fragment {
FloatingActionButton floating;
CardView cardView, cardView2;
    public Fragment_Comunicativo_Aprender() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment__comunicativo, container, false);
        cardView=view.findViewById(R.id.cardView1);
        floating=view.findViewById(R.id.floatingActionButton);
        cardView2=view.findViewById(R.id.cardView);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),Videos.class);
                startActivity(in);
            }
        });
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),Traductor.class);
                startActivity(in);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(getActivity(),Menu_Cursos.class);
                getActivity().startActivity(inte);
            }
        });
        return view;
    }

}
