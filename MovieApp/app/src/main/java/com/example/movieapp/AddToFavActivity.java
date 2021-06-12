package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class AddToFavActivity extends AppCompatActivity {

    DBHelper DB;
    TextView movieName;
    CheckBox checkBox;
    LinearLayout verticalLayout;
    LinearLayout horizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_fav);

        verticalLayout = findViewById(R.id.add_to_fav_linear);

        ArrayList<String> movieList = new ArrayList<>();

        /*
        Getting data from the database
         */
        DB = new DBHelper(this);
        Cursor result = DB.getData();

        while (result.moveToNext()) {
            movieList.add(result.getString(0));
        }

        Collections.sort(movieList, String::compareToIgnoreCase);

        for (int i = 0; i < movieList.size(); i++) {
            movieName = new TextView(this);
            movieName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkBox = new CheckBox(this);
            movieName.setGravity(Gravity.CENTER);
            movieName.setTextColor(Color.WHITE);
            checkBox.setBackgroundColor(Color.WHITE);
            checkBox.setTag(movieList.get(i));
            checkBox.setTextSize(20);
            movieName.setTextSize(20);
            movieName.setText(movieList.get(i));
            horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.addView(checkBox);
            horizontalLayout.addView(movieName);
            /*
            Setting Margins for checkboxes and text views
             */
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)checkBox.getLayoutParams();
            params.setMargins(50, 0, 0, 50); //substitute parameters for left, top, right, bottom
            checkBox.setLayoutParams(params);
            params = (LinearLayout.LayoutParams)movieName.getLayoutParams();
            params.setMargins(50,0,0,50);
            movieName.setLayoutParams(params);
            verticalLayout.addView(horizontalLayout);

        }
    }
    public void addFavourites(View view) {

        /*
        Accessing all checkboxes and text views in the vertical linear layout
         */
        for (int i = 0; i < verticalLayout.getChildCount(); i++) {
            if (verticalLayout.getChildAt(i) instanceof LinearLayout) {
                horizontalLayout =(LinearLayout) verticalLayout.getChildAt(i);
                checkBox = (CheckBox) horizontalLayout.getChildAt(0);
                movieName =(TextView) horizontalLayout.getChildAt(1);
                if (checkBox.isChecked()) {
                    Toast.makeText(AddToFavActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        DB.saveFavourites("Yes", checkBox.getTag().toString());
                }
            }

        }
    }
}