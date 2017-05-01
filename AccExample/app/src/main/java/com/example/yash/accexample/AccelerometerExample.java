package com.example.yash.accexample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerExample extends Activity implements SensorEventListener {
    TextView textView;
    StringBuilder builder = new StringBuilder();

    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};
    MediaPlayer mediaPlayer,mediaPlayer2 ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        MediaPlayer mPlayer;

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.no);
        mediaPlayer.setLooping(true);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.rains);
        mediaPlayer2.setLooping(true);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange > 2){
            direction[0] = "LEFT";

        }
        else if (xChange < -2){
            direction[0] = "RIGHT";
//            mediaPlayer.setLooping(false);
//            mediaPlayer.pause();
        }

        if (yChange > 1){
            direction[1] = "DOWN";
        }
        else if (yChange < -1){
            direction[1] = "UP";
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
//        builder.append(Float.toHexString(yChange));
        builder.append(direction[1]);

        textView.setText(builder.toString());
        if(direction[0] == "LEFT" && direction[1] == "UP")
        {
            if(mediaPlayer2.isPlaying())
                mediaPlayer2.pause();

            mediaPlayer.start();
        }

        if(direction[0] == "RIGHT" && direction[1] == "DOWN")
        {
            if(mediaPlayer.isPlaying())
                mediaPlayer.pause();

            mediaPlayer2.start();
        }

        else {
            if(mediaPlayer.isPlaying())
                mediaPlayer.pause();

            if(mediaPlayer2.isPlaying())
                mediaPlayer2.pause();

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }
}