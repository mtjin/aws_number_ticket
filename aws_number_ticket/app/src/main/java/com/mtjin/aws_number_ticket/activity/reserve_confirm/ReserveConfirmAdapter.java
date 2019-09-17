package com.mtjin.aws_number_ticket.activity.reserve_confirm;

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

public class ReserveConfirmAdapter extends RecyclerView.Adapter<ReserveConfirmAdapter.ReserveConfirmViewHolder> {
    Context context;
    ArrayList<Apply> items;
    public ReserveConfirmAdapter(ArrayList<Apply> items, Context context){
        this.context =  context;
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
    public ReserveConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_my_reserve, viewGroup, false);
        return new ReserveConfirmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserveConfirmViewHolder holder, int i) {
        final Apply model = items.get(i);
        holder.restaurantText.setText(model.getRestaurant_name());
        holder.applyDateText.setText(model.getApply_date());
        holder.reserveDateText.setText(model.getReserve_date());
        holder.telText.setText(model.getUser_tel());
        if(model.getAccept().equals("yes")){
            holder.linearLayout.setBackgroundResource(R.color.colorGreen);
        }
    }

    public class ReserveConfirmViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantText;
        TextView applyDateText;
        TextView reserveDateText;
        TextView telText;
        LinearLayout linearLayout;
        public ReserveConfirmViewHolder(@NonNull final View itemView) {
            super(itemView);

            restaurantText = itemView.findViewById(R.id.item_myreserve_tv_restaurant);
            applyDateText = itemView.findViewById(R.id.item_myreserve_tv_applydate);
            reserveDateText = itemView.findViewById(R.id.item_myreserve_tv_reservedate);
            telText= itemView.findViewById(R.id.item_myreserve_tv_tel);
            linearLayout = itemView.findViewById(R.id.myreserve_linear);

        }
    }
}
