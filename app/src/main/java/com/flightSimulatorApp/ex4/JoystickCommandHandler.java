package com.flightSimulatorApp.ex4;

import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

public class JoystickCommandHandler implements JoystickView.OnMoveListener {

    private GetSetClient client;
    private final String AILERON_LOCATION = "/controls/flight/aileron";
    private final String ELEVATOR_LOCATION = "/controls/flight/elevator";


    public JoystickCommandHandler(GetSetClient client) {
        this.client = client;
    }

    //  if the joystick is not at 0,0 then it is set, and we send the values to server.
    @Override
    public void onMove(int angle, int strength) {

        if (client != null && angle != 0 && strength != 0) {

            // get aileron, elevator from joystick, by calculation - from cartezian to algebrian format.
            final Double aileron = (Math.cos(Math.toRadians(angle)) * strength) / 100;
            final Double elevator = (Math.sin(Math.toRadians(angle)) * strength) / 100;


            AsyncTask<GetSetClient, Void, Void> a = new AsyncTask<GetSetClient, Void, Void>() {

                @Override
                protected Void doInBackground(GetSetClient... c) {

                    // send aileron, elevator to server.
                    c[0].set(AILERON_LOCATION, aileron.toString());
                    c[0].set(ELEVATOR_LOCATION, elevator.toString());
                    return null;
                }

            };
            a.execute(client);

        }
    }
}
