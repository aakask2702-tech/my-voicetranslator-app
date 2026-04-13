package com.example.bhashatranslator;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.bhashatranslator.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding b;
    private SharedPreferences prefs;
    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        b = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        boolean autoPlay = prefs.getBoolean("auto_play", true);
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        boolean hinglish = prefs.getBoolean("hinglish_mode", false);
        boolean autoDetect = prefs.getBoolean("auto_detect", true);

        b.swAutoPlay.setChecked(autoPlay);
        b.swDarkMode.setChecked(darkMode);
        b.swHinglish.setChecked(hinglish);
        b.swAutoDetect.setChecked(autoDetect);

        b.swAutoPlay.setOnCheckedChangeListener((v, c) -> prefs.edit().putBoolean("auto_play", c).apply());
        b.swHinglish.setOnCheckedChangeListener((v, c) -> prefs.edit().putBoolean("hinglish_mode", c).apply());
        b.swAutoDetect.setOnCheckedChangeListener((v, c) -> prefs.edit().putBoolean("auto_detect", c).apply());

        b.swDarkMode.setOnCheckedChangeListener((v, c) -> {
            AppCompatDelegate.setDefaultNightMode(c ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            prefs.edit().putBoolean("dark_mode", c).apply();
        });

        b.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });
    }
}