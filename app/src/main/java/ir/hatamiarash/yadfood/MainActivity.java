package ir.hatamiarash.yadfood;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import helper.SQLiteHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    int hr = 0;
    int min = 0;
    int sec = 0;
    int result = 1;
    static public int x = 1;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    BroadcastReceiver mReceiver;
    Button off;
    SQLiteHandler db;

    //TimePicker timePicker;
    EditText ethr;
    EditText etmin;
    EditText etsec, matn, onvan;
    TextView id, title;
    static Ringtone ringtone;
    static public String _VAR = "";
    static public String nametemp = "";
    static public String reach = "1";
    String TIME = "";

    Button set1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm2);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar1);
        // timePicker = (TimePicker) findViewById(R.id.timePicker1);
        db = new SQLiteHandler(getApplicationContext());
        //ethr = (EditText) findViewById(R.id.ethr);
        //etmin = (EditText) findViewById(R.id.etmin);
        //etsec = (EditText) findViewById(R.id.etsec);
        // matn = (EditText) findViewById(R.id.e1);
        //lib = (Button) findViewById(R.id.button);
        title = (TextView) findViewById(R.id.textView2);
        //set1 = (Button) findViewById(R.id.setalarm);

        onvan = (EditText) findViewById(R.id.onvan);
        //id=(TextView)findViewById(R.id.id);
        // Calendar mcurrentTime = Calendar.getInstance();
        // int hour = mcurrentTime.get(Calendar.HOUR);
        // int minute = mcurrentTime.get(Calendar.MINUTE);
        //  Log.w("hour==",String.valueOf(hour));


     /*   timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                int m = timePicker.getCurrentHour();
                int n = timePicker.getCurrentHour();
                Log.e("hadle hour", String.valueOf(timePicker.getCurrentHour()));
                Log.e("hadle min", String.valueOf(n));

            }
        });*/

//        lib.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                PersianCalendar persianCalendar = new PersianCalendar();
//                TimePickerDialog datePickerDialog = TimePickerDialog.newInstance(
//                        MainActivity.this,
//                        persianCalendar.HOUR_OF_DAY,
//                        persianCalendar.MINUTE,
//                        true
//                );
//
//                datePickerDialog.setTitle("زمان هشدار");
//                datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
//            }
//
//        });

        PersianCalendar persianCalendar = new PersianCalendar();
        TimePickerDialog datePickerDialog = TimePickerDialog.newInstance(
                MainActivity.this,
                persianCalendar.HOUR_OF_DAY,
                persianCalendar.MINUTE,
                true
        );

        datePickerDialog.setTitle("زمان هشدار");
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

//Todo gerfan saat system b sorat dorost

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH");
        DateFormat date2 = new SimpleDateFormat("mm");
        String hour = date.format(currentLocalTime);
        String minute = date2.format(currentLocalTime);

        datePickerDialog.setStartTime(Integer.valueOf(hour), Integer.valueOf(minute));
    }


    // int hour = getTime(1);
    //getTime(2);



    /*private int getTime(int type) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat;
        if (type == 1) {
            simpleDateFormat = new SimpleDateFormat("HH");
            return Integer.valueOf(simpleDateFormat.format(date));
        } else {
            simpleDateFormat = new SimpleDateFormat("mm");
            return Integer.valueOf(simpleDateFormat.format(date));
        }
    }*/


    /*@Override
    protected void onDestroy() {
        UnregisterAlarmBroadcast();//khamosh kardan alarm ############
        super.onDestroy();
    }*/

    public void onClickSetAlarm(View v) {


        nametemp = onvan.getText().toString();

        db.addAlarm(_VAR, nametemp, TIME, reach);

        Intent notificationIntent = new Intent(MainActivity.this, Alarmm.class);
      /*  notificationIntent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        notificationIntent.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);*/

        notificationIntent.putExtra("onetime", Boolean.TRUE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
        long futureInMillis = System.currentTimeMillis() + result;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.w("Start", String.valueOf(System.currentTimeMillis()));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //
        //alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        // TODO
        alarmManager.setRepeating(AlarmManager.RTC, futureInMillis, 50000, pendingIntent);
        //Intent on = new Intent(MainActivity.this, Ontime.class);
        //on.putExtra("matn", matn.getText());
        Intent mainmenu = new Intent(MainActivity.this, Activity_mainmeno.class);
        mainmenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainmenu);
        finish();
    }

  /*  public void UnregisterAlarmBroadcast() {
        Log.w("Stop", String.valueOf(System.currentTimeMillis()));
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(MainActivity.this, Alarmm.class);
        myIntent.putExtra("matn", matn.getText());

        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        //ontime.cancel();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
     if(ringtone.isPlaying())
     {ringtone.stop();}
    }*/


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String timelog = "You picked the following time: " + hourOfDay + ":" + minute;
        String time = hourOfDay + ":" + minute;
        if (hourOfDay >= 0 && hourOfDay < 10)
            time = "0" + hourOfDay + ":" + minute;
        if (minute >= 0 && minute < 10)
            time = hourOfDay + ":0" + minute;
        if (minute >= 0 && minute < 10 && hourOfDay >= 0 && hourOfDay < 10)
            time = "0" + hourOfDay + ":0" + minute;
        title.setText(time);
        TIME = time;

        // Log.d("dastitime",TIME);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}