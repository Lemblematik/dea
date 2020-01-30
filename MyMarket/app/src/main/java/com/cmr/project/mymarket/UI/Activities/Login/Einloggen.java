package com.cmr.project.mymarket.UI.Activities.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cmr.project.mymarket.Actuel_User.User;
import com.cmr.project.mymarket.Boundary.Client.Api;
import com.cmr.project.mymarket.Boundary.Client.ApiClient;
import com.cmr.project.mymarket.MainActivity;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.UI.UI_Model.DrawerItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Einloggen  extends Fragment {
    ClientResponseModel clientResponseModel;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.einloggen, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextInputEditText number_phone = getView().findViewById(R.id.number_phone);
        Button btnLogin = getView().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClientInfos(number_phone.getText().toString());
                startUseOfApp(number_phone.getText().toString());
            }
        });





    }

    private void startUseOfApp(String s) {
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.putExtra("userNbr",s);
        startActivity(intent);
    }

    public void getClientInfos (String clientNumber) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ClientResponseModel> call = api.getSpecialClientWithNumber(clientNumber);
        call.enqueue(new Callback<ClientResponseModel>() {
            @Override
            public void onResponse(Call<ClientResponseModel> call, Response<ClientResponseModel> response) {
                ClientResponseModel result = response.body();
                clientResponseModel = new ClientResponseModel();
                clientResponseModel = result;
                setViewActuelClient(clientResponseModel);
            }

            @Override
            public void onFailure(Call<ClientResponseModel> call, Throwable t) {
                Log.d("Client login Get Call", "Failed");
            }
        });
    }

    private void setViewActuelClient(ClientResponseModel clientResponseModel) {


        SharedPreferences  mPrefs = this.getActivity().getSharedPreferences("saveUser",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(clientResponseModel);
        prefsEditor.putString("actuel_user", json);
        prefsEditor.apply();
    }




}


