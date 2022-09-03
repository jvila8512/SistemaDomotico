package com.example.sistemadomotico.ui.Hora_Fecha;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sistemadomotico.R;
import com.example.sistemadomotico.ui.interfaces.Comunicacion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HoraFechaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HoraFechaFragment extends Fragment {
    View vista;
    Comunicacion comunicar;
    TextView ora,feca, mostrar;
    Button act;


    public HoraFechaFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HoraFechaFragment newInstance(String param1, String param2) {
        HoraFechaFragment fragment = new HoraFechaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.fragment_hora_fecha, container, false);

        ora=vista.findViewById(R.id.hora);
        feca=vista.findViewById(R.id.fecha);
        act=vista.findViewById(R.id.actualizar);


        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date =new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd");


                SimpleDateFormat sdf4 = new SimpleDateFormat("hh");
                SimpleDateFormat sdf5 = new SimpleDateFormat("mm");
                String dia=sdf2.format(date);
                String mes=sdf1.format(date);
                String anio=sdf.format(date);
                String hora=sdf4.format(date);
                String minutos=sdf5.format(date);


                Calendar calendario=Calendar.getInstance();


                // 0- AM  1- PM

                int pm=calendario.get(Calendar.AM_PM);


        //mostrar.setText("f"+dia+"-"+mes+"-"+anio+"   "+hora+"-"+minutos+" ---"+pm+"#");


                comunicar.enviarDatos("f"+dia+""+mes+""+anio+""+hora+""+minutos+""+pm);
                }});




        // Inflate the layout for this fragment
        return vista;
    }

    public void recibir( char datos_serie[] ){

        //si comienza con # sabemos que es lo que estamos buscando
        if (datos_serie[0] == 'd')

        {

          // AM=1 PM=2
            String AM_PM="AM";
            if(datos_serie[24]==1)
                AM_PM="AM";
            else
                AM_PM="PM";

            String o=datos_serie[14]+""+datos_serie[15]
                    +":"+datos_serie[12]+""+datos_serie[13]+
                    "."+datos_serie[10]+""+datos_serie[11]+"  "+AM_PM;

            String f=datos_serie[16]+""+datos_serie[17]+"-"+datos_serie[18]+""+datos_serie[19]+"-"+
                    ""+datos_serie[20]+
                    ""+datos_serie[21]+
                    ""+datos_serie[22]+
                    ""+datos_serie[23]




                    ;
            ora.setText(o);
            feca.setText(f);

        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        comunicar=(Comunicacion) context;


    }
}