package ir.hatamiarash.yadfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by a on 26/01/2018.
 */

public class Activity_desc extends AppCompatActivity {

     ImageView tel,gim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("راهنمای استفاده");
        setSupportActionBar(toolbar);

        tel=(ImageView)findViewById(R.id.tel4);
        gim=(ImageView)findViewById(R.id.gmail);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://telegram.me/mr_azizi7"));
                final String appName = "org.telegram.messenger";
                i.setPackage(appName);
                startActivity(i);
            }
        });

        gim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "azizimohamadreza436@gmail.com"));
                startActivity(intent);
            }
        });

        WebView web = (WebView)findViewById(R.id.web);
        web.loadUrl("file:///android_asset/desc.html");

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}