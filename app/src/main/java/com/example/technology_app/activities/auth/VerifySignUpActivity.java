package com.example.technology_app.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
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
import okhttp3.internal.Util;

public class VerifySignUpActivity extends AppCompatActivity {

    EditText edtValue;
    Api api;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_sign_up);
        initView();
        initControl();
    }

    private void initControl() {
        verify();
    }

    private void verify() {
        String email = Paper.book().read("email");
        String otp = edtValue.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập OTP", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(api.verifyCodeForSignUp(email, otp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.statusCode == 200) {
                                    Toast.makeText(getApplicationContext(), "Success: " + userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Paper.book().write("userId", userModel.getMetadata().getUser().get_id());
                                    Paper.book().write("email", userModel.getMetadata().getUser().getEmail());
                                    Paper.book().write("accessToken", userModel.getMetadata().getTokens().getAccessToken());
                                    Paper.book().write("refreshToken", userModel.getMetadata().getTokens().getRefreshToken());
                                    Paper.book().write("tokenFirebase", userModel.getMetadata().getUser().getTokenFirebase());

                                    String pass = Paper.book().read("password");
                                    assert email != null;
                                    assert pass != null;
                                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Log.d("TAG", "createUserWithEmail:success");
//                                                        FirebaseUser user = mAuth.getCurrentUser();
//                                                        updateUI(user);
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                                        Toast.makeText(VerifySignUpActivity.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                        //updateUI(null);
                                                    }
                                                }
                                            });

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        }
    }

    private void initView() {
        edtValue.findViewById(R.id.inputVerifySignUp);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
