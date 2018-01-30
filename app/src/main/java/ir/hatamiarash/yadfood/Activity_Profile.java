package ir.hatamiarash.yadfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setTheme(R.style.MaterialBaseTheme_Light);
        
        session = new SessionManger2(getApplicationContext());
        Se = new SessionManager(getApplicationContext());
        dc = new SQLiteHandler2(getApplicationContext());
        
        save = findViewById(R.id.profbutton);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        pass = findViewById(R.id.pass);
        
        if (session.isFirst()) {
            dc.CreateTable();
            session.setFirst(false);
        }
        
        List<String> list = dc.getmember();
        if (!list.isEmpty()) {
            String n = list.get(0);
            String k = list.get(2);
            String m = list.get(1);
            name.setText(n);
            number.setText(m);
            pass.setText(k);
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
                    dc.addmember(NAME1, NUMBER1, PASS1);
                    Se.setFirst(false);
                    Intent mainmenu1 = new Intent(Activity_Profile.this, Activity_mainmeno.class);
                    mainmenu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainmenu1);
                    finish();
                }
            }
        });
    }
    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
