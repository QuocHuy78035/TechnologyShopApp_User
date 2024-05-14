package com.example.technology_app.activities.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technology_app.R;
import com.example.technology_app.adapters.CartAdapter;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import org.json.JSONObject;

import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {
    Button btnCheckout;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        initView();
        initControl();
    }

    private void initControl() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createOrder();
            }
        });
    }

//    void createOrder(){
//        String userId = Paper.book().read("userId");
//        String accessToken = Paper.book().read("accessToken");
//        compositeDisposable.add(api.createOrder(userId, accessToken)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        orderModel -> {
//                            if (Objects.equals(orderModel.getStatus(), "201")) {
//                                cartModelView = cartModel;
//                                cartList = cartModel.getMetadata().getCart().getItems();
//                                Log.d("SizeCartList", "" + cartList.size());
//
//                                // Set up RecyclerView and adapter here
//                                if (cartList == null || cartList.isEmpty()) {
//                                    Log.d("CheckLengCartList", "Empty Cart");
//                                    emptyCart.setVisibility(View.VISIBLE);
//                                } else {
//                                    Log.d("CheckLengCartList", "" + cartList.size());
//                                    cartAdapter = new CartAdapter(getApplicationContext(), cartList);
//                                    recyclerView.setAdapter(cartAdapter);
//                                }
//                            } else {
//                                Log.d("Fail", "get cart Fail");
//                            }
//                        },
//                        throwable -> {
//                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                )
//        );
//    }

    private void initView() {
        btnCheckout = findViewById(R.id.btnCheckout);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}