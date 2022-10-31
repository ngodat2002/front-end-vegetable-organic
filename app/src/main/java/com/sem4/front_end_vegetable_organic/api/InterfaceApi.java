package com.sem4.front_end_vegetable_organic.api;

import com.sem4.front_end_vegetable_organic.model.CartItem;
import com.sem4.front_end_vegetable_organic.model.LoginRequest;
import com.sem4.front_end_vegetable_organic.model.Product;
import com.sem4.front_end_vegetable_organic.model.User;
import com.sem4.front_end_vegetable_organic.model.signinRequest;
import com.sem4.front_end_vegetable_organic.model.userCheckout;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface InterfaceApi {
    @Headers("Content-Type: application/json")

    @POST("/api/auth/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);

    //@POST("api/auth/signup")
    @POST("/api/auth/signup")
    Call<signinRequest> Signin(@Body signinRequest sinRequest);

    @POST("/api/auth/signupConfim/{otp}")
    Call<signinRequest> signupConfim(@Body signinRequest sinRequest, @Path("otp") int otp);

    @GET("/app/api/cart/{id}/{quantity}")
    Call<Product> addCart(@Path("id") int id, @Path("quantity") int quantity);

    @DELETE("/app/api/cart/remove/{id}")
    Call<Product> removeCart(@Path("id") int id);

    @POST("/app/api/cart/suc/checkout/{email}")
    Call<userCheckout> checkout(@Body userCheckout user, @Path("email") String email);

    @GET("/app/api/cart/clear")
    Call<CartItem> clear();
}
