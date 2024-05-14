package com.example.technology_app.activities.payment;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technology_app.R;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {
    Button btnCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        initView();
    }

    private void initView() {
        btnCheckout = findViewById(R.id.btnCheckout);
    }
}