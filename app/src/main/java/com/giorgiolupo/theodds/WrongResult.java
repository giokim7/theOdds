package com.giorgiolupo.theodds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WrongResult extends AppCompatActivity {
    Button new_challenge_btn;
    Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_result);

        //get widgets
        new_challenge_btn = findViewById(R.id.new_challenge_btn);
        home = findViewById(R.id.home);


        //send
        new_challenge_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), OnlineMode.class);
            startActivity(intent);
        });

        home.setOnClickListener(view ->{
            Intent intent = new Intent(getApplicationContext(), start.class);
            startActivity(intent);
        });


    }

    //cant go to previous page
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(getApplicationContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}
