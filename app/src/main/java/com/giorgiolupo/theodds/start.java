package com.giorgiolupo.theodds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //get dynamic links
        getDynamicLink();

        //load first fragment
        loadFragment(new Home());


        Button button = findViewById(R.id.btnseeid);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(start.this,NewRecyclerScreen.class);
            startActivity(intent);
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        // Fragment fragment;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                loadFragment(new Home());
                return true;
            case R.id.navigation_open_challenges:
                loadFragment(new AllChallenges());
                return true;
        }
        return false;
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getDynamicLink(){
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                    }

                    if(deepLink!=null){
                        redirectToChallengeReceived();
                    }

                })
                .addOnFailureListener(this, e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());    }

    private void redirectToChallengeReceived(){
        Intent intent = new Intent(getApplicationContext(), ChallengeReceived.class);
        startActivity(intent);
    }


}

