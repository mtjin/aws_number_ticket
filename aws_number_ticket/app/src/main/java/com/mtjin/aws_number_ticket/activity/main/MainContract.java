package com.mtjin.aws_number_ticket.activity.main;

import com.mtjin.aws_number_ticket.model.User;

import java.util.List;

public interface MainContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void onGetResult(List<User> users);
    }
    interface Presenter{
        void requestSearch(String restaurant); //로그인 요청
    }
}
