package com.example.technology_app.activities.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.adapters.CartAdapter;
import com.example.technology_app.models.CartModel;
import com.example.technology_app.models.Order.Checkout;
import com.example.technology_app.models.Order.Payment;
import com.example.technology_app.models.Order.Product;
import com.example.technology_app.models.Order.ProductOrder.ProductOrder;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {
    Button btnCheckout;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProductOrder productOrder;
    List<ProductOrder.Item> productOrderItems;
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
                createOrder();
            }
        });
    }

    void createOrder(){
        String userId = Paper.book().read("userId");
        String accessToken = Paper.book().read("accessToken");
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);

        // Create the list of ProductOrder.Item
        List<ProductOrder.Item> productOrderItems = GlobalVariable.listCartBuy.stream()
                .map(item -> new ProductOrder.Item(item.getProduct().get_id(), item.getQuantity()))
                .collect(Collectors.toList());

        // Serialize the list of items to JSON
        Gson gson = new Gson();
        String productOrderJson = gson.toJson(productOrderItems);

        Log.d("Serialized Products", productOrderJson);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("checkout", new Checkout(totalPrice, 0, 0, totalPrice));
        requestBody.put("shipping_address", "HCM");
        requestBody.put("payment", new Payment("CID"));
        requestBody.put("coin", 0);
        requestBody.put("voucher", null);
        requestBody.put("products", productOrderItems);
        requestBody.put("phone", "0394884438");

        compositeDisposable.add(api.createOrder(userId, accessToken, requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            if (Objects.equals(orderModel.getStatus(), "201")) {
                                Log.d("Success", "Create order success");
                                GlobalVariable.listCartBuy.clear();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("Fail", "Create order failed with status: " + orderModel.getStatus());
                            }
                        },
                        throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
                                int statusCode = httpException.code();
                                String errorBody = "";
                                try {
                                    errorBody = httpException.response().errorBody().string();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.e("Error", "HTTP Error: " + statusCode + " " + errorBody);
                            } else {
                                Log.e("Error", "Create order error: " + throwable.getMessage());
                                Log.e("Error", Log.getStackTraceString(throwable));
                            }
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );

//        compositeDisposable.add(api.createOrder(userId, accessToken, totalPrice, 0, 0, totalPrice, "HCM", "COD", 0, null, productOrderJson, "0394883848")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        orderModel -> {
//                            if (Objects.equals(orderModel.getStatus(), "201")) {
//                                Log.d("Success", "Create order success");
//                                GlobalVariable.listCartBuy.clear();
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                            } else {
//                                Log.d("Fail", "Create order failed with status: " + orderModel.getStatus());
//                            }
//                        },
//                        throwable -> {
//                            if (throwable instanceof retrofit2.HttpException) {
//                                retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
//                                int statusCode = httpException.code();
//                                String errorBody = "";
//                                try {
//                                    errorBody = httpException.response().errorBody().string();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                Log.e("Error", "HTTP Error: " + statusCode + " " + errorBody);
//                            } else {
//                                Log.e("Error", "Create order error: " + throwable.getMessage());
//                                Log.e("Error", Log.getStackTraceString(throwable));
//                            }
//                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                )
//        );
    }

    private void initView() {
        btnCheckout = findViewById(R.id.btnCheckout);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);

         productOrderItems = GlobalVariable.listCartBuy.stream()
                .map(item -> new ProductOrder.Item(item.getProduct().get_id(), item.getQuantity()))
                .collect(Collectors.toList());

        productOrder = new ProductOrder(productOrderItems);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}