package com.example.atyourservice.ForgetPassword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Login.LoginActivity;
import com.example.atyourservice.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {
    private ProgressDialog mProgress;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button Send= findViewById(R.id.forgetpasswordSend);
        EditText Email_Id = findViewById(R.id.ForgetPasswordEmail);

        mProgress =new ProgressDialog(this);
        String titleId="Email Has Been Sending...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        Send.setOnClickListener(v -> {

            String emailAddress = Email_Id.getText().toString();

            if(TextUtils.isEmpty(emailAddress)){
                Email_Id.setError("Email Cannot Be Empty");
            }else if(isValidEmailId(Email_Id.getText().toString().trim())){
                Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT).show();
            } else{
                mProgress.show();
                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task1 -> auth.fetchSignInMethodsForEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            boolean isNewUser = Objects.requireNonNull(task.getResult().getSignInMethods()).isEmpty();
                            if (isNewUser) {
                                mProgress.dismiss();
                                Toast.makeText(ForgetPassword.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (task1.isSuccessful()) {
                                    mProgress.dismiss();
                                    Toast.makeText(ForgetPassword.this, "Email has send", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }));
            }
        });
    }
    private boolean isValidEmailId(String email){

        return !Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}