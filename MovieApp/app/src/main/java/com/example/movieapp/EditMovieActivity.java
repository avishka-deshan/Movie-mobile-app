package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMovieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DBHelper DB;
    String producedYear, director, actors, ratings, review, favourites;
    ListView listView;
    EditText editText1, editText2, editText3, editText4, editText5;
    RelativeLayout relativeLayout;
    String movieTitle,movieYear,movieDirector,movieActors,movieReviews,movieRatings ,favouriteUpdate;
    int rating;
    RatingBar ratingBar;
    Boolean checkSaveData;
    Spinner spinner;
    String[] favText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        relativeLayout = findViewById(R.id.edit_movie_relative);
        DB = new DBHelper(this);
        Cursor result = DB.getData();
        ArrayList<String> movieList = new ArrayList<>();
        ratingBar = findViewById(R.id.rating_bar);



        while (result.moveToNext()) {
            if (result.getString(0).equals(DisplayMovieActivity.clickedMovieName)) {
                producedYear = result.getString(1);
                director = result.getString(2);
                actors = result.getString(3);
                rating = Integer.parseInt(result.getString(4));
                review = result.getString(5);
                favourites = result.getString(6);
            }
        }

        editText1 = findViewById(R.id.editText1);
        editText1.setText(DisplayMovieActivity.clickedMovieName);
        editText1.setEnabled(false);
        editText2 =findViewById(R.id.editText2);
        editText2.setText(producedYear);
        editText3 = findViewById(R.id.editText3);
        editText3.setText(director);
        editText4 = findViewById(R.id.editText4);
        editText4.setText(actors);
        ratingBar.setRating((float) rating);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(this, R.color.yellow), PorterDuff.Mode.SRC_ATOP);

        editText5 = findViewById(R.id.editText5);
        editText5.setText(review);

        favText = new String[]{"Favourite", "Not Favourite"};

        spinner = findViewById(R.id.spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditMovieActivity.this,
                R.layout.support_simple_spinner_dropdown_item, favText);

        adapter.setDropDownViewResource (R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if (favourites.equals("Yes")){
            spinner.setSelection(adapter.getPosition("Favourite"));
        }else if (favourites.equals("No")){
            spinner.setSelection(adapter.getPosition("Not Favourite"));
        }

            }

    public void updateData(View view) {
        try {
            if (TextUtils.isEmpty(editText1.getText())){
                editText1.setError("Movie Title is required");
            } else {
                movieTitle = editText1.getText().toString();
            }

            if (TextUtils.isEmpty(editText2.getText())){
                editText2.setError("Movie Year is required");
            } else{
                movieYear = editText2.getText().toString();
            }

            if (TextUtils.isEmpty(editText3.getText())){
                editText3.setError("Movie Director is required");
            }else {
                movieDirector = editText3.getText().toString();
            }

            if (TextUtils.isEmpty(editText4.getText())){
                editText4.setError("Movie Actors are required");
            }else{
                movieActors = editText4.getText().toString();
            }

            rating = (int) ratingBar.getRating();
            movieRatings = String.valueOf(rating);

            if (TextUtils.isEmpty(editText5.getText())){
                editText5.setError("Movie review is required");
            } else{
                movieReviews = editText5.getText().toString();
            }

            int date = Integer.parseInt(movieYear);
            int date2 = 1895;
            System.out.println(date);
            System.out.println(date2);

            if (date > date2){
                    DB.updateUserData(movieTitle, movieYear, movieDirector, movieActors, movieRatings, movieReviews,favourites);
                    checkSaveData = true;
                    if (checkSaveData){
                        Toast.makeText(EditMovieActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
            }else{
                Toast.makeText(EditMovieActivity.this, "Movie Year must be greater than 1895", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

}
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String option = parent.getItemAtPosition(position).toString();
        if (option.equals("Favourite")){
            favourites  = "Yes";
        }else if (option.equals("Not Favourite")){
            favourites = "No";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}