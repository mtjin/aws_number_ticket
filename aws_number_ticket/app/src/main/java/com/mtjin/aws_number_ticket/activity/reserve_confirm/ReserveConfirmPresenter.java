package com.mtjin.aws_number_ticket.activity.reserve_confirm;

import com.mtjin.aws_number_ticket.activity.reserve.ReserveContract;
import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.model.AdminInfo;
import com.mtjin.aws_number_ticket.model.Apply;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveConfirmPresenter implements ReserveConfirmContract.Presenter {
    ReserveConfirmContract.View view;


    public ReserveConfirmPresenter(ReserveConfirmContract.View view) {
        this.view = view;
    }

    @Override
    public void requestMyReserve(String apply_id, String user_tel, String user_pw) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdminInfo> call = apiInterface.getMyReserves(apply_id, user_tel, user_pw);
        call.enqueue(new Callback<AdminInfo>() {
            @Override
            public void onResponse(Call<AdminInfo> call, Response<AdminInfo> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body().getResult());
                    view.onToastMessage("예약한 리스트");
                }
            }

            @Override
            public void onFailure(Call<AdminInfo> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }
}

