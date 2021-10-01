package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AddPaymentActivity extends AppCompatActivity {

    private TextInputEditText cardNumberEdt, amountEdt, cvvEdt, paymentImgEdt, paymentLinkEdt, paymentDescEdt;
    private Button addCourseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        cardNumberEdt = findViewById(R.id.idEdtCourseName);
        amountEdt = findViewById(R.id.idEdtCoursePrice);
        cvvEdt = findViewById(R.id.idEdtCourseSuitedFor);
        paymentImgEdt = findViewById(R.id.idEdtCourseImageLink);
        paymentLinkEdt = findViewById(R.id.idEdtCourseLink);
        paymentDescEdt = findViewById(R.id.idEdtCourseDesc);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String cardNumber = cardNumberEdt.getText().toString();
                String amount = amountEdt.getText().toString();
                String cvv = cvvEdt.getText().toString();
                String paymentImg = paymentImgEdt.getText().toString();
                String paymentLink = paymentLinkEdt.getText().toString();
                String paymentDesc = paymentDescEdt.getText().toString();
                paymentID = cardNumber;
                PaymentRVModel paymentRVModel = new PaymentRVModel(cardNumber, amount, cvv, paymentImg, paymentLink, paymentDesc, paymentID);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot snapshot){
                            loadingPB.setVisibility(View.GONE);
                            databaseReference.child(paymentID).setValue(paymentRVModel);
                            Toast.makeText(AddPaymentActivity.this, "Payment Successfull..", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error){
                            Toast.makeText(AddPaymentActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            });
        }


}