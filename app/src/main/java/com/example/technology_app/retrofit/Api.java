package com.example.technology_app.retrofit;

import com.example.technology_app.models.AddToCartModel;
import com.example.technology_app.models.CartModel;
import com.example.technology_app.models.CategoryModel;
import com.example.technology_app.models.Order.OrderModel;
import com.example.technology_app.models.Order.ProductOrder.ProductOrder;
import com.example.technology_app.models.Products.Laptop.ProductModel;
import com.example.technology_app.models.SignUp;
import com.example.technology_app.models.UpdateCart.UpdateCartModel;
import com.example.technology_app.models.UserInfoModel;
import com.example.technology_app.models.UserModel;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<SignUp> signup(
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
    Observable<ProductModel> getAllProductByCateId(@Query("category") String cateId);

    @POST("cart")
    @FormUrlEncoded
    Observable<AddToCartModel> addToCart(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Field("product") String productId,
            @Field("quantity") int quantity
    );

    @GET("cart")
    Observable<CartModel> getCartUser(@Header("x-client-id") String userId,
                                      @Header("authorization") String author);


    @Multipart
    @PATCH("user/profile")
    Call<com.example.technology_app.models.EditProfile.UserModel> editProfile(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Part("name") RequestBody name,
            @Part("gender") RequestBody gender,
            @Part("dateOfBirth") RequestBody dateOfBirth,
            @Part MultipartBody.Part images,
            @Part("address") RequestBody address,
            @Part("mobile") RequestBody mobile
    );

//    @POST("order")
//    @FormUrlEncoded
//    Observable<OrderModel> createOrder(
//            @Header("x-client-id") String userId,
//            @Header("authorization") String author,
//            @Field("totalPrice") int totalPrice,
//            @Field("totalApplyDiscount") int totalApplyDiscount,
//            @Field("feeShip") int feeShip,
//            @Field("total") int total,
//            @Field("shipping_address") String shipping_address,
//            @Field("method") String method,
//            @Field("coin") int coin,
//            @Field("voucher") String voucher,
//            @Field("products") String products,
//            @Field("phone") String phone
//    );

    @POST("order")
    Observable<OrderModel> createOrder(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Body Map<String, Object> requestBody
    );

    @PATCH("cart/quantity")
    @FormUrlEncoded
    Observable<UpdateCartModel> updateCartQuantity(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Field("product") String productId,
            @Field("quantity") int quantity
    );

    @PATCH("cart/item")
    @FormUrlEncoded
    Observable<UpdateCartModel> removeItemInCart(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Field("product") String productId
    );
}
