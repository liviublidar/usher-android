package com.usher.live;

import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import java.util.Observable;

public abstract class TextValidator extends Observable implements TextWatcher  {
    private final TextView textView;
    private boolean isValid = true;


    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    public void setIsValid(Boolean validity){
        this.isValid = validity;
    }

    public Boolean getIsValid(){
        return this.isValid;
    }

    public TextView getOriginalTextView(){
        return this.textView;
    }
}