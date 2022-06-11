package com.JatimakmurApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.JatimakmurApp.Util.AppController;
import com.JatimakmurApp.Util.ServerAPI;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.ItemClickListener {
    SharedPreferences sharedpreferences;
    String kd_konsumen;
    ProgressDialog pd;
    public static ArrayList<Pembelian> listPembelian = new ArrayList<>();
    private RecyclerView historyRecycler;
    private HistoryAdapter historyAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Get data from shared preferences
        getSharedPrefs();

        // Untuk menyembunyikan navbar

        hideNavbar();

        initilize();

        pd = new ProgressDialog(HistoryActivity.this);
        
        recyclerHistory();

        // Filter data produk agar tidak double saat pindah activity

        filterDataDouble();
        
        
        // Fungsi untuk bottom bar

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.history, true);

        }

        // Saat menu bottom bar di klik
        bottombarListener();

    }

    private void recyclerHistory() {
        historyRecycler.setHasFixedSize(true);
        historyRecycler.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(listPembelian,HistoryActivity.this);
        historyAdapter.setClickListener(this);
        historyRecycler.setAdapter(historyAdapter);
    }

    private void getSharedPrefs() {
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        kd_konsumen = sharedpreferences.getString("kode_konsumen", null);
    }

    private void bottombarListener() {
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.home:

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
                            }
                        }, 500);

                        break;
                    case R.id.history:

                        break;
                    case R.id.account:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(HistoryActivity.this, UpdateUserActivity.class));
                            }
                        }, 500);
                        break;
                }

            }
        });
    }

    private void filterDataDouble() {
        if (historyRecycler.getAdapter().getItemCount() == 0) {
            loadJson();

        } else {


        }
    }


    private void initilize() {
        animatedBottomBar = findViewById(R.id.animatedBottomBar);
        historyRecycler = (RecyclerView) findViewById(R.id.rv_pembelian);
    }

    private void hideNavbar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private void loadJson() {

        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.cancel();
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0 ; i < array.length(); i++)
                    {
                        try {
                            JSONObject data = array.getJSONObject(i);
                            String no_nota = data.getString("no_nota");
                            String due_date = data.getString("due_date");
                            String status = data.getString("status");
                            double total_biaya = data.getDouble("total_biaya");
                            Pembelian md = new Pembelian(no_nota, due_date, status, total_biaya);
                            listPembelian.add(md);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    historyAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                Intent intent = getIntent();
                map.put("kd_kons", kd_konsumen );
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData, "json_obj_req");
    }

    public void onClick(View view, int position) {
        final Pembelian pembelian = listPembelian.get(position);
        switch (view.getId()) {
            default:
                Intent intent = new Intent(HistoryActivity.this, StatusActivity.class);
                intent.putExtra("no_nota", pembelian.getNo_nota());
                startActivity(intent);
                break;
        }
    }
}
