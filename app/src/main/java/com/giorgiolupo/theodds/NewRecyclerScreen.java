package com.giorgiolupo.theodds;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewRecyclerScreen extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ChallengeListAdapter challengeListAdapter;
    RecyclerView rvData;
    //Auth auth = new Auth();
    ProgressDialog pDialog;


    ArrayList<ModelDataClass> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recycler_screen);
        rvData = findViewById(R.id.rv_listdata);
        pDialog = new ProgressDialog(NewRecyclerScreen.this, R.style.MyProgressTheme);
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        //    auth.signIn();
        pDialog.show();
        //  databaseReference = firebaseDatabase.getReference("challenge").child(uniqueId).child("text"); //get reference
        databaseReference = firebaseDatabase.getReference("challenges");
//                child(auth.getCurrentUser()); //get reference
        Log.wtf("DATA_BASE_REFERENCE", "REFVAL" + databaseReference);
        arrayList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.wtf("LISTDATA_VALUES_ARE", "VALUES" + "key" + snapshot);
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    // String key = snapshot1.getKey();
                    ModelDataClass modelDataClass = snapshot1.getValue(ModelDataClass.class);
                    arrayList.add(modelDataClass);


                }
                pDialog.show();
                Log.wtf("LISTDATA_SIZE_ARRAY", "challner" + arrayList.size());
                challengeListAdapter = new ChallengeListAdapter(NewRecyclerScreen.this, arrayList);
                rvData.setHasFixedSize(true);
                rvData.setLayoutManager(new LinearLayoutManager(NewRecyclerScreen.this));
                rvData.setAdapter(challengeListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}