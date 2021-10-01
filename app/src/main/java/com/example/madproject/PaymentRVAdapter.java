package com.example.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PaymentRVAdapter extends RecyclerView.Adapter<PaymentRVAdapter.ViewHolder> {
        private ArrayList<PaymentRVModel> paymentRVModelArrayList;
        private Context context;
        int lastPos = -1;
        private PaymentClickInterface paymentClickInterface;

    public PaymentRVAdapter(ArrayList<PaymentRVModel> paymentRVModelArrayList, Context context, PaymentClickInterface paymentClickInterface) {
        this.paymentRVModelArrayList = paymentRVModelArrayList;
        this.context = context;
        this.paymentClickInterface = paymentClickInterface;
    }

    @NonNull
        @Override
        public PaymentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.payment_rv_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PaymentRVModel paymentRVModel = paymentRVModelArrayList.get(position);
            holder.cardNumberTV.setText(paymentRVModel.getCardNumber());
            holder.paymentAmountTV.setText("Rs. " + paymentRVModel.getPaymentAmount());
            Picasso.get().load(paymentRVModel.getPaymentImg()).into(holder.paymentIV);
            setAnimation(holder.itemView, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentClickInterface.onPaymentClick(position);
                }
            });

        }
        private void setAnimation(View itemView, int position) {
            if(position > lastPos) {
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                itemView.setAnimation(animation);
                lastPos = position;
            }
        }
        @Override
        public int getItemCount() {
            return paymentRVModelArrayList.size();
        }

        public interface PaymentClickInterface{
            void onPaymentClick(int position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cardNumberTV, paymentAmountTV;
        private ImageView paymentIV;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cardNumberTV = itemView.findViewById(R.id.idTVCardNumber);
                paymentAmountTV = itemView.findViewById(R.id.idTVPrice);
                paymentIV = itemView.findViewById(R.id.idIVCourse);
            }
        }

    }

