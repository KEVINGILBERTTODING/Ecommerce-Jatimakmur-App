package com.JatimakmurApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.JatimakmurApp.Util.ServerAPI;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserLogin extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String username, kode_konsumen;
    ProgressDialog pd;
    Button xbtnUpdate;

    TextInputLayout tlUsername, tlPassword;
    TextInputEditText teUsername, tePassword;
    SharedPreferences sharedPreferences;
    public static final String TAG_USERNAME = "username";

//    EditText xedtUsername, getXedtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_login);



        pd = new ProgressDialog(UpdateUserLogin.this);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", null);
        kode_konsumen = sharedpreferences.getString("kode_konsumen", null);

        Log.d("kodekonsumen", kode_konsumen);


        // Definisikan Input edittext dan input layouttext

        tlUsername  =   findViewById(R.id.txtlayout_username);
        tlPassword  =   findViewById(R.id.txtlayout_password);
        teUsername  =   findViewById(R.id.txtedt_username);
        tePassword  =   findViewById(R.id.txtedt_password);
        // Definisikan button

        xbtnUpdate  =   findViewById(R.id.btnUpdate);

        teUsername.setText(username);

        xbtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });



    }

    //    Method untuk update profile

    private void updateProfile() {
        pd.setMessage("Memperbarui username dan password");
        pd.setCancelable(false);
        pd.show();
        StringRequest strReq = new
                StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE_USER_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.cancel();
                                Toast.makeText(UpdateUserLogin.this, "Berhasil memperbarui username & password", Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("username", teUsername.getText().toString());
                                editor.commit();

                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.cancel();
                                Toast.makeText(UpdateUserLogin.this, "Gagal Perbarui Username & Password", Toast.LENGTH_LONG).show();
                            }
                        }) {

                    // Mengirimkan data username dan password baru ke server
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("kd_konsumen", kode_konsumen);
                        params.put("username", teUsername.getText().toString());
                        params.put("password", tePassword.getText().toString());
                        return params;
                    }

                };
        com.JatimakmurApp.Util.AppController.getInstance().addToRequestQueue(strReq);

    }

    //Fungsi button back saat di klik
    public void back(View view) {

        Intent intent = new Intent(UpdateUserLogin.this, MainActivity.class);
        //        Menyimpan username ke dalam sharedpreferance
        intent.putExtra(TAG_USERNAME, username);
        startActivity(intent);


    }
}