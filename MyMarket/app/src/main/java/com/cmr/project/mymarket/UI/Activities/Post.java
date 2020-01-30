package com.cmr.project.mymarket.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Carrier.ApiCarrier;
import com.cmr.project.mymarket.Boundary.Carrier.ApiClientCarrier;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.CarrierResponseModel;
import com.cmr.project.mymarket.UI.Adapters.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post extends AppCompatActivity {
    RecyclerView recyclerView;
    PostAdapter adapter;
    List<CarrierResponseModel> carrierResponses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getAllCarriers("Marche Biyem-assi");

        addNewCarrier();

    }

    private void addNewCarrier() {
        FloatingActionButton fab = findViewById(R.id.fabCarrier);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Post.this, AddNewCarrier.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        return true;
    }



    private void handleCartView(List<CarrierResponseModel> carrierResponses) {
        recyclerView = findViewById(R.id.post_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new PostAdapter(carrierResponses,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void getAllCarriers(String marketName){
        ApiCarrier apiWare = ApiClientCarrier.getClient().create(ApiCarrier.class);
        Call<List<CarrierResponseModel>> call = apiWare.getCarriers(marketName);
        call.enqueue(new Callback<List<CarrierResponseModel>>() {
            @Override
            public void onResponse(Call<List<CarrierResponseModel>> call, Response<List<CarrierResponseModel>> response) {
                List<CarrierResponseModel> results = response.body();
                carrierResponses = new ArrayList<>();
                carrierResponses.addAll(results);
                handleCartView(results);
                Log.d("carrier get", "gutt");

            }

            @Override
            public void onFailure(Call<List<CarrierResponseModel>> call, Throwable t) {
                Log.d("Carriers Call", "Errorr");
            }
        });
    }





}
