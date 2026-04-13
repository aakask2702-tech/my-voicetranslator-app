package com.example.bhashatranslator.auth;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bhashatranslator.MainActivity;
import com.example.bhashatranslator.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding b;
    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class)); finish(); return;
        }

        b.btnLogin.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(b.edtEmail.getText().toString(), b.edtPass.getText().toString())
                .addOnSuccessListener(a -> startActivity(new Intent(this, MainActivity.class)).let(r -> finish()))
                .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });
        b.tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}