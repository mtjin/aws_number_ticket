package com.mtjin.aws_number_ticket.activity.login;

public interface LoginContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void successLogin(int id, String userId, String restaurant ,String location ,String tel);
    }
    interface Presenter{
        void requestLogin(String userId, String userPassword); //로그인 요청
    }
}
