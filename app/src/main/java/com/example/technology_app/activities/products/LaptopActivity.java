package com.example.technology_app.activities.products;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.adapters.LaptopAdapter;
import com.example.technology_app.models.ProductModel;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Handler handler= new Handler();
    boolean isLoading = false;
    List<ProductModel> productModel;
    List<ProductModel.Product> productList;
    LaptopAdapter laptopAdapter;
    int page = 1;
    String cateId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_laptop);
        initView();
        initData();
    }

    private void initData() {
        actionToolBar();
        getData();
        addEventLoad();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isLoading){
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == productList.size() - 1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                productList.add(null);
                laptopAdapter.notifyItemInserted(productList.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                productList.remove(productList.size() - 1);
                laptopAdapter.notifyItemRemoved(productList.size());
                page = page + 1;
                laptopAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void getData() {
        Log.d("cater123", cateId);
        compositeDisposable.add(api.getLaptopProduct(cateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if (productModel.status == 200) {
                                Log.d("Success", "getlaptop Success");
                                Log.d("Success", "getlaptop Success" + productModel.getMetadata().getProduct().size());
                                if(laptopAdapter == null){
                                    productList = productModel.getMetadata().getProduct();
                                    laptopAdapter = new LaptopAdapter(getApplicationContext(), productList);
                                    recyclerView.setAdapter(laptopAdapter);
                                }else{
                                    int index = productList.size() - 1;
                                    int indexAdd = productModel.getMetadata().getProduct().size();
                                    for(int i = 0; i < indexAdd; i++){
                                        productList.add(productModel.getMetadata().getProduct().get(i));
                                    }
                                    laptopAdapter.notifyItemRangeInserted(index, indexAdd);
                                }
                            }
                        }
                )
        );
    }

    private void initView() {
        cateId = getIntent().getStringExtra("cateId");
        toolbar = findViewById(R.id.toolbar_laptopScreen);
        recyclerView = findViewById(R.id.recycleview_laptop);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        productList = new ArrayList<>();
        productModel = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}