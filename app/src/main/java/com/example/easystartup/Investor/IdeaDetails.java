package com.example.easystartup.Investor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easystartup.Investor.adapter.IdeaAdapter;
import com.example.easystartup.MainActivity;
import com.example.easystartup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class IdeaDetails extends AppCompatActivity {

   TextView enterpreneurName, enterpreneurEmail, enterpreneurNationality, enterpreneurPhoneNo, enterpreneurTitle, enterpreneurSubtitle, enterpreneurDesc;
   ImageView enterpreneurImage;
   Button logout;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_details);

        enterpreneurName = (TextView)findViewById(R.id.enterpreneurName);
        enterpreneurEmail = (TextView)findViewById(R.id.enterpreneurEmail);
        enterpreneurNationality = (TextView)findViewById(R.id.enterpreneurNationality);
        enterpreneurPhoneNo = (TextView)findViewById(R.id.enterpreneurPhoneNo);
        enterpreneurTitle = (TextView)findViewById(R.id.enterpreneurTitle);
        enterpreneurSubtitle = (TextView)findViewById(R.id.enterpreneurSubtitle);
        enterpreneurDesc = (TextView)findViewById(R.id.enterpreneurDesc);
        logout = (Button)findViewById(R.id.Logout);
        enterpreneurImage = (ImageView)findViewById(R.id.enterpreneurImage);

        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra(IdeaAdapter.HASH_EXTRA);
        final String title = hashMap.get(IdeaAdapter.TITLE);
        String subtitle = hashMap.get(IdeaAdapter.SUB_TITLE);
        String description = hashMap.get(IdeaAdapter.DECRIPTION);
        String image = hashMap.get(IdeaAdapter.Image);

        enterpreneurTitle.setText(title);
        enterpreneurSubtitle.setText(subtitle);
        enterpreneurDesc.setText(description);
        Picasso.with(getApplicationContext()).load(image).into(enterpreneurImage);

        mDatabaseReference.child("Enterpreneur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    final String Key = data.getKey();
                    mDatabaseReference.child("Enterpreneur").child(Key).child("Details").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(title.equals(dataSnapshot.child("Title").getValue().toString())){
                                Toast.makeText(IdeaDetails.this, ""+dataSnapshot.child("Title").getValue().toString(), Toast.LENGTH_SHORT).show();
                                mDatabaseReference.child("Enterpreneur").child(Key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String name = dataSnapshot.child("Name").getValue().toString();
                                        String email = dataSnapshot.child("Email").getValue().toString();
                                        String nationality = dataSnapshot.child("Nationality").getValue().toString();
                                        String phoneNo = dataSnapshot.child("PhoneNo").getValue().toString();
                                        //Log.e("sdkbjvsjkdvbs", name+" : "+email );

                                        enterpreneurName.setText(name);
                                        enterpreneurEmail.setText(email);
                                        enterpreneurNationality.setText(nationality);
                                        enterpreneurPhoneNo.setText(phoneNo);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(IdeaDetails.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }


}
