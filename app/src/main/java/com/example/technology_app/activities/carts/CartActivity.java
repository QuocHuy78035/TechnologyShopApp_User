package com.example.technology_app.activities.carts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.activities.payment.PaymentActivity;
import com.example.technology_app.adapters.CartAdapter;
import com.example.technology_app.models.CartUserModel;
import com.example.technology_app.models.EventBus.CaculatorSumEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    TextView emptyCart, totalPrice;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnBuy;
    CartAdapter cartAdapter;
    List<CartUserModel.Metadata.Cart.Item> cartList;
    int totalPriceProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        initView();
        initControl();
        calculatorSumPrice();
    }

    @SuppressLint("SetTextI18n")
    private void calculatorSumPrice() {
        totalPriceProduct = 0;
        for(int i = 0; i < cartList.size(); i++){
            totalPriceProduct += Integer.parseInt(cartList.get(i).getProduct().getSale_price()) * cartList.get(i).getQuantity();
        }
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        totalPrice.setText(totalPriceProduct +"đ");
    }

    private void initView() {
        emptyCart = findViewById(R.id.txtEmptyCart);
        recyclerView = findViewById(R.id.recycleviewCart);
        toolbar = findViewById(R.id.toolbar);
        totalPrice = findViewById(R.id.txtTotalPrice);
        btnBuy = findViewById(R.id.btnBuy);
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
            if(cartList.isEmpty()){
            emptyCart.setVisibility(View.VISIBLE);
        }else{
            cartAdapter = new CartAdapter(getApplicationContext(), cartList);
            recyclerView.setAdapter(cartAdapter);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("totalPrice", totalPriceProduct);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void eventTinhTien(CaculatorSumEvent event){
        if(event != null){
            calculatorSumPrice();
        }
    }
}