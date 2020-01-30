package com.cmr.project.mymarket.UI.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Order.ApiClientOrder;
import com.cmr.project.mymarket.Boundary.Order.ApiOrder;
import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.OrdersResponseModell;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Adapters.OrdersAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrders extends AppCompatActivity {
    List<OrdersResponseModell> ordersResponse;
    RecyclerView recyclerView;
    OrdersAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);


        getAllOrders(getActuelUserClientId());
    }


    public void getAllOrders(String sellerId) {
        ApiOrder apiWare = ApiClientOrder.getClient().create(ApiOrder.class);
        Call<List<OrdersResponseModell>> call = apiWare.getAllOrdersFromWareSellerId(sellerId);
        call.enqueue(new Callback<List<OrdersResponseModell>>() {
            @Override
            public void onResponse(Call<List<OrdersResponseModell>> call, Response<List<OrdersResponseModell>> response) {
                List<OrdersResponseModell> result = response.body();
                Log.d("Orders", result.toString());
                ordersResponse = new ArrayList<>();
                ordersResponse.addAll(result);
                setViewOfTableOrder(ordersResponse);
            }

            @Override
            public void onFailure(Call<List<OrdersResponseModell>> call, Throwable t) {
                Log.d("Orders Get Call", "Failed");
            }
        });
    }

    public String getActuelUserClientId(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel.getClientId();
    }

        private void setViewOfTableOrder(List<OrdersResponseModell> ordersResponses) {

        //Get the right width size
        TextView orders_name_client = findViewById(R.id.orders_name_client);
        int with_orders_name_client = orders_name_client.getMeasuredWidth();

        TextView orders_name = findViewById(R.id.orders_name);
        int with_orders_name = orders_name.getMeasuredWidth();

        TextView orders_count_product = findViewById(R.id.orders_count_product);
        int with_orders_count_product = orders_count_product.getMeasuredWidth();

        TextView orders_date_recuperation = findViewById(R.id.orders_date_recuperation);
        int with_orders_date_recuperation = orders_date_recuperation.getMeasuredWidth();

        TextView orders_already_taken = findViewById(R.id.orders_already_taken);
        int with_orders_already_taken = orders_already_taken.getMeasuredWidth();

        //orders_phone
        TextView orders_phone = findViewById(R.id.orders_phone);
        int with_orders_phone = orders_phone.getMeasuredWidth();



        recyclerView = findViewById(R.id.orders_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new OrdersAdapter(ordersResponses,this,with_orders_name_client,with_orders_name,with_orders_count_product,with_orders_date_recuperation,with_orders_already_taken,with_orders_phone);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            recyclerView=null;
            adapter=null;
            Runtime.getRuntime().gc();
        }
        Log.d("destroy","destroy_______________________");
    }


}
