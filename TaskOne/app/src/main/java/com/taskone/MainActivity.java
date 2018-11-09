package com.taskone;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.taskone.handlers.PlacesAdapter;
import com.taskone.model.PlacesModel;
import com.taskone.model.ResultPagePair;
import com.taskone.service.PlayLocation;
import com.taskone.utilis.Parser;

import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    RecyclerView main_rv;
    ArrayList<PlacesModel> arrayList;
    PlacesAdapter adapter;
    LinearLayoutManager manager;
    String next_page_token = null, base_url = null, radius = null, previous_page_url = null;
    EditText main_search_et;
    RelativeLayout main_settings_rl, main_left_rl, main_right_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiUI();
    }

    private void intiUI() {
        main_rv = findViewById(R.id.main_rv);
        main_search_et = findViewById(R.id.main_search_et);
        main_settings_rl = findViewById(R.id.main_settings_rl);
        main_right_rl = findViewById(R.id.main_right_rl);
        main_left_rl = findViewById(R.id.main_left_rl);
        arrayList = new ArrayList<>();
        adapter = new PlacesAdapter(this, arrayList);
        manager = new LinearLayoutManager(this);
        main_rv.setLayoutManager(manager);
        main_rv.setAdapter(adapter);
        radius = "1000";

        base_url = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=" + PlayLocation.playlat + "," + PlayLocation.playlog + "&radius=" + radius + "&types=restaurant&language=en&sensor=true&key=AIzaSyBm4ryqfno35VmnjSImUT7q-Wfyi8A8pMQ";
        Log.e(TAG, "URL-------------------->" + base_url);
        refreshList(base_url);

        main_settings_rl.setOnClickListener(this);
        main_left_rl.setOnClickListener(this);
        main_right_rl.setOnClickListener(this);

        searchData();
    }


    public void settings() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_settings);
        final EditText dialog_radius_et = dialog.findViewById(R.id.dialog_radius_et);
        Button dialog_search_btn = dialog.findViewById(R.id.dialog_search_btn);

        dialog_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_radius_et.getText().toString().isEmpty()) {
                    radius = "1000";
                } else {
                    radius = dialog_radius_et.getText().toString();
                }
                refreshList(base_url);
                dialog.cancel();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    public void refreshList(String url) {
        arrayList.clear();
        this.adapter.notifyDataSetChanged();
        new LoadRestaurentResult().execute(new String[]{url});
    }

    public void searchData() {
        main_search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<PlacesModel> filterdNames = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().toLowerCase().contains(text.toLowerCase())) {
                PlacesModel model = new PlacesModel();
                model.setName(arrayList.get(i).getName());
                filterdNames.add(model);
            }
        }
        adapter.filterList(filterdNames);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_settings_rl:
                settings();
                break;
            case R.id.main_right_rl:
                this.previous_page_url = this.base_url;
                refreshList("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?pagetoken=" + next_page_token + "&language=en&sensor=false&key=AIzaSyBm4ryqfno35VmnjSImUT7q-Wfyi8A8pMQ");
                break;
            case R.id.main_left_rl:
                refreshList(this.previous_page_url);
                break;
        }
    }

    class LoadRestaurentResult extends AsyncTask<String, PlacesModel, String> {
        ProgressDialog dialog;

        LoadRestaurentResult() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(MainActivity.this);
            this.dialog.setMessage("Loading..");
            this.dialog.show();
        }

        protected String doInBackground(String... params) {
            ResultPagePair resultPair = new Parser().getResponceNodeList(params[0]);
            NodeList list = resultPair.getItem();
            MainActivity.this.next_page_token = resultPair.getNextPageToken();
            if (list == null) {
                return null;
            }
            for (int i = 0; i < list.getLength(); i++) {
                publishProgress(new PlacesModel[]{new Parser().getResult(list, i, MainActivity.this)});
            }
            return "OK";
        }

        protected void onProgressUpdate(PlacesModel... values) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            MainActivity.this.addValues(values);
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                this.dialog.dismiss();
                Toast.makeText(MainActivity.this.getBaseContext(), "No Search Result Found.", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        }
    }

    public void addValues(PlacesModel... value) {
        for (PlacesModel primaryDetailsListPoJo : value) {
            this.adapter.addItem(primaryDetailsListPoJo);
            this.adapter.notifyDataSetChanged();
        }
    }
}
