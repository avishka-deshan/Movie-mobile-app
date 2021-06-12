package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void register(View view) {
        Intent intent = new Intent(this, RegisterMovieActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void display(View view) {
        Intent intent = new Intent(this, AddToFavActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void viewFavourites(View view) {
        Intent intent = new Intent(this, FavouritesActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void editMovies(View view) {
        Intent intent = new Intent(this, DisplayMovieActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void searchAll(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void ratings(View view) {
        Intent intent = new Intent(this, RatingsActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

/*
References
https://stackoverflow.com/questions/52213891/make-the-ratingbar-secondary-color-opaque

 */