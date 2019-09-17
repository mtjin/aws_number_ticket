package com.mtjin.aws_number_ticket.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.activity.admin.AdminActivity;
import com.mtjin.aws_number_ticket.activity.main.MainActivity;
import com.mtjin.aws_number_ticket.activity.reserve_confirm.ReserveConfirmActivity;
import com.mtjin.aws_number_ticket.activity.signup.SignupActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    EditText idEdit;
    EditText pwEdit;
    Button loginButton;
    Button signupButton;
    Button reserveButton;
    Button reserveConfirmButton;
    Button.OnClickListener onClickListener;
    ProgressDialog progressDialog;

    LoginPresenter presenter;

    private final String ID_EXTRA = "ID_EXTRA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idEdit = findViewById(R.id.login_et_id);
        pwEdit = findViewById(R.id.login_et_pw);
        loginButton = findViewById(R.id.login_btn_login);
        signupButton = findViewById(R.id.login_btn_signup);
        reserveButton = findViewById(R.id.login_btn_reserve);
        reserveConfirmButton = findViewById(R.id.login_btn_reserve_confirm);
        setOnClickListener();
        loginButton.setOnClickListener(onClickListener);
        signupButton.setOnClickListener(onClickListener);
        reserveButton.setOnClickListener(onClickListener);
        reserveConfirmButton.setOnClickListener(onClickListener);
        //다이얼로그
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려주세요 :)");

        presenter = new LoginPresenter(this);
    }

    private void setOnClickListener() {
        onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.login_btn_login:
                        String userId = idEdit.getText().toString().trim();
                        String userPw = pwEdit.getText().toString().trim();
                        if(userId.length() <= 0){
                            idEdit.setError("아이디를 입력해주세요.");
                        }else if(userPw.length() <=0){
                            pwEdit.setError("비밀번호를 입력해주세요.");
                        }else{
                            presenter.requestLogin(userId, userPw);
                        }
                        break;
                    case R.id.login_btn_signup:
                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.login_btn_reserve:
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        break;
                    case R.id.login_btn_reserve_confirm:
                        Intent confirmIntent = new Intent(LoginActivity.this, ReserveConfirmActivity.class);
                        startActivity(confirmIntent);
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
    public void successLogin(int id) {
        presenter.requestUpdateFcm(id, getFcmToken());
        Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
        adminIntent.putExtra(ID_EXTRA, id);
        startActivity(adminIntent);
    }

    private String getFcmToken(){
        SharedPreferences pref = getSharedPreferences("fcmToken", MODE_PRIVATE);
        String token = pref.getString("token","");
        return  token;
    }

}
