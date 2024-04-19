package com.example.technology_app.activities.products;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.models.ProductModel;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailProductActivity extends AppCompatActivity {
    TextView txtProductName, txtProductPrice, txtProductDes;
    Button btnAddToCart, btnWatchVid;
    ImageView imgProduct;
    ProductModel.Product product;
    Spinner spinner;
    Toolbar toolbar;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);
        initView();
        initControl();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        product = (ProductModel.Product) getIntent().getSerializableExtra("detailProduct");
        assert product != null;
        txtProductName.setText(product.getName());
        txtProductDes.setText(product.getDescription());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtProductPrice.setText(decimalFormat.format(product.getPrice()) +"đ");
        Glide.with(getApplicationContext()).load(product.getImages().get(0)).into(imgProduct);
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterSpin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterSpin);
    }

    private void initControl() {
        btnWatchVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VidDemoProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        txtProductName = findViewById(R.id.txt_detailProductName);
        txtProductPrice = findViewById(R.id.txt_detailProductPrice);
        imgProduct = findViewById(R.id.img_detailProduct);
        txtProductDes = findViewById(R.id.txtDetailProductDes);
        frameLayout = findViewById(R.id.frameCartDetailProduct);
        notificationBadge = findViewById(R.id.menu_quantity);
        spinner = findViewById(R.id.spinner);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        toolbar = findViewById(R.id.toolbar_detailProduct);
        btnWatchVid = findViewById(R.id.btnWatchVid);
    }
}