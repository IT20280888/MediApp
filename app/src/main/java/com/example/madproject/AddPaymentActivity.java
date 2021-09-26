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

    private TextInputEditText nameOnCardEdt, cardNumberEdt, expDateEdt, cvvEdt, amountEdt;
    private Button chooseRcptBtn;
    private Button paymentBtn;
    private ImageView rcptImg;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        nameOnCardEdt = findViewById(R.id.idEdtCardName);
        cardNumberEdt = findViewById(R.id.idEdtCardNumber);
        expDateEdt = findViewById(R.id.idEdtExpDate);
        cvvEdt = findViewById(R.id.idEdtCVV);
        amountEdt = findViewById(R.id.idEdtAmount);
        chooseRcptBtn = findViewById(R.id.button_choose_image);
        rcptImg = findViewById(R.id.image_view);
        paymentBtn = findViewById(R.id.idBtnAddPayment);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String nameOnCard = nameOnCardEdt.getText().toString();
                String cardNumber = cardNumberEdt.getText().toString();
                String expDate = expDateEdt.getText().toString();
                String cvv = cvvEdt.getText().toString();
                String amount = amountEdt.getText().toString();
                //String rcptImage = rcptImg.getText().toString;
                paymentID = nameOnCard;
                PaymentRVModel paymentRVModel = new PaymentRVModel(nameOnCard, cardNumber, expDate, cvv, amount, paymentID);

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