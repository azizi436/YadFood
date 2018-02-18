package ir.hatamiarash.yadfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Utils.SessionManager;
import Utils.SessionManger2;
import helper.SQLiteHandler2;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Profile extends AppCompatActivity {
    Button save;
    EditText name, number, pass;
    SQLiteHandler2 dc;

    SessionManger2 session;
    SessionManager Se;
    String ch;
    LinearLayout salam;
    TextView text;
    private Spinner spinner;
    private static final String[] addresses = {"دانشگاه ملایر", "دانشگاه صنعتی شریف", "دانشگاه تهران", "دانشگاه امام صادق(ع)", "دانشگاه بوعلی سینا", "دانشگاه رازی کرمانشاه", "دانشگاه صنعتی اصفهان", "دانشگاه شهید بهشتی", "صنعتی امیرکبیر", "علامه طباطبایی", "دانشگاه شهید چمران", "علوم پزشکی ارومیه", "علوم پزشکی مشهد", "علوم پزشکی تبریز", "علوم پزشکی تهران", "علوم پزشکی اصفهان", "دانشگاه لرستان", "دانشگاه سنندج", "دانشگاه بروجردی", "دانشگاه اراک"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setTheme(R.style.MaterialBaseTheme_Light);

        session = new SessionManger2(getApplicationContext());
        Se = new SessionManager(getApplicationContext());
        dc = new SQLiteHandler2(getApplicationContext());

        salam = findViewById(R.id.l);
        save = findViewById(R.id.profbutton);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        pass = findViewById(R.id.pass);
        text = findViewById(R.id.text);


        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_Profile.this,
                R.layout.spineer, addresses);

//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //spinner.setOnItemSelectedListener(this);

        if (session.isFirst()) {
            salam.setVisibility(View.VISIBLE);
            dc.CreateTable();
            spinner.setSelection(0);
            session.setFirst(false);
        } else {
            salam.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
        }

        List<String> list = dc.getmember();
        if (!list.isEmpty()) {

            String n = list.get(0);
            String k = list.get(2);
            String m = list.get(1);
            String choose = list.get(3);
            name.setText(n);
            number.setText(m);
            pass.setText(k);
            switch (choose) {
                case "1": {
                    spinner.setSelection(0);
                }
                break;
                case "2": {
                    spinner.setSelection(1);
                }
                break;
                case "3": {
                    spinner.setSelection(2);
                }
                break;
                case "4": {
                    spinner.setSelection(3);
                }
                break;
                case "5": {
                    spinner.setSelection(4);
                }
                break;
                case "6": {
                    spinner.setSelection(5);
                }
                break;
                case "7": {
                    spinner.setSelection(6);
                }
                break;
                case "8":
                    spinner.setSelection(7);
                    break;
                case "9":
                    spinner.setSelection(8);
                    break;
                case "10":
                    spinner.setSelection(9);
                    break;
                case "11":
                    spinner.setSelection(10);
                    break;
                case "12":
                    spinner.setSelection(11);
                    break;
                case "13":
                    spinner.setSelection(12);
                    break;
                case "14":
                    spinner.setSelection(13);
                    break;
                case "15":
                    spinner.setSelection(14);
                    break;
                case "16":
                    spinner.setSelection(15);
                    break;
                case "17":

                    spinner.setSelection(16);
                    break;
                case "18":
                    spinner.setSelection(17);
                    break;
                case "19":
                    spinner.setSelection(18);
                    break;
                case "20":
                    spinner.setSelection(19);
                    break;
            }
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty() || number.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "لطفا اطلاعات را به صورت کامل وارد کنید...", Toast.LENGTH_LONG).show();
                } else {
                    String NAME1 = name.getText().toString();
                    String NUMBER1 = number.getText().toString();
                    String PASS1 = pass.getText().toString();
                    dc.deletemember2();
                    dc.deleteAll();
                    dc.CreateTable();

                    dc.addmember(NAME1, NUMBER1, PASS1, ch);
                    if (Se.isFirst()) {
                        Se.setFirst(false);
                        Intent mainmenu1 = new Intent(Activity_Profile.this, Activity_mainmeno.class);
                        mainmenu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainmenu1);
                        Intent rahnama=new Intent(Activity_Profile.this,Activity_desc.class);
                        startActivity(rahnama);
                        finish();
                    }
                    else {
                    Intent mainmenu1 = new Intent(Activity_Profile.this, Activity_mainmeno.class);
                    mainmenu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainmenu1);
                    finish();}

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                Log.w("item==", item);
                // Log.w("SELECT=","YES");

                switch (position) {
                    case 0:
                        ch = "1";
                        break;
                    case 1:
                        ch = "2";
                        break;
                    case 2:
                        ch = "3";
                        break;
                    case 3:
                        ch = "4";
                        break;
                    case 4:
                        ch = "5";
                        break;
                    case 5:
                        ch = "6";
                        break;
                    case 6:
                        ch = "7";
                        break;
                    case 7:
                        ch = "8";
                        break;
                    case 8:
                        ch = "9";
                        break;
                    case 9:
                        ch = "10";
                        break;
                    case 10:
                        ch = "11";
                        break;
                    case 11:
                        ch = "12";
                        break;
                    case 12:
                        ch = "13";
                        break;
                    case 13:
                        ch = "14";
                        break;
                    case 14:
                        ch = "15";
                        break;
                    case 15:
                        ch = "16";
                        break;
                    case 16:
                        ch = "17";
                        break;
                    case 17:
                        ch = "18";
                        break;
                    case 18:
                        ch = "19";
                        break;
                    case 19:
                        ch = "20";
                        break;
                }

                Log.w("CH==", ch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.w("item==", "item SELECTED");
            }
        });
    }

/*
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

Log.w("SELECT=","YES");

        switch (position) {
            case 0:
                ch="1";
                break;
            case 1:
                ch="2";
                break;
            case 2:
                ch="3";
                break;

        }

        Log.w("CH==",ch);
        ch=String.valueOf(position);
    }
*/


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
