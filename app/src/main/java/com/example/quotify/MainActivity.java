package com.example.quotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuote;
    private TextView tvAuthor;
    private List<String> quotes;
    private List<String> authors;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "QuotesAppPrefs";
    private static final String FAVORITES_KEY = "favorites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuote = findViewById(R.id.tvQuote);
        tvAuthor = findViewById(R.id.tvAuthor);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnFavorites = findViewById(R.id.btnFavorites);
        Button btnViewFavorites = findViewById(R.id.btnViewFavorites);

        quotes = new ArrayList<>();
        authors = new ArrayList<>();
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        populateQuotes();
        displayRandomQuote();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomQuote();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quote = tvQuote.getText().toString();
                String author = tvAuthor.getText().toString();
                String fullQuote = quote + " - " + author;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, fullQuote);
                startActivity(Intent.createChooser(shareIntent, "Share quote via"));
            }
        });

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quote = tvQuote.getText().toString();
                String author = tvAuthor.getText().toString();
                saveToFavorites(quote, author);
            }
        });

        btnViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateQuotes() {
        quotes.add("Believe you can and you're halfway there.");
        authors.add("Theodore Roosevelt");

        quotes.add("Do what you can, with what you have, where you are.");
        authors.add("Theodore Roosevelt");

        quotes.add("It does not matter how slowly you go as long as you do not stop.");
        authors.add("Confucius");

        quotes.add("The only way to achieve the impossible is to believe it is possible.");
        authors.add("Charles Kingsleigh");

        quotes.add("Success is not how high you have climbed, but how you make a positive difference to the world.");
        authors.add("Roy T. Bennett");

        quotes.add("You are never too old to set another goal or to dream a new dream.");
        authors.add("C.S. Lewis");

        quotes.add("To see what is right and not do it is the want of courage.");
        authors.add("Confucius");

        quotes.add("What lies behind us and what lies before us are tiny matters compared to what lies within us.");
        authors.add("Ralph Waldo Emerson");

        quotes.add("You must be the change you wish to see in the world.");
        authors.add("Mahatma Gandhi");

        quotes.add("Act as if what you do makes a difference. It does.");
        authors.add("William James");

        quotes.add("Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle.");
        authors.add("Christian D. Larson");

        quotes.add("With the new day comes new strength and new thoughts.");
        authors.add("Eleanor Roosevelt");

        quotes.add("The only limit to our realization of tomorrow is our doubts of today.");
        authors.add("Franklin D. Roosevelt");

        quotes.add("Quality is not an act, it is a habit.");
        authors.add("Aristotle");

        quotes.add("You miss 100% of the shots you don’t take.");
        authors.add("Wayne Gretzky");

        quotes.add("I attribute my success to this: I never gave or took any excuse.");
        authors.add("Florence Nightingale");

        quotes.add("You must do the thing you think you cannot do.");
        authors.add("Eleanor Roosevelt");

        quotes.add("Keep your face always toward the sunshine—and shadows will fall behind you.");
        authors.add("Walt Whitman");

        quotes.add("The best time to plant a tree was 20 years ago. The second best time is now.");
        authors.add("Chinese Proverb");

        quotes.add("It always seems impossible until it's done.");
        authors.add("Nelson Mandela");
    }

    private void displayRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.size());
        String randomQuote = quotes.get(index);
        String randomAuthor = authors.get(index);
        tvQuote.setText(randomQuote);
        tvAuthor.setText(randomAuthor);
    }

    private void saveToFavorites(String quote, String author) {
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        favorites.add(quote + " - " + author);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(FAVORITES_KEY, favorites);
        editor.apply();
        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
    }
}

