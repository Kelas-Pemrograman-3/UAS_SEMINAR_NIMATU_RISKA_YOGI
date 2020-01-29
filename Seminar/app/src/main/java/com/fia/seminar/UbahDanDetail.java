package com.fia.seminar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
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

public class UbahDanDetail extends AppCompatActivity {

    EditText nama, tanggal, hari, tempat, pemateri;
    Button btnKirim;

    private RequestQueue mRequestQueue;

    private ProgressDialog pDialog;

    Intent intent;
    String detailorupdate, _id, strnama, strtanggal, strhari, strtempat, strpemateri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_dan_detail);

        mRequestQueue = Volley.newRequestQueue(this);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        nama = (EditText) findViewById(R.id.namaseminar);
        tanggal = (EditText) findViewById(R.id.tanggal);
        hari = (EditText) findViewById(R.id.hari);
        tempat = (EditText) findViewById(R.id.tempat);
        pemateri = (EditText) findViewById(R.id.pemateri);

        btnKirim = (Button) findViewById(R.id.btnKirim);

        intent = getIntent();
        detailorupdate = intent.getStringExtra("detailorupdate");
        _id         = intent.getStringExtra("_id");
        strnama      = intent.getStringExtra("namaseminar");
        strtanggal   = intent.getStringExtra("tanggalseminar");
        strhari      = intent.getStringExtra("hariseminar");
        strtempat     = intent.getStringExtra("tempatseminar");
        strpemateri  = intent.getStringExtra("pemateriseminar");


        nama.setText(strnama);
        tanggal.setText(strtanggal);
        hari.setText(strhari);
        tempat.setText(strtempat);
        pemateri.setText(strpemateri);


        if(detailorupdate.equals("detail")){
            nama.setEnabled(false);
            tanggal.setEnabled(false);
            hari.setEnabled(false);
            tempat.setEnabled(false);
            pemateri.setEnabled(false);

            btnKirim.setVisibility(View.GONE);
        }
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnama = nama.getText().toString();
                String strtanggal = tanggal.getText().toString();
                String strhari = hari.getText().toString();
                String strtempat = tempat.getText().toString();
                String strpemateri= pemateri.getText().toString();

                if (strnama.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama seminar Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strtanggal.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Tanggal Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strhari.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Hari Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strtempat.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Tempat Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strpemateri.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Pemater Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }
                ubahSeminar(strnama, strtanggal, strhari, strtempat, strpemateri);
            }
        });
    }

    private void ubahSeminar(String nama, String tanggal, String hari, String tempat, String pemateri) {

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("namaseminar", nama);
        params.put("tanggalseminar", tanggal);
        params.put("hariseminar", hari);
        params.put("tempatseminar", tempat);
        params.put("pemateriseminar", pemateri);


        pDialog.setMessage("Mohon tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, configURL.Update + _id, new JSONObject(params),
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
                                Intent i = new Intent(UbahDanDetail.this,
                                        DataSeminar.class);
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
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
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
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UbahDanDetail.this, DataSeminar.class);
        startActivity(i);
        finish();
    }
}
