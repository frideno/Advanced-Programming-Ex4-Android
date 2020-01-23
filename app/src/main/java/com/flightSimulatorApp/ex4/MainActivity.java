package com.flightSimulatorApp.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add listener to the edit texts so if both not empty the user can press the connect button
        EditText ipView = (EditText) findViewById(R.id.ipInput);
        EditText portView = (EditText) findViewById(R.id.portInput);
        ipView.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        portView.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                EditText ipView = (EditText) findViewById(R.id.ipInput);
                EditText portView = (EditText) findViewById(R.id.portInput);
                Button connectButton = (Button) findViewById(R.id.connectButton);


                // opens joystick activity, only if both fields are not null/"".
                String ip = ipView.getText().toString();
                String port = portView.getText().toString();

                if (ip != null && ip.length() > 0 && port != null && port.length() > 0) {
                    connectButton.setClickable(true);
                    connectButton.setBackgroundColor(Color.GREEN);
                } else {
                    connectButton.setClickable(false);
                    connectButton.setBackgroundColor(Color.RED);
                }
            }

        };
        ipView.addTextChangedListener(tw);
        portView.addTextChangedListener(tw);
        Button connectButton = (Button) findViewById(R.id.connectButton);

        Configuration conf = this.getResources().getConfiguration();
        double p;
        int t;
        if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            p = 0.35;
            t = 950;
        } else {
            p = 0.45;
            t = 550;
        }

        connectButton.getLayoutParams().height = (int) (p * (double) conf.screenHeightDp);
        ipView.getLayoutParams().width = t;
        portView.getLayoutParams().width = t;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration conf) {

        super.onConfigurationChanged(conf);
        EditText ipInput = (EditText) findViewById(R.id.ipInput);
        EditText portInput = (EditText) findViewById(R.id.portInput);
        TextView ipDesc = (TextView) findViewById(R.id.ipLabel);
        TextView portDesc = (TextView) findViewById(R.id.portLabel);
        Button connectButton = (Button) findViewById(R.id.connectButton);

        double p;
        int t;
        if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            p = 0.35;
            t = 950;
        } else {
            p = 0.45;
            t = 550;
        }
        connectButton.getLayoutParams().height = (int) (p * (double) conf.screenHeightDp);
        ipInput.getLayoutParams().width = t;
        portInput.getLayoutParams().width = t;

    }

    // Connect method that happens when the user click 'Connect' Button. it opens the joystick
    // client activity.
    public void connect(View view) {
        EditText ipView = (EditText) findViewById(R.id.ipInput);
        EditText portView = (EditText) findViewById(R.id.portInput);

        String ip = ipView.getText().toString();
        String port = portView.getText().toString();

        // opens joystick activity to the server of ip,port.
        Intent intent = new Intent(this, JoystickClientActivity.class);

        intent.putExtra("ip", ip);
        intent.putExtra("port", port);
        startActivity(intent);
    }




}
