package ir.hatamiarash.yadfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Interface.Refresh;
import Utils.SessionManager;
import co.ronash.pushe.Pushe;
import helper.SQLiteHandler;
import helper.SQLiteHandler2;
import model.Alarmitem;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_mainmeno extends AppCompatActivity implements Refresh {

    RecyclerView recyclerView;
    LinearLayout empty;
    LinearLayoutManager linearLayoutManager;
    public MyAdapter myAdapter;
    String uniname = "";
    String name, onvan;
    CardView cardView;
    FloatingActionButton button;
    ArrayList<Alarmitem> llist;
    SQLiteHandler db;
    SQLiteHandler2 dc;

    SessionManager session;
    private Drawer result = null;

    private long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = this;
        setContentView(R.layout.activity_mainmeno);
        Pushe.initialize(this, true);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        db = new SQLiteHandler(getApplicationContext());
        dc = new SQLiteHandler2(getApplicationContext());


        session = new SessionManager(getApplicationContext());

        llist = new ArrayList<>();
        cardView = findViewById(R.id.card);
        button = findViewById(R.id.b);
        name = MainActivity.nametemp;
        onvan = MainActivity._VAR;

        recyclerView = findViewById(R.id.recycle);
        empty = findViewById(R.id.empty);

        if (session.isFirst()) {

            db.CreateTable();
            dc.CreateTable();
            Intent start = new Intent(Activity_mainmeno.this, Activity_Profile.class);
            startActivity(start);
            finish();

        }


        String choose;


        List<String> list1 = dc.getmember();
        if (!list1.isEmpty()) {
            choose = list1.get(3);

            Log.w("CHOOSE=", choose);

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
                    uniname = "http://self.mui.ac.ir/";//olom pezeshki esfahan

                    break;
                case "17":
                    uniname = "http://nd.lu.ac.ir/";//lorestan
                    break;
                case "18":
                    uniname = "http://food.uok.ac.ir";//sanandaj
                    break;
                case "19":
                    uniname = "http://31.130.180.118";//borojerd
                    break;
                case "20":
                    uniname = "http://jeton.araku.ac.ir";//arak
                    break;

            }
        }
       /* Intent uni=getIntent();
        uniname=uni.getStringExtra("url");*/
        Log.w("name uni=", uniname);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ياد فود‌ :)");
        setSupportActionBar(toolbar);
        //item haye asli dar menu
        PrimaryDrawerItem item_home = new PrimaryDrawerItem().withIdentifier(1).withName("پروفايل");
        PrimaryDrawerItem item_register = new PrimaryDrawerItem().withIdentifier(2).withName("راهنماي استفاده");
        PrimaryDrawerItem item_about = new PrimaryDrawerItem().withIdentifier(3).withName("درباره ي ما");
        PrimaryDrawerItem item_reserve = new PrimaryDrawerItem().withIdentifier(4).withName("رزرو غذا");
        //  PrimaryDrawerItem item_send = new PrimaryDrawerItem().withIdentifier(5).withName("ارسال به دوستان");

        //item haye farie menu
        SectionDrawerItem item_section = new SectionDrawerItem().withName("با ياد فود هميشه غذا داري :)");

        IDrawerItem items[] = new IDrawerItem[]{
                item_home,
                item_reserve,
                item_register,
                //        item_send,
                item_about,
                item_section,

        };

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(new AccountHeaderBuilder()
                        .withActivity(this)
                        //back ground
                        .withHeaderBackground(R.drawable.pizza)
                        .build())
                .addDrawerItems(
                        (IDrawerItem[]) items
                )
                //inja dastorat item ha ro ezafhe mikoni...
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            long item = drawerItem.getIdentifier();
                            if (item == 1) {
                                Intent reg = new Intent(Activity_mainmeno.this, Activity_Profile.class);

                                startActivity(reg);
                                result.closeDrawer();//basthe shodan menu bad az ejreaye dastorat
                            } else if (item == 2) {
                                Intent log = new Intent(Activity_mainmeno.this, Activity_desc.class);
                                startActivity(log);
                                result.closeDrawer();
                            } else if (item == 3) {
                                Intent abt = new Intent(Activity_mainmeno.this, Activity_about.class);
                                startActivity(abt);
                                result.closeDrawer();
                            } else if (item == 4) {
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uniname));
                                startActivity(i);
                                result.closeDrawer();
                            }
                          /*  else if (item == 5) {
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                                result.closeDrawer();
                            }*/
                        }
                        return false;
                    }
                })
                .withSelectedItem(1)//1 yani id 1 b sorat pishfarz entehkab shavad
                .withSavedInstance(savedInstanceState)
                .withDrawerGravity(Gravity.END)
                .build();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(Activity_mainmeno.this, MainActivity.class);
                startActivity(t);
                // finish();

            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("reach")) {
            savedInstanceState = getIntent().getExtras();
            if (savedInstanceState != null && savedInstanceState.getInt("reach") == 1) {
                refresh();
            }
        } else
            fetchAlarms();
    }

    private void fetchAlarms() {
        List<String> list = db.getAlarm();
        for (int i = 0; i < (list.size() / 6); i++) {
            String id = list.get(i * 6);
            String title = list.get(i * 6 + 1);
            String desc = list.get(i * 6 + 2);
            String time = list.get(i * 6 + 3);
            String reach = list.get(i * 6 + 4);
            String day = list.get(i * 6 + 5);

            llist.add(new Alarmitem(id, desc, title, time, reach, day));

        }
        if (llist.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
        }
        recyclerView.removeAllViewsInLayout();
        myAdapter = new MyAdapter(this, llist);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    public void refresh() {
        llist.clear();
        fetchAlarms();
        if (llist.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    public static Activity_mainmeno t;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void a() {
        refresh();
    }

    @Override//meno
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        //moarefi item haye menu

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            result.openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }

    //khroj ba click 2 bareh
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            result.closeDrawer();
            Toast.makeText(getApplicationContext(), "براي خروج دوباره کليک کنيد", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}