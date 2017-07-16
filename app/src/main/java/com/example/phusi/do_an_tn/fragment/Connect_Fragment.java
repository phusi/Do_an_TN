package com.example.phusi.do_an_tn.fragment;

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
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.phusi.do_an_tn.MySingleton;
import com.example.phusi.do_an_tn.R;
import com.example.phusi.do_an_tn.Setting;
import com.example.seekarc_library.SeekArc;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class BoardFragment extends Fragment implements  CompoundButton.OnCheckedChangeListener {

        private int sectionNumber;
        private Setting settings;
        private EditText edIP;
        private EditText edPort;
        private Button btConnect;
        private Switch sercurity, Light, Laze, Gps;
        private TextView txtTemp,txtHum,txtGas,txtLight;
        private TextView txtTemp_1,txtHum_1,txtGas_1,txtLight_1;
        private TextView mSeekArcProgress;
        private Switch sw1,sw2;
        private SeekArc mSeekTemp,mSeekHum;
        private SeekArc mSeekTemp_1,mSeekHum_1;
        private ProgressBar progressBar;
        private ProgressBar progressBar_1;
        private HttpURLConnection connection;
        private ToggleButton toggleFire;
//        private HttpResponse response = null;

        String urlGetData = "http://smartcube.ga/dashboard/includes/data.php?getData";
        String urlGetData_1 = "http://smartcube.ga/dashboard/includes/data_1.php?getData";
        String url_sw1_true = "http://smartcube.ga/dashboard/ajax/switch/switch_1.php?checked=true";
        String url_sw1_false = "http://smartcube.ga/dashboard/ajax/switch/switch_1.php?checked=false";
        String url_sw2_true = "http://smartcube.ga/dashboard/ajax/switch/switch_2.php?checked=true";
        String url_sw2_false = "http://smartcube.ga/dashboard/ajax/switch/switch_2.php?checked=false";
        final String TAG = this.getClass().getSimpleName();

        public BoardFragment() {}

        public BoardFragment(int sectionNumber) {
            this.sectionNumber = sectionNumber;
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
                    View v1 = inflater.inflate(R.layout.fragment_control_house, container, false);
                    txtTemp = (TextView) v1.findViewById(R.id.temp);
                    txtHum = (TextView) v1.findViewById(R.id.txtHum);
                    txtGas = (TextView) v1.findViewById(R.id.gas);
                    txtLight = (TextView) v1.findViewById(R.id.light);
                    sw1 = (Switch) v1.findViewById(R.id.lamp);
                    sw2 = (Switch) v1.findViewById(R.id.fan);
                    mSeekTemp = (SeekArc) v1.findViewById(R.id.seekTemp);
                    mSeekHum = (SeekArc) v1.findViewById(R.id.seekHum);
                    progressBar = (ProgressBar) v1.findViewById(R.id.progressBar2);
                    progressBar.setVisibility(View.GONE);

                    Timer timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            /*đọc json*/
                            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, urlGetData, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                Toast.makeText(Main_activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                    try {
                                        String temp = response.getString("temp");
                                        String hum = response.getString("hum");
                                        String light = response.getString("light");
                                        String smoke = response.getString("smoke");
                                        String switch1 = response.getString("sw1");
                                        String switch2 = response.getString("sw2");

//                                Toast.makeText(getActivity(), temp, Toast.LENGTH_SHORT).show();
                                        txtTemp.setText(temp+"°C");
                                        mSeekTemp.setProgress(Integer.parseInt(temp));
                                        txtHum.setText(hum+"%");
                                        mSeekHum.setProgress(Integer.parseInt(hum));
                                        txtGas.setText(smoke);
                                        txtLight.setText(light);
                                        if (switch1.equals("1")){
                                            sw1.setChecked(true);
                                        }
                                        else {
                                            sw1.setChecked(false);
                                        }
                                        if (switch2.equals("1") ){
                                            sw2.setChecked(true);
                                        }
                                        else {
                                            sw2.setChecked(false);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                }


                            });

                            MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);//get data
        /*  ******************************************   */
                        }
                    },10,3000);



                    sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if(isChecked){
//                                Log.v("Switch State=", ""+isChecked);
//                                Toast.makeText(getActivity(), isChecked, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                StringRequest stringRequest= new StringRequest(Request.Method.GET, url_sw1_true, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                            }
                            else {
                                progressBar.setVisibility(View.VISIBLE);
                                StringRequest stringRequest= new StringRequest(Request.Method.GET, url_sw1_false, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                            }
                        }
                    });

                    sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b){
//                                Log.v("Switch State=", ""+isChecked);
//                                Toast.makeText(getActivity(), isChecked, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                StringRequest request= new StringRequest(Request.Method.GET, url_sw2_true, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                MySingleton.getInstance(getActivity()).addToRequestQueue(request);
                            }
                            else {
                                progressBar.setVisibility(View.VISIBLE);
                                StringRequest request = new StringRequest(Request.Method.GET, url_sw2_false, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                MySingleton.getInstance(getActivity()).addToRequestQueue(request);
                            }
                        }
                    });

                    return v1;

                case 2:

                    View v2 = inflater.inflate(R.layout.fragment_control_car, container, false);
                    txtTemp_1 = (TextView) v2.findViewById(R.id.temp_1);
                    txtHum_1 = (TextView) v2.findViewById(R.id.txtHum_1);
                    txtGas_1 = (TextView) v2.findViewById(R.id.gas_1);
//                    txtLight_1 = (TextView) v2.findViewById(R.id.light_1);
                    mSeekTemp_1 = (SeekArc) v2.findViewById(R.id.seekTemp_1);
                    mSeekHum_1 = (SeekArc) v2.findViewById(R.id.seekHum_1);
                    progressBar_1 = (ProgressBar) v2.findViewById(R.id.progressBar3);
                    toggleFire = (ToggleButton) v2.findViewById(R.id.imageFire1);
                    progressBar_1.setVisibility(View.GONE);

                    Timer timer_1 = new Timer();

                    timer_1.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            /*đọc json*/
                            JsonObjectRequest objectRequest= new JsonObjectRequest(Request.Method.GET, urlGetData_1, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                Toast.makeText(Main_activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                    try {
                                        String temp = response.getString("temp");
                                        String hum = response.getString("hum");
                                        Integer fire = response.getInt("fire");
                                        String smoke = response.getString("smoke");

                                        txtTemp_1.setText(temp+"°C");
                                        mSeekTemp_1.setProgress(Integer.parseInt(temp));
                                        txtHum_1.setText(hum+"%");
                                        mSeekHum_1.setProgress(Integer.parseInt(hum));
                                        txtGas_1.setText(smoke);

                                        if (fire == 1){
                                            toggleFire.setChecked(true);
                                        }else toggleFire.setChecked(false);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                }


                            });

                            MySingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);//get data
        /*  ******************************************   */
                        }
                    },30,3000);

                    return v2;

//                case 3:
//                    View v3 = inflater.inflate(R.layout.fragment_control_house, container, false);
////                    Light = (Switch) v3.findViewById(R.id.Light);
////                    Laze = (Switch) v3.findViewById(R.id.Laze);
//                    Light.setChecked(settings.getBoolean(Setting.LIGHT));
//                    Laze.setChecked(settings.getBoolean(Setting.LAZE));
//                    Light.setOnCheckedChangeListener(this);
//                    Laze.setOnCheckedChangeListener(this);
//                    return v3;

                default:
                    return null;
            }

        }




        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
//                case R.id.Gps:
//                    if (isChecked) {
//                        clientSocket.sendMessenge("gps on");
//                    } else {
//                        clientSocket.sendMessenge("gps off");
//                    }
//                    settings.putBoolean(Setting.GPS,isChecked);
//                    break;
//                case R.id.sercurity:
//                    if (isChecked) {
//                        clientSocket.sendMessenge("sercurity on");
//
//                    } else {
//                        clientSocket.sendMessenge("sercurity off");
//                    }
//                    settings.putBoolean(Setting.SERCURITY,isChecked);
//                    break;
//                case R.id.Light:
//                    if (isChecked) {
//                        clientSocket.sendMessenge("light on");
//
//                    } else {
//                        clientSocket.sendMessenge("light off");
//                    }
//                    settings.putBoolean(Setting.LIGHT,isChecked);
//                    break;

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
            return  2;
//            return connected ? 3 : 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Station A";
                case 1:
                    return "Station B";
//                case 2:
//                    return "CONTROL HOME";
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

