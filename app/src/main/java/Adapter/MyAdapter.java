package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Interface.Refresh;
import helper.SQLiteHandler;
import ir.hatamiarash.yadfood.Activity_mainmeno;
import ir.hatamiarash.yadfood.Edit_Alarm;
import ir.hatamiarash.yadfood.R;
import lib.kingja.switchbutton.SwitchMultiButton;
import model.Alarmitem;

/**
 * Created by a on 07/10/201
 * import ir.mrazizi77.alarm.R;7
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Refresh {
    private Context mContext;
    private List<Alarmitem> ModelList;
    SQLiteHandler db;
    static public String _Id = "";
    static public String givetime = "";

    public MyAdapter(Context mContext, List<Alarmitem> ModelList) {
        this.mContext = mContext;
        this.ModelList = ModelList;
        db = new SQLiteHandler(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarmitem, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Alarmitem Model = ModelList.get(position);
        holder.text.setText("هشدار " + String.valueOf(position + 1));
        holder.day.setText(Model.getDay());
        holder.name.setText(Model.getName());
        holder.desc.setText(Model.getDesc());
        if (holder.day.getText().equals("7"))//tanzim rooz
            holder.time.setText("شنبه ها " + Model.getTime());
        else if (holder.day.getText().equals("1"))
            holder.time.setText("یکشنبه ها " + Model.getTime());
        else if (holder.day.getText().equals("2"))
            holder.time.setText("دوشنبه ها " + Model.getTime());

        else if (holder.day.getText().equals("3"))
            holder.time.setText("سه شنبه ها " + Model.getTime());

        else if (holder.day.getText().equals("4"))
            holder.time.setText("چهارشنبه ها " + Model.getTime());

        else if (holder.day.getText().equals("5"))
            holder.time.setText("پنجشنبه ها " + Model.getTime());

        else if (holder.day.getText().equals("6"))
            holder.time.setText("جمعه ها " + Model.getTime());


        holder.id.setText(Model.getID());

        _Id = holder.id.getText().toString();//bad az tanzim id ra b _Id midim ta betonim toye on time database ro update konim
        givetime = holder.time.getText().toString();

        if (Model.getReach().equals("1")) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_alarm_on));
            holder.mSwitchMultiButton.setSelectedTab(0);
        } else {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_tik));
            holder.mSwitchMultiButton.setVisibility(View.VISIBLE);
            holder.mSwitchMultiButton.setSelectedTab(1);

        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAlarm(holder.id.getText().toString());
                ((Refresh) mContext).a();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(mContext, Edit_Alarm.class);
               mContext.startActivity(t);

            }
        });


        holder.mSwitchMultiButton.setText("فعال", "غیرفعال").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                if(position==0)
                { Log.w("position=",String.valueOf(position));
              db.register(_Id);}

                if(position==1)
                {
                    db.updateReach(_Id);
                    Log.w("position=",String.valueOf(position));
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, reach, time, id, text, day;
        ImageView image, delete,edit;
        SwitchMultiButton mSwitchMultiButton;

        MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.desc);
            reach = view.findViewById(R.id.reach);
            time = view.findViewById(R.id.time);
            id = view.findViewById(R.id.id);
            day = view.findViewById(R.id.day);
            image = view.findViewById(R.id.image1);
            delete = view.findViewById(R.id.image2);
            edit=view.findViewById(R.id.Edit);
            mSwitchMultiButton = view.findViewById(R.id.Switch);
        }
    }

    @Override
    public void a() {

    }
}

