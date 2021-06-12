package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IMDBResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String MY_API_KEY = RatingsActivity.MY_API_KEY;
    String movieRating;
    String movieR;
    String movieName, movieId,imageUrl;
    RelativeLayout relativeLayout;
    ListView listView;
    ArrayList<String> movieList = new ArrayList<>();
    ImageView imageView;
    static HashMap<String,String> imageSet = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_m_d_b_results);
        listView = findViewById(R.id.imdb_listView);
        imageView = findViewById(R.id.imdb_imageView);

        for (int i= 0; i < RatingsActivity.movies.size(); i++){
            String movieId = RatingsActivity.movies.get(i);
            String url_string = "https://imdb-api.com/en/API/UserRatings/"+ MY_API_KEY+"/"+movieId;
            System.out.println(url_string);
            DownloadTask task = new DownloadTask();
            task.execute(url_string);
        }

    }


    class DownloadTask extends AsyncTask<String,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            ArrayList<String> temp = new ArrayList<>();
            HashMap<String,String> tempHashMap = new HashMap<>();
            tempHashMap.clear();
            StringBuilder stb = new StringBuilder("");
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

            movieId= null;

            try {
                JSONObject json = new JSONObject(stb.toString());
                /*JSONObject json_rating = json.getJSONObject("totalRating");*/
                movieName = json.getString("fullTitle");
                movieRating = json.getString("totalRating");
                movieId =json.getString("imDbId");
                temp.add(movieName+"- Movie Rating "+ movieRating);
                tempHashMap.put(movieName,RatingsActivity.imageHashMap.get(movieId));
                getImage(tempHashMap);

            }catch (Exception ex) {
                ex.printStackTrace();
                if (movieRating == null){

                }

            }

            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            getData(s);
        }
    }

    class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap logo = null;

            try{
                InputStream inputStream = new URL(urls[0]).openStream();
                logo = BitmapFactory.decodeStream(inputStream);
            }catch(Exception e){

                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap s) {
            /*super.onPostExecute(s);*/
            imageView = findViewById(R.id.imdb_imageView);
            imageView.setImageBitmap(s);
        }
    }

    public void getData(ArrayList<String> temp2){
        movieList.addAll(temp2);
        System.out.println(movieList);
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,movieList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(18);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        listView.setAdapter(nameAdapter);
        listView.setOnItemClickListener(this);
    }

    public void getImage(HashMap<String,String> map){
         imageSet.putAll(map);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /*String userChoice  = adapterView.getItemAtPosition(i).toString();
        TextView textView = ((TextView)view);*/
        String textViewText = adapterView.getItemAtPosition(i).toString();
        System.out.println("t "+textViewText);
        int index = textViewText.indexOf("- ");
        String substring1 = null;
        if (index != -1){
            substring1 = textViewText.substring(0,index);
        }
        System.out.println("s1 "+substring1);
        System.out.println(imageSet);
        String imageUrl2 = imageSet.get(substring1);

        System.out.println(imageUrl2);
        DownLoadImageTask downLoadImageTask = new DownLoadImageTask();
        downLoadImageTask.execute(imageUrl2);
    }
}