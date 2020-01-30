package com.cmr.project.mymarket.UI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.InsertWare.ListImageSelectedActivity;
import com.cmr.project.mymarket.UI.Adapters.MyWaresAdapter;
import com.cmr.project.mymarket.UI.Adapters.WaresAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWares extends AppCompatActivity {
    public List<WareResponse> wareResponse;
    public RecyclerView recyclerView;
    public MyWaresAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wares_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAllWareAdded(getActuelUserClientId());


        //add new wares
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyWares.this, ListImageSelectedActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    public void getAllWareAdded(String wareSellerId) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<List<WareResponse>> call = apiWare.getAllWaresFromSpecialSeller(wareSellerId);
        call.enqueue(new Callback<List<WareResponse>>() {
            @Override
            public void onResponse(Call<List<WareResponse>> call, Response<List<WareResponse>> response) {
                List<WareResponse> result = response.body();
                wareResponse = new ArrayList<>();
                wareResponse.addAll(result);
                setViewOfAllSeller(wareResponse);
            }

            @Override
            public void onFailure(Call<List<WareResponse>> call, Throwable t) {
                Log.d("Seller Get Call", "Failed");
            }
        });
    }

    private void setViewOfAllSeller(List<WareResponse> wareResponse) {
        recyclerView = findViewById(R.id.my_wares_recyclerview);
        adapter = new MyWaresAdapter(wareResponse,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }
    public String getActuelUserClientId(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel.getClientId();
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