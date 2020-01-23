package com.flightSimulatorApp.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class JoystickClientActivity extends AppCompatActivity {

    private GetSetClient client;
    private JoystickCommandHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick_client);


        // sets the joystick size to be determine by screen size.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final double width = size.x;
        final double height = size.y;
        final TextView tv = (TextView) findViewById(R.id.textv);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final JoystickView joystick = (JoystickView) findViewById(R.id.joystick);

        // get the ip and port from main activity which creates it.
        Intent intent = getIntent();
        final String ip = intent.getStringExtra("ip");
        final Integer port = Integer.parseInt(intent.getStringExtra("port"));


        // thread to connect to server. return true in case of success.
        AsyncTask<GetSetClient, Void, Boolean> a = new AsyncTask<GetSetClient, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(GetSetClient... getSetClients) {
                try {
                    getSetClients[0].connect();
                } catch (Exception e) {
                    System.out.println(e);
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }

            // setting progress bar to show when trying to connect:


            @Override
            protected void onPreExecute() {

                progressBar.setVisibility(View.VISIBLE);
                joystick.setVisibility(View.INVISIBLE);
                tv.setText("Connecting...");
            }

            @Override
            protected void onPostExecute(Boolean b) {
                progressBar.setVisibility(View.GONE);
                joystick.setVisibility(View.VISIBLE);
                if (!b.booleanValue()) {
                    Toast t = Toast.makeText(getApplicationContext(), "can't connect to server at those ip, port.", Toast.LENGTH_LONG);
                    t.show();
                    finish();
                } else {
                    tv.setText("Connected To Flight Simulator on " + ip + ":" + port.toString());

                    // connects the joystick with listener.
                    joystick.setOnMoveListener(new JoystickCommandHandler(client));
                    joystick.getLayoutParams().width = (int) (0.75 * width);
                    joystick.getLayoutParams().height = (int) (0.75 * height);
                }
            }
        };
        client = new FlightSimulatorClient(ip, port);
        a.execute(client);


    }
    @Override
    protected void onDestroy() {
        try {
            client.disconnect();
        } catch (Exception e) {

        } finally {
            super.onDestroy();

        }
    }


}
