package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RatingsActivity extends AppCompatActivity {

    DBHelper DB;
    ListView listView;
    CheckedTextView checkedTextView;
    String url_string;
    public static String MY_API_KEY = "k_d6bss596";
    public static ArrayList<String> movies = new ArrayList<>();
    public static HashMap<String,String> imageHashMap = new HashMap<>();
    String checkedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_activity);
        listView = findViewById(R.id.ratings_listView);
        DB = new DBHelper(this);
        Cursor result = DB.getData();
        ArrayList<String> movieList = new ArrayList<>();
        checkedTextView = new CheckedTextView(this);

        while (result.moveToNext()) {
            movieList.add(result.getString(0));
        }
        /*if (!movieList.isEmpty()){*/
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,movieList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.BLACK);

                    // Generate ListView Item using TextView
                    return view;
                }
            };
            listView.setAdapter(nameAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // change the checkbox state
                    if (checkedTextView.isChecked()) {
                        checkedTextView.setChecked(false);
                    }
                    checkedTextView = ((CheckedTextView)view);
                    checkedTextView.setChecked(!checkedTextView.isChecked());
                    checkedTextView.setTag("Yes");
                    if (checkedTextView.isChecked()){
                        checkedText = checkedTextView.getText().toString();
                        Toast.makeText(getApplicationContext(),checkedText, Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }

    public void findMovie(View view) {
        checkedText= checkedTextView.getText().toString();
        System.out.println(checkedText);
        url_string = "https://imdb-api.com/en/API/SearchTitle/"+ MY_API_KEY+"/"+checkedText;
        System.out.println(url_string);

        movies.clear();
        DownloadTask task = new DownloadTask();
        task.execute(url_string);

        System.out.println(movies);
    }


    class DownloadTask extends AsyncTask<String,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            StringBuilder stb = new StringBuilder("");
            ArrayList<String> temp = new ArrayList<>();
            HashMap<String,String> tempHashMap = new HashMap<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line ;
                while ((line = bf.readLine()) != null) {
                    stb.append(line);
                }
            }
            catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }

            /*
            Getting the data from the url
             */
            try {
                JSONObject json = new JSONObject(stb.toString());
                JSONArray jarray = json.getJSONArray("results");
                System.out.println(jarray);
                for (int i = 0;i< jarray.length();i++) {
                    String movieId;
                    String imageUrl;
                    JSONObject movie_json = jarray.getJSONObject(i);
                    movieId = movie_json.getString("id");
                    imageUrl = movie_json.getString("image");
                    if (!movies.contains(movieId)){
                        temp.add(movieId);
                        tempHashMap.put(movieId,imageUrl);
                        getRatingDetails(tempHashMap);
                    }
                }

            }catch (Exception ex) {
                ex.printStackTrace();
            }
            /*getMovieDetails(temp);*/
            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            getMovieDetails(s);
            System.out.println("r "+s);
        }
    }

    public void getMovieDetails(ArrayList<String> moviesTemp){
        System.out.println("ada"+moviesTemp);
        movies.addAll(moviesTemp);
        System.out.println("ABB"+movies);

        if (!movies.isEmpty()){
            Intent intent = new Intent(this, IMDBResultsActivity.class);
            startActivity(intent);
        }
    }
    public void getRatingDetails(HashMap<String,String> hashMap){
        imageHashMap.putAll(hashMap);
    }
}

/*
k_o5atcdcr
k_jutyn7do
k_d6bss596
k_pw7a6goq
 */