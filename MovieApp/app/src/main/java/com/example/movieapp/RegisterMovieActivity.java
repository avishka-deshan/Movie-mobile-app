package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMovieActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText title, year, director, actors, ratings, reviews;
    Boolean checkSaveData = false;
    String movieTitle, movieYear, movieDirector, movieActors, movieRatings, movieReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);
        title = findViewById(R.id.movie_title_et);
        year = findViewById(R.id.movie_year_et);
        director = findViewById(R.id.movie_director_et);
        actors = findViewById(R.id.actor_list_et);
        ratings = findViewById(R.id.rating_et);
        reviews = findViewById(R.id.review_et);
        dbHelper = new DBHelper(this);
    }

    public void saveData(View view) {

        /*
        Making the user inputs mandatory in all fields
         */
        try {
            if (TextUtils.isEmpty(title.getText())){
                title.setError("Movie Title is required");
            } else {
                movieTitle = title.getText().toString();
            }

            if (TextUtils.isEmpty(year.getText())){
                year.setError("Movie Year is required");
            } else{
                movieYear = year.getText().toString();
            }

            if (TextUtils.isEmpty(director.getText())){
                director.setError("Movie Director is required");
            }else {
                movieDirector = director.getText().toString();
            }

            if (TextUtils.isEmpty(actors.getText())){
                actors.setError("Movie Actors are required");
            }else{
                movieActors = actors.getText().toString();
            }

            if (TextUtils.isEmpty(ratings.getText())){
                ratings.setError("Movie rating is required");
            }else {
                movieRatings = ratings.getText().toString();
            }

            if (TextUtils.isEmpty(reviews.getText())){
                reviews.setError("Movie review is required");
            } else{
                movieReviews = reviews.getText().toString();
            }
            int date = Integer.parseInt(movieYear);
            int date2 = 1895;
            System.out.println(date);
            System.out.println(date2);

            if (date > date2){
                int rate = Integer.parseInt(movieRatings);
                if (rate<=10 && rate > 0) {
                    checkSaveData = dbHelper.saveMovieData(movieTitle, movieYear, movieDirector, movieActors, movieRatings, movieReviews);
                    if (checkSaveData){
                        Toast.makeText(RegisterMovieActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterMovieActivity.this, "Rating must be between 1-10", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterMovieActivity.this, "Movie Year must be greater than 1895", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}