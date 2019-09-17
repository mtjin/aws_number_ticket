package com.mtjin.aws_number_ticket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.activity.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                getFcmToken();
                finish();
            }
        }, 2000);
    }

    //fcm토큰 얻기
    private void getFcmToken() {

        //파이어베이스
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("AAAA", token);
                        if(isSameFcmToken(token)){

                        }else{
                            Log.d("AAAA", token);
                            saveSharedFcmToken(token);
                        }
                    }
                });
    }

    //쉐어드에 FCM토큰값저장
    private void saveSharedFcmToken(String token){
        SharedPreferences pref = getSharedPreferences("fcmToken", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.commit();
    }
    //새로발급받은 토큰과 원래토큰이 같은지 비교
    private boolean isSameFcmToken(String newToken){
        SharedPreferences pref = getSharedPreferences("fcmToken", MODE_PRIVATE);
        String originalToken = pref.getString("token","");
        if(originalToken.equals(newToken)){
            return true;
        }else{
            return  false;
        }
    }
}
