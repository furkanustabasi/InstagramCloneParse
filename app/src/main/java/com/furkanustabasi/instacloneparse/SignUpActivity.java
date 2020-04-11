package com.furkanustabasi.instacloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameText, passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNameText= findViewById(R.id.sign_up_activity_name_text);
        passwordText= findViewById(R.id.password_activity_name_text);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser!=null){
            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
            startActivity(intent);
        }

    }



    public void signIn(View view){

        ParseUser.logInInBackground(userNameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignUpActivity.this, "Welcome "+ user.getUsername(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void signUp(View view){

        ParseUser user = new ParseUser();
        user.setUsername(userNameText.getText().toString());
        user.setPassword(passwordText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
