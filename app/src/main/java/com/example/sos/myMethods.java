package com.example.sos;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class myMethods {
    String word;
    char[] latter;
    String[] code;
    CameraManager cameraManager;
    String camaraId;

    public myMethods() {
    }

    public myMethods(String word, char[] latter, String[] code, CameraManager cameraManager, String camaraId) {
        this.word = word;
        this.latter = latter;
        this.code = code;
        this.cameraManager = cameraManager;
        this.camaraId = camaraId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public char[] getLatter() {
        return latter;
    }

    public void setLatter(char[] latter) {
        this.latter = latter;
    }

    public String[] getCode() {
        return code;
    }

    public void setCode(String[] code) {
        this.code = code;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public String getCamaraId() {
        return camaraId;
    }

    public void setCamaraId(String camaraId) {
        this.camaraId = camaraId;
    }

    public String englishToMorse()
    {
        String morse = "";
        for(int i =0;i<word.length();i++)
        {
            for(int j = 0;j<latter.length;j++)
            {
                if(word.charAt(i)==latter[j])
                {
                    try
                    {
                        morse = morse + code[j];
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        Log.d("soses", "englishToMorse: "+j);
                    }
                }
            }
        }
        return morse;
    }

    public void turnOn() throws CameraAccessException {
        cameraManager.setTorchMode(camaraId,true);
    }
    public void turnOn(int bright) throws CameraAccessException {
        cameraManager.setTorchMode(camaraId,true);
    }

    public void turnOff() throws CameraAccessException {
        cameraManager.setTorchMode(camaraId,false);
    }


    public void blinkOnMorse() throws CameraAccessException, InterruptedException {
        String morse = englishToMorse();
        Log.d("SOSes", "blinkOnMorse: "+morse);
        for(int i=0;i<morse.length();i++)
        {
//            Log.d("SOSes", "blinkOnMorse: "+word.charAt(i));
            if(morse.charAt(i)=='.')
            {
                turnOn();
//                TimeUnit.SECONDS.sleep(Params.shortPose);
                Thread.sleep(500);
                turnOff();
                Thread.sleep(1000);
            }
            else if (morse.charAt(i)=='-')
            {
                turnOn();
                Thread.sleep(1500);
                turnOff();
                Thread.sleep(1000);
            }
            else if (morse.charAt(i)=='/')
            {
                turnOn();
                Thread.sleep(3500);
                turnOff();
                Thread.sleep(1000);
            }
        }
    }

}
