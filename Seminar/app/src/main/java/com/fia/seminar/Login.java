package com.fia.seminar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fia.seminar.Session.SessionManager;
import com.fia.seminar.server.configURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private Button btnLinkRegister, btnLogin;
    private EditText edtEmail, edtPassword;
    private ProgressDialog pDialog;
    private RequestQueue mRequestQueue;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(this);

        if (session.isLoggedIn()) {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        edtEmail    = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin        = (Button) findViewById(R.id.btnLogin);
        btnLinkRegister = (Button) findViewById(R.id.btnLinkRegister);
        btnLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail    = edtEmail.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if(strEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Npm tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if(strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    login(strEmail, strPassword);
                }
            }
        });
    }

    private void login(String Email, String Password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", Email);
        params.put("password", Password);

        pDialog.setMessage("Mohon tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(configURL.Login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            Log.v("Response",response.toString());
                            boolean status = response.getBoolean("error");
                            String msg;
                            if(status == true){
                                msg = response.getString("pesan");
                            }else {
                                session.setLogin(true);
                                msg = response.getString("pesan");
                                Intent i = new Intent(Login.this,
                                        MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), msg,
                                    Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        // ApplicationController.getInstance().addToRequestQueue(req);
        mRequestQueue.add(req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (!pDialog.isShowing())
            pDialog.dismiss();
    }
}
