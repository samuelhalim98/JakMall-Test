package com.example.jakmall2;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.ViewHolder>  {

    private List<ListActivity> listActivities;
    private Context context;

    public AdapterActivity(List<ListActivity> listActivities, Context context) {
        this.listActivities = listActivities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_list,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ListActivity listActivity = listActivities.get(i);
        viewHolder.textViewType.setText(listActivity.getType());
        viewHolder.textViewDesc.setText(listActivity.getDesc());

    }

    @Override
    public int getItemCount() {
        return listActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewType;
        public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewType = (TextView) itemView.findViewById(R.id.TextViewType);
            textViewDesc = (TextView) itemView.findViewById(R.id.TextViewDesc);
        }
    }
}
