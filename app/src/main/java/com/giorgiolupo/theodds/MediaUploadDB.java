package com.giorgiolupo.theodds;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MediaUploadDB {
    Context context;
    Uri videouri;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference videoref =storageRef.child("/videos/" + Math.random()); //change name



    public MediaUploadDB(Context context){
        this.context = context;
    };

    public void uploadVideo() {
        if (videouri != null) {
            UploadTask uploadTask = videoref.putFile(videouri);

            uploadTask.addOnFailureListener(e -> Toast.makeText(context,"Upload failed: " + e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show()).addOnSuccessListener(
                    taskSnapshot -> Toast.makeText(context, "Upload complete",
                            Toast.LENGTH_LONG).show()).addOnProgressListener(
                    taskSnapshot -> updateProgress(taskSnapshot));

            insertDB(videouri.toString());
        } else {
            Toast.makeText(context, "Nothing to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {

        @SuppressWarnings("VisibleForTests") long fileSize =
                taskSnapshot.getTotalByteCount();

        @SuppressWarnings("VisibleForTests")
        long uploadBytes = taskSnapshot.getBytesTransferred();

        long progress = (100 * uploadBytes) / fileSize;

        //ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbar);
        //rogressBar.setProgress((int) progress);
    }

    private void insertDB(String url){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        DatabaseReference databaseReference = firebaseDatabase.getReference("Video").child("url");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(url);
                Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(context, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
