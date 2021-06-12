package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayMovieActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DBHelper DB;
    ListView listView;
    public static String clickedMovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
        listView = findViewById(R.id.listView);
        DB = new DBHelper(this);
        Cursor result = DB.getData();
        ArrayList<String> movieList = new ArrayList<>();

        while (result.moveToNext()) {
            movieList.add(result.getString(0));
        }

        if (!movieList.isEmpty()){
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,movieList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(20);

                    // Generate ListView Item using TextView
                    return view;
                }
            };

            listView.setAdapter(nameAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickedMovieName = parent.getItemAtPosition(position).toString();
        Intent intent = new Intent(this, EditMovieActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        Toast.makeText(getApplicationContext(),"Clicked: "+clickedMovieName, Toast.LENGTH_SHORT).show();
    }
}
