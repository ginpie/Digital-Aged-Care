package com.example.falldeteciton;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FallDetectionActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mySensorManager;
    public Sensor myAccelerometer;
    private final static String TAG = FallDetectionActivity.class.getSimpleName();

    /** Call when the activity is first created */
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        // get an instance of the SensorManager
        Log.d(TAG, "onCreate: Initializing sensor service.");
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // get an instance of the Accelerometer
        Log.d(TAG, "onCreate: Initializing accelerometer.");
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // register the sensor listener
        mySensorManager.registerListener(FallDetectionActivity.this, myAccelerometer, mySensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Accelerometer listener is registered.");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: X:" + event.values[0] + ", Y:" + event.values[1] + ", Z:" + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
