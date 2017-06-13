package com.example.logonrm.appboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by logonrm on 12/06/2017.
 */

public class BoundService extends Service{

    private static String LOG_TAG="BoundService";
    public IBinder mBinder = new MyBinder();
    private Chronometer mChronometer;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.v(LOG_TAG,"in onCreate");
        mChronometer =new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    @Override
    public IBinder onBind(Intent intent){
        Log.v(LOG_TAG,"in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG,"in onReBind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG,"in onUnBind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG,"in onDestroy");
        mChronometer.stop();
    }

    public String getTimestamp(){
        long elapseMillis = SystemClock.elapsedRealtime()
                            - mChronometer.getBase();

        int hours = (int) (elapseMillis / 3600000);
        int minutes= (int)(elapseMillis - hours) / 60000;
        int seconds= (int)(elapseMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis =(int)(elapseMillis - hours * 3600000 - minutes * 6000 - seconds *1000);

        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }

    public class MyBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }

}
