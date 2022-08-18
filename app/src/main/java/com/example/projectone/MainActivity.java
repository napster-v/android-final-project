package com.example.projectone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectone.activities.AuthenticationActivity;
import com.example.projectone.activities.ProductActivityScreen;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var explore = (Button) findViewById(R.id.explore);

        explore.setOnClickListener(v->{
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                var intent = new Intent(this, ProductActivityScreen.class);
                startActivity(intent);
            } else {
                var intent = new Intent(this, AuthenticationActivity.class);
                startActivity(intent);
            }
        });
    }
}