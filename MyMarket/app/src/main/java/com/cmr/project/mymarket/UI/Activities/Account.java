package com.cmr.project.mymarket.UI.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmr.project.mymarket.Actuel_User.User;
import com.cmr.project.mymarket.Boundary.Seller.ApiClientSeller;
import com.cmr.project.mymarket.Boundary.Seller.ApiSeller;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.SellerResponseModell;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account extends AppCompatActivity {
    TextView lastname;
    TextView firstname;
    TextView phone;
    TextView adress;
    public static String actuel_user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViewForSeller();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        return true;
    }

    private void setViewForSeller() {

       ClientResponseModel user = getActuelUserApp();

       //to manage authentizierung
       actuel_user_phone = user.getPhone();


        lastname = findViewById(R.id.lastname);
        lastname.append(" "+ user.getLastName());
        firstname = findViewById(R.id.firstname);
        firstname.append(" "+ user.getFirstName());
        phone = findViewById(R.id.phone);
        phone.append(" "+ user.getPhone());
        adress = findViewById(R.id.adress);
        adress.append(" "+ user.getAdress());

    }

    public ClientResponseModel getActuelUserApp(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences  mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel;
    }


}