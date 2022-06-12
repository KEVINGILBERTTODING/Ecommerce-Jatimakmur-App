package com.JatimakmurApp;

import static com.JatimakmurApp.LoginActivity.TAG_USERNAME;
import static com.JatimakmurApp.LoginActivity.my_shared_preferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.JatimakmurApp.Util.ServerAPI;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class UpdateUserActivity extends  AppCompatActivity {
    SharedPreferences sharedpreferences;
    String username, kode_konsumen;
    private TextInputEditText ti_email, ti_fullname, ti_hp, ti_kota, ti_kodepos, ti_alamat;
    private ImageView iv_gambar;
    Button btn_saveProfile, btn_logout;
    TextView tv_username, tv_updateLogin;
    ProgressDialog pd;

    private static final String TAG = MainActivity.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);



        // Get data from shared preferences

        getSharedPref();


        // Untuk menyembunyikan navbar

        hideNavbar();

        pd = new ProgressDialog(UpdateUserActivity.this);



        Log.d("kodekonsumen", kode_konsumen);


        initilize();

        tv_username.setText(username);

        loadProfile();

        // Fungsi saat button di klik
        btnClickListener();

        // Set bottombar active

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.account, true);

        }

        // Saat menu bottom bar di klik
        bottomBarListener();


    }

    private void getSharedPref() {
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", null);
        kode_konsumen = sharedpreferences.getString("kode_konsumen", null);
    }

    private void bottomBarListener() {
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.home:

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
                            }
                        }, 500);

                        break;
                    case R.id.history:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(UpdateUserActivity.this, HistoryActivity.class));
                            }
                        }, 500);

                        break;
                    case R.id.account:

                        break;
                }


            }
        });
    }

    private void btnClickListener() {
        btn_saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        tv_updateLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "onClick: bisa klik cok");
                DialogForm();
            }
        });

        btn_logout.setOnClickListener(view -> {
            Intent intent = new Intent();
            String data;

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(LoginActivity.session_status, false);
            editor.putString(TAG_USERNAME, null);
            editor.commit();

            // logout for facebook

            LoginManager.getInstance().logOut();

            // logout for google
            FirebaseAuth.getInstance().signOut();

            intent.setClass(UpdateUserActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        });
    }

    private void initilize() {
        ti_email        = findViewById(R.id.ti_email_updateUser);
        btn_logout      =   findViewById(R.id.btn_logout);
        ti_fullname     = findViewById(R.id.ti_fullname_updateUser);
        ti_hp           = findViewById(R.id.ti_hp_updateUser);
        ti_kota         = findViewById(R.id.ti_kota_updateUser);
        ti_kodepos      = findViewById(R.id.ti_kodepos_updateUser);
        ti_alamat       = findViewById(R.id.ti_alamat_updateUser);
        iv_gambar       = findViewById(R.id.iv_fotoProfile);
        btn_saveProfile = findViewById(R.id.btn_saveupdateUser);
        tv_username     = findViewById(R.id.tv_usernameUpdate);
        tv_updateLogin  = findViewById(R.id.tv_editLogin);
        animatedBottomBar = findViewById(R.id.animatedBottomBar);

    }

    private void hideNavbar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private void updateProfile() {
        pd.setMessage("Memperbarui Data Profil");
        pd.setCancelable(false);
        pd.show();
        StringRequest strReq = new
                StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE_USER_PROFILE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject res = new JSONObject(response);
                                    Toast.makeText(UpdateUserActivity.this, res.getString("message"), Toast.LENGTH_LONG).show();
                                    loadProfile();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                pd.cancel();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.cancel();
                                Toast.makeText(UpdateUserActivity.this, "Gagal memperbarui data profil", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to detail nota
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("kode_konsumen", kode_konsumen);
                        params.put("nm_konsumen", ti_fullname.getText().toString());
                        params.put("alamat", ti_alamat.getText().toString());
                        params.put("kodepos", ti_kodepos.getText().toString());
                        params.put("kota", ti_kota.getText().toString());
                        params.put("no_hp", ti_hp.getText().toString());
                        params.put("email", ti_email.getText().toString());
                        return params;
                    }
                };
        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(strReq);
    }

    private void loadProfile(){
        pd.setMessage("Mengambil Data Profil");
        pd.setCancelable(false);
        pd.show();
        StringRequest strReq = new
                StringRequest(Request.Method.POST, ServerAPI.URL_GET_USER_PROFILE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonarray = new JSONArray(response);
                                    for(int i=0; i < jsonarray.length(); i++) {
                                        JSONObject data = jsonarray.getJSONObject(i);
                                        ti_email.setText(data.getString("email"));
                                        ti_fullname.setText(data.getString("nm_konsumen"));
                                        ti_hp.setText(data.getString("no_hp"));
                                        ti_kota.setText(data.getString("kota"));
                                        ti_kodepos.setText(data.getString("kodepos"));
                                        ti_alamat.setText(data.getString("alamat"));
                                        Glide.with(UpdateUserActivity.this) //konteks bisa didapat dari activity yang sedang berjalan
                                                .load("https://kampung-anggrek.000webhostapp.com/assets/images/"+data.getString("foto")) // mengambil data dengan cara "list.get(position)" mendapatkan isi berupa objek Menu. kemudian "Menu.geturlGambar"
                                                .thumbnail(0.5f) // resize gambar menjadi setengahnya
                                                .into(iv_gambar); // mengisikan ke imageView
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                pd.cancel();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.cancel();
                                Toast.makeText(UpdateUserActivity.this, "Gagal mengambil data profil", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to detail nota
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("kode_konsumen", kode_konsumen);
                        return params;
                    }
                };
        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(strReq);
    };

    private void DialogForm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateUserActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_login_info, null);

        dialog.setView(dialogView);
        final AlertDialog alertDialog = dialog.create();

        final TextInputLayout til_username = (TextInputLayout) dialogView.findViewById(R.id.til_username_updateUser);
        final TextInputLayout til_password = (TextInputLayout) dialogView.findViewById(R.id.til_password_updateUser);
        final TextInputEditText ti_username = (TextInputEditText) dialogView.findViewById(R.id.ti_username_updateUser);
        final TextInputEditText ti_password = (TextInputEditText) dialogView.findViewById(R.id.ti_password_updateUser);
        final Button btn_exit = (Button) dialogView.findViewById(R.id.btn_exit_login_info);
        final Button btn_save = (Button) dialogView.findViewById(R.id.btn_update_login_info);

        ti_username.setText(username);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_password.setErrorEnabled(false);
                til_password.setError(null);
                if(ti_password.getText().length()<8){
                    til_password.setErrorEnabled(true);
                    til_password.setError("Password berisi minimal 8 karakter");
                }else{
                    pd.setMessage("Tunggu Sebentar");
                    pd.setCancelable(false);
                    pd.show();
                    StringRequest strReq = new
                            StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE_USER_LOGIN,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            pd.cancel();
                                            Toast.makeText(UpdateUserActivity.this, "Berhasil memperbarui username & password", Toast.LENGTH_LONG).show();
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString("username", ti_username.getText().toString());
                                            editor.commit();
                                            startActivity(new Intent(UpdateUserActivity.this, UpdateUserActivity.class));
                                            alertDialog.dismiss();
                                            finish();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            pd.cancel();
                                            Toast.makeText(UpdateUserActivity.this, "Gagal Perbarui Username & Password", Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    // Posting parameters to nota
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("kd_konsumen", kode_konsumen);
                                    params.put("username", ti_username.getText().toString());
                                    params.put("password", ti_password.getText().toString());
                                    return params;
                                }
                            };
                    com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(strReq);
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
