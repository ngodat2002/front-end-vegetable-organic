package com.sem4.front_end_vegetable_organic.retrofit;

import com.sem4.front_end_vegetable_organic.model.MenuModel;
import com.sem4.front_end_vegetable_organic.model.OrderDetailModel;
import com.sem4.front_end_vegetable_organic.model.OrderModel;
import com.sem4.front_end_vegetable_organic.model.ProductModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {

    @GET("getspmoi.php")
    Observable<ProductModel>getSpMoi();


    @GET("getloaisp.php")
    Observable<MenuModel>getLoaiSp();


    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<ProductModel>search(
            @Field("search")String search
    );
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<ProductModel>getSanPham(
            @Field("page")int page,
            @Field("category_id")int category_id
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<OrderModel>xemDonHang(
            @Field("user_id")int user_id
    );
    @POST("xemdonhangdetails.php")
    @FormUrlEncoded
    Observable<OrderDetailModel>xemDetails(
            @Field("user_id")int user_id
    );
}
