package com.mtjin.aws_number_ticket.activity.reserve_confirm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.activity.admin.AdminAdapter;
import com.mtjin.aws_number_ticket.model.Apply;

import java.util.ArrayList;
import java.util.List;

public class ReserveConfirmActivity extends AppCompatActivity implements ReserveConfirmContract.View{
    ProgressDialog progressDialog;
    ReserveConfirmPresenter presenter;
    EditText nameEdit;
    EditText telEdit;
    EditText pwEdit;
    Button searchButton;
    RecyclerView recyclerView;
    ReserveConfirmAdapter adapter;
    ArrayList<Apply> applyArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_confirm);
        nameEdit = findViewById(R.id.confirm_et_name);
        telEdit = findViewById(R.id.confirm_et_tel);
        pwEdit = findViewById(R.id.confirm_et_pw);
        searchButton = findViewById(R.id.confirm_btn_search);
        recyclerView = findViewById(R.id.confirm_recycler);
        //다이얼로그
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려주세요 :)");
        //어댑터초기화
        initAdapter();
        //프레젠터
        presenter = new ReserveConfirmPresenter(this);
        //검색클릭
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString().trim();
                String tel = telEdit.getText().toString().trim();
                String pw = pwEdit.getText().toString().trim();
                if(name.length() <=0){
                    onToastMessage("예약자 이름을 입력해주세요");
                }else if(tel.length() <=0){
                    onToastMessage("전화번호를 입력해주세요");
                }else if(pw.length() <= 0){
                    onToastMessage("비밀번호를 입력해주세요");
                }else{
                    presenter.requestMyReserve(name, tel, pw);
                }
            }
        });
    }

    private void initAdapter() {
        //어댑터
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReserveConfirmAdapter(applyArrayList, getApplicationContext());
        recyclerView.setAdapter(adapter);
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
    public void onGetResult(List<Apply> applys) {
        adapter.clear();
        applyArrayList.clear();
        applyArrayList.addAll(applys);
        adapter.notifyDataSetChanged();
        onToastMessage("onFetResult");
    }
}
