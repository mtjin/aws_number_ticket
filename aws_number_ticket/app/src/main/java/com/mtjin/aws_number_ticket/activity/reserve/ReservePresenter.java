package com.mtjin.aws_number_ticket.activity.reserve;

import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.model.Apply;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservePresenter implements  ReserveContract.Presenter {
    ReserveContract.View view;

    public ReservePresenter(ReserveContract.View view) {
        this.view = view;
    }

    @Override
    public void requestReserve(int restaurant_id, String restaurant_name, String apply_id, String apply_date, String reserve_date, String user_tel, String user_pw, String accept) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Apply>  call = apiInterface.saveReserve(restaurant_id, restaurant_name,  apply_id, apply_date, reserve_date, user_tel ,user_pw,accept);
        call.enqueue(new Callback<Apply>() {
            @Override
            public void onResponse(Call<Apply> call, Response<Apply> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body().getResult()){
                    view.onToastMessage("예약이 신청되었습니다");
                    view.onFinish();
                }
            }

            @Override
            public void onFailure(Call<Apply> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }
}
