package com.example.gullutesting3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswrodrecover;
    private TextView mgobacktologin;
    private FirebaseAuth firebaseAuth;
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgotpassword);
        transparentStausbarAndNavigation();
        autoHiddenNavigationBar();

        mforgotpassword = findViewById(R.id.forgotpassword);
        mpasswrodrecover = findViewById(R.id.passwordrecover);
        mgobacktologin = findViewById(R.id.gobacktologin);
        firebaseAuth = FirebaseAuth.getInstance();


        //if a user by mistake clicked on the forgot password buttton this take him bakc to the main activity
        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgotpassword.this, Login.class);
                startActivity(intent);

            }
        });
        //now when the user entered the registration email we need to send a verification email
        //after when the user clicked on the SUBMIT button we need to check the edit text contains some values or not

        mpasswrodrecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgotpassword.getText().toString().trim();
                if(mail.isEmpty()){
                    //if user did not entered any email and clicked on the sumbit botton then a toast will be displayed to him
                    Toast.makeText(Forgotpassword.this, "Complete Your Credentials", Toast.LENGTH_SHORT).show();
                }else{
                    //when user entered the email we will send an verification
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //here task complete means the email to reset the password is sent to the user
                            if(task.isSuccessful()){
                                //now if the email is sent you need to show the confirmation to the user
                                //and redirect the user to the login page
                                Toast.makeText(Forgotpassword.this, "Recovery mail is sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Forgotpassword.this, Login.class));
                            }else{
                                //if something went wrong or the email entered by the user does not exist
                                Toast.makeText(Forgotpassword.this, "Account Does Not EXIST", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });


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