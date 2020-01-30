package com.cmr.project.mymarket.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Order.ApiClientOrder;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.CarrierOrder;
import com.cmr.project.mymarket.UI.Activities.MailItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class TableOrdersMailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CarrierOrder> ordersItems = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;


    int with_mail_name_ware;
    int with_mail_category_ware;
    int with_mail_subcategory_ware;
    int with_mail_count_ware;
    int with_mail_position_ware;
    int with_mail_market_place;

    public TableOrdersMailAdapter(List<CarrierOrder> ordersItems, Context mContext, int with_mail_name_ware, int with_mail_category_ware, int with_mail_subcategory_ware, int with_mail_count_ware, int with_mail_position_ware, int with_mail_market_place) {
        this.ordersItems = ordersItems;
        this.mContext = mContext;
        this.inflater = inflater;
        this.with_mail_name_ware = with_mail_name_ware;
        this.with_mail_category_ware = with_mail_category_ware;
        this.with_mail_subcategory_ware = with_mail_subcategory_ware;
        this.with_mail_count_ware = with_mail_count_ware;
        this.with_mail_position_ware = with_mail_position_ware;
        this.with_mail_market_place = with_mail_market_place;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.mail_orders_table_view, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.mail_name_ware.setText(ordersItems.get(position).getWareName());
        itemHolder.mail_name_ware.setWidth(with_mail_name_ware);

        itemHolder.mail_count_ware.setText(Integer.toString(ordersItems.get(position).getCount()));
        itemHolder.mail_count_ware.setWidth(with_mail_count_ware);


        itemHolder.mail_category_ware.setText(ordersItems.get(position).getCategory());
        itemHolder.mail_category_ware.setWidth(with_mail_category_ware);


        itemHolder.mail_position_ware.setText(ordersItems.get(position).getPosition());
        itemHolder.mail_position_ware.setWidth(with_mail_position_ware);


        itemHolder.mail_market_place.setText(ordersItems.get(position).getMarketPlace());
        itemHolder.mail_market_place.setWidth(with_mail_market_place);


        itemHolder.mail_subcategory_ware.setText(ordersItems.get(position).getSubCategory());
        itemHolder.mail_subcategory_ware.setWidth(with_mail_subcategory_ware);

    }




    @Override
    public int getItemCount() {
        return ordersItems.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView mail_name_ware;
        public TextView mail_category_ware;
        public TextView mail_count_ware;
        public TextView mail_position_ware;
        public TextView mail_market_place;
        public TextView mail_subcategory_ware;

        public ItemHolder(View itemView) {
            super(itemView);
            mail_name_ware = itemView.findViewById(R.id.mail_name_ware);
            mail_category_ware = itemView.findViewById(R.id.mail_category_ware);
            mail_count_ware = itemView.findViewById(R.id.mail_count_ware);
            mail_position_ware = itemView.findViewById(R.id.mail_position_ware);
            mail_market_place = itemView.findViewById(R.id.mail_market_place);
            mail_subcategory_ware = itemView.findViewById(R.id.mail_subcategory_ware);

        }

    }

}
