package com.mtjin.aws_number_ticket.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.activity.reserve.ReserveActivity;
import com.mtjin.aws_number_ticket.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    RecyclerView recyclerView;
    EditText restaurantEdit;
    Button searchButton;
    ProgressDialog progressDialog;

    MainPresenter presenter;
    MainAdapter adapter;
    ArrayList<User> userList = new ArrayList<>();
    MainAdapter.ItemClickListener itemClickListener;

    private static final String ID_EXTRA = "ID_EXTRA";
    private static final String NAME_EXTRA = "NAME_EXTRA";
    private static final String RESTAURANT_EXTRA = "RESTAURANT_EXTRA";
    private static final String LOCATION_EXTRA = "LOCATION_EXTRA";
    private static final String TEL_EXTRA = "TEL_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycler);
        restaurantEdit = findViewById(R.id.main_et_restaurant);
        searchButton = findViewById(R.id.main_btn_searcht);

        //다이얼로그
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("검색중입니다 :)");
        //프레젠터
        presenter = new MainPresenter(this);

        //클릭리스너
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchWord = restaurantEdit.getText().toString().trim();
                presenter.requestSearch(searchWord);
            }
        });

        itemClickListener = new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, ReserveActivity.class);
                User user = userList.get(position);
                intent.putExtra(ID_EXTRA, user.getId());
                intent.putExtra(NAME_EXTRA, user.getUserId());
                intent.putExtra(RESTAURANT_EXTRA, user.getUserRestaurant());
                intent.putExtra(LOCATION_EXTRA, user.getRestaurantLocation());
                intent.putExtra(TEL_EXTRA, user.getRestaurantTel());
                Log.d("AAA", user.getUserId());
                Log.d("AAA", user.getId()+"");
                startActivity(intent);
            }
        };

        initAdapter();
    }

    private void initAdapter(){
        //어댑터
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter(getApplicationContext(),userList, itemClickListener);
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
    public void onGetResult(List<User> users) {
        adapter.clear();
        userList.clear();
        userList.addAll(users);
        adapter.notifyDataSetChanged();
    }
}
