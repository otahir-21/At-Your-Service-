package com.example.atyourservice.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Admin.MenuActivityAdmin;
import com.example.atyourservice.ForgetPassword.ForgetPassword;
import com.example.atyourservice.Menu.MenuActivity;
import com.example.atyourservice.Model.checkWorkerModel;
import com.example.atyourservice.R;
import com.example.atyourservice.SignUp.SignupActivity;
import com.example.atyourservice.Worker.WorkerMapActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Button signUpton, logins;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    public DatabaseReference databaseReference ;
    RadioButton RadioUser;
    RadioButton RadioWorker;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference2;
    int type=0;
    String w2,w3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

         EditText Email = findViewById(R.id.Email);
         EditText Password = findViewById(R.id.Password);
         signUpton =findViewById(R.id.Signupbtn);
         logins =findViewById(R.id.loginbtn);
         Button forget= findViewById(R.id.forgetpassword);
         RadioUser = findViewById(R.id.RadioUserL);
         RadioWorker = findViewById(R.id.RadioWorkerL);
         RadioGroup radioGroup = findViewById(R.id.radiogroup);
        try {
            radioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
                if(checkedId==R.id.RadioUserL){
                    type=1;
                }
                else
                if(checkedId==R.id.RadioWorkerL){
                    type=2;
                }
                else if(checkedId==R.id.RadioAdminL){
                    type=3;
                }
                else{
                    type=0;
                }
            }));
        }catch (NullPointerException e){
            System.out.println("NullPointerException thrown!");
        }
        mProgress =new ProgressDialog(this);
        String titleId="Signing in...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        logins.setOnClickListener(v -> {
            String email=Email.getText().toString();
            String password=Password.getText().toString();

            if(TextUtils.isEmpty(email)){
                Email.setError("Email Cannot Be Empty");
            }else if(isValidEmailId(Email.getText().toString().trim())){
                Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT).show();
            }else if(Password.getText().toString().length() <8){
                Toast.makeText(getApplicationContext(), "Minimum 8 Character Are Required", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(password)){
                Password.setError("Password Cannot Be Empty");
            }
            else if(type ==0) {
                Toast.makeText(LoginActivity.this, "Type Is Not Selected", Toast.LENGTH_SHORT).show();
            }
            else if(type == 1){
                mProgress.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = Objects.requireNonNull(user).getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Customers");
                        databaseReference.child(uid).get().addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                if(task1.getResult().exists()){
                                    mProgress.dismiss();
                                    Intent intent = new Intent(LoginActivity.this , MenuActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    mProgress.dismiss();
                                    Toast.makeText(LoginActivity.this, "Please Signup First", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                mProgress.dismiss();
                                Toast.makeText(LoginActivity.this, "Email Is Invalid", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }
                });
            }
            else if(type ==2){
                mProgress.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = Objects.requireNonNull(user).getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Worker");
                        databaseReference.child(uid).get().addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                if(task1.getResult().exists()){
                                    mProgress.dismiss();

                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference2 = firebaseDatabase.getReference("Worker").child(user.getUid());
                                    databaseReference2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                checkWorkerModel data = dataSnapshot.getValue(checkWorkerModel.class);
                                                 w2= data.getWorkerType();
                                                Intent intent = new Intent(LoginActivity.this, WorkerMapActivity.class);
                                                intent.putExtra("Type", w2);
                                                System.out.println(w2+"sadadeefes");
                                                startActivity(intent);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Getting Post failed, log a message
                                            Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                                            // ...
                                        }
                                    });

                                }
                                else{
                                    mProgress.show();
                                    Toast.makeText(LoginActivity.this, "Please Signup First", Toast.LENGTH_SHORT).show();
                                    mProgress.dismiss();
                                }
                            }
                            else{
                                mProgress.show();
                                Toast.makeText(LoginActivity.this, "Email Is Invalid", Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();
                            }
                        });
                    }
                    else{
                        mProgress.show();
                        Toast.makeText(LoginActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                        mProgress.dismiss();
                    }
                });
            }
            else if(type ==3){
                mProgress.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = Objects.requireNonNull(user).getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
                        databaseReference.child(uid).get().addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                if(task1.getResult().exists()){
                                    mProgress.dismiss();
                                    Intent intent = new Intent(LoginActivity.this , MenuActivityAdmin.class);
                                    startActivity(intent);
                                }
                                else{
                                    mProgress.show();
                                    Toast.makeText(LoginActivity.this, "Please Signup First", Toast.LENGTH_SHORT).show();
                                    mProgress.dismiss();
                                }
                            }
                            else{
                                mProgress.show();
                                Toast.makeText(LoginActivity.this, "Email Is Invalid", Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();
                            }
                        });
                    }
                    else{
                        mProgress.show();
                        Toast.makeText(LoginActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                        mProgress.dismiss();
                    }
                });
            }
        });
        signUpton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , SignupActivity.class);
            startActivity(intent);
        });
        forget.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , ForgetPassword.class);
            startActivity(intent);
        });
    }
    private void updateUI(FirebaseUser user) {
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