package com.example.projectone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectone.R;
import com.google.android.material.textfield.TextInputEditText;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_screen);

        var intent = getIntent();
        var checkoutTotal = (TextView) findViewById(R.id.checkoutTotal);

        Button payNow = findViewById(R.id.payNow);


        payNow.setOnClickListener(v -> {
            TextInputEditText name = findViewById(R.id.customerNameText);
            TextInputEditText email = findViewById(R.id.customerEmailText);
            TextInputEditText address = findViewById(R.id.customerShippingText);
            TextInputEditText card = findViewById(R.id.customerCardNumberText);
            TextInputEditText expiry = findViewById(R.id.customerExpiryText);
            TextInputEditText cvv = findViewById(R.id.customerCvvText);

            String nameString = name.getEditableText().toString().trim();
            String emailString = email.getEditableText().toString().trim();
            String addressString = address.getEditableText().toString().trim();
            String cardString = card.getEditableText().toString().trim();
            String expiryString = expiry.getEditableText().toString().trim();
            String cvvString = cvv.getEditableText().toString().trim();

            if (nameString.isEmpty()) {
                name.setError("Please enter a valid name.");
                name.requestFocus();
                return;
            }
            if (emailString.isEmpty()) {
                email.setError("Please enter a valid email.");
                email.requestFocus();
                return;
            }
            if (addressString.isEmpty()) {
                address.setError("Please enter a valid address.");
                address.requestFocus();
                return;
            }
            if (cardString.isEmpty() || cardString.length() < 16) {
                card.setError("Please enter a valid card number.");
                card.requestFocus();
                return;
            }
            if (expiryString.isEmpty() || expiryString.length() < 6) {
                expiry.setError("Please enter valid expiry month and year.");
                expiry.requestFocus();
                return;
            }
            if (cvvString.isEmpty() || cvvString.length() < 3) {
                cvv.setError("Please enter a valid CVV number.");
                cvv.requestFocus();
                return;
            }

            var i = new Intent(this, OrderComplete.class);
            startActivity(i);
        });


        checkoutTotal.setText("$" + Math.round(intent.getDoubleExtra("subTotal", 0.0d) * 100.0) / 100.0);
    }
}