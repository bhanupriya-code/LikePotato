package com.example.gullutesting3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText msignupemail, msignuppassword;
    private RelativeLayout msignup;
    private TextView mgotologin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        msignupemail = findViewById(R.id.signupemail);
        msignuppassword = findViewById(R.id.signuppassword);
        msignup = findViewById(R.id.signip);
        mgotologin = findViewById(R.id.gotologin);
        firebaseAuth = FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        msignup.setOnClickListener(view -> {
            //now we need to sign up the user but before that we need to verify the user
            //first we will check whether the user filled both the edit text view
            String mail = msignupemail.getText().toString().trim();
            String password = msignuppassword.getText().toString().trim();

            if(mail.isEmpty() || password.isEmpty()){
                Toast.makeText(Register.this, "Please Complete All Field", Toast.LENGTH_SHORT).show();
            }
            //to store the password on the firebase it should not be less than 7 so we need to put that
            else if(password.length() < 7){

                Toast.makeText(Register.this, "Password length can not be less than 7.", Toast.LENGTH_SHORT).show();

            }
            else{
                //register the user in the firebase
                firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                    //now here we need to check whether the user is successfully connected with the firebase or not
                    //for this we use if else condition
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Registration Successfully", Toast.LENGTH_SHORT).show();

                        //now the user is successfully registered
                        //now we will check whether the user is verified or not ny sending an verification mail to the registered email address
                        //verification required to check the mail address is correct or not
                        //for this I am creating a function
                        sendEmailverification();
                    }
                });
            }
        });
    }

    private void sendEmailverification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            //if the user exist
            //send a verification
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                //when verification mail is sent we need to show this to user by a toast
                Toast.makeText(Register.this, "Verification Sent On Mail", Toast.LENGTH_SHORT).show();
                //after verification firebase need to come out and let the user login from the newly registered email
                firebaseAuth.signOut();
                //now after sign out we need to finish this and redirect the user to the main login activity
                finish();
                startActivity(new Intent(Register.this, Login.class));
            });
        }
        else{
            //if the user is not found we need to show this message to the user
            Toast.makeText(this, "Recheck Your Entry", Toast.LENGTH_SHORT).show();
        }
    }
}