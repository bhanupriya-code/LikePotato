package com.example.gullutesting3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SpalshScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2500;
    ImageView mpotatoanimation;
    TextView msplashtext, msplashtext2;
    Animation top, bottom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalsh_screen);

        mpotatoanimation = findViewById(R.id.imagespalsh);
        msplashtext = findViewById(R.id.splashtext);
        msplashtext2 = findViewById(R.id.splashtext2);

        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);

        mpotatoanimation.setAnimation(top);
        msplashtext.setAnimation(bottom);
        msplashtext2.setAnimation(bottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpalshScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }

}