package com.example.easystartup.Investor;

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

public class InvestorSignUp extends AppCompatActivity implements View.OnClickListener{

    Button investorSigninReg;

    EditText edtNameReg, edtEmailReg, edtPasswordReg, edtCompanyReg, edtNationalityReg;
    Button btnSignup;;

    ProgressDialog progressDialogI;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_sign_up);

        progressDialogI = new ProgressDialog(this);

        investorSigninReg = (Button)findViewById(R.id.investorSigninReg);

        edtNameReg = (EditText)findViewById(R.id.investorNameReg);
        edtEmailReg = (EditText)findViewById(R.id.investorEmailReg);
        edtPasswordReg = (EditText)findViewById(R.id.investorPasswordReg);
        edtCompanyReg = (EditText)findViewById(R.id.investorCompanyReg);
        edtNationalityReg = (EditText)findViewById(R.id.investorNationalityReg);
        btnSignup = (Button) findViewById(R.id.investorBtnReg);

        btnSignup.setOnClickListener(this);

        investorSigninReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvestorSignUp.this, InvestorSignIn.class);
                startActivity(intent);

            }
        });
    }

    private void registerInvestor() {
        final String fullNameCor = edtNameReg.getText().toString();
        final String emailCor = edtEmailReg.getText().toString();
        final String passwordCor = edtPasswordReg.getText().toString();
        final String companyNameCor = edtCompanyReg.getText().toString();
        final String nationalityCor = edtNationalityReg.getText().toString();

        if (TextUtils.isEmpty(fullNameCor))
        {
            Toast.makeText(this, "Pls enter full name",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(emailCor))
        {
            Toast.makeText(this, "Pls enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordCor))
        {
            Toast.makeText(this,"Pls enter password",Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(companyNameCor))
        {
            Toast.makeText(this, "Pls enter comoany name", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(nationalityCor))
        {
            Toast.makeText(this, "Pls enter nationality", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogI.setMessage("registering user...");
        progressDialogI.show();


        mAuth.createUserWithEmailAndPassword(emailCor,passwordCor).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    saveInvestorDetailsToDatabase(fullNameCor, emailCor, companyNameCor, nationalityCor);
                }else {
                    Toast.makeText(InvestorSignUp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialogI.dismiss();
            }

        });
    }


    private void saveInvestorDetailsToDatabase(String fullName, String email, String companyName, String nationality) {
        String investorUniqueId = mAuth.getCurrentUser().getUid();

        HashMap<String, String> map = new HashMap<>();
        map.put("Name", fullName);
        map.put("Email", email);
        map.put("CompanyName", companyName);
        map.put("Nationality", nationality);

        mDataBaseRef.child("Corporates").child(investorUniqueId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(InvestorSignUp.this, "Account Registered Successfully..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InvestorSignUp.this, InvestorSignIn.class));
                } else {
                    Toast.makeText(InvestorSignUp.this, "Registration Failed..", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v==btnSignup){
            registerInvestor();
        }
    }

}
