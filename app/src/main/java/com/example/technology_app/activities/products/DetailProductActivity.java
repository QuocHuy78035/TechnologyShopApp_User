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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.models.ProductModel;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailProductActivity extends AppCompatActivity {
    TextView txtProductName, txtProductPrice, txtProductDes;
    Button btnAddToCart, btnWatchVid;
    ImageView imgProduct;
    ProductModel.Product product;
    Spinner spinner;
    Toolbar toolbar;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);
        Paper.init(this);
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

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = Paper.book().read("userId");
                String accessToken = Paper.book().read("accessToken");

                if(userId != null && accessToken != null){
                    Log.d("Success", userId);
                    Log.d("Success", accessToken);

                    compositeDisposable.add(api.addToCart(userId, accessToken, product.get_id(), 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    cartModel -> {
                                        if (cartModel.status == 200) {
                                            Log.d("Success", "add to cart Success");
                                        }else{
                                            Log.d("Fail", "add to cart Fail");
                                        }
                                    },
                                    throwable -> {
                                        Log.d( "Log","123"+ throwable.getMessage());
                                        Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            )
                    );
                }else{
                    Toast.makeText(getApplicationContext(), "Token or UserId is null", Toast.LENGTH_SHORT).show();
                }


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
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}