package com.example.simpleblog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {
    private TextView articleTitre;
    private TextView articleContenu;
    private TextView articleDate;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);

        articleTitre = findViewById(R.id.articleTitre);
        articleContenu = findViewById(R.id.articleContenu);
        articleDate = findViewById(R.id.articleDate);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        String titre = intent.getStringExtra("titre");
        String contenu = intent.getStringExtra("contenu");

        articleTitre.setText(titre);
        articleContenu.setText(contenu);

        String dateArticle = sharedPreferences.getString("dateArticle", "");
        articleDate.setText(dateArticle);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }
        });
    }
}
