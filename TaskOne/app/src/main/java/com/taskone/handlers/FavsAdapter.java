package com.taskone.handlers;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taskone.R;
import com.taskone.model.FavModel;

import java.util.ArrayList;

public class FavsAdapter extends RecyclerView.Adapter<FavsAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<FavModel> arrayList;

    public FavsAdapter(Activity activity, ArrayList<FavModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.row_favs, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void addItem(FavModel item) {
        arrayList.add(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        FavModel model = arrayList.get(position);
        holder.row_fav_name_tv.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView row_fav_name_tv;

        ViewHolder(View itemView) {
            super(itemView);
            row_fav_name_tv = itemView.findViewById(R.id.row_fav_name_tv);
        }
    }
}
