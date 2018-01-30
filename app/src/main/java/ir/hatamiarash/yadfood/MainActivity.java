package ir.hatamiarash.yadfood;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));//geraftan time goshi
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH");
        DateFormat date2 = new SimpleDateFormat("mm");
        String hour = date.format(currentLocalTime);
        String minute = date2.format(currentLocalTime);
        
        datePickerDialog.setStartTime(Integer.valueOf(hour), Integer.valueOf(minute));
    }
    
    public void onClickSetAlarm(View v) {
        nametemp = onvan.getText().toString();
        
        db.addAlarm(_VAR, nametemp, TIME, reach);
        
        Intent notificationIntent = new Intent(MainActivity.this, Alarmm.class);
        
        notificationIntent.putExtra("onetime", Boolean.TRUE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
        long futureInMillis = System.currentTimeMillis() + result;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.w("Start", String.valueOf(System.currentTimeMillis()));
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        alarmManager.setRepeating(AlarmManager.RTC, futureInMillis, 50000, pendingIntent);
        
        Intent mainmenu = new Intent(MainActivity.this, Activity_mainmeno.class);
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
        title.setText("چهارشنبه " + time);
        TIME = time;
    }
    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}