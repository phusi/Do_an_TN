package com.example.phusi.do_an_tn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.support.v4.view.ViewPager;
import android.widget.Switch;

import com.example.phusi.do_an_tn.JoyStickActivity;
import com.example.phusi.do_an_tn.Main_activity;
import com.example.phusi.do_an_tn.MapsActivity;
import com.example.phusi.do_an_tn.R;
import com.example.phusi.do_an_tn.Setting;

public class Connect_Fragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static ClientSocket clientSocket;
    private static boolean connected = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public static class BoardFragment extends Fragment implements ClientSocket.ServerListener, CompoundButton.OnCheckedChangeListener {

        private int sectionNumber;
        private Setting settings;

        private EditText edIP;
        private EditText edPort;
        private Button btConnect;
        private Switch sercurity, Light, Laze, Gps;

        public BoardFragment() {

        }

        public BoardFragment(int sectionNumber) {
            this.sectionNumber = sectionNumber;


        }


        @Override
        public void connectStatusChange(boolean status) {
            connected = status;
            btConnect.setText(status ? "DISCONNECT" : "CONNECT");
            edIP.setEnabled(!status);
            edPort.setEnabled(!status);
            mSectionsPagerAdapter.notifyDataSetChanged();

        }

        @Override
        public void newMessengeFromServer(String messenge) {

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            settings = new Setting(getActivity());
        }

        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {


            switch (sectionNumber) {
                case 1:
                    View v1 = inflater.inflate(R.layout.fragment_connect, container, false);
                    edIP = (EditText) v1.findViewById(R.id.ed_ipAddress);
                    edPort = (EditText) v1.findViewById(R.id.ed_port);
                    btConnect = (Button) v1.findViewById(R.id.bt_connect);

                    if (settings.getString(Setting.IP_ADDRESS) != null) {
                        edIP.setText(settings.getString(Setting.IP_ADDRESS));
                    }
                    if (settings.getInt(Setting.PORT) != -1) {
                        edPort.setText(Integer.toString(settings.getInt(Setting.PORT)));
                    }
                    if (connected) {
                        connectStatusChange(true);
                    }

                    btConnect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!connected) {
                                String ip = edIP.getText().toString();
                                int port = Integer.valueOf(edPort.getText().toString());
                                clientSocket = new ClientSocket(ip, port);
                                clientSocket.setServerListener(BoardFragment.this);
                                btConnect.setText("Connecting...");
                                clientSocket.connect();
                                settings.putString(Setting.IP_ADDRESS, ip);
                                settings.putInt(Setting.PORT, port);
//                                connectStatusChange(true);
                            } else {

                                clientSocket.disconnect();
                                connectStatusChange(false);
                            }

                        }
                    });
                    return v1;
                case 2:

                    View v2 = inflater.inflate(R.layout.fragment_control_car, container, false);
                    Gps = (Switch) v2.findViewById(R.id.Gps);
                    sercurity = (Switch) v2.findViewById(R.id.sercurity);
                    Gps.setChecked(settings.getBoolean(Setting.GPS));
                    sercurity.setChecked(settings.getBoolean(Setting.SERCURITY));

                    Gps.setOnCheckedChangeListener(this);
                    sercurity.setOnCheckedChangeListener(this);
                    return v2;

                case 3:
                    View v3 = inflater.inflate(R.layout.fragment_control_house, container, false);
                    Light = (Switch) v3.findViewById(R.id.Light);
                    Laze = (Switch) v3.findViewById(R.id.Laze);
                    Light.setChecked(settings.getBoolean(Setting.LIGHT));
                    Laze.setChecked(settings.getBoolean(Setting.LAZE));
                    Light.setOnCheckedChangeListener(this);
                    Laze.setOnCheckedChangeListener(this);
                    return v3;
                case 4:
//                    Intent i =  i = new Intent(Connect_Fragment.this, com.example.phusi.do_an_tn.JoyStickActivity.class);
//                    startActivity(i);
//                    View v4 = inflater.inflate(R.layout.activity_joy_stick, container, false);
//                    return v4;

                default:
                    return null;
            }

        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.Gps:
                    if (isChecked) {
                        clientSocket.sendMessenge("gps on");
                    } else {
                        clientSocket.sendMessenge("gps off");
                    }
                    settings.putBoolean(Setting.GPS,isChecked);
                    break;
                case R.id.sercurity:
                    if (isChecked) {
                        clientSocket.sendMessenge("sercurity on");

                    } else {
                        clientSocket.sendMessenge("sercurity off");
                    }
                    settings.putBoolean(Setting.SERCURITY,isChecked);
                    break;
                case R.id.Light:
                    if (isChecked) {
                        clientSocket.sendMessenge("light on");

                    } else {
                        clientSocket.sendMessenge("light off");
                    }
                    settings.putBoolean(Setting.LIGHT,isChecked);
                    break;
                case R.id.Laze:
                    if (isChecked) {
                        clientSocket.sendMessenge("laze on");

                    } else {
                        clientSocket.sendMessenge("laze off");
                    }
                    settings.putBoolean(Setting.LAZE,isChecked);
                    break;


            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            return new BoardFragment(position + 1);


        }


        @Override
        public int getCount() {

//            return  1;
            return connected ? 3 : 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CONNECT";
                case 1:
                    return "CONTROL CAR";
                case 2:
                    return "CONTROL HOME";


            }
            return null;
        }


    }

    public void onStop() {
        super.onStop();
//        if (connected)
//            clientSocket.disconnect();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (!connected) {
//            clientSocket.connect();
//        }
    }


}

