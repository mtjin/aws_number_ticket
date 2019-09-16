package com.mtjin.aws_number_ticket.activity.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.activity.login.LoginActivity;

public class SignupActivity extends AppCompatActivity implements SignupContract.View{
    EditText idEdit;
    EditText pwEdit;
    EditText pwConfirmEdit;
    EditText restaurantEdit;
    EditText locationEdit;
    EditText telEdit;
    Button duplicateIdCheckEdit;
    Button okButton;
    ProgressDialog progressDialog;
    //value
    SignupPresenter presenter;
    boolean isChecked = false; //아이디 중복체크여부
    Button.OnClickListener onClickListener;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        idEdit = findViewById(R.id.signup_et_id);
        pwEdit = findViewById(R.id.signup_et_pw);
        pwConfirmEdit = findViewById(R.id.signup_et_pwconfrim);
        restaurantEdit = findViewById(R.id.signup_et_restaurant);
        locationEdit = findViewById(R.id.signup_et_location);
        telEdit = findViewById(R.id.signup_et_tel);
        duplicateIdCheckEdit = findViewById(R.id.signup_btn_check);
        okButton = findViewById(R.id.signup_btn_ok);
        setOnClickListener();
        duplicateIdCheckEdit.setOnClickListener(onClickListener);
        okButton.setOnClickListener(onClickListener);

        //다이얼로그
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려주세요 :)");

        //프레젠터
        presenter = new SignupPresenter(this);
    }

    private void setOnClickListener(){
        onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.signup_btn_check:
                         userId = idEdit.getText().toString().trim();
                        Log.d("FFFF", userId);
                        if(userId.length() <= 0){
                           idEdit.setError("아이디를 입력해주세요");
                        }else{
                            presenter.requestCheckId(userId);
                        }
                        break;
                    case R.id.signup_btn_ok:
                        String pwd = pwEdit.getText().toString().trim();
                        String pwd2 = pwConfirmEdit.getText().toString().trim();
                        String restaurant = restaurantEdit.getText().toString().trim();
                        String location = locationEdit.getText().toString().trim();
                        String tel = telEdit.getText().toString().trim();
                        if(!isChecked){
                            onToastMessage("아이디 중복체크를 먼저 해주세요.");
                        }else if(pwd.length() <= 0){
                            onToastMessage("비밀번호를 작성해주세요.");
                        }else if(!pwd.equals(pwd2)){
                            onToastMessage("비밀번호가 서로 다릅니다.");
                        }else if(restaurant.length() <= 0){
                            onToastMessage("음식점 이름 작성해주세요.");
                        }else if(locationEdit.length() <= 0 ){
                            onToastMessage("가게 장소를 작성해주세요");
                        } else if(telEdit.length() <= 0){
                            onToastMessage("전화번호를 입력해주세요");
                        }
                        else{
                            presenter.requestSignup(userId, pwd, restaurant ,location, tel);
                            finish();
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void onToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void blockUserId() {
        idEdit.setFocusable(false);
        idEdit.setEnabled(false);
        duplicateIdCheckEdit.setEnabled(false);
        isChecked = true;
    }
}
