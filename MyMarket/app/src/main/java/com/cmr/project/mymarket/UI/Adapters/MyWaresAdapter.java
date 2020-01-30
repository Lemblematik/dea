package com.cmr.project.mymarket.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.InsertWare.ListImageSelectedActivity;
import com.cmr.project.mymarket.UI.Activities.MyWares;
import com.cmr.project.mymarket.UI.Activities.WareItem;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWaresAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<WareResponse> waresItems = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;



    public MyWaresAdapter(List<WareResponse> waresItems, Context mContext) {
        this.waresItems = waresItems;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.mywares_item_view, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.nameTextView.setText(waresItems.get(position).getName());
        itemHolder.priceTextView.setText(waresItems.get(position).getPrice() + " FCFA");
        itemHolder.imageView.setImageResource(R.drawable.logo);
        //itemHolder.imageView.setImageResource(carrierItems.get(position).getIcon());
        loadBitmapByPicasso(mContext,setImagesToaBitMap(waresItems.get(position).getFotos().get(0)),itemHolder.imageView);


        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, WareItem.class);
                intent.putExtra("wareId",waresItems.get(position).getWareId());
                mContext.startActivity(intent);
            }
        });



        //delete wares
        itemHolder.my_wares_delete_ware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waresItems.get(position).setCart(false);
                deleteWare(waresItems.get(position).getWareId());
                mContext.startActivity(new Intent(mContext, MyWares.class));
            }
        });

        //update
        itemHolder.ware_update_infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListImageSelectedActivity.class);
                intent.putExtra("wareId",waresItems.get(position).getWareId());
                mContext.startActivity(intent);
            }
        });





    }

    public void deleteWare(String wareId){
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<Void> call = api.deleteWare(wareId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Ware Delete Call", "Gut");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });
    }


    @Override
    public int getItemCount() {
        return waresItems.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView imageView;
        public CardView cardView;
        public TextView priceTextView;
        public ImageButton my_wares_delete_ware;
        public ImageButton ware_update_infos;
        public ItemHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.ware_title);
            imageView = (ImageView) itemView.findViewById(R.id.ware_image);
            cardView = itemView.findViewById(R.id.ware_item);
            priceTextView = itemView.findViewById(R.id.ware_price);
            my_wares_delete_ware = itemView.findViewById(R.id.wares_delete_ware);
            ware_update_infos = itemView.findViewById(R.id.ware_update_infos);
        }

    }

    private void loadBitmapByPicasso(Context pContext, Bitmap pBitmap, ImageView pImageView) {
        try {
            Uri uri = Uri.fromFile(File.createTempFile("temp_file_name", ".jpg", pContext.getCacheDir()));
            OutputStream outputStream = pContext.getContentResolver().openOutputStream(uri);
            pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            Picasso.get().load(uri).into(pImageView);
        } catch (Exception e) {
            Log.e("LoadBitmapByPicasso", e.getMessage());
        }
    }
    private Bitmap setImagesToaBitMap(String fotos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(fotos, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;


    }
}