package com.example.sistemadomotico;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.sistemadomotico.ui.interfaces.Cambiar;
import com.example.sistemadomotico.ui.interfaces.CambiarFragment;
import com.example.sistemadomotico.ui.interfaces.Comunicacion;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassFragment extends Fragment  {

    View vista;
    PatternLockView mPatternLockView;
    String password;
    Cambiar cambiarFragment1;
    TextView t;
    Button b;



    public PassFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PassFragment newInstance(String param1, String param2) {
        PassFragment fragment = new PassFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_pass, container, false);

        t=vista.findViewById(R.id.text);
        b=vista.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFragment1.cambiar1(1);

            }
        });



        mPatternLockView = (PatternLockView) vista.findViewById(R.id.pattern_lock_view);

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }


            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", 0);
                password = sharedPreferences.getString("password", "0");


                // if drawn pattern is equal to created pattern you will navigate to home screen
                if (password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {

                   cambiarFragment1.cambiar1(1);

                  //

                 t.setText(password);

                } else {
                    //other wise you will get error wrong password
                  Toast.makeText(getContext(),"Patrón Incorrecto",Toast.LENGTH_LONG).show();

                    mPatternLockView.clearPattern();
                }


            }

            @Override
            public void onCleared() {

            }
        });



        // Inflate the layout for this fragment
        return vista;
    }

    private void sms(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cambiarFragment1=(Cambiar) context;

    }

    public void recibirPass(String pass) {

   //t.setText(pass);
  // password=pass;

    }
}