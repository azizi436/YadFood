package ir.hatamiarash.yadfood;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;
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


public class Alarmm extends WakefulBroadcastReceiver {

    String curtime;
    Vibrator vibrator;

    @Override
    public void onReceive(final Context context, Intent intent) {


        check(context);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void check(Context context) {
        SQLiteHandler db = new SQLiteHandler(context);
        List<String> list = new ArrayList<>();

        list = db.getAlarm();
        for (int i = 0; i < (list.size() / 5); i++) {
            String id = list.get(i * 5);
            String desc = list.get(i * 5 + 2);
            String time = list.get(i * 5 + 3);
            String reach = list.get(i * 5 + 4);
            //Log.e("time of database",String.valueOf(time));
            //Log.e("time of phone ", String.valueOf(time2));



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


            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
            date.setTimeZone(TimeZone.getTimeZone("GMT+3:30"));
            String localTime = date.format(currentLocalTime);
            if (localTime.length() == 4) {
                curtime = localTime.substring(0, 4);
            } else {
                curtime = localTime.substring(0, 5);
            }

            Log.w("TIME", String.valueOf(curtime));
            Log.w("Dasti", time);
            Log.w("reach=", reach);
            Calendar Emrooz = Calendar.getInstance();
            int currentday = Emrooz.get(Calendar.DAY_OF_WEEK);
            Log.w("emrooz=", String.valueOf(currentday));

            //todo --->***agar reach.equals("1") b if ezafhe shavad hoshdar tekrAR nemishavad***
            if (time.equals(curtime) && currentday == 4) {

                /*Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                ringtone = RingtoneManager.getRingtone(context, uri);*/

                Intent in = new Intent(context, Ontime.class);
                in.putExtra("desc", desc);
                in.putExtra("id", id);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // ringtone.play();
                context.startActivity(in);
                // vibrator.vibrate(20000);
                Toast.makeText(context, "وقتشه غذاتو رزرو کنی!", Toast.LENGTH_LONG).show();

            }


        }
    }

}