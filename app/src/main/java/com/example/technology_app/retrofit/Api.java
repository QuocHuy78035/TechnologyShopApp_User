package com.example.technology_app.retrofit;

import com.example.technology_app.models.CategoryModel;
import com.example.technology_app.models.ProductModel;
import com.example.technology_app.models.UserInfoModel;
import com.example.technology_app.models.UserModel;
import com.google.firebase.auth.UserInfo;

import io.paperdb.Paper;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String userId = Paper.book().read("userId");
    String accessToken = Paper.book().read("accessToken");
    String id = "";

    @POST("login")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @PATCH("user/tokenFirebase")
    @FormUrlEncoded
    Observable<UserModel> updateFirebaseToken(
            @Field("userId") String userId,
            @Field("tokenFirebase") String tokenFirebase
    );

    @GET("category")
    Observable<CategoryModel> getCategory();


    @GET("user")
    Observable<UserInfoModel> getUserInfo(
            @Header("x-client-id") String userId,
            @Header("authorization") String author
    );

    @GET("product")
    Observable<ProductModel> getLaptopProduct(@Query("category") String cateId);

    @GET("product/{productId}")
    Observable<ProductModel> getDetailProduct(@Path("productId") String productId);

}
