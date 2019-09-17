package com.mtjin.aws_number_ticket.activity.reserve;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mtjin.aws_number_ticket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ReserveActivity extends AppCompatActivity implements ReserveContract.View {
    TextView yearText;
    TextView monthText;
    TextView dayText;
    TextView hourText;
    TextView minuteText;
    TextView nameText;
    TextView restaurantText;
    TextView locationText;
    TextView telText;
    Button datepickButton;
    Button timepickButton;
    Button okButton;
    EditText nameEdit;
    EditText telEdit;
    EditText pwEdit;

    ReservePresenter presenter;

    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerdialog;

    int id;
    String restaurantName ="";

    private static final String ID_EXTRA = "ID_EXTRA";
    private static final String NAME_EXTRA = "NAME_EXTRA";
    private static final String RESTAURANT_EXTRA = "RESTAURANT_EXTRA";
    private static final String LOCATION_EXTRA = "LOCATION_EXTRA";
    private static final String TEL_EXTRA = "TEL_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        yearText = findViewById(R.id.reserve_tv_year);
        monthText = findViewById(R.id.reserve_tv_month);
        dayText = findViewById(R.id.reserve_tv_day);
        hourText = findViewById(R.id.reserve_tv_hour);
        minuteText = findViewById(R.id.reserve_tv_minute);
        nameText = findViewById(R.id.reserve_tv_name);
        restaurantText = findViewById(R.id.reserve_tv_restaurant);
        locationText = findViewById(R.id.reserve_tv_location);
        telText =findViewById(R.id.reserve_tv_tel);
        datepickButton = findViewById(R.id.reserve_btn_datepick);
        timepickButton = findViewById(R.id.reserve_btn_timepick);
        nameEdit = findViewById(R.id.reserve_et_name);
        telEdit = findViewById(R.id.reserve_et_tel);
        pwEdit = findViewById(R.id.reserve_et_pw);
        okButton = findViewById(R.id.reserve_btn_ok);
        presenter = new ReservePresenter(this);
        processIntent();


        //다이얼로그
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려주세요 :)");

        initDatePicker();
        initTimePicker();

        datepickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        timepickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerdialog.show();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString().trim();
                String tel = telEdit.getText().toString().trim();
                String pw = pwEdit.getText().toString().trim();
                String date = yearText.getText().toString().trim() +"-"+monthText.getText().toString().trim()+"-"+dayText.getText().toString().trim();
                String time = hourText.getText().toString().trim() + ":" + minuteText.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm", Locale.KOREA);
                Calendar calendar = Calendar.getInstance();
                Date date2 = calendar.getTime();
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                String currentDate = sdf.format(date2);

                if(name.length() <=0){
                    onToastMessage("예약자 이름을 입력해주세요");
                }else if(tel.length() <=0){
                    onToastMessage("전화번호를 입력해주세요");
                }else if(pw.length() <= 0){
                    onToastMessage("비밀번호를 입력해주세요");
                }
                else if(date.contains("?") || time.contains("?")){
                    onToastMessage("예약날짜와 시간을 정해주세요");
                }else{
                    presenter.requestReserve(id, restaurantName, name, currentDate, date +" "+ time, tel, pw, "no" );
                }
            }
        });


    }

    private void processIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(ID_EXTRA,0);
        nameText.setText(intent.getStringExtra(NAME_EXTRA));
        restaurantName = intent.getStringExtra(RESTAURANT_EXTRA);
        restaurantText.setText(restaurantName);
        locationText.setText(intent.getStringExtra(LOCATION_EXTRA));
        telText.setText(intent.getStringExtra(TEL_EXTRA));
    }

    private void initDatePicker() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        Calendar pickedDate = Calendar.getInstance(tz, Locale.KOREA);
        Calendar minDate = Calendar.getInstance(tz, Locale.KOREA);

        pickedDate.set(pickedDate.get(Calendar.YEAR), pickedDate.get(Calendar.MONTH), pickedDate.get(Calendar.DATE));

        datePickerDialog = new DatePickerDialog(
                ReserveActivity.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(getApplicationContext(), "선택날짜 : " + year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
                        yearText.setText(year + "");
                        monthText.setText(month + 1 + "");
                        dayText.setText(dayOfMonth + "");
                    }
                },

                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
        );

        minDate.set(minDate.get(Calendar.YEAR), minDate.get(Calendar.MONTH) , minDate.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        datePickerDialog.setMessage("예약날짜 선택");
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
                String state = "오전";
                int hour = hourOfDay;
                // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                if (hourOfDay >= 12) {
                    hour -= 12;
                    state = "오후";
                }
                //orderby 시간순서 쿼리문을 위해서 앞에 0을 붙여줌
                String putHour = String.valueOf(hour);
                String putMinute = String.valueOf(minute);
                if(putHour.length() <= 1){
                    putHour = "0"+putHour;
                }
                if(putMinute.length() <= 1){
                    putMinute = "0" + putMinute;
                }
                hourText.setText(state + " "+putHour); //오전, 오후도 같이넣어줬다.
                minuteText.setText(putMinute);
            }
        };
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(tz, Locale.KOREA);
        timePickerdialog = new TimePickerDialog(this, listener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
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
    public void onFinish() {
        finish();
    }
}
