package com.example.picturesstories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ExampleViewHolder>  {
    private ArrayList<MainRecItem> mainRecItems;

    private List<MainRecItem> exampleList;
    private List<MainRecItem> exampleListFull;
    private Context mContext;
    private List<AddImage> mAddImages;
    private setOnClickListenerMainPicture listenerMainPicture;

    public MainAdapter(Context context, List<AddImage> addImages , setOnClickListenerMainPicture listenerMainPicture) {
        this.listenerMainPicture = listenerMainPicture;
        this.mContext = context;
        this.mAddImages = addImages;
    }



    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_item);
            mTextView1 = itemView.findViewById(R.id.tv_title);

        }
    }
    MainAdapter(List<MainRecItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    public MainAdapter(ArrayList<MainRecItem> mainlist) {
        mainRecItems = mainlist;
    }


    @Override
    public  ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_rec_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;

    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        AddImage addImage = mAddImages.get(position);
        if (addImage.getImage() != null){
            Glide.with(mContext).load(addImage.getImage()).
                    placeholder(R.drawable.ic_launcher_background).centerCrop().
                    error(android.R.color.holo_red_dark).into(holder.mImageView);
        }
        holder.mTextView1.setText(addImage.getTitle());


//        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerMainPicture.onClickItem(addImage.getId() , addImage.getTitleGroup());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddImages.size();
    }


    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MainRecItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                Toast.makeText(mContext, "Eror!", Toast.LENGTH_SHORT).show();
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface setOnClickListenerMainPicture{
        void onClickItem(String idItem , String titleGroup);
    }
}