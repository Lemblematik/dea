package com.cmr.project.mymarket.UI.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.cmr.project.mymarket.UI.Activities.Corb_header;
import com.cmr.project.mymarket.UI.Activities.WareItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<WareResponse> waresItems = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;



    public CartAdapter(List<WareResponse> waresItems, Context mContext) {
        this.waresItems = waresItems;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.cart_ware_item_view, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.nameTextView.setText(waresItems.get(position).getName());
        itemHolder.priceTextView.setText(waresItems.get(position).getPrice());
        itemHolder.imageView.setImageResource(R.drawable.logo);
        //itemHolder.imageView.setImageResource(carrierItems.get(position).getIcon());
        itemHolder.cart_ware_name_seller.append(waresItems.get(position).getWareSellerName());



        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, WareItem.class);
                intent.putExtra("wareId",waresItems.get(position).getWareId());
                mContext.startActivity(intent);
            }
        });

        //handle close btn
        itemHolder.delete_ware_in_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waresItems.get(position).setCart(false);
                updateWare(waresItems.get(position),waresItems.get(position).getWareId());
                mContext.startActivity(new Intent(mContext,Corb_header.class));
            }
        });

        //handle add btn
        itemHolder.cart_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waresItems.get(position).setCount(waresItems.get(position).getCount() + 1);
                updateWare(waresItems.get(position),waresItems.get(position).getWareId());
                mContext.startActivity(new Intent(mContext,Corb_header.class));
            }
        });
        //handle minus btn

        itemHolder.cart_minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(waresItems.get(position).getCount()>1){
                    waresItems.get(position).setCount(waresItems.get(position).getCount() - 1);
                    updateWare(waresItems.get(position),waresItems.get(position).getWareId());
                    mContext.startActivity(new Intent(mContext,Corb_header.class));
                }
            }
        });
        //set count
        itemHolder.cart_article_count.setText(Integer.toString(waresItems.get(position).getCount()));


    }

    //Update Wares present in ware
    public void updateWare(WareResponse wareRequest, String wareId) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = apiWare.patchWareInfos(wareId,wareRequest);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                Log.d("Update Ware Call", "gut");
            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Update Ware Call", "failed");
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
        public TextView cart_ware_name_seller;
        public ImageButton delete_ware_in_cart;
        public ImageButton cart_minus_btn;
        public ImageButton cart_add_btn;
        public TextView cart_article_count;
        public ItemHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.cart_ware_name);
            imageView = (ImageView) itemView.findViewById(R.id.cart_ware_img);
            cardView = itemView.findViewById(R.id.cart_group);
            priceTextView = itemView.findViewById(R.id.cart_ware_price);
            cart_ware_name_seller = itemView.findViewById(R.id.cart_ware_name_seller);
            delete_ware_in_cart = itemView.findViewById(R.id.delete_ware_in_cart);
            cart_minus_btn = itemView.findViewById(R.id.cart_minus_btn);
            cart_add_btn = itemView.findViewById(R.id.cart_add_btn);
            cart_article_count = itemView.findViewById(R.id.cart_article_count);
        }

    }
}
