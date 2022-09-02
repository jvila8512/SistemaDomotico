package com.example.sistemadomotico;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.sistemadomotico.ui.Hora_Fecha.HoraFechaFragment;
import com.example.sistemadomotico.ui.alarma.AlarmaFragment;
import com.example.sistemadomotico.ui.interfaces.Cambiar;
import com.example.sistemadomotico.ui.interfaces.CambiarFragment;
import com.example.sistemadomotico.ui.interfaces.Comunicacion;
import com.example.sistemadomotico.ui.luces.LucesFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemadomotico.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Comunicacion, CambiarFragment, Cambiar {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static  final int REQUEST_ENABLE_BT=0;
    private static  final int REQUEST_DISCOVER_BT=1;
    // String for MAC address
    private static int address = -1;
    private static int direccion = -1;
    private static String direccionSistema="";
    private boolean canExitApp=false;




    Handler bluetoothIn;

    final int handlerState = 0;             //used to identify handler message
    BluetoothAdapter btAdapter = null;
    BluetoothSocket btSocket = null;
    Set<BluetoothDevice> divice;
    StringBuilder recDataString = new StringBuilder();
    private ProgressBar bProgreso;

    ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("8ce2525c0-200a-11e0-ac64-0800200c9a66");
    //private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    CharSequence items[] = new CharSequence[0];
    MediaPlayer mediaPlayer ;
    MediaPlayer mediaPlayerError ;
    NavController navController;
    String pass="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        mediaPlayer = MediaPlayer.create(this, R.raw.sistema);
        mediaPlayerError = MediaPlayer.create(this, R.raw.error);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setVisibility(View.GONE);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_luces,
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.crearPassFragment,
                R.id.p,
                R.id.passFragment,
                R.id.nav_alarma,
                R.id.nav_hora

                )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);




        btAdapter= BluetoothAdapter.getDefaultAdapter();



       // try {
            if (!btAdapter.isEnabled()){

                btAdapter.enable();
            }
           // Thread.sleep(800);
      //  } catch (InterruptedException e) {
       //     e.printStackTrace();
      //  }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                navController.navigate(R.id.p);


               mostrarDialogo();
               /*

                ProgressDialog mProgressDialog = ProgressDialog.show (getBaseContext(), "Conectando...",
                        "Espere se esta conectando",
                        true,
                        true,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {


                            }
                        });
                mProgressDialog.show();*/

      //Conectar con el sistema





  //Fin de la conexion

            }
        }, 2000);




        bluetoothIn = new Handler() {

            @SuppressLint("HandlerLeak")
            public void handleMessage(android.os.Message msg) {



                if (msg.what == handlerState) {          //if message is what we want
                    String readMessage = (String) msg.obj;
                    recDataString.append(readMessage); //determinar el final de la línea
                    int endOfLineIndex = recDataString.indexOf("#"); //sigue agregando a la cadena hasta #

                    if (endOfLineIndex > 0) { //asegúrese de que haya datos antes ~
                        //Pico la cadena hasta el numeral
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);


                        int total=dataInPrint.length();
                        char datos_serie[]=new char[total];
                        datos_serie= dataInPrint.toCharArray();
                        int dataLength = dataInPrint.length();



                        NavHostFragment navegador= (NavHostFragment) getSupportFragmentManager().getPrimaryNavigationFragment();
                        FragmentManager manejador=navegador.getChildFragmentManager();
                        Fragment activo=manejador.getPrimaryNavigationFragment();


                        if(activo instanceof LucesFragment){
                            LucesFragment l= (LucesFragment) activo;
                            l.recibir(datos_serie);


                        }

                       if(activo instanceof AlarmaFragment){
                            AlarmaFragment l= (AlarmaFragment) activo;
                            l.recibir(datos_serie);


                        }
                        if(activo instanceof HoraFechaFragment){
                            HoraFechaFragment l= (HoraFechaFragment) activo;
                            l.recibir(datos_serie);


                        }





                        recDataString.delete(0, recDataString.length());      //clear all string data
                        dataInPrint = " ";

                    }

                }
            }
        };


//// Vamos a crear el patron por defecto

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", "0124678");
        editor.apply();
        pass = sharedPreferences.getString("password", "0");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


  public void mostrarbarra(){

      if (btAdapter.isEnabled()) {
          Set<BluetoothDevice> divice = btAdapter.getBondedDevices();
          int cont = 0;
          for (BluetoothDevice devices : divice) {
              //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
              items[cont] = devices.getAddress() + "";
              cont++;
              //mPairedDevicesArrayAdapter.add(devices.getAddress());
          }

          // BluetoothDevice device = btAdapter.getRemoteDevice((String) items[direccion]);
          direccionSistema=(String) items[direccion];

          BluetoothDevice device = btAdapter.getRemoteDevice((String) items[direccion]);

          try {
              btSocket = (BluetoothSocket) createBluetoothSocket(device);

          } catch (IOException e) {
              Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
          }
          // Establish the Bluetooth socket connection.
          try {
              btSocket.connect();


          } catch (IOException e) {
              try {
                  //sms("cerro la conexion pero voy a abrirla");
                  btSocket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);

                  btSocket.connect();
                  // sms("Conectoooooo");

                  mediaPlayer.start();






              } catch (IOException e2) {
                  try {
                      btSocket.close();

                      sms("Cerorrrrrr");


                      divice= btAdapter.getBondedDevices();

                      if (divice.size() > 0) {

                          items = new CharSequence[divice.size()];
                          cont=0;

                          for (BluetoothDevice devices :  divice) {
                              //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                              items[cont]=devices.getName() + "-"+ devices.getAddress();
                              cont++;
                              //mPairedDevicesArrayAdapter.add(devices.getAddress());
                          }

                          AlertDialog a=ListDialog(items);
                          a.show();
                          cont=0;}
                      mediaPlayerError.start();



                  } catch (IOException e1) {
                      e1.printStackTrace();
                  }
                  //insert code to deal with this
              } catch (NoSuchMethodException e1) {
                  e1.printStackTrace();
                  sms("Cerorrrrrr111");
              } catch (IllegalAccessException e1) {
                  e1.printStackTrace();
                  sms("Cerorrrrrr2");
              } catch (InvocationTargetException e1) {
                  e1.printStackTrace();
                  sms("Cerorrrrrr3");
              }
          }
          mConnectedThread = new ConnectedThread(btSocket);
          mConnectedThread.start();

          //navController.navigate(R.id.passFragment);


      }else
      {  sms("Debe conectar el Bluetooh");
          try {
              Thread.sleep(2*1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }

          Set<BluetoothDevice> divice= btAdapter.getBondedDevices();

          if (divice.size() > 0) {

              items = new CharSequence[divice.size()];
              int cont=0;

              for (BluetoothDevice devices :  divice) {
                  //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                  items[cont]=""+ devices.getAddress();
                  cont++;
                  //mPairedDevicesArrayAdapter.add(devices.getAddress());
              }

              AlertDialog a=ListDialog(items);
              a.show();
              cont=0;
          }



      }


     // sms(direccionSistema);



    }


    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //navController.setE


        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    /**
     * Crea un Diálogo con una lista de selección simple
     *
     * @return La instancia del diálogo
     */
    public AlertDialog ListDialog(CharSequence []a) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);




        final CharSequence[] finalItems = a;



        builder.setTitle("Dispositivos BLuetooth")
                .setIcon(R.drawable.bluetooth_connected_blanco)
                .setItems(finalItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        direccion=which;






 // Vamos a uardar la direccion del sistema


                        if (btAdapter.isEnabled()) {
                            Set<BluetoothDevice> divice = btAdapter.getBondedDevices();
                            int cont = 0;
                            for (BluetoothDevice devices : divice) {
                                //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                                items[cont] = devices.getAddress() + "";
                                cont++;
                                //mPairedDevicesArrayAdapter.add(devices.getAddress());
                            }

                            BluetoothDevice device = btAdapter.getRemoteDevice((String) items[direccion]);

                            try {
                                btSocket = (BluetoothSocket) createBluetoothSocket(device);

                            } catch (IOException e) {
                                Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
                            }
                            // Establish the Bluetooth socket connection.
                            try {
                                btSocket.connect();


                            } catch (IOException e) {
                                try {
                                    //sms("cerro la conexion pero voy a abrirla");
                                    btSocket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);

                                    btSocket.connect();
                                   // sms("Conectoooooo");

                                    mediaPlayer.start();






                                } catch (IOException e2) {
                                    try {
                                        btSocket.close();

                                        sms("Cerorrrrrr");


                                        divice= btAdapter.getBondedDevices();

                                        if (divice.size() > 0) {

                                            items = new CharSequence[divice.size()];
                                             cont=0;

                                            for (BluetoothDevice devices :  divice) {
                                                //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                                                items[cont]=devices.getName() + "-"+ devices.getAddress();
                                                cont++;
                                                //mPairedDevicesArrayAdapter.add(devices.getAddress());
                                            }

                                            AlertDialog a=ListDialog(items);
                                            a.show();
                                            cont=0;}
                                        mediaPlayerError.start();



                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                    //insert code to deal with this
                                } catch (NoSuchMethodException e1) {
                                    e1.printStackTrace();
                                    sms("Cerorrrrrr111");
                                } catch (IllegalAccessException e1) {
                                    e1.printStackTrace();
                                    sms("Cerorrrrrr2");
                                } catch (InvocationTargetException e1) {
                                    e1.printStackTrace();
                                    sms("Cerorrrrrr3");
                                }
                            }
                            mConnectedThread = new ConnectedThread(btSocket);
                            mConnectedThread.start();

                           //navController.navigate(R.id.passFragment);


                        }else
                        {  sms("Debe conectar el Bluetooh");
                            try {
                                Thread.sleep(2*1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Set<BluetoothDevice> divice= btAdapter.getBondedDevices();

                            if (divice.size() > 0) {

                                items = new CharSequence[divice.size()];
                                int cont=0;

                                for (BluetoothDevice devices :  divice) {
                                    //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                                    items[cont]=""+ devices.getAddress();
                                    cont++;
                                    //mPairedDevicesArrayAdapter.add(devices.getAddress());
                                }

                                AlertDialog a=ListDialog(items);
                                a.show();
                                cont=0;
                            }



                        }





















                       // Toast.makeText(getBaseContext(),"Seleccionaste:" + finalItems[which],
                                //Toast.LENGTH_SHORT)
                               // .show();
                    }
                });

/*
       builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {




                if (btAdapter.isEnabled()){

                    Set<BluetoothDevice> divice= btAdapter.getBondedDevices();

                    if (divice.size() > 0) {

                        items = new CharSequence[divice.size()];
                        int cont=0;

                        for (BluetoothDevice devices :  divice) {
                            //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                            items[cont]=devices.getName() + "-"+ devices.getAddress();
                            cont++;
                            //mPairedDevicesArrayAdapter.add(devices.getAddress());
                        }

                        AlertDialog a=ListDialog(items);
                        a.show();
                        cont=0;

                        // img.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
                        //  binding.conectar.setEnabled(true);












                    } else {
                        sms("Ningun dispositivo pudo ser emparejado");

                    }









                }else
                    sms("Debe conectar Bluetooth");





            }
        });
     */
        builder.setCancelable(false);
        return builder.create();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public boolean enviarDatos(String datos) {

      //  Fragment luces=getFragmentManager().findFragmentById(R.id.nav_luces);

        mConnectedThread.write(datos);


        return true;
    }

    @Override
    public void recibirDatos() {

    }

    @Override
    public void cambiar(int n) {

        if(n==1){
            binding.navView.setVisibility(View.VISIBLE);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_luces);


        }
    }

    @Override
    public void cambiar1(int n) {

        if(n==1){


            navController.navigate(R.id.nav_luces);


        }

    }




    //create new class for connect thread
    private class ConnectedThread extends Thread {

        private  BluetoothSocket so;

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            so=socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = so.getInputStream();
                tmpOut = so.getOutputStream();

            } catch (IOException e) {


            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;



            // Keep looping to listen for received messages
            while (true) {

                try {
                    bytes = mmInStream.read(buffer);         //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {


                   // Toast.makeText(getBaseContext(), "Error de recibo", Toast.LENGTH_LONG).show();



                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
               Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                // finish();


               // mostrarDialogo1();

                //navController.navigate(R.id.p);


            }
        }
        private  void mostrarDialogo1(){
            BluetoothAdapter btAdapter1 = BluetoothAdapter.getDefaultAdapter();

            divice= btAdapter1.getBondedDevices();

            //if (divice.size() > 0) {

            items = new CharSequence[divice.size()];
            int cont=0;

            for (BluetoothDevice devices :  divice) {
                //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
                items[cont]=devices.getName() ;
                cont++;
                //mPairedDevicesArrayAdapter.add(devices.getAddress());
            }
            //items[0]="assas" ;

            AlertDialog a=ListDialog(items);
            a.show();
            cont=0;

            // }



        }
    }


    public  void mostrarDialogo(){

        //btAdapter= BluetoothAdapter.getDefaultAdapter();

        divice= btAdapter.getBondedDevices();

        //if (divice.size() > 0) {

        items = new CharSequence[divice.size()];
        int cont=0;

        for (BluetoothDevice devices :  divice) {
            //  mPairedDevicesArrayAdapter.add(devices.getName() + "\n" + devices.getAddress());
            items[cont]=devices.getName() ;
            cont++;
            //mPairedDevicesArrayAdapter.add(devices.getAddress());
        }

        AlertDialog a=ListDialog(items);
        a.show();
        cont=0;

        // }



    }

    private void sms(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onBackPressed() {

       if(!canExitApp){
           canExitApp=true;
           sms("Presione otra vez para salir");
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   canExitApp=false;

               }
           },2000);




       }else{
          finish();

       }
    }
}