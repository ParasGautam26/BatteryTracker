package com.azizsheikh.batterytracker;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView info;
    boolean isCharging;
    Button submit;
    int level,comparelevel=74;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info=(TextView) findViewById(R.id.info);
        submit=(Button) findViewById(R.id.submit);

        Intent i= new Intent(MainActivity.this, MyService.class);
        // potentially add data to the intent
        startService(i);


        final IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        final Intent batteryStatus = this.registerReceiver(null, ifilter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                // Are we charging / charged?
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                 level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);


                if (level==comparelevel){
                    info.setText(""+level);

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        Intent i= new Intent(MainActivity.this, MyService.class);
        startService(i);
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Intent i= new Intent(MainActivity.this, MyService.class);
        startService(i);
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        super.onStop();
    }
}
