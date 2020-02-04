package com.cmr.project.mymarket.UI.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Carrier.ApiCarrier;
import com.cmr.project.mymarket.Boundary.Carrier.ApiClientCarrier;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.CarrierResponseModel;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.UI.Adapters.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post extends AppCompatActivity {
    RecyclerView recyclerView;
    PostAdapter adapter;
    List<CarrierResponseModel> carrierResponses;
    boolean isCheck;
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
                if(checkIfUserAlreadyAsCarrier(getActuelUserClientId())){
                    openDialogToInformAccountAlready();
                }else{
                    becomeCarrier();
                }

            }
        });
    }

    private void openDialogToInformAccountAlready() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Infos");
        alert.setMessage("You are already one account as Carrier. Do you want to delete this?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteCarrierAccount(getActuelUserClientId());
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Post.this, "Your account is not deleted", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    private void deleteCarrierAccount(String carrierId) {
        ApiCarrier api = ApiClientCarrier.getClient().create(ApiCarrier.class);
        Call<Void> call = api.deleteAccount(carrierId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Carrier Delete Call", "Succeed");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Carrier Delete Call", "Failed");
            }
        });
    }

    private boolean checkIfUserAlreadyAsCarrier(String clientId) {
        ApiCarrier api = ApiClientCarrier.getClient().create(ApiCarrier.class);
        Call<CarrierResponseModel> call = api.getCarrier(clientId);
        call.enqueue(new Callback<CarrierResponseModel>() {
            @Override
            public void onResponse(Call<CarrierResponseModel> call, Response<CarrierResponseModel> response) {
                CarrierResponseModel result = response.body();
                if (result == null){
                    isCheck = false;
                }else{
                    isCheck = true;
                }
            }

            @Override
            public void onFailure(Call<CarrierResponseModel> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });
        return isCheck;
    }

    public String getActuelUserClientId(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel.getClientId();
    }

    private void becomeCarrier() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Conditions to be the next carrier");
        alert.setMessage("The first and important condition is : you have to know all shops position about the market like an \"Apacheur\", so that you can help the client to make the shop");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Post.this, AddNewCarrier.class));
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Post.this, "You are not carrier", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
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
