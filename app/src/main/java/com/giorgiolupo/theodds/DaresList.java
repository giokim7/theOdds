package com.giorgiolupo.theodds;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DaresList {
    List<String> normalDares = Arrays.asList
            ("What are the odds that you show me your dog?", "What are the odds that you show me your phone?");
    List<String> sexyDares = Arrays.asList
            ("What are the odds that you show me your leg?", "What are the odds that you show me your 10 last pic?");
    Random rand = new Random();

    public DaresList(){};

    public List getNormalDares(){
        return this.normalDares;
    }

    public List getSexyDares(){
        return this.sexyDares;
    }

    public String getRandom(String type){
        if(type.equals("sexy")){
            return sexyDares.get(rand.nextInt(sexyDares.size()));
        } else {
            return normalDares.get(rand.nextInt(normalDares.size()));
        }
    }
}

