package afeka.battleship;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Binder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class GameService extends Service implements SensorEventListener,LocationListener {
    //sensors
    private final float SENSITIVE_OF_CHECKING = (float) 0.2;
    private final int SENSOR_COUNTER = 5;
    private final int TIMER_PERIOD = 10000;

    private MySensorListener mMySensorListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private int counterSamples = 0;
    private boolean isSensorExist = false;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private float[][] mLastOrientationArr = new float[SENSOR_COUNTER][3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] firstOrientation = new float[3];
    private float[] mOrientation = new float[3];

    //binder
    private final IBinder mBinder = new MyLocalBinder();
    //location
    private Location lastLocation;
    LocationManager locationManager;
    //timer
    private Timer clockTimer = new Timer();
    private TimerListener mTimerListener;




    public GameService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (mAccelerometer != null && mMagnetometer != null) { //first orientation
            isSensorExist = true;
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, firstOrientation);
        }
        clock();
        setLocation();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);

          //   Log.e("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
          //            mOrientation[0], mOrientation[1], mOrientation[2]));
        }
       // mLastOrientationArr[counterSamples++] = mOrientation;
        System.arraycopy(mOrientation,0,mLastOrientationArr[counterSamples++],0,mOrientation.length);

        if (counterSamples == SENSOR_COUNTER) {
            checkMoves(mLastOrientationArr);
            counterSamples = 0;
        }

    }


    public class MyLocalBinder extends Binder {
        GameService getService() {
            // returns the local service
            return GameService.this;
        }

        void registerTimeListener(TimerListener listener) {
            mTimerListener = listener;

        }

        void registerSensorListener(MySensorListener listener) {
            if (isSensorExist) {
                mLastAccelerometerSet = false;

                mLastMagnetometerSet = false;
                mSensorManager.registerListener(GameService.this, mAccelerometer, mSensorManager.SENSOR_DELAY_NORMAL);
                mSensorManager.registerListener(GameService.this, mMagnetometer, mSensorManager.SENSOR_DELAY_NORMAL);

                mMySensorListener = listener;
            }
        }

        void DeleteTimerListener() {
            mTimerListener = null;

        }

        void DeleteSensorListener() {
            if (isSensorExist) {
                mSensorManager.unregisterListener(GameService.this, mAccelerometer);
                mSensorManager.unregisterListener(GameService.this, mMagnetometer);

                mMySensorListener = null;
            }
        }

    }

    public interface TimerListener {

        void timePassed();

    }

    public interface MySensorListener {

        void moveChanged();

    }

    public void clock() {
        Thread t = new Thread(new Runnable() { //start an event every few seconds
            @Override
            public void run() {

                clockTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {


                        if (mTimerListener != null)
                            mTimerListener.timePassed();

                    }
                }, 0, TIMER_PERIOD);
            }
        });
        t.start();

    }

    private void checkMoves(float[][] mLastOrientationArr) { // check if there is exceptional move
        float[]vector = avgMoves(mLastOrientationArr);

        for (int i = 0; i < vector.length; i++) {
            if (Math.abs(vector[i] - firstOrientation[i]) < SENSITIVE_OF_CHECKING)
                return;
        }

        mMySensorListener.moveChanged();
    }

    private float[] avgMoves(float[][] matMoves) { //calculate average of last moves
        float avgArr[] = new float[matMoves[0].length], sum = 0;


        for (int i = 0; i < matMoves[0].length; i++) {
            for (int j = 0; j < matMoves.length; j++) {
                sum += matMoves[j][i];
            }
            avgArr[i] = sum / matMoves.length;
            sum=0;
        }
        return avgArr;
    }
    private void setLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    public Location getLastLocation() {
        return lastLocation;
    }

}
