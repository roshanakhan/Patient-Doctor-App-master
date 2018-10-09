package com.app.patient;

import android.content.res.Configuration;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera;
import android.os.PowerManager.WakeLock;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class heartrate extends AppCompatActivity /*implements SensorEventListener*/ {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_heartrate);
    }

    private static final int beatsArraySize = 3;
    private static final int[] averageArray = new int[4];
    private static final int[] beatsArray = new int[beatsArraySize];
    private static final int averageArraySize = 4;
    private static int averageIndex = 0;
    private static long starttime = 0;
    private static int beatsIndex = 0;

    private static WakeLock wakelock = null;


    public static enum thing {
        GREEN, RED
    }

    ;


    private static thing currentType = thing.GREEN;

    public static thing getCurrent() {
        return currentType;
    }


    private static SurfaceHolder surface;
    private static Camera camera;

    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static double beats = 0;

    public void onConfiguration(Configuration config) {
        super.onConfigurationChanged(config);
        /*if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }*/
    }

    protected void onResume() {
        super.onResume();
        wakelock.acquire();
        //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        camera = Camera.open();
        starttime = System.currentTimeMillis();
    }

    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener((SensorEventListener) this);
        wakelock.release();
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;

    }

    private static PreviewCallback previewCallback = new PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {
            if (bytes == null)
                throw new NullPointerException();

            Camera.Size size = camera.getParameters().getPreviewSize();

            if (size == null)
                throw new NullPointerException();

            if (!processing.compareAndSet(false, true)) return;

            int width = size.width;
            int height = size.height;

            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(bytes.clone(), height, width);

            if (imgAvg == 0 | imgAvg == 255) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCount = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCount++;
                }
            }

            int rollingAverage = (averageArrayCount > 0) ? (averageArrayAvg / averageArrayCount) : 0;
            thing newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = thing.RED;
                if (newType != currentType) {
                    beats++;
                }
            } else if (imgAvg > rollingAverage) {
                newType = thing.GREEN;
            }
            if (averageIndex == averageArraySize) {
                averageIndex = 0;
            }
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            if (newType != currentType) {
                currentType = newType;
            }

            long endtime = System.currentTimeMillis();
            double totalTime = (endtime - starttime) / 1000;
            if (totalTime >= 10) {
                double bps = (beats / totalTime);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180) {
                    starttime = System.currentTimeMillis();
                    beats = 0;
                    processing.set(false);
                    return;
                }

                if (beatsIndex == beatsArraySize) {
                    beatsIndex = 0;
                    beatsArray[beatsIndex] = dpm;
                    beatsIndex++;
                }

                int beatsArrayAvg = 0;
                int beatsArrayCount = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCount++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCount);

            }
            processing.set(false);


        }

        ;
    };

    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

            try {
                camera.setPreviewDisplay(surface);
            } catch (Throwable throwable) {
                Log.e("", "", throwable);
            }


        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(i1, i2, parameters);
            if(size!=null){
                parameters.setPreviewSize(size.width, size.height);

            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder){

        }
    };

    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters){
        Camera.Size result = null;

        for(Camera.Size size : parameters.getSupportedPreviewSizes()){
            if(size.width<= width && size.height<=height){
                if(result == null){
                    result = size;
                }else{
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if(newArea<resultArea){
                        result = size;
                    }

                }
            }
        } return result;
    }

}