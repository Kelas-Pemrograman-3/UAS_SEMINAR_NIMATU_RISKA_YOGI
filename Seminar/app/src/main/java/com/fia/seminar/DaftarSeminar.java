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

public class DaftarSeminar extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private EditText edtEmail, edtNama, edtAlamat;
    private Button btnDaftar;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_seminar);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtEmail = (EditText) findViewById(R.id.emailPeserta);
        edtNama = (EditText) findViewById(R.id.namaPeserta);
        edtAlamat = (EditText) findViewById(R.id.alamatPeserta);

        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stremail = edtEmail.getText().toString();
                String strnama = edtNama.getText().toString();
                String stralamat = edtAlamat.getText().toString();

                if (stremail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Email Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strnama.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if (stralamat.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Alamat Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    inputdata(stremail,strnama, stralamat);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DaftarSeminar.this, MainActivity.class);
        startActivity(i);
        finish();

        super.onBackPressed();
    }

    private void inputdata(String email, String nama, String alamat) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("nama", nama);
        params.put("alamat", alamat);

        pDialog.setMessage("Mohon Tunggu...");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(configURL.InputPeserta, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            String msg;
                            if (status == true) {
                                msg = response.getString("pesan");
                            } else {
                                msg = response.getString("pesan");

                                Intent i = new Intent(DaftarSeminar.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                            VolleyLog.v("Response:%n %s", response.toString(4));
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
//        ApplicationController.getInstance().addToRequestQueue(req);
        mRequestQueue.add(req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
