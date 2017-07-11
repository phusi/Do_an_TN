package com.example.phusi.do_an_tn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.phusi.do_an_tn.fragment.ClientSocket;
import com.example.seekarc_library.SeekArc;
import com.example.phusi.do_an_tn.Setting;

import java.util.Timer;

import customviews.JoyStickView;


public class JoyStickActivity extends Activity implements ClientSocket.ServerListener{
    private JoyStickView joyStickView;
    private ClientSocket clientSocket;
    private SeekArc mSeekArc;
    private TextView mSeekArcProgress;
    private TextView nhietdo1;
    private TextView kcach1;
    private TextView kcachtrai1;
    private TextView kcachphai1;
    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private ToggleButton toggleButton;


    private Setting settings;
    private Animation btAnim;
    private boolean socketConnected = false;
    private String messenge;
    private String send;
    private String messenge1;
    private boolean connected = false;
    private Timer timer;

    protected int getLayoutFile() {
        return R.layout.activity_joy_stick;
    }
    int port;

    public int flag=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_joy_stick);
        initViews();
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//               // if (flag==0) {
//                    clientSocket.sendMessenge("DKC");
//                 //       flag=1;
//                //}
//            }
//        },5000,6000);

        nhietdo1 = (TextView) findViewById(R.id.nhietdo1);
        kcach1=(TextView)findViewById(R.id.kcach1);
        kcachphai1=(TextView)findViewById(R.id.kcachphai1);
        kcachtrai1=(TextView)findViewById(R.id.kcachtrai1);
//        button=(Button) findViewById(R.id.button);
//        button1=(Button)findViewById(R.id.button1);

        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);

        mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
                mSeekArcProgress.setText(String.valueOf(progress));


            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                joyStickView.setClientSocket(clientSocket);
//                clientSocket.sendMessenge("speed:");
                String messenge3 = mSeekArcProgress.getText().toString();
                clientSocket.sendMessenge("speed"+messenge3);
            }
        });


        settings = new Setting(this);
        clientSocket = new ClientSocket(settings.getString(Setting.IP_ADDRESS), settings.getInt(Setting.PORT));
        clientSocket.setServerListener(this);
        clientSocket.connect();


        joyStickView = (JoyStickView) findViewById(R.id.joy_stick_view);

        toggleButton1 = (ToggleButton)findViewById(R.id.toggleButton1);
        toggleButton2 = (ToggleButton)findViewById(R.id.toggleButton2);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton.isChecked()){
                    clientSocket.sendMessenge("MODE1");
                }
                else {
                    clientSocket.sendMessenge("MODE0");

                }
            }
        });

        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton1.isChecked()){
                    clientSocket.sendMessenge("ONSAU");
                }
                else {
                    clientSocket.sendMessenge("OFFSAU");

                }
            }
        });
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton2.isChecked()){
                    clientSocket.sendMessenge("ONTRUOC");
                }
                else {
                    clientSocket.sendMessenge("OFFTRUOC");

                }
            }
        });



    }

    private void initViews() {
        btAnim = AnimationUtils.loadAnimation(this, R.anim.bt_anim);
//        btT = (Button) findViewById(R.id.bt_t);

        // prSpeed = (ProgressBar) findViewById(R.id.pr_speed);
        joyStickView = (JoyStickView) findViewById(R.id.joy_stick_view);
        //joyStickView.setSpeedProgress(prSpeed);

    }


    @Override
    protected void onStart() {
        super.onStart();
        clientSocket.connect();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        clientSocket.disconnect();
//    }

    @Override
    public void connectStatusChange(boolean status) {
        this.socketConnected = status;
        if (status) {
            joyStickView.setClientSocket(clientSocket);
            clientSocket.sendMessenge("OK");
        }
    }

    @Override
    public void newMessengeFromServer(String messenge) {
////        Intent i = getIntent();
////        String a = i.getStringExtra("msg");
//        String a;
//        String Find1 = "kcg";
//        String Find2 = "kcp";
//        String Find3 = "kct";
//        String Find4 = "ndo";

        String result[] = messenge.split("[:]");
        for (int i=0;i<result.length;i++){
                if(result[3].contains("ACK"))
                {
                    result[0].replace("-","0");
                    result[1].replace("-","0");
                    result[2].replace("-","0");
                    kcachtrai1.setText(result[0]);
                    kcach1.setText(result[1]);
                    kcachphai1.setText(result[2]);
                  //  flag=0;
                }

//
//            if (result[i].contains(Find2)){
//                StringBuffer n =new StringBuffer( result[i]).reverse();
//                n.delete(0,3);
//                a = new StringBuffer(n).reverse().toString();
//                kcachphai1.setText(a);
//            }
//            if (result[i].contains(Find3)){
//                StringBuffer n =new StringBuffer( result[i]).reverse();
//                n.delete(0,3);
//                a = new StringBuffer(n).reverse().toString();
//                kcachtrai1.setText(a);
//            }
//            if (result[i].contains(Find4)){
//                StringBuffer n =new StringBuffer( result[i]).reverse();
//                n.delete(0,3);
//                a = new StringBuffer(n).reverse().toString();
//                nhietdo1.setText(a);
//            }
        }
//        String a = messenge;
//        String Find1 = "kcg";
//        String Find2 = "kcp";
//        String Find3 = "kct";
//        String Find4 = "ndo";
//        if(a.contains(Find1)) {
//            StringBuffer n = new StringBuffer(a).reverse();
//            n.delete(0,4);
//            a = new StringBuffer(n).reverse().toString();
//            kcach1.setText(a);
//        }
//        if(a.contains(Find2)) {
//            StringBuffer n = new StringBuffer(a).reverse();
//            n.delete(0,4);
//            a = new StringBuffer(n).reverse().toString();
//            kcachphai1.setText(a);
//        }
//        if(messenge.contains(Find3)) {
//            StringBuffer n = new StringBuffer(messenge).reverse();
//            n.delete(0,4);
//            messenge = new StringBuffer(n).reverse().toString();
//            kcachtrai1.setText(messenge);
//        }
//        if(messenge.contains(Find4)) {
//            StringBuffer n = new StringBuffer(messenge).reverse();
//            n.delete(0,4);
//            messenge = new StringBuffer(n).reverse().toString();
//            nhietdo1.setText(messenge);
//        }
    }
    public void buzzer(View view)
    {
        switch (view.getId()){
            case R.id.button:
                clientSocket.sendMessenge("ONLOA");
            default:

        }
    }



    //    public void onClick(View v) {
//        v.startAnimation(btAnim);
//        String messenge2;
////        if (!socketConnected) {
////            return;
////        }
//        switch (v.getId()) {
//            case R.id.bt_t:
//               messenge2 ="DKC";
//                messenge2 = messenge2 + "\n";
//                Toast.makeText(this, "Do khoang cach", Toast.LENGTH_SHORT).show();
//                clientSocket.sendMessenge(messenge2);
//                break;
////            case R.id.button:
////                clientSocket.sendMessenge("DT");
////                break;
////            case R.id.button1:
////                clientSocket.sendMessenge("DS");
////                break;
////            case R.id.bt_r:
////                clientSocket.sendMessenge("DBR;");
////                break;
////            case R.id.bt_t:
////                clientSocket.sendMessenge("DBT;");
////                break;
//            default:
//                messenge2 = "xxx";
//        }
//   }
    protected void onStop() {
        super.onStop();
        if(this.connected){
            clientSocket.disconnect();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!connected){
            clientSocket.connect();
        }
    }
//    public void onBackPressed() {
//       clientSocket.disconnect();
//        finish();
//    }

}
