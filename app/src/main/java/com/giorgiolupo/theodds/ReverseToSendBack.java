package com.giorgiolupo.theodds;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReverseToSendBack extends AppCompatActivity {
    TextView range_txt, text_text;
    EditText number_txt;
    Button send_btn;
    String uniqueId;
    ReverseDB reverseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reverse_to_send_back);
        //get widgets
        range_txt = findViewById(R.id.range_txt);
        number_txt = findViewById(R.id.number_reverse_challenger);
        send_btn = findViewById(R.id.send_btn);
        text_text = findViewById(R.id.text_text);

        //create obj
        reverseDB = new ReverseDB(getApplicationContext());

        //get unique ID
        uniqueId = getIntentUniqueId();

        //display text
        reverseDB.setRangeTxt(uniqueId, range_txt);
        reverseDB.setChallengeText(uniqueId, text_text);

        //send
        send_btn.setOnClickListener(view -> {
            reverseDB.checkIfNoAreSame(uniqueId, number_txt.getText().toString());
        });



    }


    //get uniqueid
    private String getIntentUniqueId(){
        return getIntent().getExtras().getString("uniqueId");
    }

}

