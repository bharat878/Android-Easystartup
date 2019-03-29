package com.example.easystartup.Startup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easystartup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StartUPSignUp extends AppCompatActivity implements View.OnClickListener{

    Button startupSignInReg;

    EditText edtfullName, edtEmail, edtPassword, edtPhoneNo, edtNationality;
    Button btnSignup;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_upsign_up);

        progressDialog = new ProgressDialog(this);

        startupSignInReg = (Button)findViewById(R.id.startupSigninReg);

        edtfullName= (EditText)findViewById(R.id.startupNameReg);
        edtEmail = (EditText)findViewById(R.id.startupEmailReg);
        edtPassword = (EditText)findViewById(R.id.startupPasswordReg);
        edtPhoneNo = (EditText)findViewById(R.id.startupPhoneReg);
        edtNationality = (EditText)findViewById(R.id.startupNationalityReg);
        btnSignup = (Button)findViewById(R.id.startBtnReg);

        btnSignup.setOnClickListener(this);

        startupSignInReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartUPSignUp.this, StartUptSignIn.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser()
    {
        final String fullName = edtfullName.getText().toString();
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();
        final String phoneNo = edtPhoneNo.getText().toString();
        final String nationality = edtNationality.getText().toString();


        if (TextUtils.isEmpty(fullName))
        {
            Toast.makeText(this, "Pls enter full name",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Pls enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Pls enter password",Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(phoneNo))
        {
            Toast.makeText(this, "Pls enter phone no", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(nationality))
        {
            Toast.makeText(this, "Pls enter nationality", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    saveUserDetailsToDatabase(fullName, email, phoneNo, nationality);
                }else {
                    Toast.makeText(StartUPSignUp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

        private void  saveUserDetailsToDatabase(String fullName, String email, String phoneNo, String  nationality)
        {
            String userUniqueId = firebaseAuth.getCurrentUser().getUid();

            HashMap<String, String> map = new HashMap<>();
            map.put("Name", fullName);
            map.put("Email", email);
            map.put("PhoneNo", phoneNo);
            map.put("Nationality", nationality);

            mDataBaseRef.child("Enterpreneur").child(userUniqueId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(StartUPSignUp.this, "Account Registered Successfully..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartUPSignUp.this, StartUptSignIn.class));
                    } else {
                        Toast.makeText(StartUPSignUp.this, "Registration Failed..", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }


    @Override
    public void onClick(View v) {
        if (v==btnSignup) {
            registerUser();
        }
    }
}
