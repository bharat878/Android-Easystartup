package com.example.easystartup.Investor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easystartup.Investor.IdeaDetails;
import com.example.easystartup.Investor.model.MyModel;
import com.example.easystartup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

//import java.util.List;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder> {
    RecyclerView mRecyclerView;

    public static final String TITLE = "TITLE";
    public static final String SUB_TITLE = "SUBTITLE";
    public static final String DECRIPTION = "DISCRIPTION";
    public static final String Image = "Photo";
    public static final String HASH_EXTRA = "HASH_EXTRA";
     Context mContext;
     ArrayList<MyModel> mArrayList;

     //String emailD;

    public IdeaAdapter(Context mContext, ArrayList<MyModel> mArrayList)
    {
        this.mContext = mContext;

        this.mArrayList = mArrayList;
    }
    @NonNull
    @Override
    public IdeaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.idea_list, viewGroup,false);
        return new IdeaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IdeaViewHolder ideaViewHolder, final int i) {

        ideaViewHolder.title.setText(mArrayList.get(i).getTitle());
        ideaViewHolder.subTitle.setText(mArrayList.get(i).getSubtitle());

        Picasso.with(mContext)
                .load(mArrayList.get(i).getImage())
                .fit()
                .centerCrop()
                .into(ideaViewHolder.ImageView);


        ideaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(TITLE, mArrayList.get(i).getTitle());
                hashMap.put(SUB_TITLE, mArrayList.get(i).getSubtitle());
                hashMap.put(DECRIPTION, mArrayList.get(i).getDescription());
                hashMap.put(Image, mArrayList.get(i).getImage());

                Intent intent = new Intent(mContext, IdeaDetails.class);
                intent.putExtra(HASH_EXTRA,hashMap);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

    }

    public  class IdeaViewHolder extends RecyclerView.ViewHolder {

        public TextView title,subTitle;
        public ImageView ImageView;
        public IdeaViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ideaTitle);
            subTitle = itemView.findViewById(R.id.ideaSubTitle);
          //  email = itemView.findViewById(R.id.ideaEmail);
            ImageView = itemView.findViewById(R.id.imageView);
        }
    }
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

}
