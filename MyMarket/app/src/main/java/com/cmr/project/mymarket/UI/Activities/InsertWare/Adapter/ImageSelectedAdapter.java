package com.cmr.project.mymarket.UI.Activities.InsertWare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Picture;


import java.util.List;

public class ImageSelectedAdapter extends RecyclerView.Adapter<ImageSelectedAdapter.ImageItemViewHolder>{
    private Context context;
    private static List<Picture> pictures;

    public ImageSelectedAdapter(Context context,List<Picture> pictures){
        this.context=context;
        this.pictures=pictures;
    }

    @NonNull
    @Override
    public ImageSelectedAdapter.ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_imge_selected,parent,false);
        return new ImageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSelectedAdapter.ImageItemViewHolder holder, int position) {
        holder.bind(pictures.get(position));

    }

    @Override
    public int getItemCount() {
        //if(ShopsContent.receiveBitmap != null) return pictures.size() +1;
        return pictures.size();
    }

    public class ImageItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSeleted;
        public ImageItemViewHolder(View itemView) {
            super(itemView);
            imageViewSeleted=itemView.findViewById(R.id.imageViewPictureSelectedItem);
        }

        public void bind(Picture picture) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).override(200).placeholder(R.drawable.ic_launcher_background);
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_camera)
//                    .error(R.drawable.ic_send)
//                    .priority(Priority.HIGH);
            Glide.with(context)
                    .load(picture.getPath())
                    .apply(options)
                    .into(imageViewSeleted);
        }
    }
}
