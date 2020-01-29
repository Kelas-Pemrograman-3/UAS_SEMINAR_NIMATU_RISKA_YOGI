package com.fia.seminar.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fia.seminar.DataSeminar;
import com.fia.seminar.Model.PesertaModel;
import com.fia.seminar.R;
import com.fia.seminar.UbahDanDetail;
import com.fia.seminar.server.configURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterPeserta extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<PesertaModel> item;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    public AdapterPeserta(Activity activity, List<PesertaModel> item, RequestQueue mRequestQueue, ProgressDialog pDialog) {
        this.activity = activity;
        this.item = item;
        this.mRequestQueue = mRequestQueue;
        this.pDialog = pDialog;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.konten_seminar, null);


        TextView namaseminar = (TextView) convertView.findViewById(R.id.txtnamaseminar);
        TextView tanggalseminar = (TextView) convertView.findViewById(R.id.txttgl);
        TextView pemateriseminar = (TextView) convertView.findViewById(R.id.txtpemateri);
        Button btnDetail = (Button) convertView.findViewById(R.id.btnDetail);
        Button btnUbah = (Button) convertView.findViewById(R.id.btnUbah);
        Button btnHapus = (Button) convertView.findViewById(R.id.btnHapus);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(Html.fromHtml("<font color='#000000'><b>Yakin ingin menghapus data ini ?</b></font>"))
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hapusData(item.get(position).get_id());
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                }).show();
            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, UbahDanDetail.class);
                i.putExtra("_id", item.get(position).get_id());
                i.putExtra("namaseminar", item.get(position).getNamaseminar());
                i.putExtra("tanggalseminar", item.get(position).getTanggalseminar());
                i.putExtra("hariseminar", item.get(position).getHariseminar());
                i.putExtra("tempatseminar", item.get(position).getTempatseminar());
                i.putExtra("pemateriseminar", item.get(position).getPemateriseminar());
                i.putExtra("detailorupdate", "detail");
                activity.startActivity(i);
                activity.finish();
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, UbahDanDetail.class);
                i.putExtra("_id", item.get(position).get_id());
                i.putExtra("namaseminar", item.get(position).getNamaseminar());
                i.putExtra("tanggalseminar", item.get(position).getNamaseminar());
                i.putExtra("hariseminar", item.get(position).getHariseminar());
                i.putExtra("tempatseminar", item.get(position).getTempatseminar());
                i.putExtra("pemateriseminar", item.get(position).getPemateriseminar());
                i.putExtra("detailorupdate", "update");
                activity.startActivity(i);
                activity.finish();
            }
        });

        namaseminar.setText(item.get(position).getNamaseminar());
        tanggalseminar.setText(item.get(position).getTanggalseminar());
        pemateriseminar.setText(item.get(position).getPemateriseminar());

        return convertView;
    }
    private void hapusData(String _id) {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, configURL.Delete + _id, null,
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
                                Intent i = new Intent(activity, DataSeminar.class);
                                activity.startActivity(i);
                                activity.finish();
                            }
                            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
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
}
