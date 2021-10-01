package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PaymentRVAdapter.PaymentClickInterface{

    private RecyclerView paymentRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PaymentRVModel> paymentRVModelsArrayList;
    private RelativeLayout bottomSheetRL;
    //private RelativeLayout homeRL;
    private PaymentRVAdapter paymentRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paymentRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");
        paymentRVModelsArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        paymentRVAdapter = new PaymentRVAdapter(paymentRVModelsArrayList, this, this);
        paymentRV.setLayoutManager(new LinearLayoutManager(this));
        paymentRV.setAdapter(paymentRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddPaymentActivity.class));
            }
        });

        getAllPayments();

    }

        private void getAllPayments() {
        paymentRVModelsArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                paymentRVModelsArrayList.add(snapshot.getValue(PaymentRVModel.class));
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPaymentClick(int position) {
        displayBottomSheet(paymentRVModelsArrayList.get(position));
    }

    private void displayBottomSheet(PaymentRVModel paymentRVModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView cardNumberTV = layout.findViewById(R.id.idTVCourseName);
        TextView descriptionTV = layout.findViewById(R.id.idTVDescription);
        TextView cvvTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView amountTV = layout.findViewById(R.id.idTVPrice);
        ImageView paymentIV = layout.findViewById(R.id.idIVCourse);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);

        cardNumberTV.setText(paymentRVModel.getCardNumber());
        descriptionTV.setText(paymentRVModel.getPaymentDescription());
        amountTV.setText("Rs. "+ paymentRVModel.getPaymentAmount());
        cvvTV.setText(paymentRVModel.getPaymentCvv());
        Picasso.get().load(paymentRVModel.getPaymentImg()).into(paymentIV);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditPaymentActivity.class);
                i.putExtra("payment", paymentRVModel);
                startActivity(i);
            }
        });

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View v) {
               Intent i = new Intent(Intent.ACTION_VIEW);
               i.setData(Uri.parse(paymentRVModel.getPaymentLink()));
               startActivity(i);
          }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                Toast.makeText(this, "User Logged Out..", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}