package com.example.sistemadomotico.ui.luces;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sistemadomotico.R;
import com.example.sistemadomotico.ui.interfaces.Comunicacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LucesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LucesFragment extends Fragment   {


    Button  BtnCuarto1,BtnCuarto2,BtnCuarto3,BtnBanno,BtnSala,BtnComedor,BtnCocina,BtnPasillo,BtnFrente, BtnApagar;
    int btnCuarto1=0,btnCuarto2=0,btnCuarto3=0,btnBanno=0,btnSala=0,btnComedor=0,btnCocina=0,btnPasillo=0,btnFrente=0;

    TextView ora,feca;
    ImageView img,imgCuarto1,imgCuarto2,imgCuarto3,imgBanno,imgSala,imgComedor,imgCocina,imgPasillo,imgPatio;


    View vista;
    Comunicacion comunicar;

    public LucesFragment() {

    }


    public static LucesFragment newInstance(String param1, String param2) {
        LucesFragment fragment = new LucesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_luces, container, false);




              BtnCuarto1=vista.findViewById(R.id.btn_cuarto1);
              BtnCuarto2=vista.findViewById(R.id.btn_cuarto2);
              BtnCuarto3=vista.findViewById(R.id.btn_cuarto3);
              BtnBanno=vista.findViewById(R.id.btnBanno);
              BtnSala=vista.findViewById(R.id.btnSala);
              BtnComedor=vista.findViewById(R.id.btnComedor);
              BtnCocina=vista.findViewById(R.id.btnCocina);
              BtnPasillo=vista.findViewById(R.id.btnPasillo);
              BtnFrente=vista.findViewById(R.id.btnFrente);
              BtnApagar=vista.findViewById(R.id.btnApagar);


                imgCuarto1=vista.findViewById(R.id.imgCuarto1);
                imgCuarto2=vista.findViewById(R.id.imgCuarto2);
                imgCuarto3=vista.findViewById(R.id.imgCuarto3);
                imgBanno=vista.findViewById(R.id.imgBanno);
                imgSala=vista.findViewById(R.id.imgSala);
                imgComedor=vista.findViewById(R.id.imgComedor);
                imgCocina=vista.findViewById(R.id.imgCocina);
                imgPasillo=vista.findViewById(R.id.imgPasillo);
                imgPatio=vista.findViewById(R.id.imgPatio);

                ora=vista.findViewById(R.id.ora);
                feca=vista.findViewById(R.id.feca);


        BtnCuarto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l1#");}});

        BtnCuarto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l2#");}});
        BtnCuarto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l4#");}});
        BtnBanno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l3#");}});
        BtnSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l5#");}});

        BtnComedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l6#");}});
        BtnCocina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l7#");}});

        BtnPasillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l8#");}});

        BtnFrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l9#");}});




        BtnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { comunicar.enviarDatos("l0#");}});


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

            if(datos_serie[1] == '1'){
                imgCuarto1.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgCuarto1.setImageResource(R.drawable.apagado);

            if(datos_serie[2] == '1'){
                imgCuarto2.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
               imgCuarto2.setImageResource(R.drawable.apagado);



            if(datos_serie[3] == '1'){
                imgBanno.setImageResource(R.drawable.ence);
                // btnCuarto2=1;
            }else
               imgBanno.setImageResource(R.drawable.apagado);

            if(datos_serie[4] == '1'){
                imgCuarto3.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgCuarto3.setImageResource(R.drawable.apagado);

            if(datos_serie[5] == '1'){
                imgSala.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgSala.setImageResource(R.drawable.apagado);

            if(datos_serie[6] == '1'){
                imgComedor.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgComedor.setImageResource(R.drawable.apagado);

            if(datos_serie[7] == '1'){
               imgCocina.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgCocina.setImageResource(R.drawable.apagado);



            if(datos_serie[8] == '1'){
                imgPasillo.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgPasillo.setImageResource(R.drawable.apagado);

            if(datos_serie[9] == '1'){
                imgPatio.setImageResource(R.drawable.ence);
                // btnCuarto1=1;
            }else
                imgPatio.setImageResource(R.drawable.apagado);


            String AM_PM="AM";
          /*  if(datos_serie[24]==1)
                AM_PM="AM";
            else
                AM_PM="PM";*/

            String o="Hora: "+datos_serie[14]+""+datos_serie[15]
                    +":"+datos_serie[12]+""+datos_serie[13]+
                    "."+datos_serie[10]+""+datos_serie[11]+"  "+AM_PM;
            String f="Fecha: "+datos_serie[16]+""+datos_serie[17]+"-"+datos_serie[18]+""+datos_serie[19]+"-"+
                    ""+datos_serie[20]+
                    ""+datos_serie[21]+
                    ""+datos_serie[22]+
                    ""+datos_serie[23]




                    ;
            ora.setText(o);
            feca.setText(f);



        }





    } //Fin del metodo
}