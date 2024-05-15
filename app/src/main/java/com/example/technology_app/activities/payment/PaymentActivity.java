package com.example.technology_app.activities.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.models.NotiSendData;
import com.example.technology_app.models.OnlineBanking.CreateOrder;
import com.example.technology_app.models.Order.Checkout;
import com.example.technology_app.models.Order.Payment;
import com.example.technology_app.models.Order.ProductOrder.ProductOrder;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.ApiPushNotification;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.retrofit.RetrofitClientPushNoti;
import com.example.technology_app.utils.AppInfo;
import com.example.technology_app.utils.GlobalVariable;
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
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
    Button btnCheckout, btnCheckoutZalopay;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProductOrder productOrder;
    List<ProductOrder.Item> productOrderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);

        initView();
        initControl();
    }

    private void initControl() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder("cod");
            }
        });
        btnCheckoutZalopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPaymentZalopay();
            }
        });
    }

    private void pushNotification(){
        String token = "cT08FWn7REavpLCXzgsl1c:APA91bGmyvSkQszBnczPNDehYviMGL-bMnOEi4EyEMuBqDlpyv6D0qMouXbXG-DVIeR_TjBe85Ml4Qe1_IVbTa0amE0Kd7FDjmCZEvfs24f85-gP7QbzH7_vViXW85mLnMlrf1nomUn-";
        Map<String, String> data = new HashMap<>();
        data.put("title", "Notification");
        data.put("body", "you have placed your order.");
        NotiSendData notiSendData = new NotiSendData(token, data);
        ApiPushNotification apiPushNotification = RetrofitClientPushNoti.getInstance().create(ApiPushNotification.class);
        compositeDisposable.add(apiPushNotification.sendNotification(notiSendData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        notiResponse -> {

                        },
                        throwable -> {
                            Log.d( "Log","123"+ throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }
    private void requestPaymentZalopay(){
        CreateOrder orderApi = new CreateOrder();
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);
        try {
            JSONObject data = orderApi.createOrder(String.valueOf(totalPrice));
            //JSONObject data = orderApi.createOrder("100000");
            Log.d("Amount", String.valueOf(totalPrice));
            String code = data.getString("return_code");
            //Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

            if (code.equals("1")) {
               String token = data.getString("zp_trans_token");
                createOrder("onlineBanking");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(PaymentActivity.this)
                                        .setTitle("Payment Success")
                                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setNegativeButton("Cancel", null).show();
                            }

                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(PaymentActivity.this)
                                .setTitle("User Cancel Payment")
                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(PaymentActivity.this)
                                .setTitle("Payment Fail")
                                .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOrder(String method){
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
        requestBody.put("payment", new Payment(method));
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
                                pushNotification();
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
                                    errorBody = Objects.requireNonNull(Objects.requireNonNull(httpException.response()).errorBody()).string();
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
    }

    private void initView() {
        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckoutZalopay = findViewById(R.id.btnCheckoutZalopay);
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