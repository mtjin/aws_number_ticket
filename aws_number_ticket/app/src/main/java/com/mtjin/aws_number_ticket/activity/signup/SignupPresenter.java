package com.mtjin.aws_number_ticket.activity.signup;

import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPresenter  implements SignupContract.Presenter{

    private SignupContract.View view;

    public SignupPresenter(SignupContract.View view) {
        this.view = view;
    }


    @Override
    public void requestCheckId(String userId) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.checkUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean isDuplicate = response.body().getResult(); //중복된아이디인지
                    if(isDuplicate){ //중복
                        view.onToastMessage("중복된 아이디가 이미 존재합니다.");
                    }else{
                        view.onToastMessage("사용가능한 아이디입니다.");
                        view.blockUserId();
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
    public void requestSignup(String userId, String userPassword, String userRestaurant, String restaurantLocation,  String restaurantTel) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.saveUser(userId, userPassword, userRestaurant, restaurantLocation,restaurantTel );
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean isSuccess = response.body().getResult();
                    if(isSuccess){
                        view.onToastMessage("회원가입 되었습니다.");
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
