package com.example.pharmaease20;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
import com.bumptech.glide.Glide;

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

        name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        price.setText("Price: " + cursor.getString(cursor.getColumnIndexOrThrow("price")));

        String imagePath = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        Glide.with(context).load(imagePath).into(imageView);  // Load image using Glide library
    }
}
