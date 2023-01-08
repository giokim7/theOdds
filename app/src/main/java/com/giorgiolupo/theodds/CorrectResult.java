package com.giorgiolupo.theodds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CorrectResult extends AppCompatActivity {
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correct_result);

        //get widgets
        next_btn = findViewById(R.id.next_btn);

        //go to page
        next_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UploadVideo.class);
            intent.putExtra("uniqueId", getIntent().getExtras().getString("uniqueId"));
            startActivity(intent);
        });



    }

    //cant go to previous page
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
