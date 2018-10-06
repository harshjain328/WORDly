package com.noobs.tictechtoehack.models;

import com.google.gson.annotations.SerializedName;

public class Word {
    @SerializedName("text")
    private String text;

    public Word(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
