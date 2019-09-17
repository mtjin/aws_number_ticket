package com.mtjin.aws_number_ticket.activity.reserve_confirm;

import com.mtjin.aws_number_ticket.model.Apply;

import java.util.List;

public interface ReserveConfirmContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void showProgress();
        void hideProgress();
        void onGetResult(List<Apply> applys);
    }

    interface Presenter{
        void requestMyReserve(String apply_id, String user_tel, String user_pw); //내가예약한 레스토랑 확인
    }
}
