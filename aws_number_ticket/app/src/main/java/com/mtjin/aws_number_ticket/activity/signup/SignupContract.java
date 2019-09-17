package com.mtjin.aws_number_ticket.activity.signup;

import android.content.Context;


public interface SignupContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void blockUserId(); //중복확인 후 아이디 변경 못하게막기
    }
    interface Presenter{
        void requestCheckId(String userId); //아이디 중복확인 요청
        void requestSignup(String userId, String userPassword, String userRestaurant, String restaurantLocation , String restaurantTel, String fcm); //회원가입 요청
    }
}
