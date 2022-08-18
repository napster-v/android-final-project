package com.example.projectone.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectone.R;
import com.example.projectone.pojo.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class AuthenticationActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            String emailString = email.getText().toString().trim();
            String passwordString = password.getText().toString().trim();

            if (emailString.isEmpty()) {
                email.setError("Email is empty");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                email.setError("Enter the valid email address");
                email.requestFocus();
                return;
            }

            if (passwordString.isEmpty()) {
                password.setError("Enter the password");
                password.requestFocus();
                return;
            }

            if (password.length() < 6) {
                password.setError("Length of the password should be more than 6");
                password.requestFocus();
                return;
            }

            auth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    db.collection("cart").whereEqualTo("userId",
                            FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task1.getResult()) {
                                preferences.edit().putString("cartId", document.getId()).apply();
                            }
                        }
                    });
                }
                startActivity(new Intent(this, ProductActivityScreen.class));
            });
        });

        register.setOnClickListener(v -> {
            String emailString = email.getText().toString().trim();
            String passwordString = password.getText().toString().trim();

            if (emailString.isEmpty()) {
                email.setError("Email is empty");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                email.setError("Enter the valid email address");
                email.requestFocus();
                return;
            }

            if (passwordString.isEmpty()) {
                password.setError("Enter the password");
                password.requestFocus();
                return;
            }

            if (password.length() < 6) {
                password.setError("Length of the password should be more than 6");
                password.requestFocus();
                return;
            }

            auth.createUserWithEmailAndPassword(email.getText().toString(),
                    password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "You have successfully registered.", Toast.LENGTH_SHORT).show();

                    db.collection("cart").add(new Cart(FirebaseAuth.getInstance().getUid())).addOnCompleteListener(t -> {
                        preferences.edit().putString("cartId", t.getResult().getId()).apply();
                    });
                    Toast.makeText(this, "User created successfully.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(this, ProductActivityScreen.class));
                }

                if (task.isCanceled()) {
                    Toast.makeText(this, Objects.requireNonNull(task.getException()).toString(),
                            Toast.LENGTH_SHORT).show();
                }

                task.addOnFailureListener(e -> {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            });
        });
    }
}