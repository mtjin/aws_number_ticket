package com.mtjin.aws_number_ticket.activity.admin;

import com.mtjin.aws_number_ticket.api.ApiClient;
import com.mtjin.aws_number_ticket.api.ApiInterface;
import com.mtjin.aws_number_ticket.model.AdminInfo;
import com.mtjin.aws_number_ticket.model.Apply;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPresenter implements AdminContract.Presenter {

    AdminContract.View view;

    public AdminPresenter(AdminContract.View view) {
        this.view = view;
    }

    @Override
    public void requestAdmin(int restaurant_id) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdminInfo> call = apiInterface.getApplys(restaurant_id);
        call.enqueue(new Callback<AdminInfo>() {
            @Override
            public void onResponse(Call<AdminInfo> call, Response<AdminInfo> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body().getResult());
                    view.onToastMessage("내 가게 예약 리스트");
                }
            }

            @Override
            public void onFailure(Call<AdminInfo> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void requestCancel(int id) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Apply> call = apiInterface.updateCancel(id);
        call.enqueue(new Callback<Apply>() {
            @Override
            public void onResponse(Call<Apply> call, Response<Apply> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getResult();
                    if(success){
                        view.onToastMessage("취소했습니다.");
                    }else{
                        view.onToastMessage("취소 실패했습니다.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Apply> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void requestAdminCertain(int restaurant_id, String reserve_date) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdminInfo> call = apiInterface.getApplysCertain(restaurant_id, reserve_date);
        call.enqueue(new Callback<AdminInfo>() {
            @Override
            public void onResponse(Call<AdminInfo> call, Response<AdminInfo> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body().getResult());
                    view.onToastMessage(reserve_date + "검색완료");
                }
            }

            @Override
            public void onFailure(Call<AdminInfo> call, Throwable t) {
                view.hideProgress();
                view.onToastMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void requestAccept(int id) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Apply> call = apiInterface.updateAccept(id);
        call.enqueue(new Callback<Apply>() {
            @Override
            public void onResponse(Call<Apply> call, Response<Apply> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getResult();
                    if(success){
                        view.onToastMessage("수락을 성공했습니다.");
                    }else{
                        view.onToastMessage("수락을 실패했습니다.");
                    }
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
