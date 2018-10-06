package com.noobs.tictechtoehack.models;

import com.google.gson.annotations.SerializedName;

public class Meaning {
    @SerializedName("text")
    private String text;
    @SerializedName("definition")
    private String definition;
    @SerializedName("example")
    private String example;
    @SerializedName("error")
    private String error;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Meaning(String text, String definition) {
        this.text = text;
        this.definition = definition;
    }

    public Meaning(String text, String definition, String example) {
        this.text = text;
        this.definition = definition;
        this.example = example;
    }

    public Meaning(String error) {
        this.error = error;
    }
}
