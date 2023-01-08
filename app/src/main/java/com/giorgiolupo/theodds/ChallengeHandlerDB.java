package com.giorgiolupo.theodds;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChallengeHandlerDB {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Context context;


    public ChallengeHandlerDB(Context context){
        this.context = context;
    };


    //private void check db
    public void checkStepDB(String uniqueId){
        databaseReference = firebaseDatabase.getReference("challenges").child(uniqueId).child("step"); //get reference

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String step = dataSnapshot.getValue(String.class); //get step value
                sendToCorrectStep(step, uniqueId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    //send to correct page
    public void sendToCorrectStep(String step, String uniqueId){
        //step 1 : creation
        //step 2: receiver
        //step 3: send back
        //step 4: reverse
        //step 5: reverse to send back
        //step 6: manda video/foto (c'è video url)
        switch(step)
        {
            //case 1: //go to create challenge, but there is not
            case "2": // receiver
                Intent intent = new Intent(context, ChallengeReceived.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("uniqueId", uniqueId);
                context.startActivity(intent);
                break;
            case "3": //send back
                Intent intent1 = new Intent(context, ChallengeToSendBack.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("uniqueId", uniqueId);
                context.startActivity(intent1);
                break;
            case "4": //reverse
                Intent intent2 = new Intent(context, Reverse.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("uniqueId", uniqueId);
                context.startActivity(intent2);
                break;
            case "5": //reverse to send back
                Intent intent3 = new Intent(context, ReverseToSendBack.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.putExtra("uniqueId", uniqueId);
                context.startActivity(intent3);
                break;
            case "6r":
            case"6c": //send video or media
                Intent intent4 = new Intent(context, CorrectResult.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent4.putExtra("uniqueId", uniqueId);
                context.startActivity(intent4);
                break;
            case "7r":
            case "7c": //visualize video
                Intent intent5 = new Intent(context, ExoPlayer.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent5.putExtra("uniqueId", uniqueId);
                context.startActivity(intent5);
                break;
            case "8": //finish
                Intent intent6 = new Intent(context, FinishChallenge.class);
                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent6.putExtra("uniqueId", uniqueId);
                context.startActivity(intent6);
                break;
            default:
                System.out.println("Default ");
        }
    }





}
