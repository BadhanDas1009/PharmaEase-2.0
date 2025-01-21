package com.example.pharmaease20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 100;

    private EditText etMedicineName, etPrice, etDescription, etQuantity;
    private RadioGroup rgMedicineType;
    private Button btnSubmit, btnUploadImage;
    private ImageView ivMedicineImage;
    private Uri imageUri;
    private MedicineDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // Hide the default Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etMedicineName = findViewById(R.id.etMedicineName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        etQuantity = findViewById(R.id.etQuantity);
        rgMedicineType = findViewById(R.id.rgMedicineType);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivMedicineImage = findViewById(R.id.ivMedicineImage);

        dbHelper = new MedicineDatabaseHelper(this);

        // Check for permission for Android versions below Android 10 (API level 29)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }
        }

        // Open image picker when the "Upload Medicine Image" button is clicked
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Handle submit button click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etMedicineName.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String type = ((RadioButton) findViewById(rgMedicineType.getCheckedRadioButtonId())).getText().toString();

                // Handle empty or invalid quantity input safely
                int quantity;
                try {
                    quantity = Integer.parseInt(etQuantity.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(InsertActivity.this, "Invalid quantity!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Store image path if available, else empty string
                String imagePath = (imageUri != null) ? imageUri.toString() : "";

                // Log values for debugging
                Log.d("DB_INSERT", "Inserting: " + name + ", " + price + ", " + description + ", " + type + ", " + quantity + ", " + imagePath);

                // Insert into database
                long result = dbHelper.insertMedicine(name, price, description, type, quantity, imagePath);

                if (result == -1) {
                    Toast.makeText(InsertActivity.this, "Insertion failed!", Toast.LENGTH_SHORT).show();
                    Log.e("DB_INSERT", "Error inserting data.");
                } else {
                    Toast.makeText(InsertActivity.this, "Medicine Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Open image picker to select an image
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivMedicineImage.setImageBitmap(bitmap);  // Display the image
                    ivMedicineImage.setVisibility(View.VISIBLE);  // Make the image view visible
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Handle permission result for Android 6.0 and above
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, continue with image picker
                openImagePicker();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission Denied! Cannot access images.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
