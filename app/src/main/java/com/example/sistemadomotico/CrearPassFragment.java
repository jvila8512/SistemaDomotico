package com.example.sistemadomotico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.sistemadomotico.ui.interfaces.CambiarFragment;
import com.example.sistemadomotico.ui.interfaces.Comunicacion;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPassFragment extends Fragment {
    // Initialize pattern lock view
    PatternLockView mPatternLockView;
    View vista;
    CambiarFragment cambiarFragment;


    public CrearPassFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CrearPassFragment newInstance(String param1, String param2) {
        CrearPassFragment fragment = new CrearPassFragment();








        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_crear_pass, container, false);



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
                // Shared Preferences to save state
                SharedPreferences sharedPreferences = vista.getContext().getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                editor.apply();

                String  password = sharedPreferences.getString("password", "0");
               System.out.println(password);
                cambiarFragment.cambiar(1);
               // texto.setText(password);



            }
            @Override
            public void onCleared() {

            }
        });

        // Inflate the layout for this fragment
        return vista;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cambiarFragment=(CambiarFragment) context;


    }


}