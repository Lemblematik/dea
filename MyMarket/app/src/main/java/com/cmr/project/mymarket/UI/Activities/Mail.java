package com.cmr.project.mymarket.UI.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Carrier.ApiCarrier;
import com.cmr.project.mymarket.Boundary.Carrier.ApiClientCarrier;
import com.cmr.project.mymarket.Boundary.Mail.ApiClientMail;
import com.cmr.project.mymarket.Boundary.Mail.ApiMail;
import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.MailResponse;
import com.cmr.project.mymarket.UI.Adapters.MailAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mail extends AppCompatActivity {
    RecyclerView recyclerView;
    MailAdapter adapter;
    List<MailResponse> mailResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_mail);

        getAllEmail(getActuelUserClientId());
    }



    private void setViewMail(List<MailResponse> mailResponse) {
        recyclerView = findViewById(R.id.mail_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new MailAdapter(mailResponse,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void getAllEmail(String carrierId){
        ApiMail apiWare = ApiClientMail.getClient().create(ApiMail.class);
        Call<List<MailResponse>> call = apiWare.getAllMyMails(carrierId);
        call.enqueue(new Callback<List<MailResponse>>() {
            @Override
            public void onResponse(Call<List<MailResponse>> call, Response<List<MailResponse>> response) {
                List<MailResponse> results = response.body();
                mailResponse = new ArrayList<>();
                mailResponse.addAll(results);
                setViewMail(results);
                Log.d("Mail get", "gutt");
            }
            @Override
            public void onFailure(Call<List<MailResponse>> call, Throwable t) {
                Log.d("Mail get Call", "Errorr");
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
}