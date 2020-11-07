package com.scrappers.sensortraining;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener2 {
    SensorManager sensorManager;
    Sensor gyroscope;
    double OmegaX=0.0;
    double OmegaY=0.0;
    double OmegaZ=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Run_Sensor();
    }

    public void Run_Sensor(){
        try {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if ( sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null ){
                gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                sensorManager.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(getApplicationContext(), "No Gyroscope is found in this device !", Toast.LENGTH_LONG).show();
            }
        }catch (Exception error){
            error.printStackTrace();
        }

    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        //now we are to get rate of motion of (Angular velocity around) x , y ,z in rad/s
        try {
            new CountDownTimer(10000,100){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    //getting Angular Velocity due to rotation around x-axis
                    OmegaX =event.values[0];
                    //getting Angular Velocity due to rotation around y-axis
                    OmegaY = event.values[1];
                    //getting Angular Velocity due to rotation around z-axis
                    OmegaZ = event.values[2];
                    Toast.makeText(getApplicationContext(),"Omega at x = " + OmegaX + "\n" +
                            "Omega at y = " + OmegaY + "\n" +
                            "Omega at z = " + OmegaZ + "\n",Toast.LENGTH_LONG).show();
                    this.cancel();
                }
            }.start();

        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sensorManager.unregisterListener(this,gyroscope);
    }
}
