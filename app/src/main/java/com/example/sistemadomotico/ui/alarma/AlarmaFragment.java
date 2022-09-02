package com.example.sistemadomotico.ui.alarma;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sistemadomotico.R;
import com.example.sistemadomotico.ui.interfaces.Comunicacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmaFragment extends Fragment {

    Button Btnenviar,Btnactivar;
    TextView tiempoActivar,Activada;

    EditText tiempo;

    View vista;
    Comunicacion comunicar;



    public AlarmaFragment() {
        // Required empty public constructor
    }


    public static AlarmaFragment newInstance(String param1, String param2) {
        AlarmaFragment fragment = new AlarmaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.fragment_alarma, container, false);

        Btnenviar=vista.findViewById(R.id.enviar);
        Btnactivar=vista.findViewById(R.id.activar);
        tiempoActivar=vista.findViewById(R.id.tiempoActivado);
        Activada=vista.findViewById(R.id.estadoAlarma);
        tiempo=vista.findViewById(R.id.tiempo);


        Btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enviamos el tiempo elegido por el usuario.Debe ser mayor que 15
                comunicar.enviarDatos("a1"+tiempo.getText()+"#");}});

        Btnactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enviamos el tiempo elegido por el usuario.Debe ser mayor que 15
                comunicar.enviarDatos("a2#");}});


        return vista;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        comunicar=(Comunicacion) context;


    }

    public void recibir( char datos_serie[] ){


        //si comienza con # sabemos que es lo que estamos buscando
        if (datos_serie[0] == 'd')

        {
            String ala="";


            String tim="Tiempo Espera("+""+datos_serie[25]+""+datos_serie[26]+")";
            tiempoActivar.setText(tim);

            if(datos_serie[27]=='1')
            {  Activada.setText("Alarma: Activada");
               Activada.setTextColor(Color.RED);}
            else
            {   Activada.setText("Alarma: Desactivada");
                Activada.setTextColor(Color.GREEN);

            }



                    ;




        }





    } //Fin del metodo
}