package com.example.easystartup.Investor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
      //  String key = mDatabaseReference.child("Enterpreneur").push().getKey();

       // Toast.makeText(ideaView.this,"the id is"+key,Toast.LENGTH_SHORT).show();
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
                                if(!key.equals("Email") && !key.equals("Name") && !key.equals("Nationality") && !key.equals("PhoneNo")){
                                    MyModel myModel = data.getValue(MyModel.class);
                                    Log.d("datbase image","this is"+myModel);
                                    mArrayList.add(myModel);
                                }
                            }
                            IdeaAdapter mAdapter = new IdeaAdapter(getApplicationContext(),mArrayList);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                     //   upload kon se class se krwa rha hai
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
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
//                {
//                    String key = postSnapshot.getKey().toString();
//                    String value = postSnapshot.getValue().toString();
//                    Log.e("onDataChange", key+" - Key Value - "+value);
//                    MyModel myModel = postSnapshot.getValue(MyModel.class);
//                    String uid = postSnapshot.getValue().toString();
//                    mArrayList.add(myModel);
//                }
////
////                mAdapter = new IdeaAdapter(ideaView.this,mArrayList);
////                mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ideaView.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ArrayList<MyModel> mArrayList = new ArrayList<MyModel>();
//        MyModel myModel = new MyModel("Java","android",R.drawable.amazon);
//        mArrayList.add(myModel);

    }
}
