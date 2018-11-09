package com.taskone.handlers;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taskone.R;
import com.taskone.database.DataBase;
import com.taskone.model.PlacesModel;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<PlacesModel> arrayList;

    public PlacesAdapter(Activity activity, ArrayList<PlacesModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.row_places, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void addItem(PlacesModel item) {
        arrayList.add(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final DataBase db = new DataBase(activity);
        final PlacesModel model = arrayList.get(position);
        holder.row_place_name_tv.setText(model.getName());

        if (model.isStatus()) {
            holder.row_places_fav_iv.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_star));
        } else {
            holder.row_places_fav_iv.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_star_border));
        }

        holder.row_places_fav_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setStatus(true);
                db.insertNewsFeedListTable(model.getName());
                notifyDataSetChanged();
            }
        });
    }

    public void filterList(ArrayList<PlacesModel> filterdNames) {
        this.arrayList = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView row_place_name_tv;
        RelativeLayout row_places_fav_rl;
        ImageView row_places_fav_iv;

        ViewHolder(View itemView) {
            super(itemView);
            row_place_name_tv = itemView.findViewById(R.id.row_place_name_tv);
            row_places_fav_rl = itemView.findViewById(R.id.row_places_fav_rl);
            row_places_fav_iv = itemView.findViewById(R.id.row_places_fav_iv);
        }
    }
}
