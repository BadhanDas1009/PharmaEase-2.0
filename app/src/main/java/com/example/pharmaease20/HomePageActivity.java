package com.example.pharmaease20;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

public class HomePageActivity extends AppCompatActivity {

    MedicineDatabaseHelper dbHelper;
    ListView listViewMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Database Helper
       // dbHelper = new MedicineDatabaseHelper(this);
     //   listViewMedicines = findViewById(R.id.listViewMedicines);

        // Load data into ListView
        //loadMedicines();

        // Logout Button
         ImageButton logoutButton = findViewById(R.id.btn_log_out_homepage);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Ends the current activity
            }
        });

    }


}
