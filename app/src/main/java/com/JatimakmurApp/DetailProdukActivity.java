package com.JatimakmurApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;

import static com.JatimakmurApp.LoginActivity.TAG_USERNAME;

public class DetailProdukActivity extends AppCompatActivity {

    String username;
    SharedPreferences sharedpreferences;

    TextView tv_namaBarang_detail, tv_jumlahBarang_detail,
            tv_satuan_detail, tv_hargaBarang_detail, tv_deskripsi_detail;
    ImageView iv_gambar_detail;

    ImageButton btnBack;

    String gambar, nama, satuan, deskripsi;
    int jumlah, harga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);

        initilize();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);

        Intent intent = getIntent();
        this.gambar     = intent.getStringExtra("gambar");
        this.nama       = intent.getStringExtra("nama");
        this.harga      = intent.getIntExtra("harga",0);
        this.jumlah     = intent.getIntExtra("stok",0);
//        this.satuan     = intent.getStringExtra("satuan");
        this.deskripsi  = intent.getStringExtra("deskripsi");

        tv_namaBarang_detail.setText(nama);
//        tv_jumlahBarang_detail.setText(String.valueOf(jumlah));
//        tv_satuan_detail.setText(satuan);
        tv_hargaBarang_detail.setText("Rp. "+nf.format(harga));
        tv_deskripsi_detail.setText(deskripsi);
        Glide.with(DetailProdukActivity.this) //konteks bisa didapat dari activity yang sedang berjalan
                .load(gambar) // mengambil data dengan cara "list.get(position)" mendapatkan isi berupa objek Menu. kemudian "Menu.geturlGambar"
                .thumbnail(0.5f) // resize gambar menjadi setengahnya
                .into(iv_gambar_detail); // mengisikan ke imageView


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(nama);




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initilize() {

        tv_namaBarang_detail    = (TextView) findViewById(R.id.tv_namaBarang_detail);
//        tv_jumlahBarang_detail  = (TextView) findViewById(R.id.tv_jumlahBarang_detail);
//        tv_satuan_detail        = (TextView) findViewById(R.id.tv_satuan_detail);
        tv_hargaBarang_detail   = (TextView) findViewById(R.id.tv_hargaBarang_detail);
        tv_deskripsi_detail     = (TextView) findViewById(R.id.tv_deskripsi_detail);
        iv_gambar_detail        = (ImageView) findViewById(R.id.iv_gambar_detail);

        btnBack                 =   findViewById(R.id.btn_back);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    //fungsi untuk tombol menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_call_center:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:085155057752"));
                startActivity(intent);
                return true;
            case R.id.action_sms_center:
                String number = "085155057752";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number,null)));
                return true;
            case R.id.action_maps:
                Uri gmmIntentUri = Uri.parse("geo:-7.079667,110.329499?q=-7.079667,110.329499");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;
            case R.id.action_profile:
                startActivity(new Intent(DetailProdukActivity.this,UpdateUserActivity.class));
                return true;
            case R.id.action_logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                finish();
                startActivity(new Intent(DetailProdukActivity.this,LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
