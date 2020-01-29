package com.fia.seminar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fia.seminar.Session.SessionManager;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private CircularRevealCardView keluar,buatSeminar,lihatSeminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this);

        buatSeminar  = (CircularRevealCardView)findViewById(R.id.card);
        lihatSeminar = (CircularRevealCardView) findViewById(R.id.card2);
        keluar       = (CircularRevealCardView) findViewById(R.id.card3);

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSkip(false);
                session.setSessid(0);
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        lihatSeminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, DataSeminar.class);
                startActivity(a);
                finish();
            }
        });

        buatSeminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, BuatSeminar.class);
                startActivity(a);
                finish();
            }
        });
    }
}
