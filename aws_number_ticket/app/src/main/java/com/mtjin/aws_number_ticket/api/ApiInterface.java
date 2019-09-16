package com.mtjin.aws_number_ticket.api;

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
    @POST("restaurant/search")
    Call<RestaurantInfo> getRestaurants(
            @Field("userRestaurant") String userRestaurant
    );

    @FormUrlEncoded
    @POST("restaurant/insert")
    Call<Apply> saveReserve(
            @Field("restaurant_id") String restaurant_id,
            @Field("apply_id") String apply_id,
            @Field("apply_date") String apply_date,
            @Field("reserve_date") String reserve_date,
            @Field("user_tel") String user_tel
    );
}
