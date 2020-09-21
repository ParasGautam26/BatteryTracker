package com.azizsheikh.batterytracker;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Aziz on 2/10/2018.
 */

public class MyService extends Service {
    @Nullable
    boolean isCharging;
    int level,comparelevel=52;
    int time=0;
    boolean stop=false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread t=new Thread(){

            @Override
            public void run(){

                while(!stop){

                    try {

                        sleep(1000);

                    }
                    catch (InterruptedException e){

                        e.printStackTrace();

                    }
                    time++;
                    if (time %5 ==0){

                        final IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

                        final Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
                        // Are we charging / charged?
                        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                                status == BatteryManager.BATTERY_STATUS_FULL;
                        level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                        float batteryPct = level / (float)scale;
                        batteryPct=batteryPct*100;
                        if (level>=comparelevel){

                            MediaPlayer ring = MediaPlayer.create(MyService.this, R.raw.battery);
                            ring.start();
                            
                        }

                    }

                }
            }
        };
        t.start();

        return MyService.START_NOT_STICKY;
    }
}
