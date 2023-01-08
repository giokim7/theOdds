package com.giorgiolupo.theodds;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChallengeDB {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Context context;


    //constructor
    public ChallengeDB(Context context){
        this.context = context;
    }

    //display challenge text
    public void setChallengeText(String uniqueId, TextView textView){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId).child("text"); //get reference
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String challenge_text = snapshot.getValue(String.class);
                textView.setText(challenge_text);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    //display range
    public void setRangeTxt(String uniqueId, TextView textView){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId).child("range"); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String range = snapshot.getValue(String.class);
                textView.setText("Range to guess within is: " + range);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //insert range number in DB
    public void insertRangeAndNoInDB(String uniqueId, String range, String number_receiver){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("range").setValue(range);
                databaseReference.child("number_receiver").setValue(number_receiver);
                databaseReference.child("step").setValue("3");
                //on success
                finishStep();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Data Not Sent",Toast.LENGTH_SHORT).show();
            }
        });
    }


    //get receiver name
    public void setChallengerText(String uniqueId, TextView textView) {
        DatabaseReference dbRefChallenger = firebaseDatabase.getReference("challenges").child(uniqueId).child("challenger"); //get reference

        //get challenger
        dbRefChallenger.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String challenger = snapshot.getValue(String.class);
                textView.setText(challenger);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


  /*  public void setReceiverText(String uniqueId, TextView textView) {
        DatabaseReference dbReceiver = firebaseDatabase.getReference("challenges").child(uniqueId).child("receiver"); //get reference

        //get receiver
        dbReceiver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String receiver = snapshot.getValue(String.class);
                textView.setText(receiver);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }*/

    //checck if number sender == number receiver
    public void checkIfNoAreSame(String uniqueId, String number_sender){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String number_receiver = snapshot.child("number_receiver").getValue(String.class);

                if(number_receiver.equals(number_sender)){
                    insertStepInDB("6r", uniqueId); //step 5 and sending video or whatever
                    goResultPage("correct", uniqueId); //inform about good result
                } else {
                    insertStepInDB("4", uniqueId); //step 4= reverse
                    goResultPage("wrong", uniqueId); //inform about wrong result
                };

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


    }


    //insert correct step in DB to redirect next user
    protected void insertStepInDB(String step, String uniqueId){
        DatabaseReference databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("step").setValue(step);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    //go to wrong page
    protected void goResultPage(String result, String uniqueId){
        if(result.equals("wrong")){
            Intent intent = new Intent(context, WrongResult.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if(result.equals("correct")){
            Intent intent2 = new Intent(context, CorrectResult.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("uniqueId", uniqueId);
            context.startActivity(intent2);
        } else if(result.equals("reverse_correct")){
            Intent intent = new Intent(context, UploadVideo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("uniqueId", uniqueId);
            context.startActivity(intent);
        }

    }

    //finish step
    protected void finishStep(){
        Toast.makeText(context,"Data Sent, Wait for Answer",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, start.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}

