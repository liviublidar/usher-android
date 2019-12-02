package com.usher.live;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Observable;
import java.util.Observer;
import android.text.method.PasswordTransformationMethod;

public class MainActivity extends AppCompatActivity implements Observer {
    private Button somethingButton;
    private TextView helloText, loginEmailError;
    private EditText loginEmail;
    private TextInputLayout loginPassword;
    private TextInputEditText loginPasswordText;
    private TextValidator passwordPresent, emailValidator;
    private OnClickListener togglePasswordButtonListener;

    /**
     * runs all the initialisation jobs needed for this activity
     */
    private final void performInitJobs(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        this.grabViewElements();
        this.attachLoginFormListeners();
        this.configureViewElements();
        this.startGradientSwing();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.performInitJobs();
    }

    /**
     * login button will go here
     * @param view
     */
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
        } else if (validatorMessage.getTextView().equals(this.loginPasswordText)){
            if(validatorMessage.option.equals("pwdIcon")){
                if(validatorMessage.valid){ //has text in, show eye
                    this.loginPassword.setEndIconDrawable(R.drawable.ic_visibility_white_24dp);
                    //only observe once for this,  never again. if it changes once, remove observer
                    this.passwordPresent.deleteObserver(this);//
                }
            }
        }

    }

    /**
     * get the view element instances and attach them to the class
     */
    private final void grabViewElements(){
        //grab view elements
        this.helloText = findViewById(R.id.helloTxt);
        this.somethingButton = findViewById(R.id.somethingButton);
        this.loginEmail = findViewById(R.id.loginEmail);
        this.loginPassword = findViewById(R.id.loginPassword);
        this.loginPasswordText = findViewById(R.id.loginPasswordText);
        this.loginEmailError = findViewById(R.id.loginEmailError);
    }

    /**
     * creates and attaches listeners to login form
     */
    private final void attachLoginFormListeners(){
        /**
         * checks if email is valid
         */
        this.emailValidator = new TextValidator(this.loginEmail) {
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

        /**
         * checks if password is present
         */
        this.passwordPresent = new TextValidator(this.loginPasswordText) {
            @Override public void validate(TextView textView, String text) {
                if (text.isEmpty()){
                    this.setIsValid(false);

                } else {
                    this.setIsValid(true);
                }
                setChanged();
                notifyObservers(new ValidatorMessage(this.getOriginalTextView(), this.getIsValid(), "pwdIcon"));
            }
        };

        /**
         * handles clicking on the show/hide password icon
         */
        this.togglePasswordButtonListener = new OnClickListener() {
            public void onClick(View v)
            {
                TextInputLayout loginPassword = findViewById(R.id.loginPassword);
                EditText editText = loginPassword.getEditText();
                if (editText == null) {
                    return;
                }
                // Store the current cursor position
                final int selection = editText.getSelectionEnd();
                if (editText != null && editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                    editText.setTransformationMethod(null);
                    loginPassword.setEndIconDrawable(R.drawable.ic_visibility_off_white_24dp);
                } else {
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    loginPassword.setEndIconDrawable(R.drawable.ic_visibility_white_24dp);

                }
                // And restore the cursor position
                editText.setSelection(selection);
            }
        };

        this.emailValidator.addObserver(this);
        this.passwordPresent.addObserver(this);
        this.loginEmail.addTextChangedListener(emailValidator);
        this.loginPasswordText.addTextChangedListener(passwordPresent);
        this.loginPassword.setEndIconOnClickListener(this.togglePasswordButtonListener);
    }

    /**
     * if initial state configuration on elements is needed programmatically, do it here
     */
    private final void configureViewElements(){
        this.loginPassword.setHintEnabled(false);
        this.loginPassword.setEndIconCheckable(false); //for screen reader purpose, to not interpret it as checkbox
    }

    /**
     * gradient magic goes here
     */
    private final void startGradientSwing(){
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
}
