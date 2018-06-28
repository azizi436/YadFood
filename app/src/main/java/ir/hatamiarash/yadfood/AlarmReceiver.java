package ir.hatamiarash.yadfood;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import helper.SQLiteHandler;

/**
 * Created by MohammadReza on 4/27/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver(){}
    @Override
    public void onReceive(final Context context, Intent intent) {
       //Toast.makeText(context, "تکرار", Toast.LENGTH_LONG).show();
       check(context);
        Intent intent1=new Intent(context,AlarmReceiver.class);
        final PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent1,0);
        final AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000,pendingIntent);
    }

    private void check(Context context) {
        SQLiteHandler db = new SQLiteHandler(context);
        List<String> list = new ArrayList<>();

        list = db.getAlarm();
        for (int i = 0; i < (list.size() / 6); i++) {
            String id = list.get(i * 6);
            String desc = list.get(i * 6 + 2);
            String time = list.get(i * 6 + 3);
            String reach = list.get(i * 6 + 4);
            String day = list.get(i * 6 + 5);

          /*  Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            Toast.makeText(context, day, Toast.LENGTH_LONG).show();
            switch (day) {
                case Calendar.SUNDAY:
                    // Current day is Sunday

                case Calendar.MONDAY:
                    // Current day is Monday

                case Calendar.TUESDAY:
                    // etc.
            }*/


         /*   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
            date.setTimeZone(TimeZone.getTimeZone("GMT+3:30"));
            String localTime = date.format(currentLocalTime);
            if (localTime.length() == 4) {
                curtime = localTime.substring(0, 4);
            } else {
                curtime = localTime.substring(0, 5);
            }*/

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("HH");
            DateFormat date2 = new SimpleDateFormat("mm");
            String hour = date.format(currentLocalTime);
            String minute = date2.format(currentLocalTime);

            //curtime=hour+":"+minute;
            int currenttime = Integer.valueOf(hour + minute);
            Log.w("TIME", String.valueOf(currenttime));

           // time = time.substring(0, 2) + time.substring(3, 5);//kharej kardan ":" az time
            String hour_db =time.substring(0, 2);
            String Min_db=time.substring(3, 5);

            int originaltime = Integer.valueOf(time);

            Log.w("Dasti", String.valueOf(originaltime));
            Log.w("reach=", reach);
            Calendar Emrooz = Calendar.getInstance();
            int currentday = Emrooz.get(Calendar.DAY_OF_WEEK);
            Log.w("emrooz=", String.valueOf(currentday));
            Log.w("roz data bsse=", day);
            Log.w("EKHTELAF=", String.valueOf(currenttime - originaltime));
            //Toast.makeText(context, "تکرار", Toast.LENGTH_LONG).show();
            Long TIME1=(Long.valueOf(hour)*8400)+(Long.valueOf(minute)*60);
            Long TIME2=(Long.valueOf(hour_db)*8400)+(Long.valueOf(Min_db)*60);

            Long EKHTELAF=TIME2-TIME1;

            Toast.makeText(context, String.valueOf(EKHTELAF), Toast.LENGTH_LONG).show();
            Log.w("EKHTELAF=",String.valueOf(EKHTELAF));
            //todo --->***agar reach.equals("1") b if ezafhe shavad hoshdar tekrAR nemishavad***
            if (originaltime == currenttime || EKHTELAF == 1 && String.valueOf(currentday).equals(day)) {

                /*Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                ringtone = RingtoneManager.getRingtone(context, uri);*/

                Intent in = new Intent(context, Ontime.class);
                in.putExtra("desc", desc);
                in.putExtra("id", id);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // ringtone.play();
                context.startActivity(in);
                // vibrator.vibrate(20000);


            }


        }
    }

    }
