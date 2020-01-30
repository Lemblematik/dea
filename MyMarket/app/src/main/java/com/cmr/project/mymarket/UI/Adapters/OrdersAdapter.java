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
import com.cmr.project.mymarket.Boundary.Order.ApiOrder;
import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.OrdersResponseModell;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.MyOrders;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<OrdersResponseModell> ordersItems = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;


    int with_orders_name_client;
    int with_orders_name;
    int with_orders_count_product;
    int with_orders_date_recuperation;
    int with_orders_already_taken;
    int with_orders_phone;


    public OrdersAdapter(List<OrdersResponseModell> ordersItems, Context mContext, int with_orders_name_client, int with_orders_name, int with_orders_count_product, int with_orders_date_recuperation, int with_orders_already_taken, int with_orders_phone) {
        this.ordersItems = ordersItems;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.with_orders_name_client = with_orders_name_client;
        this.with_orders_name = with_orders_name;
        this.with_orders_count_product = with_orders_count_product;
        this.with_orders_date_recuperation = with_orders_date_recuperation;
        this.with_orders_already_taken = with_orders_already_taken;
        this.with_orders_phone = with_orders_phone;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.orders_item_view, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.orders_name_product.setText(ordersItems.get(position).getNameProduct());
        itemHolder.orders_name_product.setWidth(with_orders_name);

        itemHolder.orders_count_product.setText(Integer.toString(ordersItems.get(position).getNumberProduct()));
        itemHolder.orders_count_product.setWidth(with_orders_count_product);

        //TODO
        itemHolder.orders_name_client.setText(ordersItems.get(position).getNameClient());
        itemHolder.orders_name_client.setWidth(with_orders_name_client);



        itemHolder.orders_date_recuperation.setText(ordersItems.get(position).getRecoveryDate());
        itemHolder.orders_date_recuperation.setWidth(with_orders_date_recuperation);

        //orders_phone_item
        itemHolder.orders_phone_item.setText(ordersItems.get(position).getPhoneNbr());
        itemHolder.orders_phone_item.setWidth(with_orders_phone);

        itemHolder.orders_already_taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOrders(ordersItems.get(position).getOrdersId());
                mContext.startActivity(new Intent(mContext, MyOrders.class));
            }
        });

        itemHolder.orders_already_taken.setWidth(with_orders_already_taken);
    }

    private void deleteOrders(String ordersId) {
        ApiOrder apiOrder = ApiClientOrder.getClient().create(ApiOrder.class);
        Call<Void> call = apiOrder.deleteOrders(ordersId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Delete Order Call", "gut");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Delete Order Call", "failed");
            }
        });
    }


    @Override
    public int getItemCount() {
        return ordersItems.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView orders_name_client;
        public TextView orders_name_product;
        public TextView orders_count_product;
        public TextView orders_date_recuperation;
        public Button orders_already_taken;
        public TextView orders_phone_item;

        public ItemHolder(View itemView) {
            super(itemView);
            orders_name_client = itemView.findViewById(R.id.orders_name_client);
            orders_name_product = itemView.findViewById(R.id.orders_name_product);
            orders_count_product = itemView.findViewById(R.id.orders_count_product);
            orders_date_recuperation = itemView.findViewById(R.id.orders_date_recuperation);
            orders_phone_item = itemView.findViewById(R.id.orders_phone_item);
            orders_already_taken = itemView.findViewById(R.id.orders_already_taken);

        }

    }
}