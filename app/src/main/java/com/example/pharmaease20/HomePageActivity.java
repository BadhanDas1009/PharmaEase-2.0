package com.example.pharmaease20;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    private ListView listViewMedicines;
    private MedicineDatabaseHelper dbHelper;
    private ImageButton btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listViewMedicines = findViewById(R.id.listViewMedicines);
        btnLogOut = findViewById(R.id.btn_log_out_homepage);

        dbHelper = new MedicineDatabaseHelper(this);

        loadMedicines();

        btnLogOut.setOnClickListener(v -> {
            Toast.makeText(HomePageActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        });

        listViewMedicines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomePageActivity.this, "Medicine selected!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMedicines() {
        Cursor cursor = dbHelper.getAllMedicines();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No medicines available", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] from = new String[]{"name", "price", "type"};
        int[] to = new int[]{R.id.tvMedicineName, R.id.tvMedicinePrice, R.id.tvMedicineType};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.medicine_list_item,
                cursor,
                from,
                to,
                0
        );

        listViewMedicines.setAdapter(adapter);
    }
}
