package com.JatimakmurApp;

import static com.JatimakmurApp.LoginActivity.TAG_USERNAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.JatimakmurApp.OngkirApi.OngkirActivity;
import com.JatimakmurApp.Util.ServerAPI;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class DagingActivity extends AppCompatActivity implements ProdukAdapter.ItemClickListener {

    Toast toast;
    public static String username, userName, userImageUrl;
    SharedPreferences sharedpreferences;

    androidx.appcompat.widget.Toolbar toolbar;
    ProgressDialog pd;
    public static ArrayList<Produk> mItems = new ArrayList<>();
    private ProdukAdapter produkadapter;
    public static CartAdapter cartAdapter;
    RecyclerView mRecyclerview;
    RecyclerView cartRecycler;
    TextView tv_Username, tv_category;
    ImageView imgProfile;
    SearchView searchView;
    ImageButton btnBuah, btnDaging, btnSusu, btnSayur, btnTelur, btnLainnya;
    ImageButton btnCall, btnLoc, btnChat;

    LinearLayout bottomSheetLayout;
    RelativeLayout colapseBottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    SwipeRefreshLayout refreshProduct;

    MaterialButton btn_checkout, btn_clearcart;
    public static ArrayList<Produk> cart = new ArrayList<>();

    public static TextView txtTot;

    ViewFlipper v_flipper;
    int images[] = {R.drawable.slider1,
            R.drawable.slider2,R.drawable.slider3
            ,R.drawable.slider4,R.drawable.slider5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daging);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        toast = Toast.makeText(getApplicationContext(), null,Toast.LENGTH_SHORT);


        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userName = preferences.getString("username","");
        userImageUrl = preferences.getString("userPhoto","");


        pd = new ProgressDialog(DagingActivity.this);


        // Inisialisasi method initilize

        initilize();

        // Inisialisasi method checkLogin

        checkLogin();

        tv_category.setText("Kategori Buah");

        buttonListener();

        mRecyclerview.setHasFixedSize(true);

        cartRecycler.setHasFixedSize(true);


        // Saat dilakukan refresh product

        refreshProduct.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshProduct.setRefreshing(false);
                loadJson();
            }
        });


        //set recycler view 2 kolom

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        mRecyclerview.setLayoutManager(layoutManager);
        produkadapter = new ProdukAdapter(mItems, this); //memanggil adapter
        mRecyclerview.setAdapter(produkadapter);
        produkadapter.setClickListener(this);

        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cart);
        cartRecycler.setAdapter(cartAdapter);

        //mengambil data dari API

        loadJson();




        // Fungsi untuk serchView

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                produkadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                produkadapter.getFilter().filter(newText);
                return false;
            }
        });

        //inisialisasi bottomsheet

        initBottomsheet();

    }

    private void buttonListener() {



        btn_clearcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.clear();
                cartAdapter.notifyDataSetChanged();
                getTotal();
                for(int i=0; i< mItems.size(); i++ ){
                    mItems.get(i).setJmlBeli(0);
                }
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.isEmpty()){
                    Toast.makeText(DagingActivity.this,"Pilih barang terlebih dahulu", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(DagingActivity.this, OngkirActivity.class));
                }
            }
        });

        // Menu Kategori

        btnBuah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DagingActivity.this, BuahActivity.class));
            }
        });

        btnSayur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DagingActivity.this, SayurActivity.class));
            }
        });

        btnDaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DagingActivity.this, DagingActivity.class));
            }
        });

        btnTelur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DagingActivity.this, TelurActivity.class));
            }
        });

        btnSusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DagingActivity.this, SusuActivity.class));
            }
        });

        btnLainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DagingActivity.this, OtherActivity.class));
            }
        });


    }


    //fungsi ambil data dari API

    private void loadJson(){
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DAGING,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley","response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
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
                        refreshProduct.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                        refreshProduct.setRefreshing(false);

                    }
                });

        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(reqData);
    }

    //menjumlah total harga
    public void getTotal() {
        int tot=0;
        txtTot=(TextView) findViewById(R.id.totalHarga);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        for(int i=0; i<cart.size();i++){
            tot += (cart.get(i).getHarga() * cart.get(i).getJmlBeli());
            Log.d("vvvv", cart.get(i).getHarga()+" aaa  "+ cart.get(i).getJmlBeli());
        }
        txtTot.setText("Rp. "+decimalFormat.format(tot));
    }

    //fungsi klik pada daftar barang

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
                cartAdapter.notifyDataSetChanged();
                return;
            default:
                Intent intent = new Intent(DagingActivity.this, DetailProdukActivity.class);
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


//    // fungsi untuk option menu
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_profile:
//                startActivity(new Intent(BU.this,UpdateUserActivity.class));
//                return true;
//            case R.id.action_history:
//                startActivity(new Intent(MainActivity.this,HistoryActivity.class));
//                return true;
//            case R.id.action_call_center:
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:085155057752"));
//                startActivity(intent);
//                return true;
//            case R.id.action_sms_center:
//                String number = "085155057752";
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number,null)));
//                return true;
//            case R.id.action_maps:
//                Uri gmmIntentUri = Uri.parse("geo:-7.079667,110.329499?q=-7.079667,110.329499");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
//                return true;
//            case R.id.action_logout:
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putBoolean(LoginActivity.session_status, false);
//                editor.putString(TAG_USERNAME, null);
//                editor.commit();
//
//                // logout for facebook
//
//                LoginManager.getInstance().logOut();
//
//                // Logout for google
//
//                FirebaseAuth.getInstance().signOut();
//
//
//                finish();
//                startActivity(new Intent(MainActivity.this,LoginActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        getSupportActionBar().setElevation(0);
//        return true;
//    }

    //method inisialisasi botttomsheet
    private void initBottomsheet() {
        // get the bottom sheet view
        bottomSheetLayout = findViewById(R.id.bs_ll);
        colapseBottomSheet = findViewById(R.id.bs_colapse);
        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        //ketika bottomsheet di klik maka akan expand
        colapseBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //menutup bottom sheet ketika tekan di luar bottomsheet
            if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                bottomSheetLayout.getGlobalVisibleRect(outRect);

                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        return super.dispatchTouchEvent(event);
    }

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new
//                AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setMessage("Apakah kamu ingin keluar?");
//        builder.setPositiveButton("Iya", new
//                DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int
//                            which) {
//                        //if user pressed "yes", then he is allowed to exit from application
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        startActivity(intent);
//                        int pid = android.os.Process.myPid();
//                        android.os.Process.killProcess(pid);
//                    }
//                });
//        builder.setNegativeButton("Tidak", new
//                DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int
//                            which) {
//                        //if user select "No", just cancel this dialog and continue with app
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    public void checkout(View view) {
        if(cart.isEmpty()){
            Toast.makeText(DagingActivity.this,"Pilih barang terlebih dahulu", Toast.LENGTH_LONG).show();
        }else{
            startActivity(new Intent(DagingActivity.this, OngkirActivity.class));
        }
    }

    private void initilize(){
        tv_Username     =   findViewById(R.id.tv_UserName);
        tv_category     =   findViewById(R.id.tv_category);
        imgProfile      =   findViewById(R.id.profile_image);
        searchView      =   findViewById(R.id.search_barr);
        refreshProduct  =   findViewById(R.id.refreshProduct);
        btn_checkout    =   findViewById(R.id.btn_checkout);
        btn_clearcart   =   findViewById(R.id.btn_clearcart);
        cartRecycler    =   (RecyclerView) findViewById(R.id.rc_cart2);
        mRecyclerview   =   (RecyclerView) findViewById(R.id.recycler_view);
        v_flipper       =   findViewById(R.id.v_flipper);


        btnBuah         =   findViewById(R.id.btnBuah);
        btnSayur        =   findViewById(R.id.btnSayur);
        btnDaging       =   findViewById(R.id.btnDaging);
        btnSusu         =   findViewById(R.id.btnSusu);
        btnTelur        =   findViewById(R.id.btnTelur);
        btnLainnya      =   findViewById(R.id.btnLainnya);
        btnCall         =   findViewById(R.id.btnCall);
        btnLoc          =   findViewById(R.id.btnLokasi);
        btnChat         =   findViewById(R.id.btnChat);

    }

    private void checkLogin() {
        // Jika login menggunakan Google

        if (userName != null) {
            Glide.with(this).load(userImageUrl).into(imgProfile);
            tv_Username.setText("Hai" + userName);

        }

        // Jika login menggunakan facebook account

        if(AccessToken.getCurrentAccessToken()!=null){


            AccessToken accessToken  = AccessToken.getCurrentAccessToken();


            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted( JSONObject jsonObject, GraphResponse graphResponse) {
                    try {


                        // get nama dan image profile facebook account

                        String fullName = jsonObject.getString("name" );
                        String url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");

                        tv_Username.setText(fullName);
                        Picasso.get().load(url).into(imgProfile);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameters=new Bundle();
            parameters.putString("fields","id,name,link,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();



        }

        // Jika login menggunakan email

        if(username!=null){
            tv_Username.setText("Hai, " + username);
            imgProfile.setVisibility(View.GONE);
        }
    }
}
