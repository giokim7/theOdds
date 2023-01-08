package com.giorgiolupo.theodds;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ReverseDB extends ChallengeDB {
    public ReverseDB(Context context) {
        super(context);
    }

   //display range
    public void setRangeTxt(String uniqueId, TextView textView){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId).child("range"); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String range = snapshot.getValue(String.class);
                Integer reverse_range = (Integer.parseInt(range)-1);
                textView.setText("Range to choose within is: " + reverse_range);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //insert range number in DB
    public void insertReverseNoInDB(String uniqueId, String number_reverse_receiver){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //insert reverse
                databaseReference.child("reverse").child("number_receiver").setValue(number_reverse_receiver);
                insertStepInDB("5",uniqueId);
                //on success
                finishStep();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Data Not Sent",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //checck if number sender == number receiver
    public void checkIfNoAreSame(String uniqueId, String number_sender){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId).child("reverse"); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String number_receiver = snapshot.child("number_receiver").getValue(String.class);//get number from db to compare

                if(number_receiver.equals(number_sender)){
                    insertStepInDB("6c",uniqueId);
                    goResultPage("reverse_correct", uniqueId);
                } else {
                    insertStepInDB("8",uniqueId);
                    goResultPage("wrong", null);
                };

        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


    }



}
