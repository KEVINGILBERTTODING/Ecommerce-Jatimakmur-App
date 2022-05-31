package com.kencana.kampunganggrek;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kencana.kampunganggrek.Util.AppController;
import com.kencana.kampunganggrek.Util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.kencana.kampunganggrek.LoginActivity.TAG_USERNAME;
import static com.kencana.kampunganggrek.LoginActivity.session_status;

public class RegisterActivity extends AppCompatActivity {

    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    int success;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    String tag_json_obj = "json_obj_req";

    TextInputLayout til_email, til_user, til_pass, til_confpass;
    TextInputEditText ti_email, ti_user, ti_pass, ti_confpass;
    TextView signin;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    &&
                    conMgr.getActiveNetworkInfo().isAvailable()
                    &&
                    conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }

        sharedpreferences = getSharedPreferences(my_shared_preferences,Context.MODE_PRIVATE);

        ti_email = findViewById(R.id.ti_email_signup);
        ti_user = findViewById(R.id.ti_user_signup);
        ti_pass  = findViewById(R.id.ti_pass_signup);
        ti_confpass  = findViewById(R.id.ti_confpass_signup);
        til_email = findViewById(R.id.til_email_signup);
        til_user = findViewById(R.id.til_user_signup);
        til_pass = findViewById(R.id.til_pass_signup);
        til_confpass = findViewById(R.id.til_confpass_signup);
        signin = findViewById(R.id.button_signinSignup);
        signup = findViewById(R.id.button_signupSignup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ti_email.getText().toString();
                String username = ti_user.getText().toString();
                String password = ti_pass.getText().toString();
                String confir_password = ti_confpass.getText().toString();

                //reset indikator error
                til_email.setErrorEnabled(false);
                til_email.setError(null);
                til_user.setErrorEnabled(false);
                til_user.setError(null);
                til_pass.setErrorEnabled(false);
                til_pass.setError(null);
                til_confpass.setErrorEnabled(false);
                til_confpass.setError(null);

                //validasi form
                if( !isValidEmail(email) || username.isEmpty() || password.length() < 8 || !confir_password.equals(password)){
                    if(!isValidEmail(email)){
                        til_email.setErrorEnabled(true);
                        til_email.setError("Email tidak valid");
                    }
                    if(username.isEmpty()){
                        til_user.setErrorEnabled(true);
                        til_user.setError("Masukan Nama Pengguna");
                    }
                    if(password.length() < 8) {
                        til_pass.setErrorEnabled(true);
                        til_pass.setError("Password berisi minimal 8 karakter");
                    }
                    if(!confir_password.equals(password)) {
                        til_confpass.setErrorEnabled(true);
                        til_confpass.setError("Password tidak sama");
                    }
                }else{
                    try {
                        if (conMgr.getActiveNetworkInfo() !=
                                null
                                &&
                                conMgr.getActiveNetworkInfo().isAvailable()
                                &&
                                conMgr.getActiveNetworkInfo().isConnected()) {
                            register(email, username, password);
                        } else {
                            Toast.makeText(getApplicationContext(), "Periksa Koneksi Internet", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void register(final String email, final String username, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Tunggu Sebentar ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, ServerAPI.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Check for error node in json
                    if (success == 1) {
                        String username = jObj.getString(TAG_USERNAME);
                        Log.e("Berhasil Mendaftar!", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status,true);
                        editor.putString(TAG_USERNAME, username);
                        editor.putString("kode_konsumen", jObj.getString("kd_konsumen"));
                        editor.commit();
                        // Memanggil main activity
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra(TAG_USERNAME, username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Register Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //validasi email
    public final static boolean isValidEmail(CharSequence target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //hide keyboard when touch other view
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
