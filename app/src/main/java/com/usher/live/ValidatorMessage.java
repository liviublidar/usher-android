package com.usher.live;

import android.widget.TextView;

public class ValidatorMessage {

    public TextView originalTextView;
    public boolean valid;

    public ValidatorMessage(TextView originalTextView, Boolean validity){
        this.originalTextView = originalTextView;
        this.valid = validity;
    }

    @Override
    public String toString(){
        return Boolean.toString(valid);
    }

    public TextView getTextView(){
        return this.originalTextView;
    }

}