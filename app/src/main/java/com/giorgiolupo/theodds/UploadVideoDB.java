package com.giorgiolupo.theodds;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UploadVideoDB extends ChallengeDB {


    public UploadVideoDB(Context context) {
        super(context);
    }

    public void insertDB(String url, String uniqueId){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        DatabaseReference databaseReference = firebaseDatabase.getReference("Challenge").child(uniqueId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("video").setValue(url);
                Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
