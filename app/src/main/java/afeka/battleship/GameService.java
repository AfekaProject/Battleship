package afeka.battleship;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Binder;
import android.support.v4.app.ActivityCompat;
import java.util.Timer;
import java.util.TimerTask;

public class GameService extends Service implements SensorEventListener, LocationListener {
    //sensors
    private final float SENSITIVE_OF_CHECKING = (float) 0.2;
    private final int SENSOR_COUNTER = 15;
    private final int TIMER_PERIOD = 30*1000;

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
    private LocationManager locationManager;

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
    public boolean onUnbind(Intent intent) {
        locationManager.removeUpdates(this);
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSensors();
        clock();
        setLocation();
    }

private void initSensors(){
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (mSensorManager != null) {
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    if (mAccelerometer != null && mMagnetometer != null) { //first orientation
        isSensorExist = true;
        SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
        SensorManager.getOrientation(mR, firstOrientation);
    }
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

        }
        System.arraycopy(mOrientation, 0, mLastOrientationArr[counterSamples++], 0, mOrientation.length);

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
        void onOrientationChanged();
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
        float[] vector = avgMoves(mLastOrientationArr);
        for (int i = 0; i < vector.length; i++) {
            if (Math.abs(vector[i] - firstOrientation[i]) < SENSITIVE_OF_CHECKING)
                return;
        }
        mMySensorListener.onOrientationChanged();
    }

    private float[] avgMoves(float[][] matMoves) { //calculate average of last moves
        float avgArr[] = new float[matMoves[0].length], sum = 0;
        for (int i = 0; i < matMoves[0].length; i++) {
            for (int j = 0; j < matMoves.length; j++) {
                sum += matMoves[j][i];
            }
            avgArr[i] = sum / matMoves.length;
            sum = 0;
        }
        return avgArr;
    }

    private void setLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            lastLocation = setDummy();
            return;
        }
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
           lastLocation = setDummy();
        }
        else{

            Criteria crit = new Criteria();
            crit.setAccuracy(Criteria.ACCURACY_FINE);
            crit.setPowerRequirement(Criteria.NO_REQUIREMENT);
            lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, true));
            if (gps_enabled){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            else if (network_enabled){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
    }

    public Location setDummy (){
        Location lastLocation = new Location("dummy");
        lastLocation.setLatitude(32.113086);
        lastLocation.setLongitude(34.818021);
        return lastLocation;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location!=null)
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
