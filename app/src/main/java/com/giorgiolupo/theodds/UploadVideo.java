package com.giorgiolupo.theodds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadVideo extends AppCompatActivity {
    private Uri videouri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoref;
    Button upload_btn;
    Double rand;
    TextView text_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video);

        text_text = findViewById(R.id.text_text);
        ChallengeDB challengeDB = new ChallengeDB(getApplicationContext());
        challengeDB.setChallengeText(getIntent().getExtras().getString("uniqueId"), text_text);

        upload_btn = findViewById(R.id.upload_btn);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        rand = Math.random();
        videoref =storageRef.child("/videos/" + rand); //change name

        //set button listener upload
        upload_btn.setOnClickListener(view -> upload());



    }

    public void upload() {
        if (videouri != null) {
            UploadTask uploadTask = videoref.putFile(videouri);

            uploadTask.addOnFailureListener(e -> Toast.makeText(getApplicationContext(),
                    "Upload failed: " + e.getLocalizedMessage(),

                    Toast.LENGTH_LONG).show()).addOnSuccessListener(
                    taskSnapshot -> Toast.makeText(getApplicationContext(), "Upload complete",

                            Toast.LENGTH_LONG).show()).addOnProgressListener(
                    taskSnapshot -> updateProgress(taskSnapshot));

            uploadTask.addOnCompleteListener(task -> getUrlAsync());



        } else {
            Toast.makeText(getApplicationContext(), "Nothing to upload",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {

        @SuppressWarnings("VisibleForTests") long fileSize =
                taskSnapshot.getTotalByteCount();

        @SuppressWarnings("VisibleForTests")
        long uploadBytes = taskSnapshot.getBytesTransferred();

        long progress = (100 * uploadBytes) / fileSize;

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbar);
        progressBar.setProgress((int) progress);
    }

    public void record(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        videouri = data.getData();
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" +
                        videouri, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }





    // Calls the server to securely obtain an unguessable download Url
    public void getUrlAsync () {

        // Points to the root reference
        videoref.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
            insertDB(downloadUrl.toString());
        });
    }

        private void insertDB(String url){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        DatabaseReference databaseReference = firebaseDatabase.getReference("challenges").child(getIntent().getExtras().getString("uniqueId"));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("reverse").exists()){ //check if
                    databaseReference.child("video").setValue(url);
                    databaseReference.child("step").setValue("7r");
                } else {
                    databaseReference.child("video").setValue(url);
                    databaseReference.child("step").setValue("7c");
                }


                // after adding this data we are showing toast message.
                Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
                //finish step
                ChallengeDB challengeDB = new ChallengeDB(getApplicationContext());
                challengeDB.finishStep();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //cant go to previous page
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(getApplicationContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }


}