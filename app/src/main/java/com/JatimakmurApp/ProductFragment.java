package com.JatimakmurApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.JatimakmurApp.Util.ServerAPI;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductFragment extends Fragment  implements ProdukAdapter.ItemClickListener{

    public static ArrayList<Produk> mItems = new ArrayList<>();
    private ProdukAdapter produkadapter;
    public static CartAdapter cartAdapter;
    RecyclerView mRecyclerview, cartRecycler;
    public static ArrayList<Produk> cart = new ArrayList<>();
    ProgressDialog pd;

    public static TextView txtTot;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview =  inflater.inflate(R.layout.fragment_product, container, false);

        mRecyclerview       =   (RecyclerView) rootview.findViewById(R.id.recycler_view);
        cartRecycler    =   (RecyclerView)rootview.findViewById(R.id.rc_cart2);
        mRecyclerview.setHasFixedSize(true);
        GridLayoutManager   layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerview.setLayoutManager(layoutManager);

        txtTot=(TextView) rootview.findViewById(R.id.totalHarga);

        produkadapter = new ProdukAdapter(mItems, getContext()); //memanggil adapter
        mRecyclerview.setAdapter(produkadapter);
        produkadapter.setClickListener(this);

        mItems.clear();
        loadJson();


        return rootview;


    }

    private void loadJson() {

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_SAYUR, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response :" + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                Produk md = new Produk();
                                md.setKode(data.getString("kd_barang"));
                                md.setNama(data.getString("nm_barang"));
                                md.setSatuan(data.getString("satuan"));
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setHarga(data.getInt("harga"));
                                md.setHarga_beli(data.getInt("harga_beli"));
                                md.setStok(data.getInt("stok"));
                                md.setStok_min(data.getInt("stok_min"));
                                md.setImg(data.getString("gambar"));
                                mItems.add(md);
                                ProdukAdapter.listBarangfull.add(md); //masukkan ke arraylist listBarangfull yang ada di ProdukAdapter
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        produkadapter.notifyDataSetChanged();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());

                    }
                });

        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(reqData);



    }


    @Override
    public void onClick(View view, int position) {

        final Produk produk = mItems.get(position);
        switch (view.getId()) {
            case R.id.img_card:
                produk.setJmlBeli(produk.getJmlBeli()+1);
                cart.clear();
                for(int i=0; i< mItems.size(); i++ ){
                    if(mItems.get(i).getJmlBeli()>0){
                        cart.add(mItems.get(i));
                    }
                }
                getTotal();
                return;
            default:
                Intent intent = new Intent(getContext(), DetailProdukActivity.class);
                intent.putExtra("gambar", produk.getImg());
                intent.putExtra("nama", produk.getNama());
                intent.putExtra("harga", produk.getHarga());
                intent.putExtra("stok", produk.getStok());
                intent.putExtra("satuan", produk.getSatuan());
                intent.putExtra("deskripsi", produk.getDeskripsi());
                startActivity(intent);
                break;
        }
    }

    //menjumlah total harga
    public void getTotal() {
        int tot=0;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        for(int i=0; i<cart.size();i++){
            tot += (cart.get(i).getHarga() * cart.get(i).getJmlBeli());
            Log.d("vvvv", cart.get(i).getHarga()+" aaa  "+ cart.get(i).getJmlBeli());
        }
        txtTot.setText("Rp. "+decimalFormat.format(tot));
    }
}