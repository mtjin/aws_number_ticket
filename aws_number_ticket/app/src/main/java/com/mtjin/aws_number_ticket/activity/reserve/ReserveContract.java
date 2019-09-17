package com.mtjin.aws_number_ticket.activity.reserve;

public interface ReserveContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void onFinish();
    }

    interface Presenter{
        void requestReserve(int restaurant_id, String restaurant_name, String apply_id, String apply_date, String reserve_date, String user_tel, String user_pw, String accept, String fcm); //레스토랑 예약
        void requestFcm(int id);
    }
}
