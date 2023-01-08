package com.giorgiolupo.theodds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChallengeListAdapter extends RecyclerView.Adapter<ChallengeListAdapter.ViewHolder> {
    Context context;
    ChallengeHandlerDB challengeHandlerDB;
    ArrayList<ModelDataClass> arrayList;

    //Context context;
    public ChallengeListAdapter(Context context, ArrayList<ModelDataClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ChallengeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ChallengeListAdapter.ViewHolder viewHolder = new ChallengeListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChallengeListAdapter.ViewHolder holder, int position) {
        //create obj
        challengeHandlerDB = new ChallengeHandlerDB(context);

        ModelDataClass modelDataClass = arrayList.get(position);
        holder.challenger_txt.setText(modelDataClass.getChallenger_name());
        holder.receiver_txt.setText(modelDataClass.getReceiver_name());
        holder.userid.setText(modelDataClass.getKeyvalue());
        setTurn(holder.turn_txt, modelDataClass.getStep(), modelDataClass.getChallenger_name(), modelDataClass.getReceiver_name());

        //when click on single item, go to page
        holder.relativeLayout.setOnClickListener(v -> {
            //send to correct page based on what step
            challengeHandlerDB.checkStepDB(modelDataClass.getKeyvalue());
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //set turn based on step
    private void setTurn(TextView textView, String step, String challenger_name, String receiver_name){

        if(step.equals("2") || step.equals("4") || step.equals("6r") || step.equals("7r")){
            textView.setText("The turn of:" + receiver_name); //set step
        } else if(step.equals("3") || step.equals("5") || step.equals("6c") || step.equals("7c")){
            textView.setText("The turn of:" + challenger_name); //set step
        } else {
            textView.setText("Challenge Finished" ); //set step
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView challenger_txt, userid, turn_txt;
        public TextView receiver_txt;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            turn_txt = itemView.findViewById(R.id.turn_text);
            challenger_txt = itemView.findViewById(R.id.challengers_txt);
            receiver_txt = itemView.findViewById(R.id.receiver_txt);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            userid = itemView.findViewById(R.id.id);

        }
    }
}

