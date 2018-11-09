package com.taskone;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.taskone.database.DB_PARAMS;
import com.taskone.database.DataBase;
import com.taskone.handlers.FavsAdapter;
import com.taskone.model.FavModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavsActivity extends AppCompatActivity {
    Toolbar fav_toolbar;
    RecyclerView fav_rv;
    ArrayList<FavModel> arrayList;
    FavsAdapter adapter;
    LinearLayoutManager manager;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);
        intiUI();
    }

    private void intiUI() {
        db = new DataBase(this);
        fav_toolbar = findViewById(R.id.fav_toolbar);
        fav_rv = findViewById(R.id.fav_rv);
        arrayList = new ArrayList<>();
        adapter = new FavsAdapter(this, arrayList);
        manager = new LinearLayoutManager(this);
        fav_rv.setAdapter(adapter);
        fav_rv.setLayoutManager(manager);

        getDBData();
        setuptoolbar();
    }

    public void setuptoolbar() {
        fav_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        fav_toolbar.setTitle(R.string.favorites);
        fav_toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        fav_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDBData() {
        arrayList.clear();
        try {
            Cursor c = db.getAllData();
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex(DB_PARAMS.COLUMNS.favListColumn.name));
                    FavModel model = new FavModel();
                    model.setName(name);
                    model.setStatus(true);
                    model.setId(Integer.parseInt(c.getString(0)));

                    hasDuplicates(arrayList);
                    arrayList.add(model);

                } while (c.moveToNext());
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void hasDuplicates(List<FavModel> models) {
        final List<String> usedNames = new ArrayList<String>();
        Iterator<FavModel> it = models.iterator();
        while (it.hasNext()) {
            FavModel model = it.next();
            final String name = model.getName();
            if (usedNames.contains(name)) {
                it.remove();
            } else {
                usedNames.add(name);
            }
        }
    }

}
