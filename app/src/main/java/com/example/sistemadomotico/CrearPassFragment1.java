package com.example.sistemadomotico;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.sistemadomotico.ui.interfaces.CambiarFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPassFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPassFragment1 extends Fragment {
    // Initialize pattern lock view
    PatternLockView mPatternLockView;
    View vista;
    CambiarFragment cambiarFragment;
    TextView texto;
    Button b;
    String password;


    public CrearPassFragment1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CrearPassFragment1 newInstance(String param1, String param2) {
        CrearPassFragment1 fragment = new CrearPassFragment1();








        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_c, container, false);



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

                    cambiarFragment.cambiar(1);

                    //

                   // t.setText(password);

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cambiarFragment=(CambiarFragment) context;


    }
}