package com.giorgiolupo.theodds;

public class ModelDataClass {
    ///CreateOnlineChallengeDB
//    challenger_name: close
//    receiver_name:
//    step:
//    text:
    String challenger_name;
    String receiver_name;
    String step;
    String text;
    String user_id;
    String challenge_id;
    String keyvalue;


    public ModelDataClass() {

    }



    public String getChallenger_name() {
        return challenger_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getStep() {
        return step;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser_id() {
        return user_id;
    }


    public String getString() {
        return challenge_id;
    }
    public String getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }
}
