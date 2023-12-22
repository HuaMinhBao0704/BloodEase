package com.example.bloodeasebackup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SelectBloodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_blood);

        // Find the Search Blood Button
        Button searchBloodButton = findViewById(R.id.Search_Blood_Button);

        // Set a click listener on the button
        searchBloodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start LikedListActivity
                Intent likedListIntent = new Intent(SelectBloodActivity.this, LikedListActivity.class);
                startActivity(likedListIntent);
            }
        });
    }
}
