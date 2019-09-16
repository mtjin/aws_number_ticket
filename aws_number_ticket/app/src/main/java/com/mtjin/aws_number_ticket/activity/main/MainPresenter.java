package com.mtjin.aws_number_ticket.activity.main;

import com.mtjin.aws_number_ticket.activity.login.LoginContract;
import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.model.RestaurantInfo;
import com.mtjin.aws_number_ticket.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements  MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void requestSearch(String restaurant) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RestaurantInfo> call = apiInterface.getRestaurants(restaurant);
        call.enqueue(new Callback<RestaurantInfo>() {
            @Override
            public void onResponse(Call<RestaurantInfo> call, Response<RestaurantInfo> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body().getResult());
                    view.onToastMessage("검색완료");
                }
            }

            @Override
            public void onFailure(Call<RestaurantInfo> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }
}
