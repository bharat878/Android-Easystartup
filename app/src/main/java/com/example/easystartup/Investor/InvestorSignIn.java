package com.example.easystartup.Investor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easystartup.R;
import com.example.easystartup.resetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InvestorSignIn extends AppCompatActivity implements View.OnClickListener {

    Button investorSignup;
    EditText edtemailInvestor, edtPasswordInvestor;
    Button btnLoginInvestor;

    ProgressDialog progressDialogI;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_sign_in);

        progressDialogI = new ProgressDialog(this);

        investorSignup = (Button)findViewById(R.id.investorSignup);

        edtemailInvestor = (EditText)findViewById(R.id.investorEmail);
        edtPasswordInvestor = (EditText)findViewById(R.id.investorPassword);
        btnLoginInvestor = (Button)findViewById(R.id.investorBtnIn);

        btnLoginInvestor.setOnClickListener(this);

        investorSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvestorSignIn.this, InvestorSignUp.class);
                startActivity(intent);

            }
        });


        TextView forgetPasswordInvestor = (TextView)findViewById(R.id.investorForget);

        forgetPasswordInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InvestorSignIn.this, resetPassword.class));
            }
        }
        );
    }
//
//
//    public void showForgetPasswordInvestor() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View dialogView = inflater.inflate(R.layout.forgetpassword, null);
//
//        dialogBuilder.setView(dialogView);
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//
//        final EditText resetEmail;
//        final Button btnReset;
//
//        resetEmail = (EditText)findViewById(R.id.resetEmail);
//        btnReset = (Button) findViewById(R.id.btnReset);
//
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    Toast.makeText(InvestorSignIn.this,"its working",Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//    }

    private void investorLogin() {
        String emailCor = edtemailInvestor.getText().toString();
        String passwordCor = edtPasswordInvestor.getText().toString();

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

        progressDialogI.setMessage("registering user...");
        progressDialogI.show();

        mAuth.signInWithEmailAndPassword(emailCor,passwordCor).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Log.w("createUserWithEmail:failure", task.getException());
                    Toast.makeText(InvestorSignIn.this,"logged in successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InvestorSignIn.this,ideaView.class));
                    //  finish();
                }
                else
                {
                    Toast.makeText(InvestorSignIn.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
                progressDialogI.dismiss();
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v==btnLoginInvestor)
        {
            investorLogin();
        }
    }

}
