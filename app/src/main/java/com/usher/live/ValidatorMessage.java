package com.usher.live;

import android.widget.TextView;

import java.lang.reflect.Array;

public class ValidatorMessage {

    public TextView originalTextView;
    public boolean valid;
    public String option = "";

    public ValidatorMessage(TextView originalTextView, Boolean validity){
        this.originalTextView = originalTextView;
        this.valid = validity;
    };

    public ValidatorMessage(TextView originalTextView, Boolean validity, String option){
        this.originalTextView = originalTextView;
        this.valid = validity;
        this.option = option;

    }

    @Override
    public String toString(){
        return "valid: " + String.valueOf(this.valid) + "\n option: " + this.option + "\n";
    }

    public TextView getTextView(){
        return this.originalTextView;
    }

}