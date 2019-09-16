package com.mtjin.aws_number_ticket.activity.Reserve;

import com.mtjin.aws_number_ticket.model.User;

import java.util.List;

public interface ReserveContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void onFinish();
    }

    interface Presenter{
        void requestReserve(String restaurant_id, String apply_id, String apply_date, String reserve_date, String user_tel); //로그인 요청
    }
}
