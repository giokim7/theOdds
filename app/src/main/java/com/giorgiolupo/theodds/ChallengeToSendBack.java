package com.giorgiolupo.theodds;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeToSendBack extends AppCompatActivity {
    EditText number_txt;
    TextView range_txt, text_text;
    Button send_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_to_send_back);

        //get widgets
        number_txt = findViewById(R.id.number_txt);
        send_btn = findViewById(R.id.send_btn);
        range_txt = findViewById(R.id.range_txt);
        text_text = findViewById(R.id.text_text);

        //get intent
        String uniqueId = getIntent().getExtras().getString("uniqueId");

        //make obj
        ChallengeDB challengeDB = new ChallengeDB(getApplicationContext());

        //set text
        challengeDB.setRangeTxt(uniqueId, range_txt);
        challengeDB.setChallengeText(uniqueId, text_text);

        //check if number == db
        send_btn.setOnClickListener(view -> {
            challengeDB.checkIfNoAreSame(uniqueId, number_txt.getText().toString()); //check if guessed or not
        });



    }

}
