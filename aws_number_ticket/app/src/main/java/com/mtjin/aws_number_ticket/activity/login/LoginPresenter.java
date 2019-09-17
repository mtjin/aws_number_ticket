package com.mtjin.aws_number_ticket.activity.login;

import com.mtjin.aws_number_ticket.activity.signup.SignupContract;
import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.api.Fcm;
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
        Call<User> call = apiInterface.getLogin(userId, userPassword);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean isSuccess = response.body().getResult();
                    if(isSuccess){
                        view.onToastMessage("로그인 성공.");
                        view.successLogin(response.body().getId());
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

    @Override
    public void requestUpdateFcm(int id, String fcm) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.updateFcm(id, fcm);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getResult();
                    if(success){
                        view.onToastMessage("FCM변경성공");
                    }else{
                        view.onToastMessage("FCM변경실패");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
