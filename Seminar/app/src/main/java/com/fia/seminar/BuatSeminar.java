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

public class BuatSeminar extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private EditText edtnama,edttgl,edthari,edttempat,edtpemateri;
    private Button btntambah;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_seminar);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtnama      = (EditText) findViewById(R.id.edtnama);
        edttgl     = (EditText) findViewById(R.id.edtwaktu);
        edthari = (EditText) findViewById(R.id.edthari);
        edttempat = (EditText) findViewById(R.id.edttempat);
        edtpemateri = (EditText) findViewById(R.id.edtpemateri);



        btntambah = findViewById(R.id.btntambah);

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnama = edtnama.getText().toString();
                String strtgl = edttgl.getText().toString();
                String strhari = edthari.getText().toString();
                String strtempat = edttempat.getText().toString();
                String strpemateri = edtpemateri.getText().toString();

                if (strnama.isEmpty()){
                    Toast.makeText(getApplicationContext()," Nama Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if (strtgl.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tanggal Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if (strhari.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Hari Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if (strtempat.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tempat Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if (strpemateri.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Pemateri Tidak Boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else {
                    inputdata(strnama, strtgl, strhari, strtempat, strpemateri);
                }
            }
        });

    }

    // Navigasi Back tombol
    @Override
    public void onBackPressed() {
        Intent i = new Intent(BuatSeminar.this,MainActivity.class);
        startActivity(i);
        finish();

        super.onBackPressed();
    }

    private void inputdata(String namaseminar,String tanggalseminar, String hariseminar,String tempatseminar,String pemateri){
//        final String URL = "/volley/resource/12";
// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("namaseminar", namaseminar);
        params.put("tanggalseminar", tanggalseminar);
        params.put("hariseminar", hariseminar);
        params.put("tempatseminar", tempatseminar);
        params.put("pemateriseminar", pemateri);

        pDialog.setMessage("Mohon Tunggu...");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(configURL.InputSeminar, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            String msg;
                            if (status== true){
                                msg = response.getString("pesan");
                            }else {
                                msg = response.getString("pesan");

                                Intent i = new Intent(BuatSeminar.this, BuatSeminar.class);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(),  msg, Toast.LENGTH_LONG).show();
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
