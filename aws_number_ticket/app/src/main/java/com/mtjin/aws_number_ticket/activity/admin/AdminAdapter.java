package com.mtjin.aws_number_ticket.activity.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.model.Apply;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    Context context;
    ArrayList<Apply> items;
    AdminContract.Presenter presenter;

    public AdminAdapter(ArrayList<Apply> items, Context context , AdminContract.Presenter presenter){
        this.context =  context;
        this.presenter = presenter;
        addItems(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public  void addItem(Apply item){
        items.add(item);
    }


    public void addItems(ArrayList<Apply> items){
        this.items = items;
    }


    public void clear(){
        items.clear();
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_apply, viewGroup, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int i) {
        final Apply model = items.get(i);
        holder.userText.setText(model.getApply_id());
        holder.applyDateText.setText(model.getApply_date());
        holder.reserveDateText.setText(model.getReserve_date());
        holder.telText.setText(model.getUser_tel());
        if(model.getAccept().equals("yes")){
            holder.okButton.setText("취소");
            holder.linearLayout.setBackgroundResource(R.color.colorGreen);
        }else{
            holder.okButton.setText("수락");
            holder.linearLayout.setBackgroundResource(R.color.colorWhite);
        }

        holder.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.okButton.getText().toString().trim().equals("수락")) {
                    presenter.requestAccept(model.getId());
                    holder.linearLayout.setBackgroundResource(R.color.colorYellow);
                    holder.okButton.setText("취소");
                }else{
                    presenter.requestCancel(model.getId());
                    holder.linearLayout.setBackgroundResource(R.color.colorYellow);
                    holder.okButton.setText("수락");
                }
            }
        });
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView userText;
        TextView applyDateText;
        TextView reserveDateText;
        TextView telText;
        Button okButton;

        LinearLayout linearLayout;
        public AdminViewHolder(@NonNull final View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.item_apply_tv_username);
            applyDateText = itemView.findViewById(R.id.item_apply_tv_applydate);
            reserveDateText = itemView.findViewById(R.id.item_apply_tv_reservedate);
            telText = itemView.findViewById(R.id.item_apply_tv_tel);
            okButton =itemView.findViewById(R.id.item_apply_btn_ok);
            linearLayout = itemView.findViewById(R.id.apply_linear);
        }
    }
}
