package com.mtjin.aws_number_ticket.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtjin.aws_number_ticket.R;
import com.mtjin.aws_number_ticket.model.User;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private Context context;
    private ArrayList<User> items;
    private ItemClickListener itemClickListener;


    public MainAdapter(Context context, ArrayList<User> items, ItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(User item) {
        items.add(item);
    }


    public void addItems(ArrayList<User> items) {
        this.items = items;
    }


    public void clear() {
        items.clear();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_restaurant, viewGroup, false);
        return new MainViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int i) {
        final User model = items.get(i);
        holder.userNameText.setText(model.getUserId());
        holder.restaurantText.setText(model.getUserRestaurant());
        holder.locationText.setText(model.getRestaurantLocation());
        holder.telText.setText(model.getRestaurantTel());
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements ItemClickListener, View.OnClickListener{
        TextView userNameText;
        TextView restaurantText;
        TextView locationText;
        TextView telText;
        Button reserveButton;

        ItemClickListener itemClickListener;

        public MainViewHolder(@NonNull final View itemView,  ItemClickListener itemClickListener) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.item_restaurant_tv_username);
            restaurantText = itemView.findViewById(R.id.item_restaurant_tv_restaurant);
            locationText = itemView.findViewById(R.id.item_restaurant_tv_location);
            telText = itemView.findViewById(R.id.item_restaurant_tv_tel);
            reserveButton = itemView.findViewById(R.id.item_restaurant_btn_reserve);
            this.itemClickListener = itemClickListener;
            reserveButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void onItemClick(View view, int position) {
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

