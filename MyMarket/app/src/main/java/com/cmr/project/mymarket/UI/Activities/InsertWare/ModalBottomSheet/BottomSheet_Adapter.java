package com.cmr.project.mymarket.UI.Activities.InsertWare.ModalBottomSheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Picture;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class BottomSheet_Adapter  extends RecyclerView.Adapter<BottomSheet_Adapter.BottomSheetImageItemViewHolder> implements ItemTouchHelpergGallery{
    private Context context;
    private List<Picture> pictures;

    public BottomSheet_Adapter(Context context,List<Picture> pictures){
        this.context=context;
        this.pictures=pictures;
    }

    @NonNull
    @Override
    public BottomSheet_Adapter.BottomSheetImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.bottom_sheet_item,parent,false);
        return new BottomSheet_Adapter.BottomSheetImageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheet_Adapter.BottomSheetImageItemViewHolder holder, int position) {
        holder.bind(pictures.get(position));
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        Picture prev = pictures.remove(fromPos);
        pictures.add(toPos>fromPos ? toPos-1 : toPos, prev);
        notifyItemMoved(fromPos,toPos);
    }

    public class BottomSheetImageItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSeleted;
        ImageButton bottomsheet_delete;
        public BottomSheetImageItemViewHolder(View itemView) {
            super(itemView);
            imageViewSeleted=itemView.findViewById(R.id.bottomSheet_img);
            bottomsheet_delete = itemView.findViewById(R.id.bottom_sheet_delete);
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
