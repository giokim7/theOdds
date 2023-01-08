package com.giorgiolupo.theodds;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OnlineMode extends AppCompatActivity {
    ImageView list_img, swap_img, fire_img;
    Button send_btn;
    EditText dare_txt, challenger_txt, receiver_txt;
    private DaresList daresList = new DaresList();
    CreateOnlineChallengeDB createOnlineChallengeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_mode);

        //widgets
        list_img = findViewById(R.id.list_img);
        swap_img = findViewById(R.id.swap_img);
        fire_img = findViewById(R.id.fire_img);
        dare_txt = findViewById(R.id.dare_txt);
        send_btn = findViewById(R.id.send_btn);
        challenger_txt = findViewById(R.id.challenger_txt);
        receiver_txt = findViewById(R.id.receiver_txt);

        //login
        Auth auth = new Auth(getApplicationContext());
        auth.signIn();

        //get intent of text from all dares if there is any
        getIntentText();

          //create onlimodeDB
        createOnlineChallengeDB = new CreateOnlineChallengeDB(getApplicationContext());

        //on click
        list_img.setOnClickListener(view -> {
            openPopup();
        });
        swap_img.setOnClickListener(view -> {
            dare_txt.setText(daresList.getRandom("normal")); //get all normal dares
        });
        fire_img.setOnClickListener(view -> {
            dare_txt.setText(daresList.getRandom("sexy")); //get all sexy dares
        });
        send_btn.setOnClickListener(view -> {
            if (checkInput() == true) {
                //send user to DB
                createUniqueID(); //create unique id in DB
            } else {
                Toast.makeText(getApplicationContext(), "Enter your name and your friend name", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //create unique ID for challenge
    private void createUniqueID() {
        createOnlineChallengeDB.createChallengeDB(dare_txt.getText().toString(), challenger_txt.getText().toString(), receiver_txt.getText().toString());
    }


    //open popup to choose normal or sexy
    public void openPopup() {
        AlertDialog.Builder alertdi = new AlertDialog.Builder(this);
        alertdi.setMessage("Do you want to see the sexy dares or normal dares?");
        alertdi.setPositiveButton("Normal Dares", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), AllDares.class);
            startActivity(intent);
        });
        alertdi.setNegativeButton("Sexy Dares", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), AllDaresSexy.class);
            startActivity(intent);
        });
        alertdi.create().show();
    }

    //get intent from all dares to store
    private void getIntentText() {
        if (getIntent().getExtras() != null) {
            String text = getIntent().getExtras().getString("text");
            dare_txt.setText(text); //set as text
        }
        ;
    }

    //check if fields are entered
    private boolean checkInput() {
        if (challenger_txt.getText().toString().isEmpty() || receiver_txt.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }






}