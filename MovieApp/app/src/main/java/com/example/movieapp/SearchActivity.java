package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText searchET;
    String searchTxt;
    DBHelper DB;
    ListView listView;
    ArrayList<String> store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchET = findViewById(R.id.search_editText);
        DB = new DBHelper(this);
        listView = findViewById(R.id.search_listView);
        store = new ArrayList<>();
    }

    public void search(View view) {
        store.clear();
        searchTxt = ("%" +searchET.getText().toString()+ "%");
        /*
        If no user inout
         */
        if (searchET.getText().toString().equals("")){
            Toast.makeText(SearchActivity.this, "No User Input", Toast.LENGTH_SHORT).show();
            /*
            Search if user input is available
             */
        }else{
            Cursor result = DB.searchData(searchTxt);
            while (result.moveToNext()) {
                store.add(result.getString(0));
            }

            if (store.isEmpty()){
                Toast.makeText(SearchActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
            }

            /*
            Creating and styling the listView
             */
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, store){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.WHITE);

                    // Generate ListView Item using TextView
                    return view;
                }
            };
            listView.setAdapter(nameAdapter);
        }
    }
}