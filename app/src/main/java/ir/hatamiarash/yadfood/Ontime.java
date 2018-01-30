package ir.hatamiarash.yadfood;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Interface.Refresh;
import helper.SQLiteHandler;
import helper.SQLiteHandler2;

/**
 * Created by a on 28/09/2017.
 */

public class Ontime extends AppCompatActivity implements Refresh {
    TextView barname;
    Button offAlarm, retry, site;
    String _VAR, _Id;
    static Ringtone ringtone;
    SQLiteHandler db;
    SQLiteHandler2 dc;
    static public String matngir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ontime);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Intent in = getIntent();
        String m = in.getStringExtra("desc");
        String n = in.getStringExtra("id");
        String esm="";
        dc= new SQLiteHandler2(getApplicationContext());

        List<String> list = new ArrayList<>();
        list = dc.getmember();
        int i = 0;
        esm=list.get(i).toString();
        barname = (TextView) findViewById(R.id.t1);
        barname.setVisibility(View.VISIBLE);
      //  if(m.equals(null))


            barname.setText(esm +" غذاتو رزرو کن !!");

      //  else
       // { barname.setText(m);}


        KeyguardManager manager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("abc");
        lock.disableKeyguard();

        db = new SQLiteHandler(getApplicationContext());

        site = (Button) findViewById(R.id.site);
        retry = (Button) findViewById(R.id.retry);
        offAlarm = (Button) findViewById(R.id.off1);

        PowerManager pm = (PowerManager) getSystemService(getApplicationContext().POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP, "");

        wl.acquire();
        onResume();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
        ringtone.play();
        long[] pattern = {1000, 2000, 1000, 3000,1000,2000};
       // v.vibrate(4000);
        v.vibrate(pattern, -1);


        offAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                v.cancelLongPress();
                //onDestroy();
                Intent mainmenu = new Intent(Ontime.this, Activity_mainmeno.class);
                // mainmenu.putExtra("reach", '1');
                startActivity(mainmenu);
                finish();

            }
        });

        try {
            Activity_mainmeno.t.refresh();
        } catch (NullPointerException e) {

        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nalarm = new Intent(Ontime.this, MainActivity.class);
                startActivity(nalarm);
                finish();
            }
        });

        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://food.malayeru.ac.ir"));
                startActivity(i);
                finish();
            }
        });


        db.updateReach(n);
    }


    private Window wind;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        /******block is needed to raise the application if the lock is*********/
        wind = this.getWindow();
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    /* ^^^^^^^block is needed to raise the application if the lock is*/
    }


    static public void cancel() {

        if (ringtone.isPlaying()) {
            ringtone.stop();
        }
    }

    @Override
    protected void onDestroy() {
        //khamosh kardan alarm ############
        ringtone.stop();
        super.onDestroy();
    }

    @Override
    public void a() {

    }

}