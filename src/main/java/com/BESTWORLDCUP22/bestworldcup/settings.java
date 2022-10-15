package com.BESTWORLDCUP22.bestworldcup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import android.widget.ImageButton;

import com.BESTWORLDCUP22.bestworldcup.databinding.ActivitySettingsBinding;

import java.util.Locale;

public class settings extends LangCompat {

    private ActivitySettingsBinding binding;
    ImageButton mgeSelect, menSelect, mptSelect;
    SwitchCompat msoundswitch;
    SwitchCompat mvibrowswitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        LanguageManager lang = new LanguageManager(this);

        msoundswitch = findViewById(R.id.soundswitch);
        mvibrowswitch = findViewById(R.id.vibroswitch);

        SharedPreferences getsound = getSharedPreferences("sound_switch",MODE_PRIVATE);
        SharedPreferences getvibration = getSharedPreferences("vibro_switch",MODE_PRIVATE);
        msoundswitch.setChecked(getsound.getBoolean("sound_switch", false));
        mvibrowswitch.setChecked(getvibration.getBoolean("vibro_switch", false));


        //language choice code
        binding.enSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang.updateResources("en");
                recreate();
            }
        });

        binding.geSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang.updateResources("de");
                recreate();
            }
        });
        binding.ptSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang.updateResources("pt");
                recreate();
            }
        });

    }

    public void Switchsound(View view) {
        if(msoundswitch.isChecked()){
            SharedPreferences.Editor soundswitchPreferences = getSharedPreferences("sound_switch",MODE_PRIVATE).edit();
            soundswitchPreferences.putBoolean("sound_switch", true);
            soundswitchPreferences.apply();
            msoundswitch.setChecked(true);

        }else{
            SharedPreferences.Editor soundswitchPreferences = getSharedPreferences("sound_switch",MODE_PRIVATE).edit();
            soundswitchPreferences.putBoolean("sound_switch", false);
            soundswitchPreferences.apply();
            msoundswitch.setChecked(false);
        }
    }

    public void Switchvibro(View view) {
        if(mvibrowswitch.isChecked()){
            SharedPreferences.Editor vibrationswitchPreferences = getSharedPreferences("vibro_switch",MODE_PRIVATE).edit();
            vibrationswitchPreferences.putBoolean("vibro_switch", true);
            vibrationswitchPreferences.apply();
            mvibrowswitch.setChecked(true);

        }else{
            SharedPreferences.Editor vibrationswitchPreferences = getSharedPreferences("vibro_switch",MODE_PRIVATE).edit();
            vibrationswitchPreferences.putBoolean("vibro_switch", false);
            vibrationswitchPreferences.apply();
            mvibrowswitch.setChecked(false);
        }
    }


    private void setLocale(String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());


    }
}