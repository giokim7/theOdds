package com.giorgiolupo.theodds;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChallengeReceived extends AppCompatActivity {

    String uniqueId;
    EditText range_txt, number_txt, text_text;
    Button send_btn;
    ChallengeDB challengeDB;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_received);

        //get widget
        range_txt = findViewById(R.id.range_txt);
        number_txt = findViewById(R.id.number_txt);
        send_btn = findViewById(R.id.send_btn);
        text_text = findViewById(R.id.text_text);

        //sign in receiving user
        Auth auth = new Auth(getApplicationContext());
        signIn();

        //create obj
        challengeDB = new ChallengeDB(getApplicationContext());
        getUniqueId();

        //send range and number to DB
        send_btn.setOnClickListener(view -> {
            if (checkInput(range_txt.getText().toString(), number_txt.getText().toString()) == true) {
                challengeDB.insertRangeAndNoInDB(uniqueId, range_txt.getText().toString(), number_txt.getText().toString());
            }
        });

    }

    private void signIn(){
        if (mAuth.getCurrentUser() == null) {                   //check if the user is new then signIn anonymously
            mAuth.signInAnonymously().                                 //.signInAnonymously is a method provided by Firebase
                    addOnCompleteListener(task -> {
                if (task.isSuccessful())                    // check the required task is completed successfully
                {
                     currentUser = mAuth.getCurrentUser().getUid();
                }
            })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", e.getMessage());            //return error in logs
                    });
        } else {
            currentUser = mAuth.getCurrentUser().getUid();
        }

    }


    private void getUniqueId(){
        if(getIntent().getExtras() != null){ //means there is no uri
            //get intent
            uniqueId = getIntent().getExtras().getString("uniqueId");
            setChallengeText(getIntent().getExtras().getString("uniqueId"), text_text); //set text of the challenge
            Toast.makeText(getApplicationContext(), uniqueId, Toast.LENGTH_SHORT).show();
            pushReceiverIdIntoDB(); // push the id of the receiver in the DB
        } else {
            openPopup();

        }
    }


    private void setChallengeText(String uniqueId, EditText editText){
        challengeDB.setChallengeText(uniqueId, text_text);
    }

    private void pushReceiverIdIntoDB(){
        FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId);
        databaseReference.child("receiver_id").setValue(currentUser);
    }

    private boolean checkInput(String range, String number) {
        if (Integer.parseInt(range) > 10) {
            Toast.makeText(getApplicationContext(), "Enter number <= 10", Toast.LENGTH_SHORT).show();
            return false;
        } else if((Integer.parseInt(number)>Integer.parseInt(range))){
            Toast.makeText(getApplicationContext(), "Your number must be inferior than your range", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void openPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Insert ID");
        alert.setMessage("Insert ID that your friend sent you through direct message");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        //user push the OK button
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            // Do something with value!
            uniqueId = input.getText().toString();
            setChallengeText(uniqueId, text_text); //set text of the challenge
            pushReceiverIdIntoDB(); // push the id of the receiver in the DB

        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Canceled.
        });

        alert.show();
    }


}

