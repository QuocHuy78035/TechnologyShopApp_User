package com.example.technology_app.activities.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.technology_app.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtPass, edtPassConfirm;
    TextView txtNavigatorLogin;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }

    private void initControl() {
    }

    private void initView() {
        edtPass = findViewById(R.id.inputUserNameRegister);
        edtPass = findViewById(R.id.inputPassRegister);
        edtPassConfirm = findViewById(R.id.inputPassConfirmRegister);
        btnRegister = findViewById(R.id.btnRegister);
    }
}