package com.example.easystartup.Investor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easystartup.Investor.model.MyModel;
import com.example.easystartup.R;
import com.squareup.picasso.Picasso;

//import java.util.List;

import java.util.ArrayList;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder> {

     Context mContext;
     ArrayList<MyModel> mArrayList;

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
    public void onBindViewHolder(@NonNull IdeaViewHolder ideaViewHolder, int i) {

        ideaViewHolder.title.setText(mArrayList.get(i).getTitle());
        ideaViewHolder.subTitle.setText(mArrayList.get(i).getSubtitle());
//        Picasso.with(mContext)
//                .load(myModel.getImages())
//                .fit()
//                .centerCrop()
//                .into(ideaViewHolder.ImageView);
    }

    public  class IdeaViewHolder extends RecyclerView.ViewHolder {

        public TextView title,subTitle;
        public ImageView ImageView;
        public IdeaViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ideaTitle);
            subTitle = itemView.findViewById(R.id.ideaSubTitle);
            ImageView = itemView.findViewById(R.id.imageView);
        }
    }
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

}
