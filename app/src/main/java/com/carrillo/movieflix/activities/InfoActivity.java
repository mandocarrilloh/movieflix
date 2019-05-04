package com.carrillo.movieflix.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.carrillo.movieflix.R;

public class InfoActivity extends AppCompatActivity {

    private Button btcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        initListener();
    }

    private void initView(){
        btcall = findViewById(R.id.bt_call);
    }

    private void initListener(){
        btcall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:3012232554"));
                startActivity(intent);
            }
        });
    }
}