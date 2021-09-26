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

    private TextInputEditText nameOnCardEdt, cardNumberEdt, expDateEdt, cvvEdt, amountEdt;
    private Button chooseRcptBtn;
    private Button updatePaymentBtn, deletePaymentBtn;
    private ImageView rcptImg;
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
        nameOnCardEdt = findViewById(R.id.idEdtCardName);
        cardNumberEdt = findViewById(R.id.idEdtCardNumber);
        expDateEdt = findViewById(R.id.idEdtExpDate);
        cvvEdt = findViewById(R.id.idEdtCVV);
        amountEdt = findViewById(R.id.idEdtAmount);
        chooseRcptBtn = findViewById(R.id.button_choose_image);
        rcptImg = findViewById(R.id.image_view);
        updatePaymentBtn = findViewById(R.id.idBtnEditPayment);
        deletePaymentBtn = findViewById(R.id.idBtnDeletePayment);
        loadingPB = findViewById(R.id.idPBLoading);
        paymentRVModel = getIntent().getParcelableExtra("payment");
        if(paymentRVModel!=null){
            nameOnCardEdt.setText(paymentRVModel.getNameOnCard());
            cardNumberEdt.setText(paymentRVModel.getCardNumber());
            expDateEdt.setText(paymentRVModel.getExpDate());
            cvvEdt.setText(paymentRVModel.getCvv());
            amountEdt.setText(paymentRVModel.getAmount());
            //rcptImg.setText(paymentRVModel).get
            paymentID = paymentRVModel.getPaymentID();

        }

        databaseReference = firebaseDatabase.getReference("Payments").child(paymentID);
        updatePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String nameOnCard = nameOnCardEdt.getText().toString();
                String cardNumber = cardNumberEdt.getText().toString();
                String expDate = expDateEdt.getText().toString();
                String cvv = cvvEdt.getText().toString();
                String amount = amountEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("nameOnCard", nameOnCard);
                map.put("cardNumber", cardNumber);
                map.put("expDate", expDate);
                map.put("cvv", cvv);
                map.put("amount", amount);
                //map.put("rcptImg", rcptImg);
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