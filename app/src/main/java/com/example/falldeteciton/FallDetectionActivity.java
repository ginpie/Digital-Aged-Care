package com.example.falldeteciton;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class FallDetectionActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mySensorManager;
    public Sensor myAccelerometer;

    private final static String TAG = FallDetectionActivity.class.getSimpleName();
    private TextView xText, yText, zText;

    private LineChart myChart;
    private Thread thread;
    private boolean plotData = true;

    /** Call when the activity is first created */
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_falldetection);

        // get an instance of the SensorManager
        Log.d(TAG, "onCreate: Initializing sensor service.");
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // get an instance of the Accelerometer
        Log.d(TAG, "onCreate: Initializing accelerometer.");
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // register the sensor listener
        mySensorManager.registerListener(FallDetectionActivity.this, myAccelerometer, mySensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Accelerometer listener is registered.");

        // assign TextViews
        xText = (TextView) findViewById(R.id.textViewX);
        yText = (TextView) findViewById(R.id.textViewY);
        zText = (TextView) findViewById(R.id.textViewZ);

        // initialize LineChart
        myChart = (LineChart) findViewById(R.id.lineChart);
        myChart.getDescription().setEnabled(true);
        myChart.getDescription().setText("Real Time Accelerometer Data");
        myChart.getDescription().setTextSize(15f);

        myChart.setTouchEnabled(false);
        myChart.setDragEnabled(false);
        myChart.setScaleEnabled(false);
        myChart.setDrawGridBackground(false);
        myChart.setPinchZoom(false);
        myChart.setBackgroundColor(Color.LTGRAY);

        LineData lineData = new LineData();
        lineData.setValueTextColor(Color.WHITE);
        myChart.setData(lineData);

        // get the legend
        Legend l = myChart.getLegend();
        // modify the legend
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLACK);

        XAxis xl = myChart.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setTextSize(15f);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        YAxis yl = myChart.getAxisLeft();
        yl.setTextColor(Color.BLACK);
        yl.setTextSize(20f);
        yl.setDrawGridLines(false);
        yl.setAxisMaximum(10f);
        yl.setAxisMinimum(-10f);
        yl.setDrawGridLines(true);

        YAxis yr = myChart.getAxisRight();
        yr.setEnabled(false);

        myChart.getAxisLeft().setDrawGridLines(false);
        myChart.getXAxis().setDrawGridLines(false);
        myChart.setDrawBorders(false);

        // Start to plot
        startPlot();
    }

    private void startPlot(){
        if (thread != null){
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    plotData = true;
                    try {
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (thread != null){
            thread.interrupt();
        }
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: ||| X:" + event.values[0] + ", Y:" + event.values[1] + ", Z:" + event.values[2]);
        DecimalFormat df = new DecimalFormat("#.#");
        float x2 = Float.valueOf(df.format(event.values[0]));
        float y2 = Float.valueOf(df.format(event.values[1]));
        float z2 = Float.valueOf(df.format(event.values[2]));

        xText.setText("X: " + x2);
        yText.setText("Y: " + y2);
        zText.setText("Z: " + z2);

        if (plotData){
            addEntry(event);
            plotData = false;
        }
    }

    private void addEntry(SensorEvent event){
        LineData lineData = myChart.getData();

        if (lineData != null){
            ILineDataSet set = lineData.getDataSetByIndex(0);

            if (set == null){
                set = createSet();
                lineData.addDataSet(set);
            }

            // add entries of xValues to the data
            lineData.addEntry(new Entry(set.getEntryCount(), event.values[0]), 0);
            // enable the chart know when its data has changed
            lineData.notifyDataChanged();
            myChart.notifyDataSetChanged();
            myChart.setMaxVisibleValueCount(1500);
            myChart.moveViewToX(lineData.getEntryCount());
        }
    }

    private LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, "dynamic data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(4f);
        set.setColor(Color.DKGRAY);
        set.setHighlightEnabled(false);
        set.setDrawValues(true);
        set.setDrawCircleHole(false);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setCubicIntensity(0.2f);
        return set;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    @Override
    protected void onDestroy() {
        mySensorManager.unregisterListener(FallDetectionActivity.this);
        thread.interrupt();
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
