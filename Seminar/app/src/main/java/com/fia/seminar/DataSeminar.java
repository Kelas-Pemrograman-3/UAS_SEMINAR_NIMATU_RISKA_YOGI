package com.fia.seminar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fia.seminar.Adapter.AdapterPeserta;
import com.fia.seminar.Model.PesertaModel;
import com.fia.seminar.server.configURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataSeminar extends AppCompatActivity {

    ProgressDialog pDialog;
    AdapterPeserta adapter;
    ListView list;
    ArrayList<PesertaModel> newsList = new ArrayList<PesertaModel>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_seminar);

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterPeserta(DataSeminar.this, newsList, mRequestQueue, pDialog);
        list.setAdapter(adapter) ;
        getAllData();
    }

    private void getAllData() {
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, configURL.DataSeminar, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    PesertaModel seminar = new PesertaModel();
                                    seminar.set_id(jsonObject.getString("_id"));
                                    seminar.setNamaseminar(jsonObject.getString("namaseminar"));
                                    seminar.setTanggalseminar(jsonObject.getString("tanggalseminar"));
                                    seminar.setHariseminar(jsonObject.getString("hariseminar"));
                                    seminar.setTempatseminar(jsonObject.getString("tempatseminar"));
                                    seminar.setPemateriseminar(jsonObject.getString("pemateriseminar"));
                                    newsList.add(seminar);
                                }

                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
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
    public void onBackPressed(){
        Intent i = new Intent(DataSeminar.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
