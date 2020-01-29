package com.fia.seminar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.fia.seminar.server.configURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

    public class Register extends AppCompatActivity {
    private Button btnLinkLogin, btnRegis;
    private EditText edtEmail, edtPassword, edtNama, edtAlamat;
    private ProgressDialog pDialog;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtEmail = (EditText) findViewById(R.id.emailRegis);
        edtPassword = (EditText) findViewById(R.id.passwordRegis);
        edtNama = (EditText) findViewById(R.id.namaRegis);
        edtAlamat = (EditText) findViewById(R.id.alamat);

        btnRegis        = (Button) findViewById(R.id.btnRegis);
        btnLinkLogin    = (Button) findViewById(R.id.btnLinkLogin);
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtEmail.getText().toString();
                String strPassword = edtEmail.getText().toString();
                String strNama = edtEmail.getText().toString();
                String strAlamat = edtEmail.getText().toString();

                if(strEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if(strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if(strNama.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if(strAlamat.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Alamat tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    registrasi(strEmail, strPassword, strNama, strAlamat);
                }
            }
        });
    }

    private void registrasi(String strEmail, String strPassword, String strNama, String strAlamat) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", strEmail);
        params.put("password", strPassword);
        params.put("nama", strNama);
        params.put("alamat", strAlamat);

        pDialog.setMessage("Mohon tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(configURL.Register, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            String msg;
                            if(status == true){
                                msg = response.getString("pesan");
                            }else {
                                msg = response.getString("pesan");
                                Intent i = new Intent(Register.this,
                                        Login.class);
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
