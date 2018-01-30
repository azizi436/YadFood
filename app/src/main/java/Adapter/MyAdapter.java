package Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Interface.Refresh;
import helper.SQLiteHandler;
import ir.hatamiarash.yadfood.R;
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
        holder.name.setText(Model.getName());
        holder.desc.setText(Model.getDesc());
        holder.time.setText("چهارشنبه " + Model.getTime());
        holder.id.setText(Model.getID());
        
        _Id = holder.id.getText().toString();//bad az tanzim id ra b _Id midim ta betonim toye on time database ro update konim
        givetime = holder.time.getText().toString();
        
        if (Model.getReach().equals("1")) {
            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_alarm_on));
        } else
            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_alarm_off));
        
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAlarm(holder.id.getText().toString());
                ((Refresh) mContext).a();
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
        TextView name, desc, reach, time, id, text;
        ImageView image, delete;
        
        MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.desc);
            reach = view.findViewById(R.id.reach);
            time = view.findViewById(R.id.time);
            id = view.findViewById(R.id.id);
            image = view.findViewById(R.id.image1);
            delete = view.findViewById(R.id.image2);
        }
    }
    
    @Override
    public void a() {
    
    }
}

