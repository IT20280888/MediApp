package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditPaymentActivity extends AppCompatActivity {

    private TextInputEditText cardNumberEdt, amountEdt, cvvEdt, paymentImgEdt, paymentLinkEdt, paymentDescEdt;
    private Button updatePaymentBtn, deletePaymentBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentID;
    private PaymentRVModel paymentRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);
        firebaseDatabase = FirebaseDatabase.getInstance();
        cardNumberEdt = findViewById(R.id.idEdtCourseName);
        amountEdt = findViewById(R.id.idEdtCoursePrice);
        cvvEdt = findViewById(R.id.idEdtCourseSuitedFor);
        paymentImgEdt = findViewById(R.id.idEdtCourseImageLink);
        paymentLinkEdt = findViewById(R.id.idEdtCourseLink);
        paymentDescEdt = findViewById(R.id.idEdtCourseDesc);
        updatePaymentBtn = findViewById(R.id.idBtnAddCourse);
        deletePaymentBtn = findViewById(R.id.idBtnDeleteCourse);
        loadingPB = findViewById(R.id.idPBLoading);
        paymentRVModel = getIntent().getParcelableExtra("payment");
        if(paymentRVModel!=null){
            cardNumberEdt.setText(paymentRVModel.getCardNumber());
            amountEdt.setText(paymentRVModel.getPaymentAmount());
            cvvEdt.setText(paymentRVModel.getPaymentCvv());
            paymentImgEdt.setText(paymentRVModel.getPaymentImg());
            paymentLinkEdt.setText(paymentRVModel.getPaymentLink());
            paymentDescEdt.setText(paymentRVModel.getPaymentDescription());
            paymentID = paymentRVModel.getPaymentID();

        }

        databaseReference = firebaseDatabase.getReference("Payments").child(paymentID);
        updatePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String cardNumber = cardNumberEdt.getText().toString();
                String amount = amountEdt.getText().toString();
                String cvv = cvvEdt.getText().toString();
                String paymentImg = paymentImgEdt.getText().toString();
                String paymentLink = paymentLinkEdt.getText().toString();
                String paymentDesc = paymentDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("cardNumber", cardNumber);
                map.put("paymentAmount", amount);
                map.put("paymentCvv", cvv);
                map.put("paymentImg", paymentImg);
                map.put("paymentLink", paymentLink);
                map.put("paymentDescription", paymentDesc);
                map.put("paymentID", paymentID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPaymentActivity.this, "Payment Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPaymentActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPaymentActivity.this, "Failed to update Payment..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deletePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePayment();
            }
        });
    }

    private void deletePayment() {
        databaseReference.removeValue();
        Toast.makeText(this, "Payment Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPaymentActivity.this, MainActivity.class));
    }
}