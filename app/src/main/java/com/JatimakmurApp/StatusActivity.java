package com.JatimakmurApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.JatimakmurApp.Util.AppController;
import com.JatimakmurApp.Util.DownloadTask;
import com.JatimakmurApp.Util.ServerAPI;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.JatimakmurApp.Util.ServerAPI.DOWNLOAD_NOTA;

public class StatusActivity extends AppCompatActivity {

    TextView status, tanggal, nota, username, total, totalBeli, totalOngkir;
    TextView tv_provinsi, tv_kota,tv_alamatLengkap, tv_expedisi, total2, totalBeli2, totalOngkir2;
    ImageView imgStatus, gambar;
    Button btnsimpan, btncetak, btngallery;
    ProgressDialog pd;
    DecimalFormat decimalFormat;
    Bitmap bitmap;
    RelativeLayout strukPembayaran, strukTagihan;

    String alamat, provinsi, kabupaten, expedisi;

    GalleryPhoto mGalery;
    private final int TAG_GALLERY = 2222;
    String selected_photo = null;

    String date;

    public static final int progress_bar_type = 0;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        hideNavigationBar();

        status = (TextView) findViewById(R.id.text_status);
        tanggal = (TextView) findViewById(R.id.tanggal);
        nota = (TextView) findViewById(R.id.no_nota);
        username = (TextView) findViewById(R.id.user);

        tv_provinsi = findViewById(R.id.tv_provinsi);
        tv_kota =  findViewById(R.id.tv_kabupaten);
        tv_alamatLengkap = findViewById(R.id.tv_alamat);
        totalBeli2 = findViewById(R.id.total_beli2);
        totalOngkir2 = findViewById(R.id.total_ongkir2);
        total2 = findViewById(R.id.total_biaya2);
        tv_expedisi = findViewById(R.id.tv_expedisi);

        strukPembayaran =   findViewById(R.id.struk_pembayaran);
        strukTagihan = findViewById(R.id.struk_tagihan);

        totalBeli = (TextView) findViewById(R.id.total_beli);
        totalOngkir = (TextView) findViewById(R.id.total_ongkir);
        total = (TextView) findViewById(R.id.total_biaya);
        imgStatus = (ImageView) findViewById(R.id.img_status);
        gambar = (ImageView) findViewById(R.id.inp_gambar);
        btngallery = (Button) findViewById(R.id.btn_gallery);
        btncetak = (Button) findViewById(R.id.btn_cetak);
        btnsimpan = (Button) findViewById(R.id.btn_simpan);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        decimalFormat = new DecimalFormat("#,##0.00");
        pd = new ProgressDialog(StatusActivity.this);
        mGalery = new GalleryPhoto(getApplicationContext());


        loadJson();



        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(mGalery.openGalleryIntent(),TAG_GALLERY);
                ActivityCompat.requestPermissions(
                        StatusActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        TAG_GALLERY
                );
            }
        });
        btncetak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = DOWNLOAD_NOTA+nota.getText().toString();
                new DownloadTask(StatusActivity.this, url, nota.getText().toString());
            }
        });
        btnsimpan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                    simpanData();
                }


        });
    }

    private void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }


    private void loadJson() {
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_DASHBOARD_JUAL, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                pd.cancel();
                Log.d("JSON RESPONSE", "onResponse: "+response);
                try {
                    JSONObject data = new JSONObject(response);
                    username.setText(data.getString("username"));
                    tanggal.setText(data.getString("tanggal"));
                    nota.setText(data.getString("no_nota"));
                    totalBeli.setText("Rp. " + decimalFormat.format(data.getInt("pembelian")));
                    totalOngkir.setText("Rp. " + decimalFormat.format(data.getInt("ongkir")));
                    total.setText("Rp. " + decimalFormat.format(data.getInt("total_biaya")));

                    tv_expedisi.setText(data.getString("kurir"));
                    tv_alamatLengkap.setText(data.getString("tujuan"));
                    tv_provinsi.setText(data.getString("provinsi"));
                    tv_kota.setText(data.getString("kabupaten"));

                    totalBeli2.setText("Rp. " + decimalFormat.format(data.getInt("pembelian")));
                    totalOngkir2.setText("Rp. " + decimalFormat.format(data.getInt("ongkir")));
                    total2.setText("Rp. " + decimalFormat.format(data.getInt("total_biaya")));

                    if (data.getString("status").equalsIgnoreCase("Sudah dibayar")) {
                        status.setText("Sudah Dibayar");

                        strukPembayaran.setVisibility(View.VISIBLE);
                        strukTagihan.setVisibility(View.GONE);





//                        status.setTextColor(R.color.colorPrimary);
//
//                        imgStatus.setImageResource(R.drawable.berhasil);
//
//                        btngallery.setVisibility(View.GONE);
//
//                        btnsimpan.setVisibility(View.GONE);

//                        findViewById(R.id.rekening).setVisibility(View.GONE);
                    } else if (data.getString("status").equalsIgnoreCase("Belum dibayar")) {

                        strukTagihan.setVisibility(View.VISIBLE);
                        strukPembayaran.setVisibility(View.GONE);

//                        status.setText("BelumDibayar");
//                        status.setTextColor(Color.RED);
//
//                        imgStatus.setImageResource(R.drawable.gagal);

//                        btncetak.setVisibility(View.GONE);
                    }
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
                String no_nota = intent.getStringExtra("no_nota");
                map.put("no_nota", no_nota );
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData, "json_obj_req");
    }

    private void simpanData() {
        pd.setMessage("Mengirim Data");
        pd.setCancelable(false);
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_UPLOAD_GAMBAR,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            pd.cancel();
                            Log.d("TAG", "onResponse: "+response);
                            try {
                                JSONObject res = new JSONObject(response);
                                Toast.makeText(StatusActivity.this, "pesan : " +
                                        res.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                            intent.putExtra("no_nota", nota.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.cancel();
                    Toast.makeText(StatusActivity.this,
                            "pesan : Gagal Kirim Data", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("no_nota", nota.getText().toString());
                    map.put("gambar", imageString);
                    return map;
                }
            };


            AppController.getInstance().addToRequestQueue(sendData);
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == TAG_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, TAG_GALLERY);
            }
            else {
                Toast.makeText(this, "Tidak ada perizinan untuk mengakses gambar", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  RESULT_OK && requestCode == TAG_GALLERY && data != null && data.getData() != null){

                Uri uri_path = data.getData();

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_path);
                    gambar.setImageBitmap(bitmap);

                    Snackbar.make(findViewById(android.R.id.content), "Success Loader Image", Snackbar.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                    Snackbar.make(findViewById(android.R.id.content), "Something Wrong", Snackbar.LENGTH_SHORT).show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    public void back(View view) {
        finish();
    }
}



