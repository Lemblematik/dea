package com.cmr.project.mymarket.UI.Activities.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;

import com.cmr.project.mymarket.Actuel_User.User;
import com.cmr.project.mymarket.Boundary.Client.Api;
import com.cmr.project.mymarket.Boundary.Client.ApiClient;
import com.cmr.project.mymarket.MainActivity;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.RequestModell.ClientRequestModel;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registrieren  extends Fragment {
    ClientResponseModel clientResponseModel;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registrieren, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnLogin = getView().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWare(getInfosFromView());
            }
        });
    }

    //POST
    public void createWare(ClientRequestModel clientRequest) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ClientResponseModel> call = api.createClient(clientRequest);
        call.enqueue(new Callback<ClientResponseModel>() {
            @Override
            public void onResponse(Call<ClientResponseModel> call, Response<ClientResponseModel> response) {
                Log.d("Client Post  Call", "Success");
                ClientResponseModel result = response.body();
                clientResponseModel = new ClientResponseModel();
                clientResponseModel = result;
                setViewActuelClient(clientResponseModel);
                startUseOfApp();
            }

            @Override
            public void onFailure(Call<ClientResponseModel> call, Throwable t) {
                Log.d("Client Post  Call", "Errorr");
            }
        });
    }

    private void setViewActuelClient(ClientResponseModel clientResponseModel) {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("saveUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(clientResponseModel);
        prefsEditor.putString("actuel_user", json);
        prefsEditor.apply();
    }

    private void startUseOfApp() {
        startActivity(new Intent(this.getContext(), MainActivity.class));
    }

    public ClientRequestModel getInfosFromView(){
        ClientRequestModel clientRequestModel = new ClientRequestModel();

        TextInputEditText lastname_registrate = getView().findViewById(R.id.lastname_registrate);
        clientRequestModel.setLastName(lastname_registrate.getText().toString());
        TextInputEditText firstname_registrate = getView().findViewById(R.id.firstname_registrate);
        clientRequestModel.setFirstName(firstname_registrate.getText().toString());
        TextInputEditText phone_registrate = getView().findViewById(R.id.phone_registrate);
        clientRequestModel.setPhone(phone_registrate.getText().toString());
        TextInputEditText adress_registrate = getView().findViewById(R.id.adress_registrate);
        clientRequestModel.setAdress(adress_registrate.getText().toString());
        return clientRequestModel;

    }

}
