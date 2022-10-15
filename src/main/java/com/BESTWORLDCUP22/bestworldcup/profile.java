package com.BESTWORLDCUP22.bestworldcup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


public class profile extends LangCompat {

    MediaPlayer mediaPlayer;
    Vibrator vibrator;
    public  boolean sound_boolean, vibration_boolean;
    TextView mtestingtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        mediaPlayer = MediaPlayer.create(this,R.raw.clicksound);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mtestingtext = findViewById(R.id.testingtext2);

        mtestingtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sound_boolean){
                    mediaPlayer.start();
                }else{

                }
                if(vibration_boolean)  ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150,10));;
                startActivity(new Intent(profile.this, settings.class));
            }
        });
    }
    @Override
    protected void onResume() {
        SharedPreferences getsound = getSharedPreferences("sound_switch",MODE_PRIVATE);
        SharedPreferences getvibration = getSharedPreferences("vibro_switch",MODE_PRIVATE);
        sound_boolean = getsound.getBoolean("sound_switch",false);
        vibration_boolean = getvibration.getBoolean("vibro_switch",false);


        super.onResume();
    }
    private void setLocale(String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());


    }

}