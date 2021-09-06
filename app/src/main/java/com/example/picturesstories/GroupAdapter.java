package com.example.picturesstories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ExampleViewHolder> {
private ArrayList<PictuerGoupItem> pictuerGoupItems;

    private List<PictuerGoupItem> exampleList;
    private List<PictuerGoupItem> exampleListFull;
    private Context mContext;
    private List<ViewImage> mViewImage;
    private setOnClickListenerGroupPicture listenerGroupPicture;

    public GroupAdapter(Context context, List<ViewImage> viewImages , setOnClickListenerGroupPicture listenerGroupPicture) {
        mContext = context;
        mViewImage = viewImages;
        this.listenerGroupPicture = listenerGroupPicture;
    }



public static class ExampleViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageView;

    public ExampleViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.image_group);


    }
}
    GroupAdapter(List<PictuerGoupItem> exampleList ) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);

    }


    public GroupAdapter (ArrayList<PictuerGoupItem> pictuerlist) {
        pictuerGoupItems = pictuerlist;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_group_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;

    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
//        PictuerGoupItem currentItem = pictuerGoupItems.get(position);
        ViewImage viewImage = mViewImage.get(position);
        if (viewImage.getImage() != null){
            Glide.with(mContext).load(viewImage.getImage()).
                    placeholder(R.drawable.ic_launcher_background).centerCrop().
                    error(android.R.color.holo_red_dark).into(holder.mImageView);
        }
//        holder.mImageView.setImageResource(currentItem.getmImageResource());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerGroupPicture.onClickItem(viewImage.getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mViewImage.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PictuerGoupItem> filteredList = new ArrayList<>();
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


    public interface setOnClickListenerGroupPicture{
        void onClickItem(String idItem);
    }



}
