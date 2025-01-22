package com.example.pharmaease20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button logoutButton = findViewById(R.id.btn_log_out_admin_page);
        Button InsertButton = findViewById(R.id.btn_insert);
        Button ViewMedicineListButton = findViewById(R.id.btn_view_medicine_list);

     //click listener for ViewDruglistButton
        ViewMedicineListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this,ViewMedicineList.class);
                startActivity(intent);

            }
        });


        // Set Click Listener for Logout Button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Login Page
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);

                // Clear Back Stack
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

                // Finish current activity
                finish();
            }
        });


        //click listener for insert button:
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

    }
}