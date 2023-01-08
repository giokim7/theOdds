package com.giorgiolupo.theodds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Home extends Fragment {
    Button online_btn;
    Button live_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);

        //take wigets
        online_btn = view.findViewById(R.id.online_btn);
        live_btn = view.findViewById(R.id.live_btn);


        //click listenere
        online_btn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), OnlineMode.class);
            startActivity(intent);
        });

        live_btn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), LiveMode.class);
            startActivity(intent);
        });


        return view;
    }


}