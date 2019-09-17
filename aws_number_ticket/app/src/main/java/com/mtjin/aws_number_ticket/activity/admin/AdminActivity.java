package com.mtjin.aws_number_ticket.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.model.Apply;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AdminActivity extends AppCompatActivity implements AdminContract.View {
    ProgressDialog progressDialog;
    AdminPresenter presenter;
    AdminAdapter adminAdapter;
    ArrayList<Apply> applyArrayList = new ArrayList<>();

    RecyclerView recyclerView;
    TextView yearText;
    TextView monthText;
    TextView dayText;
    Button dateButton;
    Button searchButton;

    private final String ID_EXTRA = "ID_EXTRA";
    int id;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        yearText = findViewById(R.id.admin_tv_year);
        monthText = findViewById(R.id.admin_tv_month);
        dayText = findViewById(R.id.admin_tv_day);
        recyclerView = findViewById(R.id.admin_recycler);
        dateButton = findViewById(R.id.admin_btn_date);
        searchButton = findViewById(R.id.admin_btn_search);

        //다이얼로그
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려주세요 :)");
        //데이트픽커 초기화
        initDatePicker();
        //프레젠터
        presenter = new AdminPresenter(this);
        //인텐트처리
        processIntent();
        //어댑터초기화
        initAdapter();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = yearText.getText().toString().trim() +"-"+monthText.getText().toString().trim()+"-"+dayText.getText().toString().trim();
                if(date.contains("?") ){
                    onToastMessage("예약날짜를 정해주세요");
                }else{
                    presenter.requestAdminCertain(id, date);
                }
            }
        });

    }

    private void processIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(ID_EXTRA, 0);
    }

    private void initAdapter() {
        //어댑터
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
        recyclerView.setLayoutManager(layoutManager);
        adminAdapter = new AdminAdapter(applyArrayList, getApplicationContext(), presenter);
        recyclerView.setAdapter(adminAdapter);
        presenter.requestAdmin(id);
    }

    private void initDatePicker() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        Calendar pickedDate = Calendar.getInstance(tz, Locale.KOREA);

        pickedDate.set(pickedDate.get(Calendar.YEAR), pickedDate.get(Calendar.MONTH) , pickedDate.get(Calendar.DATE));

        datePickerDialog = new DatePickerDialog(
                AdminActivity.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(getApplicationContext(), "선택날짜 : " + year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
                        yearText.setText(year + "");
                        monthText.setText((month + 1) + "");
                        dayText.setText(dayOfMonth + "");
                    }
                },

                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
        );

        datePickerDialog.setMessage("날짜 선택");
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
        adminAdapter.clear();
        applyArrayList.clear();
        applyArrayList.addAll(applys);
        adminAdapter.notifyDataSetChanged();
    }

}
