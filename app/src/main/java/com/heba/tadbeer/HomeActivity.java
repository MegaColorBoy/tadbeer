package com.heba.tadbeer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void goto_page(View view){
        String tag = ((Button)view).getTag().toString();

        Intent in = null;
        switch (tag){
            case "Bills":
                in = new Intent(this, BillsActivity.class);
                break;
            case "Cards":
                in = new Intent(this, CardsActivity.class);
                break;
            case "Warranties":
                in = new Intent(this, WarrantiesActivity.class);
                break;
        }
        if(in != null){
            startActivity(in);
        }
    }
}
