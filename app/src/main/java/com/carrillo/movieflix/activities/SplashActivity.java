package com.carrillo.movieflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.carrillo.movieflix.R;

public class SplashActivity extends AppCompatActivity {
    protected boolean active = true;
    protected int splashTime = 3900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } finally {
                    openApp();
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.move_up);
                }
            }
        };
        splashThread.start();
    }

    public void openApp() {
        Intent i = new Intent(this, TrendingActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}