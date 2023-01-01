package com.example.atyourservice.SignUp;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Login.LoginActivity;
import com.example.atyourservice.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    Button Signbtn, regToLoginBtn;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private ProgressDialog mProgress;
    RadioButton RadioUser;
    RadioButton RadioWorker;
    RadioButton RadioAdmin;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Signbtn = findViewById(R.id.SignUpBtn);
        regToLoginBtn = findViewById(R.id.AlreadyHaveAccount);

        final EditText regUsername = findViewById(R.id.RegUsername);
        final EditText regEmail = findViewById(R.id.RegEmail);
        final EditText regPhoneNo = findViewById(R.id.RegPhoneNo);
        final EditText regPassword = findViewById(R.id.RegPassword);
        RadioUser = findViewById(R.id.RadioUser);
        RadioWorker = findViewById(R.id.RadioWorker);
        RadioAdmin = findViewById(R.id.RadioAdmin);

        RadioGroup radioGroup = findViewById(R.id.RGroup);

        radioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
            if (checkedId == R.id.RadioUser) {
                type = 1;
            } else if (checkedId == R.id.RadioWorker) {
                type = 2;
            } else if (checkedId == R.id.RadioAdmin) {
                type = 3;
            } else {
                type = 0;
            }
        }));
        mProgress = new ProgressDialog(this);
        String titleId = "SignUp is in progress...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        Signbtn.setOnClickListener(v -> {

            String username = regUsername.getText().toString();
            String email = regEmail.getText().toString();
            String password = regPassword.getText().toString();
            String cn = regPhoneNo.getText().toString();
            String Admin = RadioAdmin.getText().toString();
            String User = RadioUser.getText().toString();
            String worker = RadioWorker.getText().toString();

            if (TextUtils.isEmpty(username)) {
                regUsername.setError("Name cannot Be Empty");
            } else if (TextUtils.isEmpty(email)) {
                regEmail.setError("Email Cannot Be Empty");
            } else if (isValidEmailId(regEmail.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(cn)) {
                regPhoneNo.setError("Contact Number Cannot Be Empty");
            } else if (regPhoneNo.getText().toString().length() < 10) {
                Toast.makeText(getApplicationContext(), "Minimum 10 Character Are Required", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                regPassword.setError("Password Cannot Be Empty");
            } else if (regPassword.getText().toString().length() < 8) {
                Toast.makeText(getApplicationContext(), "Minimum 8 Character Are Required", Toast.LENGTH_SHORT).show();
            } else if (type == 0) {
                Toast.makeText(SignupActivity.this, "Type Is Not Selected", Toast.LENGTH_SHORT).show();
            } else if (type == 1) {
                mProgress.show();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        Toast.makeText(SignupActivity.this, "s", Toast.LENGTH_SHORT).show();
                        Customer cus = new Customer(username, email, cn, User);

                        getInstance().getReference("Customers").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(cus).
                                addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "User Has Been Registered Successfully", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        mProgress.show();
                                        Toast.makeText(SignupActivity.this, "Failed To Registered User! Try Again", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                    }
                                });
                    } else {
                        mProgress.show();
                        Toast.makeText(SignupActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                });
            } else if (type == 2) {
                mProgress.show();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Customer cus = new Customer(username, email, cn, worker);
                        getInstance().getReference("Worker").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(cus).
                                addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Worker Has Been Registered Successfully", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        mProgress.show();
                                        Toast.makeText(SignupActivity.this, "Failed To Registered Worker ! Try Again", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                    }
                                });
                    } else {
                        mProgress.show();
                        Toast.makeText(SignupActivity.this, "Failed To Registered", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                });
            } else if (type == 3) {
                mProgress.show();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Customer cus = new Customer(username, email, cn, Admin);
                        getInstance().getReference("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(cus).
                                addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Admin Has Been Registered Successfully", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        mProgress.show();
                                        Toast.makeText(SignupActivity.this, "Failed To Registered Admin ! Try Again", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                    }
                                });
                    } else {
                        mProgress.show();
                        Toast.makeText(SignupActivity.this, "Failed To Registered", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                });
            }
        });

        regToLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmailId(String email) {

        return !Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
