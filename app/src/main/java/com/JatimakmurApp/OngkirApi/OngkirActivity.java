package com.JatimakmurApp.OngkirApi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.JatimakmurApp.CartAdapter;
import com.JatimakmurApp.MainActivity;
import com.JatimakmurApp.R;
import com.JatimakmurApp.StatusActivity;
import com.JatimakmurApp.Util.ServerAPI;
import com.JatimakmurApp.OngkirApi.adapter.CityAdapter;
import com.JatimakmurApp.OngkirApi.adapter.ProvinceAdapter;
import com.JatimakmurApp.OngkirApi.api.ApiService;
import com.JatimakmurApp.OngkirApi.api.ApiUrl;
import com.JatimakmurApp.OngkirApi.model.city.ItemCity;
import com.JatimakmurApp.OngkirApi.model.cost.ItemCost;
import com.JatimakmurApp.OngkirApi.model.province.ItemProvince;
import com.JatimakmurApp.OngkirApi.model.province.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.JatimakmurApp.MainActivity.cart;

public class OngkirActivity extends AppCompatActivity {

    private EditText etAlamat, etToProvince, etToCity;
    TextView totBeli;
    private Spinner spinnerCourier;

    private AlertDialog.Builder alert;
    private AlertDialog ad;
    private EditText searchList;
    private ListView mListView;

    RecyclerView rc_cart;

    private ProvinceAdapter adapter_province;
    private List<Result> ListProvince = new ArrayList<Result>();

    private CityAdapter adapter_city;
    private List<com.JatimakmurApp.OngkirApi.model.city.Result> ListCity = new ArrayList<com.JatimakmurApp.OngkirApi.model.city.Result>();

    private ProgressDialog progressDialog;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
    TextView tv_origin, tv_destination, tv_expedisi, tv_coast,tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongkir);


        etAlamat = (EditText) findViewById(R.id.etAlamat);
        etToProvince = (EditText) findViewById(R.id.etToProvince);
        etToCity = (EditText) findViewById(R.id.etToCity);
        spinnerCourier = (Spinner) findViewById(R.id.spinnerCourier);

        tv_origin = (TextView) findViewById(R.id.tv_origin);
        tv_destination = (TextView) findViewById(R.id.tv_destination);
        tv_expedisi = (TextView) findViewById(R.id.tv_expedisi);
        tv_coast = (TextView) findViewById(R.id.tv_coast);
        tv_time = (TextView) findViewById(R.id.tv_time);

        //menampilkan total harga
        int tot=0;
        totBeli = (TextView) findViewById(R.id.totalHarga2);

        for(int i = 0; i< cart.size(); i++){
            tot += (cart.get(i).getHarga() * cart.get(i).getJmlBeli());
        }
        totBeli.setText("Rp. "+decimalFormat.format(tot));

        //menampilkan daftar cart
        rc_cart = (RecyclerView) findViewById(R.id.rc_cart2);
        rc_cart.setHasFixedSize(true);
        rc_cart.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter cartAdapter = new CartAdapter(cart);
        rc_cart.setAdapter(cartAdapter);

        etToProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                popUpProvince(etToProvince, etToCity);
            }
        });

        etToCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                try {
                    if (etToProvince.getTag().equals("")) {
                        etToProvince.setError("Please chooise your to province");
                    } else {
                        popUpCity(etToCity, etToProvince);
                    }

                } catch (NullPointerException e) {
                    etToProvince.setError("Please chooise your to province");
                }

            }
        });

        String[] kurir = {"-- Pilih Kurir --", "JNE", "POS", "TIKI"};
        //Inisialiasi Array Adapter dengan memasukkan String Array
        final ArrayAdapter<String> kurirAdapter = new ArrayAdapter<>(OngkirActivity.this,
                android.R.layout.simple_spinner_dropdown_item,kurir);
        //Memasukan Adapter pada Spinner
        spinnerCourier.setAdapter(kurirAdapter);
        spinnerCourier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(OngkirActivity.this,"anda memilih "+kurirAdapter.getItem(position),Toast.LENGTH_SHORT).show();
                if(position>0){
                    if(etToCity.getText().toString().equals("")) {
                        etToCity.setError("Pilih Tujuan Pengiriman");
                    }else{
                        progressDialog = new ProgressDialog(OngkirActivity.this);
                        progressDialog.setMessage("Please wait..");
                        progressDialog.show();
                        getCoast(etToCity.getTag().toString(),spinnerCourier.getSelectedItem().toString().toLowerCase());
                    }
                }else{
                    reset();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//
        Button btnProcess = (Button) findViewById(R.id.btnProcess);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = etToCity.getText().toString();
                String expedisi = spinnerCourier.getSelectedItem().toString().toLowerCase();
                Log.d("expedisi", "onClick: " + expedisi);

                if (destination.equals("")){
                    etToCity.setError("Pilih Tujuan Pengiriman");
                } else {
                    transaksi();
                }

            }
        });
    }
    public void popUpProvince(final EditText etProvince, final EditText etCity ) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(OngkirActivity.this);
        alert.setTitle("Daftar Provinsi");
        alert.setMessage("Pilih provinsi tujuan");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = (EditText) alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new OngkirActivity.MyTextWatcherProvince(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = (ListView) alertLayout.findViewById(R.id.listItem);

        ListProvince.clear();
        adapter_province = new ProvinceAdapter(OngkirActivity.this, ListProvince);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                Result cn = (Result) o;

                etProvince.setError(null);
                etProvince.setText(cn.getProvince());
                etProvince.setTag(cn.getProvinceId());

                etCity.setText("");
                etCity.setTag("");

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(OngkirActivity.this);
        progressDialog.setMessage("Mengambil Data Provinsi..");
        progressDialog.show();

        getProvince();

    }

    public void popUpCity(final EditText etCity, final EditText etProvince) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(OngkirActivity.this);
        alert.setTitle("Daftar Kab/Kota");
        alert.setMessage("Pilih kab/kota tujuan");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = (EditText) alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new OngkirActivity.MyTextWatcherCity(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = (ListView) alertLayout.findViewById(R.id.listItem);

        ListCity.clear();
        adapter_city = new CityAdapter(OngkirActivity.this, ListCity);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                com.JatimakmurApp.OngkirApi.model.city.Result cn = (com.JatimakmurApp.OngkirApi.model.city.Result) o;

                etCity.setError(null);
                etCity.setText(cn.getCityName());
                etCity.setTag(cn.getCityId());

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(OngkirActivity.this);
        progressDialog.setMessage("Mengambil data Kab/Kota..");
        progressDialog.show();

        getCity(etProvince.getTag().toString());

    }

    private class MyTextWatcherProvince implements TextWatcher {

        private View view;

        private MyTextWatcherProvince(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.searchItem:
                    adapter_province.filter(editable.toString());
                    break;
            }
        }
    }

    private class MyTextWatcherCity implements TextWatcher {

        private View view;

        private MyTextWatcherCity(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.searchItem:
                    adapter_city.filter(editable.toString());
                    break;
            }
        }
    }

    public void getProvince() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemProvince> call = service.getProvince();

        call.enqueue(new Callback<ItemProvince>() {
            @Override
            public void onResponse(Call<ItemProvince> call, Response<ItemProvince> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        Result itemProvince = new Result(
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince()
                        );

                        ListProvince.add(itemProvince);
                        mListView.setAdapter(adapter_province);
                    }

                    adapter_province.setList(ListProvince);
                    adapter_province.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(OngkirActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemProvince> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OngkirActivity.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCity(String id_province) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCity> call = service.getCity(id_province);

        call.enqueue(new Callback<ItemCity>() {
            @Override
            public void onResponse(Call<ItemCity> call, Response<ItemCity> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        com.JatimakmurApp.OngkirApi.model.city.Result itemProvince = new com.JatimakmurApp.OngkirApi.model.city.Result(
                                response.body().getRajaongkir().getResults().get(a).getCityId(),
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince(),
                                response.body().getRajaongkir().getResults().get(a).getType(),
                                response.body().getRajaongkir().getResults().get(a).getCityName(),
                                response.body().getRajaongkir().getResults().get(a).getPostalCode()
                        );

                        ListCity.add(itemProvince);
                        mListView.setAdapter(adapter_city);
                    }

                    adapter_city.setList(ListCity);
                    adapter_city.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(OngkirActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemCity> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OngkirActivity.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCoast(String destination, String courier) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCost> call = service.getCost(
                "a868f02b0acee22c5cd0b64a4922aa69",
                "399",
                destination,
                "2000",
                courier
        );

        call.enqueue(new Callback<ItemCost>() {
            @Override
            public void onResponse(Call<ItemCost> call, Response<ItemCost> response) {

                Log.v("wow", "json : " + new Gson().toJson(response));
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    int statusCode = response.body().getRajaongkir().getStatus().getCode();

                    if (statusCode == 200){

                        tv_origin.setText(response.body().getRajaongkir().getOriginDetails().getCityName()+" (Postal Code : "+
                                response.body().getRajaongkir().getOriginDetails().getPostalCode()+")");

                        tv_destination.setText( etAlamat.getText().toString() + "\n" + response.body().getRajaongkir().getDestinationDetails().getCityName()+" (Postal Code : "+
                                response.body().getRajaongkir().getDestinationDetails().getPostalCode()+")");

                        tv_expedisi.setText(response.body().getRajaongkir().getResults().get(0).getCode().toUpperCase()+" - "+
                                response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getService());

                        tv_coast.setText("Rp. "+decimalFormat.format(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getValue()));

                        tv_time.setText(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getEtd());

                    } else {

                        String message = response.body().getRajaongkir().getStatus().getDescription();
                        Toast.makeText(OngkirActivity.this, message, Toast.LENGTH_SHORT).show();
                        spinnerCourier.setSelection(0,true);
                    }

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(OngkirActivity.this, error, Toast.LENGTH_SHORT).show();
                    spinnerCourier.setSelection(0,true);
                }

            }

            @Override
            public void onFailure(Call<ItemCost> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(OngkirActivity.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void reset(){

        tv_origin.setText("-");
        tv_destination.setText("-");
        tv_expedisi.setText("-");
        tv_coast.setText("-");
        tv_time.setText("-");
        spinnerCourier.setSelection(0,true);

    }

    public void transaksi(){
        progressDialog = new ProgressDialog(OngkirActivity.this);
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest strReq = new
                StringRequest(Request.Method.POST, ServerAPI.URL_JUAL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("RESPONSE transaksi", "onResponse: "+response);
                                progressDialog.cancel();
                                try {
                                    JSONObject res = new JSONObject(response);
                                    Log.d("nonota", "onResponse: "+res.getString("no_nota"));
                                    for (int i = 0; i < cart.size();i++) {
                                        createDetailJual(res.getString("no_nota"), cart.get(i).getKode(), String.valueOf(cart.get(i).getHarga()), String.valueOf(cart.get(i).getJmlBeli()));
                                    }
                                    Toast.makeText(OngkirActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                    cart.clear();
                                    MainActivity.cartAdapter.notifyDataSetChanged();
                                    for(int i=0; i< MainActivity.mItems.size(); i++ ){
                                        MainActivity.mItems.get(i).setJmlBeli(0);
                                    }
                                    MainActivity.txtTot.setText("Rp. 0");
                                    Intent intent = new Intent(OngkirActivity.this, StatusActivity.class);

                                    // Mengirim data menggunakan intent
                                    intent.putExtra("no_nota", res.getString("no_nota") );
                                    intent.putExtra("provinsi", etToProvince.getText().toString());
                                    intent.putExtra("kota", etToCity.getText().toString());
                                    intent.putExtra("alamat", etAlamat.getText().toString());
                                    intent.putExtra("kurir", spinnerCourier.getSelectedItem().toString());

                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.cancel();
                                Toast.makeText(OngkirActivity.this, "Gagal Checkout", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to nota
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", MainActivity.username);

                        double pembelian = 0;
                        for(int i=0; i<cart.size();i++){pembelian += (cart.get(i).getHarga() * cart.get(i).getJmlBeli());}
                        params.put("pembelian", Double.toString(pembelian));

                        params.put("tujuan", etAlamat.getText().toString());
                        params.put("kode_kab", etToCity.getTag().toString());
                        params.put("kurir", spinnerCourier.getSelectedItem().toString().toLowerCase());
                        params.put("expedisi", tv_expedisi.getText().toString());
                        params.put("wkt_pengiriman", tv_time.getText().toString());

                        double ongkir = Double.parseDouble(tv_coast.getText().toString().replace("Rp. ","").replace(",",""));
                        params.put("ongkir", Double.toString(ongkir));
                        params.put("total", Double.toString(pembelian+ongkir));

                        Log.d("Param dikirim", "getParams: " +
                                MainActivity.username+"\n"+
                                pembelian+"\n"+
                                etAlamat.getText().toString()+"\n"+
                                etToCity.getTag().toString()+"\n"+
                                spinnerCourier.getSelectedItem().toString().toLowerCase()+"\n"+
                                tv_expedisi.getText().toString()+"\n"+
                                tv_time.getText().toString()+"\n"+
                                ongkir+"\n"+
                                (pembelian+ongkir)
                        );

                        return params;
                    }
                };
        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(strReq);
    };

    private void createDetailJual(final String no_nota, final String kode, final String harga, final String jumlah) {

        StringRequest strReq = new
                StringRequest(Request.Method.POST, ServerAPI.URL_DETAILJUAL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject res = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to detail nota
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("no_nota", no_nota);
                        params.put("kode", kode);
                        params.put("harga", harga);
                        params.put("jumlah", jumlah);
                        return params;
                    }
                };
        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(strReq);
    }


}
