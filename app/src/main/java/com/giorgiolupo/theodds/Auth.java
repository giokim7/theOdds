package com.giorgiolupo.theodds;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context context;
    String currentUser;


    public Auth(Context context) {
        this.context = context;
    }

    //sign in
    public void signIn() {
        if (mAuth.getCurrentUser() == null) {                           //check if the user is new then signIn anonymously
            mAuth.signInAnonymously().                                 //.signInAnonymously is a method provided by Firebase
                    addOnCompleteListener(task -> {
                Toast.makeText(context, "CALL", Toast.LENGTH_SHORT).show();
                if (task.isSuccessful())                    // check the required task is completed successfully
                {
                    currentUser = mAuth.getCurrentUser().getUid();
                } else {
                    Toast.makeText(context, "NOPE", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            currentUser = mAuth.getCurrentUser().getUid();
        }
    }

    ;


    //get current user
    public String getCurrentUser() {
        return mAuth.getCurrentUser().getUid();
    }

}

