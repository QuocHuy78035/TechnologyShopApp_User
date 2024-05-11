package com.example.technology_app.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.technology_app.R;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtUserName, edtPass, edtPassConfirm;
    TextView txtNavigatorLogin;

    Button btnRegister;
    Api api;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }

    private void initControl() {
        register();
    }

    private void register() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String passConfirm = edtPassConfirm.getText().toString().trim();
        String userName = edtUserName.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passConfirm)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập user name", Toast.LENGTH_SHORT).show();
        } else {
            if (pass.equals(passConfirm)) {
                //post data
                compositeDisposable.add(api.signup(userName, email, pass, passConfirm)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.getStatusCode() == 200) {

                                        Paper.book().write("password", pass);
//                                        Utils.currentUser.setEmail(email);
//                                        Utils.currentUser.setPass(pass);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Fail: " + userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Log.d("Log", "123" + throwable.getMessage());
                                    Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
                );

            } else {
                Toast.makeText(getApplicationContext(), "Mật khẩu và mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        edtEmail = findViewById(R.id.inputEmailRegister);
        edtUserName = findViewById(R.id.inputUserNameRegister);
        edtPass = findViewById(R.id.inputPassRegister);
        edtPassConfirm = findViewById(R.id.inputPassConfirmRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txtNavigatorLogin = findViewById(R.id.txtNavigatorLogin);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}