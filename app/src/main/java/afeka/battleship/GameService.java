package afeka.battleship;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class GameService extends Service implements SensorEventListener {

    private final IBinder mBinder= new MyLocalBinder();
    private final float SENSITIVE_OF_CHECKING =(float) 0.2;
    private final int SENSOR_DELAY =1000000;
    private final int TIMER_PERIOD = 10000;
    private boolean isSensorExist  = false;
    private Timer clockTimer = new Timer();
    private TimerListener mTimerListener;
    private MySensorListener mMySensorListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private float[] mR = new float[9];
    private float[] firstOrientation = new float[3];
    private float[] mOrientation = new float[3];

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
        mAccelerometer =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) ;

        if(mAccelerometer != null && mMagnetometer != null)
        {
            isSensorExist = true;
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, firstOrientation);
        }
        else {
            //keep playing without sensors
        }
        clock();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("SENSOR","SENSOR");
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

              //  Log.e("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
        //          mOrientation[0], mOrientation[1], mOrientation[2]));
    }

            checkMoves(mOrientation);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class MyLocalBinder extends Binder{
       /* LocalService getService() {
            // returns the local service
            return LocalService.this;
        }*/

        void registerTimeListener(TimerListener listener) {
            mTimerListener = listener;

        }

        void registerSensorListener(MySensorListener listener) {
            if(isSensorExist) {
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
            if(isSensorExist) {
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

        void  moveChanged();

    }

    public void clock() {
        Thread t = new Thread(new Runnable() {
            @Override
           public void run() {

                clockTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {


                        if(mTimerListener!=null)
                        mTimerListener.timePassed();

                    }
                }, 0, TIMER_PERIOD);
            }
        });
        t.start();

    }

    private void checkMoves(float[] vector){

        for(int i=0 ; i<3; i++) {
            if(Math.abs(vector[i]-firstOrientation[i]) < SENSITIVE_OF_CHECKING )
                return;
        }
        mMySensorListener.moveChanged();
    }

}