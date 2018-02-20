package ir.hatamiarash.yadfood;


import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private String uniname;
    private String choose;
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
        choose=list.get(3);
        barname = (TextView) findViewById(R.id.t1);
        barname.setVisibility(View.VISIBLE);
        //  if(m.equals(null))


        switch (choose) {
            case "1": {
                uniname = "http://food.malayeru.ac.ir";//malayer
                Log.w("case1=", uniname);
            }
            break;
            case "2": {
                uniname = "http://dining.sharif.ir";//sharif
                Log.w("case2=", uniname);
            }
            break;
            case "3": {
                uniname = "http://dinig1.ut.ac.ir";//tehran
                Log.w("case3=", uniname);
            }
            break;
            case "4": {
                uniname = "http://food.isu.ac.ir";//emam sadegh
                Log.w("case4=", uniname);
            }
            break;
            case "5": {
                uniname = "http://refahi.basu.ac.ir/";//boali hmd
                Log.w("case5=", uniname);
            }
            break;
            case "6": {
                uniname = "https://food.razi.ac.ir/";//razi
                Log.w("case6=", uniname);
            }
            break;
            case "7": {
                uniname = "http://dining.iut.ac.ir";//sanaty esf
                Log.w("case7=", uniname);
            }
            break;
            case "8":
                uniname = "https://dining.sbu.ac.ir";//beheshti teh
                break;

            case "9":
                uniname = "http://samad.aut.ac.ir";//sanati amirkabir
                break;

            case "10":
                uniname = "http://foodstudent.atu.ac.ir/";// alameh tabatabai
                break;

            case "11":
                uniname = "http://food.scu.ac.ir/";//chamran
                break;

            case "12":
                uniname = "http://jeton.umsu.ac.ir/";//pezehki oromieh
                break;

            case "13":
                uniname = "http://stufood.mums.ac.ir";//pezeshki mashhad
                break;

            case "14":
                uniname = "http://nutrition.tbzmed.ac.ir/";//pezeshki tabriz
                break;

            case "15":
                uniname = "http://food.tums.ac.ir";//pezeshki teh
                break;

            case "16":
                uniname="http://self.mui.ac.ir/";//olom pezeshki esfahan

                break;
            case "17":
                uniname = "http://nd.lu.ac.ir/";//lorestan
                break;
            case "18":
                uniname="http://food.uok.ac.ir";//sanandaj
                break;
            case "19":
                uniname="http://31.130.180.118";//borojerd
                break;
            case "20":
                uniname = "http://jeton.araku.ac.ir";//arak

        }







        barname.setText(esm +" غذاتو رزرو کن !!");
        Toast.makeText(getApplicationContext(), esm+" وقتشه غذاتو رزرو کنی! ", Toast.LENGTH_LONG).show();
        notification(esm+"غذاتو رزرو کن","اگه هنوز غذاتو رزرو نکردی اینجا کلیک کن");
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
        long[] pattern = {1000, 1500, 1000, 1500,1000,1500};
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
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uniname));
                startActivity(i);
                finish();
            }
        });


        db.updateReach(n);
    }


    private Window wind;


    public void notification(String title, String message) {
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(uniname));
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),
                (int) System.currentTimeMillis(), in, 0);

        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
        b.setAutoCancel(true)
                //.setDefaults(NotificationCompat.DEFAULT_ALL)
                .setBadgeIconType(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher,3)
                .setTicker("یادفود :)")
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pIntent);

        //.setSubText("متن");
        //.setDeleteIntent()
        //.setContentInfo("سلام");

        b.build().flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, b.build());
    }


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