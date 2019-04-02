package com.example.easystartup.Investor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.easystartup.Investor.adapter.IdeaAdapter;
import com.example.easystartup.Investor.model.MyModel;
import com.example.easystartup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ideaView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private IdeaAdapter mAdapter;

    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
   private ArrayList<MyModel> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_view);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mDatabaseReference.child("Enterpreneur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String key = postSnapshot.getKey();

                    mDatabaseReference.child("Enterpreneur").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                String key = data.getKey();
                                String value = data.getValue().toString();

                                if(!key.equals("Email") &&!key.equals("Name") && !key.equals("Nationality") && !key.equals("PhoneNo")){
                                    MyModel myModel = data.getValue(MyModel.class);
                                 //  Toast.makeText(ideaView.this," "+myModel,Toast.LENGTH_SHORT).show();
                                   mArrayList.add(myModel);
                               }
                            }
                            IdeaAdapter mAdapter = new IdeaAdapter(getApplicationContext(),mArrayList);
                            mRecyclerView.setAdapter(mAdapter);
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
    }
}
