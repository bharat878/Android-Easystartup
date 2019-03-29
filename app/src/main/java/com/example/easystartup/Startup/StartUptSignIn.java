package com.example.easystartup.Startup;

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

public class StartUptSignIn extends AppCompatActivity implements View.OnClickListener{

    Button StartupSignup;

    EditText edtEmailLogin, edtPasswordLogin;
    Button loginStartup;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_upt_sign_in);

        progressDialog = new ProgressDialog(this);

        StartupSignup = (Button)findViewById(R.id.startupSignup);

        edtEmailLogin = (EditText)findViewById(R.id.startupEmail);
        edtPasswordLogin = (EditText)findViewById(R.id.startupPassword);
        loginStartup = (Button) findViewById(R.id.startBtnIn);

        loginStartup.setOnClickListener(this);

        StartupSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartUptSignIn.this, StartUPSignUp.class);
                startActivity(intent);
            }
        });

        TextView forgetPassword = (TextView)findViewById(R.id.startupForget);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartUptSignIn.this, resetPassword.class));
            }
        }
        );
    }

//
//    public void showForgetPassword() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View dialogView = inflater.inflate(R.layout.forgetpassword, null);
//
//        dialogBuilder.setView(dialogView);
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//    }


    private void userLogin() {
        final String email = edtEmailLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();

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
        progressDialog.setMessage("logging user...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Log.w("createUserWithEmail:failure", task.getException());
                    Toast.makeText(StartUptSignIn.this,"logged in successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(StartUptSignIn.this, startupIdea.class);
                    intent.putExtra("Email", email);
                    startActivity(intent);

                    //  startActivity(new Intent(StartUptSignIn.this,startupIdea.class));
                  //  finish();
                }
                else
                {
                    Toast.makeText(StartUptSignIn.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==loginStartup){
            userLogin();
        }
    }

}
