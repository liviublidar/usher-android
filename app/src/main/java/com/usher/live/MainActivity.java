package com.usher.live;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer {
    private Button somethingButton;
    private TextView helloText, loginEmailError;
    private EditText loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setup the main activity state
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //grab view elements
        this.helloText = findViewById(R.id.helloTxt);
        this.somethingButton = findViewById(R.id.somethingButton);
        this.loginEmail = findViewById(R.id.loginEmail);
        this.loginEmailError = findViewById(R.id.loginEmailError);

        //instantiate form validators
        TextValidator emailValidator = new TextValidator(this.loginEmail) {
            @Override public void validate(TextView textView, String text) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                    this.setIsValid(true);

                } else {
                    this.setIsValid(false);
                }

                setChanged();
                notifyObservers(new ValidatorMessage(this.getOriginalTextView(), this.getIsValid()));
            }
        };

        //register this instance as observer to validators
        emailValidator.addObserver(this);

        //add textListeners
        this.loginEmail.addTextChangedListener(emailValidator);

        //background agradient animation
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


    }

    public void onSomethingButtonClick(View view) {
        this.helloText.setVisibility(View.VISIBLE);
    }

    @Override
    public void update(Observable o, Object value) {
        ValidatorMessage validatorMessage = (ValidatorMessage) value;
        if(validatorMessage.getTextView().equals(this.loginEmail)){
            if(validatorMessage.valid){
                this.loginEmailError.setVisibility(View.GONE);
            } else {
                this.loginEmailError.setVisibility(View.VISIBLE);
            }
        }

    }
}
