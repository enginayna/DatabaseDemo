package com.example.databasedemo;

public class Dictionary {
    private String ingilizce;
    private String turkce;
    private int word_id;

    public Dictionary(){
//android room database
    }

    public Dictionary(String ingilizce, String turkce) {
        this.ingilizce = ingilizce;
        this.turkce = turkce;
    }

    public Dictionary(int word_id , String ingilizce, String turkce ) {
        this.ingilizce = ingilizce;
        this.turkce = turkce;
        this.word_id = word_id;
    }

    public String getIngilizce() {
        return ingilizce;
    }

    public void setIngilizce(String ingilizce) {
        this.ingilizce = ingilizce;
    }

    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }
}
