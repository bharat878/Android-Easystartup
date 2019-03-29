package com.example.easystartup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easystartup.Investor.InvestorSignIn;
import com.example.easystartup.Startup.StartUptSignIn;

public class Welcome extends AppCompatActivity {

    Button startup, investor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startup = (Button)findViewById(R.id.startup);
        investor = (Button)findViewById(R.id.investor);

        startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, StartUptSignIn.class);
                startActivity(intent);
            }
        });

        investor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, InvestorSignIn.class);
                startActivity(intent);
            }
        });
    }


}
