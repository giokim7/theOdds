package com.giorgiolupo.theodds;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String currentUser = FirebaseAuth.getInstance().getCurrentUser().toString();
            DatabaseReference databaseReference = firebaseDatabase.getReference("users");
            databaseReference.child("user").setValue(currentUser);


        /*
         setContentView(R.layout.logo);

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(2 * 1000);

                    Intent intent = new Intent(Logo.this, start.class);
                    startActivity(intent);

                    // After 5 seconds redirect to another intent

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };
        // start thread
        background.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}

         */
    }
}