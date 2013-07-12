package com.frozair.androidgames;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerTest extends Activity implements SensorEventListener{
    TextView textView;
    StringBuilder builder = new StringBuilder();

    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        textView = new TextView(this);
        setContentView(textView);

        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0)
            textView.setText("No accelerometer installed");
        else
        {
            // Get accelerometer, it returns a list but there is only 1
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            if(!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME))
                textView.setText("Could not register sensor lister");

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        builder.setLength(0);
        builder.append("x: ");
        builder.append(sensorEvent.values[0]);
        builder.append(", y: ");
        builder.append(sensorEvent.values[1]);
        builder.append(", z: ");
        builder.append(sensorEvent.values[2]);
        textView.setText(builder.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing to do here
    }
}
