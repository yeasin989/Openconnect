package com.test.testproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.oneconnect.top.Active;
import com.oneconnect.top.OneConnect;
import com.oneconnect.top.informaction;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.textview);

        /*Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OneConnect oneConnect = new OneConnect();
                    oneConnect.initialize(MainActivity.this, "qGjWrUnCh6O9UeUW8LHlGEULofZmW.7crqhXrS0ZYBaBQxclZY");
                    try {
                        String FREE_SERVERS = oneConnect.fetch(true);
                        String PREMIUM_SERVERS = oneConnect.fetch(false);
                        textview.setText(FREE_SERVERS+PREMIUM_SERVERS);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();*/


        informaction informaction = new informaction(this);
        informaction.infopkg();

        Active active = new Active(this);
        active.checkActive();


    }
}