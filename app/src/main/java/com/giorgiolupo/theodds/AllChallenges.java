package com.giorgiolupo.theodds;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllChallenges extends Fragment {
    ProgressDialog pDialog;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ChallengeListAdapter challengeListAdapter;
    RecyclerView rvData;
    Auth auth = new Auth(getContext());
    ArrayList<ModelDataClass> arrayList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_challenges, container, false);
        pDialog = new ProgressDialog(getActivity(), R.style.MyProgressTheme);
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        rvData = view.findViewById(R.id.recycler1);
        pDialog.show();

        databaseReference = firebaseDatabase.getReference("challenges");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                  if(auth.getCurrentUser() != null){ // if user already logged in 
                      if(snapshot1.child("receiver_id").exists() ){ //if DB not empty
                          String key = snapshot1.getKey();
                          String challenger_id = snapshot1.child("challenger_id").getValue().toString();
                          String receiver_id = snapshot1.child("receiver_id").getValue().toString();

                          if (challenger_id.equalsIgnoreCase(auth.getCurrentUser())
                                  || receiver_id.equalsIgnoreCase(auth.getCurrentUser())) { // if user exists in DB
                              ModelDataClass modelDataClass = snapshot1.getValue(ModelDataClass.class);
                              if (modelDataClass != null) {
                                  modelDataClass.setKeyvalue(key);

                              }
                              arrayList.add(modelDataClass);

                          } else {
                              Toast.makeText(getContext(), "No Challenges Yet",Toast.LENGTH_SHORT).show();
                          }

                      } else {
                          Toast.makeText(getContext(), "No Challenges Yet",Toast.LENGTH_SHORT).show();
                      }
                  }

                }
                pDialog.dismiss();
                Log.wtf("LISTDATA_SIZE_ARRAY", "challner" + arrayList.size());
                challengeListAdapter = new ChallengeListAdapter(getActivity(), arrayList);
                rvData.setHasFixedSize(true);
                rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvData.setAdapter(challengeListAdapter);


                Log.wtf("LISTDATA", "challner" + arrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.wtf("LISTDATA", "CANCELEED" + error.getMessage());
                pDialog.dismiss();
            }
        });

        return view;
    }


}


