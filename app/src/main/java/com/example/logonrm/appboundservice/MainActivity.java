package com.example.logonrm.appboundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BoundService mBoundeService;
    boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timestampText = (TextView) findViewById(R.id.tvTempo);
        Button btprintTimeStamp = (Button) findViewById(R.id.btPrintTS);
        Button btStopServ = (Button) findViewById(R.id.btStopService);

        btprintTimeStamp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mServiceBound){
                        timestampText.setText(mBoundeService.getTimestamp());
                    }
                }
            }
        );

        btStopServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceBound){
                    unbindService(mServiceConnection);
                    mServiceBound =false;
                }
                Intent intent = new Intent(MainActivity.this,BoundService.class);
                stopService(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mServiceBound){
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundeService = myBinder.getService();
            mServiceBound =true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };


}
