package com.example.sberteacher;

public class State {
    private String rus;
    private String tat;

    public State(String rus, String tat){
        this.rus = rus;
        this.tat = tat;
    }

    public String getRus(){
        return this.rus;
    }

    public String getTat(){
        return this.tat;
    }

    public String setTat(String tat){
        return this.tat;
    }

    public String setRus(String rus){
        return this.rus;
    }
}
