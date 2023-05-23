package com.example.simpleblog;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddArticle extends AppCompatActivity {
    private TextView buttonAjouter;
    private SQLiteDatabase database;
    private EditText inputcontenu;
    private EditText inputtitre;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_article);

        inputtitre = findViewById(R.id.inputtitre);
        inputcontenu = findViewById(R.id.inputcontenu);
        buttonAjouter = findViewById(R.id.buttonAjouter);
        database = openOrCreateDatabase("articles", 0, null);
        createTableIfNotExists();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(inputtitre.getText().toString().trim(), inputcontenu.getText().toString().trim());
                inputtitre.setText("");
                inputcontenu.setText("");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddArticle.this, DetailActivity.class));
                finish();
            }
        });
    }

    private void createTableIfNotExists() {
        database.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT, contenu TEXT)");
    }

    private void insertData(String titre, String contenu) {
        ContentValues values = new ContentValues();
        values.put("titre", titre);
        values.put("contenu", contenu);
        long rowId = database.insert("articles", null, values);

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dateArticle", currentDate);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
