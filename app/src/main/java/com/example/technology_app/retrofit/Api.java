package com.example.technology_app.retrofit;

import com.example.technology_app.models.CartModel;
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
    @POST("login")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("signup")
    @FormUrlEncoded
    Observable<UserModel> signup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("passwordConfirm") String passwordConfirm
    );

    @POST("verify?type=signUp")
    @FormUrlEncoded
    Observable<UserModel> verifyCodeForSignUp(
            @Field("email") String name,
            @Field("OTP") String otp
    );

    @POST("verify?type=forgotPwd")
    @FormUrlEncoded
    Observable<UserModel> verifyCodeForForgotPass(
            @Field("email") String name,
            @Field("OTP") String otp
    );

    @POST("forgotPassword")
    @FormUrlEncoded
    Observable<UserModel> forgotPass(
            @Field("email") String name
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

    @POST("cart")
    @FormUrlEncoded
    Observable<CartModel> addToCart(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Field("product") String productId,
            @Field("quantity") int quantity
    );

}
