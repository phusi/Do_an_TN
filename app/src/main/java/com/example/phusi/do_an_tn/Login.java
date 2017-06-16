package com.example.phusi.do_an_tn;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phusi.do_an_tn.smart_config.demo_activity.EsptouchDemoActivity;
import com.example.phusi.do_an_tn.Setting;
public class Login extends AppCompatActivity {
    EditText username,password;
    Button  btlogin;
    Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.ed_user);
        password = (EditText)findViewById(R.id.ed_password);
        btlogin  = (Button)findViewById(R.id.btb_sign_in);
        setting = new Setting(this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(username.getText().toString().equals("smartcube") && password.getText().toString().equals("1234")){
                    Intent i = new Intent(Login.this,Main_activity.class);
                    startActivity(i);
                };

            }
        },0);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                setting.putString(Setting.USER,user);
                setting.putString(Setting.PASSWORD,pass);
                    if(username.getText().toString().equals("smartcube") && password.getText().toString().equals("1234"))
                    {
                        Intent i = new Intent(Login.this,Main_activity.class);
                        startActivity(i);
                        finish();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), getString(R.string.not_login), Toast.LENGTH_LONG).show();
                    }

            }
        });
        check_login();
    }
public void check_login(){
    if(setting.getString(Setting.USER)!= null)
    {
        username.setText(setting.getString(Setting.USER));
    }
    if(setting.getString(Setting.PASSWORD)!= null)
    {
        password.setText(setting.getString(Setting.PASSWORD));
    }
}
}
