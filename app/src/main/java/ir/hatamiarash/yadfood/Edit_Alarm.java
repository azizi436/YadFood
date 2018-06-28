package ir.hatamiarash.yadfood;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;
import java.util.TimeZone;

import Adapter.MyAdapter;
import helper.SQLiteHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by MohammadReza on 5/3/2018.
 */


public class Edit_Alarm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    int result = 1;
    static public int x = 1;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    SQLiteHandler db;
    EditText onvan;
    TextView title;
    static public String _VAR = "";
    static public String nametemp = "";
    static public String reach = "1";
    String TIME = "";
    String day = "";
    Spinner spinner2;
    private static final String[] dayname = {"شنبه ها", "  یکشنبه ها", " دوشنبه ها", "سه شنبه ها", "چهارشنبه ها", "پنجشنبه ها", "جمعه ها"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm2);


        Toolbar toolbar1 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar1);

        db = new SQLiteHandler(getApplicationContext());

        title = findViewById(R.id.textView2);
        onvan = findViewById(R.id.onvan);

        PersianCalendar persianCalendar = new PersianCalendar();
        TimePickerDialog datePickerDialog = TimePickerDialog.newInstance(
                Edit_Alarm.this,
                persianCalendar.HOUR_OF_DAY,
                persianCalendar.MINUTE,
                true
        );

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Edit_Alarm.this,
                R.layout.day_spinner, dayname);

        spinner2.setAdapter(adapter);


        List<String> list = db.getAlarm();
        for (int i = 0; i < (list.size() / 6); i++) {
            String id = list.get(i * 6);
            String title2 = list.get(i * 6 + 1);
            String desc = list.get(i * 6 + 2);
            String time = list.get(i * 6 + 3);
            String reach = list.get(i * 6 + 4);
            String day1 = list.get(i * 6 + 5);
            if (MyAdapter._Id.equals(id)) {
                String choose = day1;
                Log.w("chooseeee=",choose);
                title.setText(time);
                datePickerDialog.setStartTime(Integer.valueOf(time.substring(0, 2)), Integer.valueOf(time.substring(3, 5)));
                switch (choose) {
                    case "1": {
                        spinner2.setSelection(1);
                    }
                    break;
                    case "2": {
                        spinner2.setSelection(2);
                    }
                    break;
                    case "3": {
                        spinner2.setSelection(3);
                    }
                    break;
                    case "4": {
                        spinner2.setSelection(4);
                    }
                    break;
                    case "5": {
                        spinner2.setSelection(5);
                    }
                    break;
                    case "6": {
                        spinner2.setSelection(6);
                    }
                    break;
                    case "7": {
                        spinner2.setSelection(0);
                    }
                    break;
                   /* case "7":
                        spinner2.setSelection(7);
                        break;*/
                }
            }
        }
        datePickerDialog.setTitle("زمان هشدار");
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH");
        DateFormat date2 = new SimpleDateFormat("mm");
        String hour = date.format(currentLocalTime);
        String minute = date2.format(currentLocalTime);



        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();

                switch (position) {
                    case 0:
                        day = "7";
                        break;
                    case 1:
                        day = "1";
                        break;
                    case 2:
                        day = "2";
                        break;
                    case 3:
                        day = "3";
                        break;
                    case 4:
                        day = "4";
                        break;
                    case 5:
                        day = "5";
                        break;
                    case 6:
                        day = "6";
                        break;

                }

                Log.w("day==", day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.w("item==", "item SELECTED");
            }


        });

        List<String> list2 = db.getAlarm();
        if (!list2.isEmpty()) {
            for (int j = 0; j < (list.size() / 6); j++) {

                day = list.get(j * 6 + 5);

            }
            switch (day) {
                case "1": {
                    spinner2.setSelection(1);
                }
                break;
                case "2": {
                    spinner2.setSelection(2);
                }
                break;
                case "3": {
                    spinner2.setSelection(3);
                }
                break;
                case "4": {
                    spinner2.setSelection(4);
                }
                break;
                case "5": {
                    spinner2.setSelection(5);
                }
                break;
                case "6": {
                    spinner2.setSelection(6);
                }
                break;
                case "7": {
                    spinner2.setSelection(0);
                }
            }
        }

//tanzim click b roye title
        title.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v) {
            PersianCalendar persianCalendar = new PersianCalendar();
            TimePickerDialog datePickerDialog = TimePickerDialog.newInstance(
                    Edit_Alarm.this,
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

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("HH");
            DateFormat date2 = new SimpleDateFormat("mm");
            String hour = date.format(currentLocalTime);
            String minute = date2.format(currentLocalTime);

            datePickerDialog.setStartTime(Integer.valueOf(hour), Integer.valueOf(minute));

        }
    });

    //********************baghi mandan ba khamosh shodan mobile******************************************
    ComponentName receiver = new ComponentName(getApplicationContext(), Alarmm.class);
    PackageManager pm = getApplication().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
    PackageManager.DONT_KILL_APP);


//************************************************************************************
    }

    public void onClickSetAlarm(View v) {

        nametemp = onvan.getText().toString();
        db.deleteAlarm(MyAdapter._Id);
        db.addAlarm(_VAR, nametemp, TIME, reach, day);

        Intent notificationIntent = new Intent(Edit_Alarm.this, Alarmm.class);

        notificationIntent.putExtra("onetime", Boolean.TRUE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
        long futureInMillis = System.currentTimeMillis();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //*******Calendar calendar=
//todo set inxat repeat with Alarmmanger.rtc
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, futureInMillis, 20000, pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,futureInMillis,pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, futureInMillis + 5000, 60000, pendingIntent);
        // Log.w("realtime=",String.valueOf( SystemClock.elapsedRealtime()));

        Intent mainmenu = new Intent(Edit_Alarm.this, Activity_mainmeno.class);
        mainmenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainmenu);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        if (hourOfDay >= 0 && hourOfDay < 10)
            time = "0" + hourOfDay + ":" + minute;
        if (minute >= 0 && minute < 10)
            time = hourOfDay + ":0" + minute;
        if (minute >= 0 && minute < 10 && hourOfDay >= 0 && hourOfDay < 10)
            time = "0" + hourOfDay + ":0" + minute;
        title.setText(time);
        TIME = time;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
// Start the initial runnable task by posting through the handler


// Define the code block to be executed
/*    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {

            // Do something here on the main thread
            Toast.makeText(getApplicationContext(),"salam",Toast.LENGTH_LONG);
            Log.d("Handlers", "Called on main threadmmmmm");
            Log.w("Handlers", "Called on main1");
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 2000);
        }
    };
}*/

    /*
    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            Toast.makeText(getApplicationContext(),"salam",Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getApplicationContext(), Checker_service.class);
                            startService(intent);

                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 1000 ms
    }
}*/

