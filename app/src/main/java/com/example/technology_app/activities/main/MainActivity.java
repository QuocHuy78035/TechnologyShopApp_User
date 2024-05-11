package com.example.technology_app.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.activities.auth.LoginActivity;
import com.example.technology_app.activities.chat.ChatActivity;
import com.example.technology_app.activities.products.LaptopActivity;
import com.example.technology_app.adapters.CategoryAdapter;
import com.example.technology_app.models.CategoryModel;
import com.example.technology_app.models.NotiSendData;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.ApiPushNotification;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.retrofit.RetrofitClientPushNoti;
import com.example.technology_app.utils.GlobalVariable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ImageView imgAvt;
    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Api api;
    List<CategoryModel.Category> listCate;
    ListView listView;
    CategoryAdapter categoryAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initView();
        getToken();
        initControl();
    }

    private void initControl() {
        actionViewFlipper();
        actionBar();
        getCategory();
        getInfoUser();
        //pushNotification();
        getEventClick();
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

    private void getInfoUser() {
        String userId = Paper.book().read("userId");
        String accessToken = Paper.book().read("accessToken");
        assert accessToken != null;
        Log.d("accessToken", accessToken);
        compositeDisposable.add(api.getUserInfo(userId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userInfoModel -> {
                            if (userInfoModel.getStatus() == 200) {
                                if (userInfoModel.getMetadata() != null) {
                                    Log.v("check","123" + userInfoModel.getMetadata().user.name);
                                    Glide.with(getApplicationContext()).load(userInfoModel.getMetadata().user.getAvatar()).into(imgAvt);
                                } else {
                                    Toast.makeText(getApplicationContext(), "No categories available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        throwable -> {
                            Log.d( "Log","123"+ throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void actionViewFlipper() {
        List<String> mauQuangCao = new ArrayList<>();
        mauQuangCao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mauQuangCao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mauQuangCao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for (int i = 0; i < mauQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mauQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void getCategory() {
        compositeDisposable.add(api.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryModel -> {
                            if (categoryModel.getStatus() == 200) {
                                if (categoryModel.getMetadata() != null) {
                                    listCate = categoryModel.getMetadata().getCategories();
                                    listCate.add(new CategoryModel.Category("123", "Log out", "", "", 0, "https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png"));
                                    listCate.add(new CategoryModel.Category("123", "Chat", "", "", 0, "https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png"));
                                    categoryAdapter = new CategoryAdapter(listCate, getApplicationContext());
                                    listView.setAdapter(categoryAdapter);
                                    Log.d("CategoryTest", String.valueOf(listCate.size()));
                                } else {
                                    Toast.makeText(getApplicationContext(), "No categories available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        throwable -> {
                            Log.d( "Log","123"+ throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }


    private void getEventClick() {
//        imgSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(intent);
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent ram = new Intent(getApplicationContext(), LaptopActivity.class);
                        ram.putExtra("cateId", listCate.get(0).get_id());
                        startActivity(ram);
                        break;
                    case  1:
                        Intent laptop = new Intent(getApplicationContext(), LaptopActivity.class);
                        laptop.putExtra("cateId", listCate.get(1).get_id());
                        startActivity(laptop);
                        break;
                    case 2:
                        Intent signOut = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(signOut);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        break;
                    case 3:
                        Intent chat = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(chat);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        break;
                }
            }
        });
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if(!TextUtils.isEmpty(s)){
                            String userId = Paper.book().read("userId");
                            if(userId != null){
                                compositeDisposable.add(api.updateFirebaseToken(userId, s)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                userModel -> {
                                                }
                                        )
                                );
                            }else{
                                Log.d("Check UserId", "No UserId");
                            }
                        }
                    }
                });
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initView() {
        Paper.init(this);
        imgAvt = findViewById(R.id.imgAvtProfile);
        viewFlipper = findViewById(R.id.viewflipperHomeScreen);
        toolbar = findViewById(R.id.toolbarHomeScreen);
        drawerLayout = findViewById(R.id.drawerlayoutHomeScreen);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        listCate = new ArrayList<>();
        listView = findViewById(R.id.listviewHomeScreen);
        recyclerView = findViewById(R.id.recycleviewHomeScreen);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}