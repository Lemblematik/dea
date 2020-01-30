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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.CarrierResponseModel;
import com.cmr.project.mymarket.UI.Activities.Corb_header;
import com.cmr.project.mymarket.UI.Activities.Post;
import com.cmr.project.mymarket.UI.Activities.SeeCarrier;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SeeCarrierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<CarrierResponseModel> carrierItems = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;



    public SeeCarrierAdapter(List<CarrierResponseModel> carrierItems, Context mContext) {
        this.carrierItems = carrierItems;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.carrier_item_view, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;

        //Apreciation
        carrierItems.get(position).setAppreciation("todo");


        itemHolder.nameTextView.append(carrierItems.get(position).getLastName());
        itemHolder.carrier_ware_firstname.append(carrierItems.get(position).getFirstName());
        itemHolder.carrier_ware_age.append(carrierItems.get(position).getAge());
        itemHolder.carrier_ware_ownDescription.append(carrierItems.get(position).getOwnDescription());
        itemHolder.carrier_ware_number.append(carrierItems.get(position).getPhoneNbr());
        itemHolder.carrier_ware_marketName.append(carrierItems.get(position).getMarketName());
        itemHolder.carrier_ware_marketName.append(carrierItems.get(position).getMarketName());
        itemHolder.carrier_ware_appreciation.append(carrierItems.get(position).getAppreciation());
        itemHolder.carrier_ware_function.append(carrierItems.get(position).getFunction());

        //image
        loadBitmapByPicasso(mContext,setImagesToaBitMap(carrierItems.get(position).getFoto()),itemHolder.imageView);
        /*
        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Corb_header.class);
                Corb_header.livraison_linearlayout.setVisibility(View.VISIBLE);
                Corb_header.editTextLivraison.setText(carrierItems.get(position).getFirstName() + " " + carrierItems.get(position).getLastName());
                mContext.startActivity(intent);
            }
        });

         */

        itemHolder.choose_carrier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (itemHolder.choose_carrier.isChecked()){
                    Intent intent = new Intent(mContext, Corb_header.class);
                    intent.putExtra("carrier_id",carrierItems.get(position).getCarrierId());
                    intent.putExtra("carrier_infos",carrierItems.get(position).getFirstName() + " " + carrierItems.get(position).getLastName());
                    mContext.startActivity(intent);
                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return carrierItems.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView imageView;
        public CardView cardView;
        public TextView carrier_ware_firstname;
        public TextView carrier_ware_age;
        public TextView carrier_ware_ownDescription;
        public TextView carrier_ware_number;
        public TextView carrier_ware_marketName;
        public TextView carrier_ware_appreciation;
        public TextView carrier_ware_function;
        public CheckBox choose_carrier;

        public ItemHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.carrier_ware_img);
            nameTextView = itemView.findViewById(R.id.carrier_ware_name);
            cardView = itemView.findViewById(R.id.cart_group_carrier);
            carrier_ware_firstname = itemView.findViewById(R.id.carrier_ware_firstname);
            carrier_ware_age = itemView.findViewById(R.id.carrier_ware_age);
            carrier_ware_ownDescription = itemView.findViewById(R.id.carrier_ware_ownDescription);
            carrier_ware_number = itemView.findViewById(R.id.carrier_ware_number);
            carrier_ware_marketName = itemView.findViewById(R.id.carrier_ware_marketName);
            carrier_ware_appreciation = itemView.findViewById(R.id.carrier_ware_appreciation);
            carrier_ware_function = itemView.findViewById(R.id.carrier_ware_function);
            choose_carrier = itemView.findViewById(R.id.choose_carrier);
            choose_carrier.setVisibility(View.VISIBLE);
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
