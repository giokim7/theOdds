package com.giorgiolupo.theodds;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Reverse extends AppCompatActivity {
    TextView range_txt, text_text;
    EditText number_txt;
    Button send_btn;
    String uniqueId;
    ReverseDB reverseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reverse);

        //get widgets
        range_txt = findViewById(R.id.range_txt);
        number_txt = findViewById(R.id.number_reverse_receiver);
        send_btn = findViewById(R.id.send_btn);
        text_text = findViewById(R.id.text_text);

        //create obj
        reverseDB = new ReverseDB(getApplicationContext());

        //open popup for choice
        openPopupToChoose();

        //get unique ID
        uniqueId = getIntentUniqueId();

        //display text
        reverseDB.setRangeTxt(uniqueId, range_txt);
        reverseDB.setChallengeText(uniqueId, text_text);

        //send
        send_btn.setOnClickListener(view -> {
            reverseDB.insertReverseNoInDB(uniqueId, number_txt.getText().toString());
        });



    }

    //choose if reverse or not
    private void openPopupToChoose(){
        AlertDialog.Builder alertdi = new AlertDialog.Builder(this);
        alertdi.setMessage("Your challenger did not guess your number. Do you want to reverse?");
        alertdi.setPositiveButton("Yes", (dialog, which) -> {});
        alertdi.setNegativeButton("No", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), start.class);
            startActivity(intent);
        });
        alertdi.create().show();
    }

    //get uniqueid
    private String getIntentUniqueId(){
        return getIntent().getExtras().getString("uniqueId");
    }

}