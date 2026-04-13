package com.example.bhashatranslator;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.bhashatranslator.auth.LoginActivity;
import com.example.bhashatranslator.databinding.ActivityMainBinding;
import com.example.bhashatranslator.util.LanguageUtils;
import com.example.bhashatranslator.viewmodel.TranslationViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TranslationViewModel vm;
    private ActivityResultLauncher<String> micPermission;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class)); finish(); return;
        }

        vm = new ViewModelProvider(this).get(TranslationViewModel.class);
        micPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {});

        // Setup Spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, LanguageUtils.LANGUAGE_NAMES);
        binding.spinnerFrom.setAdapter(adapter);
        binding.spinnerTo.setAdapter(adapter);
        binding.spinnerFrom.setSelection(0); // Auto/English default
        binding.spinnerTo.setSelection(1); // Hindi

        // Observers
        vm.inputText.observe(this, text -> binding.edtInput.setText(text));
        vm.outputText.observe(this, text -> binding.tvOutput.setText(text));
        vm.isLoading.observe(this, load -> binding.progressBar.setVisibility(load ? View.VISIBLE : View.GONE));
        vm.detectedLang.observe(this, lang -> binding.tvDetected.setText("Detected: " + lang));
        vm.error.observe(this, err -> { if(err!=null) Toast.makeText(this, err, Toast.LENGTH_LONG).show(); });

        // Mic Click
        binding.btnMic.setOnClickListener(v -> {
            micPermission.launch(Manifest.permission.RECORD_AUDIO);
            vm.startListening();
        });

        // Translate Button
        binding.btnTranslate.setOnClickListener(v -> {
            String from = LanguageUtils.LANGUAGE_CODES[binding.spinnerFrom.getSelectedItemPosition()];
            String to = LanguageUtils.LANGUAGE_CODES[binding.spinnerTo.getSelectedItemPosition()];
            vm.translate(binding.edtInput.getText().toString(), from, to);
        });

        // Speaker Button
        binding.btnSpeak.setOnClickListener(v -> {
            String text = vm.outputText.getValue();
            String code = LanguageUtils.LANGUAGE_CODES[binding.spinnerTo.getSelectedItemPosition()];
            if(text != null) vm.speakText(text, code);
        });

        // Settings & Logout
        binding.btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        binding.btnLogout.setOnClickListener(v -> { FirebaseAuth.getInstance().signOut(); finish(); });
    }
}