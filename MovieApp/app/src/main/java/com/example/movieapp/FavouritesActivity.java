package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    DBHelper DB;
    String movieNameDB;
    String favourites;
    CheckBox checkBox;
    TextView movieName;
    LinearLayout horizontalLayout;
    LinearLayout verticalLayout;
    ArrayList<String> movieList;
    ArrayList<String> tempStore;
    ArrayList<CheckBox> checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        movieList = new ArrayList<>();
        checkBoxList = new ArrayList<>();

        DB = new DBHelper(this);
        Cursor result = DB.getData();

        verticalLayout = findViewById(R.id.favourites_vertical);
        tempStore = new ArrayList<>();

        while (result.moveToNext()) {
            movieNameDB = result.getString(0);
            favourites = result.getString(6);
            if (favourites.equals("Yes")) {
                movieList.add(movieNameDB);
            }
        }

        for (int i = 0; i < movieList.size(); i++) {

            movieName = new TextView(this);
            movieName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkBox = new CheckBox(this);
            movieName.setGravity(Gravity.CENTER);
            movieName.setTextColor(Color.WHITE);
            checkBox.setBackgroundColor(Color.YELLOW);
            checkBox.setHighlightColor(Color.WHITE);
            checkBox.setDrawingCacheBackgroundColor(Color.RED);
            checkBox.setTag(movieList.get(i));
            checkBox.setTextSize(20);
            checkBox.setChecked(true);
            checkBoxList.add(checkBox);
            movieName.setTextSize(20);
            movieName.setText(movieList.get(i));
            horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.addView(checkBox);
            horizontalLayout.addView(movieName);
            /*
            Setting the margins of the text views and checkboxes
             */
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)checkBox.getLayoutParams();
            params.setMargins(50, 0, 0, 50); //substitute parameters for left, top, right, bottom
            checkBox.setLayoutParams(params);
            params = (LinearLayout.LayoutParams)movieName.getLayoutParams();
            params.setMargins(50,0,0,50);
            movieName.setLayoutParams(params);
            verticalLayout.addView(horizontalLayout);

        }

        for (int r = 0; r <movieList.size(); r++) {
            int finalI1 = r;
            /*
            Getting the unchecked movies to an arrayList
             */
            checkBoxList.get(r).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CompoundButton) view).isChecked()){
                        tempStore.remove(movieList.get(finalI1));
                    } else {
                        tempStore.add(movieList.get(finalI1));
                    }
                }
            });
        }
    }
    public void removeFavourites(View view) {
        /*
        Removing the unchecked movies from favourites in the database
         */
        for (int i = 0; i < tempStore.size(); i++) {
                DB.saveFavourites("No", tempStore.get(i));
                Toast.makeText(FavouritesActivity.this, "Removed Favourites Successfully", Toast.LENGTH_SHORT).show();
            }

        }

}