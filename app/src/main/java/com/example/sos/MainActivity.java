package com.example.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    CameraManager cameraManager;
    String camaraId;

    EditText editText;

    Button transmit,flash;
    
    SeekBar seekBar;

    int brightness=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        transmit = findViewById(R.id.button1);
        seekBar = findViewById(R.id.seekBar);
        flash = findViewById(R.id.button);
        seekBar.setMax(5);
        seekBar.setMin(1);
        seekBar.setEnabled(false);

        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
            camaraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Log.d("SOSes", "onCreate: "+e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        transmit.setOnClickListener(v->{
            if(editText.getText()!=null)
            {
                myMethods methods = new myMethods(editText.getText().toString().toLowerCase(),Params.latter,Params.code,cameraManager,camaraId);
                Log.d("SOSes", "onResume: "+methods.englishToMorse());
                try {
                    methods.blinkOnMorse();
                } catch (CameraAccessException | InterruptedException e) {
                    Log.d("SOSes", "onResume: "+e.getMessage());
                }
            }
            else Toast.makeText(this, "There is nothig to transmit", Toast.LENGTH_SHORT).show();
        });

        transmit.setOnLongClickListener(v->{
            myMethods methods = new myMethods("sos",Params.latter,Params.code,cameraManager,camaraId);
            Log.d("SOSes", "onResume: "+methods.englishToMorse());
            try {
                methods.blinkOnMorse();
            } catch (CameraAccessException | InterruptedException e) {
                Log.d("SOSes", "onResume: "+e.getMessage());
            }
            return true;
        });
        
        flash.setOnClickListener(v->{
            if(flash.getText().toString().equalsIgnoreCase("flash"))
            {
                try {
                    cameraManager.setTorchMode(camaraId,true);
                    flash.setText("OFF");
                    seekBar.setEnabled(true);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(flash.getText().toString().equalsIgnoreCase("off"))
            {
                try {
                    seekBar.setProgress(1);
                    cameraManager.setTorchMode(camaraId,false);
                    flash.setText("Flash");
                    seekBar.setEnabled(false);

                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                brightness = i;
                try {
                    cameraManager.turnOnTorchWithStrengthLevel(camaraId,i);
                } catch (CameraAccessException e) {
//                    throw new RuntimeException(e);
//                    Toast.makeText(MainActivity.this, "Your device don't support this feature", Toast.LENGTH_SHORT).show();
//                    seekBar.setEnabled(false);
                }
                catch (IllegalArgumentException iae){
                    Toast.makeText(MainActivity.this, "Your device don't support this feature", Toast.LENGTH_SHORT).show();
                    seekBar.setProgress(1);
                    seekBar.setEnabled(false);
                }
                flash.setText("OFF");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        
        
        
    }
}