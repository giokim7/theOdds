package com.giorgiolupo.theodds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AllDaresSexy extends AppCompatActivity {

    private ListView listView;
    private DaresList daresList = new DaresList();
    private List<String> sexyDares = daresList.getSexyDares();
    CreateOnlineChallengeDB createOnlineChallengeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_dares);

        //get widgets
        listView = findViewById(R.id.list);

        //set array
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,
                sexyDares);
        listView.setAdapter(arr);

        //on single item click
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //send back to previous page
            String selectedItem = (String) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), OnlineMode.class);
            intent.putExtra("text", selectedItem);
            startActivity(intent);
        });


    }

}