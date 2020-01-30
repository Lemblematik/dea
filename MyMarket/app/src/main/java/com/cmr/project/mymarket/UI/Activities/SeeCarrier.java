package com.cmr.project.mymarket.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Carrier.ApiCarrier;
import com.cmr.project.mymarket.Boundary.Carrier.ApiClientCarrier;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.CarrierResponseModel;
import com.cmr.project.mymarket.UI.Adapters.PostAdapter;
import com.cmr.project.mymarket.UI.Adapters.SeeCarrierAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeCarrier extends AppCompatActivity {
    List<CarrierResponseModel> carrierResponses;
    RecyclerView recyclerView;
    SeeCarrierAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_carrier);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAllCarriers("Marche Biyem-assi");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        //((RadioButton)Corb_header.radioGroup.getChildAt(1)).setChecked(true);
        return true;
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

            }

            @Override
            public void onFailure(Call<List<CarrierResponseModel>> call, Throwable t) {
                Log.d("All Carriers Call", "Errorr");
            }
        });
    }

    private void handleCartView(List<CarrierResponseModel> carrierResponses) {
        recyclerView = findViewById(R.id.see_carrier_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new SeeCarrierAdapter(carrierResponses,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}