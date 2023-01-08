package com.giorgiolupo.theodds;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class test extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {                                       //check if the user is new then signIn anonymously
            mAuth.signInAnonymously().                                 //.signInAnonymously is a method provided by Firebase
                    addOnCompleteListener(task -> {
                if (task.isSuccessful())                    // check the required task is completed successfully
                {
                    Toast.makeText(getApplicationContext(), mAuth.getUid(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", e.getMessage());            //return error in logs
                    });
        } else {
            Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
        }

    }

    ;


    //get current user
    public String getCurrentUser() {
        return currentUser.getUid();
    }

}

