package com.mtjin.aws_number_ticket.activity.login;

import com.mtjin.aws_number_ticket.activity.signup.SignupContract;
import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void requestLogin(String userId, String userPassword) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.checkUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean isSuccess = response.body().getResult();
                    if(isSuccess){
                        view.onToastMessage("로그인 성공.");
                        view.successLogin(response.body().getUserId(), response.body().getUserRestaurant(), response.body().getRestaurantLocation(), response.body().getRestaurantTel() );
                    }else{
                        view.onToastMessage("아이디와 패스워드를 확인해주세요.");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }
}
