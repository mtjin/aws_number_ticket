package com.mtjin.aws_number_ticket.api;

import com.mtjin.aws_number_ticket.model.AdminInfo;
import com.mtjin.aws_number_ticket.model.Apply;
import com.mtjin.aws_number_ticket.model.RestaurantInfo;
import com.mtjin.aws_number_ticket.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/join")
    Call<User> saveUser(
            @Field("userId") String userId,
            @Field("userPassword") String userPassword,
            @Field("userRestaurant") String userRestaurant,
            @Field("restaurantLocation") String restaurantLocation,
            @Field("restaurantTel") String restaurantTel
    );

    @FormUrlEncoded
    @POST("user/checkId")
    Call<User> checkUser(
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<User> getLogin(
            @Field("userId") String userId,
            @Field("userPassword") String userPassword
    );

    @FormUrlEncoded
    @POST("restaurant/search")
    Call<RestaurantInfo> getRestaurants(
            @Field("userRestaurant") String userRestaurant
    );

    @FormUrlEncoded
    @POST("restaurant/insert")
    Call<Apply> saveReserve(
            @Field("restaurant_id") int restaurant_id,
            @Field("restaurant_name") String restaurant_name,
            @Field("apply_id") String apply_id,
            @Field("apply_date") String apply_date,
            @Field("reserve_date") String reserve_date,
            @Field("user_tel") String user_tel,
            @Field("user_pw") String user_pw,
            @Field("accept") String accept
    );

    @FormUrlEncoded
    @POST("restaurant/admin")
    Call<AdminInfo> getApplys(
            @Field("restaurant_id") int restaurant_id
    );

    @FormUrlEncoded
    @POST("restaurant/accept")
    Call<Apply> updateAccept(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("restaurant/cancel")
    Call<Apply> updateCancel(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("restaurant/admin/certain")
    Call<AdminInfo> getApplysCertain(
            @Field("restaurant_id") int restaurant_id,
            @Field("reserve_date") String reserve_date
    );

    @FormUrlEncoded
    @POST("user/reserve")
    Call<AdminInfo> getMyReserves(
            @Field("apply_id") String apply_id,
            @Field("user_tel") String user_tel,
            @Field("user_pw") String user_pw
    );
}
