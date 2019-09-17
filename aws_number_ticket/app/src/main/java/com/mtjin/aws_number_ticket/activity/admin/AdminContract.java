package com.mtjin.aws_number_ticket.activity.admin;

import com.mtjin.aws_number_ticket.model.Apply;
import com.mtjin.aws_number_ticket.model.User;

import java.util.List;

public interface AdminContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void onGetResult(List<Apply> applys);
    }
    interface Presenter{
        void requestAdmin(int restaurant_id); //내 가게 예약한 목록 보기
        void requestAccept(int id); //예약수락하기
        void requestCancel(int id); //내 가게 예약한 목록 보기
        void requestAdminCertain(int restaurant_id, String reserve_date); //특정 날짜 내가게 예약한 목록 보기
        void resquestAcceptAlarm(int id , boolean isAccept); //예약수락 알림
    }

}
