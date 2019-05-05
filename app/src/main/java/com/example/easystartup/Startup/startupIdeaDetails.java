package com.example.easystartup.Startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easystartup.MainActivity;
import com.example.easystartup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class startupIdeaDetails extends AppCompatActivity {
    TextView showTitle, showSubtitle, showDesc;
    ImageView showImaage;
    Button showLogout;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

   // Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_idea_details);

        showTitle = (TextView)findViewById(R.id.showTitle);
        showSubtitle = (TextView)findViewById(R.id.showSubtitle);
        showDesc = (TextView)findViewById(R.id.showDesc);
        showImaage = (ImageView) findViewById(R.id.showImage);
        showLogout = (Button)findViewById(R.id.showButton);

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseReference.child("Enterpreneur").child(currentUserId).child("Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("Title").getValue().toString();
                String subtitle= dataSnapshot.child("Subtitle").getValue().toString();
                String desc= dataSnapshot.child("Description").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                showTitle.setText(title);
                showSubtitle.setText(subtitle);
                showDesc.setText(desc);
                Picasso.with(getApplicationContext()).load(image).into(showImaage);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

     showLogout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             mAuth.signOut();
             startActivity(new Intent(startupIdeaDetails.this, MainActivity.class));
            finish();
         }
     });
    }
}
