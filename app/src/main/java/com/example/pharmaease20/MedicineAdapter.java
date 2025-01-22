package com.example.pharmaease20;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;

public class MedicineAdapter extends SimpleCursorAdapter {

    private LayoutInflater inflater;
    private int layout;
    private Context context;

    public MedicineAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.inflater = LayoutInflater.from(context);
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.medicineName);
        TextView price = view.findViewById(R.id.medicinePrice);
        ImageView imageView = view.findViewById(R.id.medicineImage);

        name.setText(cursor.getString(cursor.getColumnIndexOrThrow(MedicineDatabaseHelper.COLUMN_NAME)));
        price.setText("Price: " + cursor.getString(cursor.getColumnIndexOrThrow(MedicineDatabaseHelper.COLUMN_PRICE)));

        // Retrieve BLOB image from cursor
        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(MedicineDatabaseHelper.COLUMN_IMAGE));

        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ic_placeholder);  // Default placeholder if no image
        }
    }
}
