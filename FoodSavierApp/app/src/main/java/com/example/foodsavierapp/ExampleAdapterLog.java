package com.example.foodsavierapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExampleAdapterLog extends RecyclerView.Adapter<ExampleAdapterLog.ExampleViewHolderLog> {
    private  ArrayList<ExampleItemLog> myExampleList;
    private OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }

    public static class ExampleViewHolderLog extends RecyclerView.ViewHolder {
        public ImageView imageViewIcon;
        public TextView textViewFoodName;
        public TextView textViewStatus;
        public TextView textViewExpirattionDate;
        public TextView textViewFoodQuantity;
        public ImageView imageViewTrashIcon;

        public ExampleViewHolderLog(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.imageViewCardView);
            textViewFoodName = itemView.findViewById(R.id.foodNameCardView);
            textViewStatus = itemView.findViewById(R.id.statusCardView);
            textViewExpirattionDate = itemView.findViewById(R.id.expirationDateCardView);
            textViewFoodQuantity = itemView.findViewById(R.id.foodQuantityCardView);
            imageViewTrashIcon = itemView.findViewById(R.id.trashIconCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            imageViewTrashIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapterLog(ArrayList<ExampleItemLog> exampleList) {
        myExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolderLog onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_element_of_log, parent, false);
        ExampleViewHolderLog evh = new ExampleViewHolderLog(v, myListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolderLog holder, int position) {
        ExampleItemLog currentItem = myExampleList.get(position);

        holder.imageViewIcon.setImageResource(currentItem.getCardImage());
        holder.textViewFoodName.setText(currentItem.getCardFoodName());
        holder.textViewStatus.setText(currentItem.getCardStatus());
        holder.textViewExpirattionDate.setText(currentItem.getCardExpiredDate());
        holder.textViewFoodQuantity.setText(currentItem.getCardFoodQuantity());

    }

    @Override
    public int getItemCount() {
        return myExampleList.size();
    }
}
