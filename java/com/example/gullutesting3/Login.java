package com.example.gullutesting3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText mloginemail, mloginpassword;
    private RelativeLayout mlogin, mgotosignup;
    private TextView mgotoforgotpassword;
    private FirebaseAuth firebaseAuth;
    private View decorView;
    private ProgressBar mprogressbarofmainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        transparentStausbarAndNavigation();
        autoHiddenNavigationBar();
        mloginemail = findViewById(R.id.loginemail);
        mloginpassword = findViewById(R.id.loginpassword);
        mlogin = findViewById(R.id.login);
        mgotoforgotpassword = findViewById(R.id.gotoforgotpassword);
        mgotosignup = findViewById(R.id.gotosinup);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mprogressbarofmainactivity = findViewById(R.id.progressbarofmainactivity);

        if (firebaseUser != null) {
            //this is for when the user is already logged in then direct send user to the main content of app
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));

        }
        mgotosignup.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
        mgotoforgotpassword.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Forgotpassword.class);
            startActivity(intent);

        });

        mlogin.setOnClickListener(view -> {
            String mail = mloginemail.getText().toString().trim();
            String password = mloginpassword.getText().toString().trim();
            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Complete All Field", Toast.LENGTH_SHORT).show();
            } else {
                //login the user

                //before login the user I want to show the progress bar
                mprogressbarofmainactivity.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                    //when someone clicks on login button we will first check the account exist or not
                    if (task.isSuccessful()) {
                        //this function will check the entered email is verified or not
                        checkmailVerification();
                    } else {
                        Toast.makeText(Login.this, "Account Does not exist", Toast.LENGTH_SHORT).show();
                        mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    private void checkmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser.isEmailVerified()) {
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        } else {

            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Email Is Not Verified", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
    private void transparentStausbarAndNavigation(){
        if(Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21){
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if(Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );

        }
        if(Build.VERSION.SDK_INT >= 21){
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    |WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }


    }

    private void setWindowFlag(int bits, boolean b) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if(b){
            winParams.flags |= bits;
        }else{
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }
    private void autoHiddenNavigationBar(){

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());

                }
            }
        });
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

}