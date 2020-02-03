package com.cmr.project.mymarket.UI.Activities.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Adapters.WaresAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {
    public List<WareResponse> wareResponses;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homes, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllWaresFromCategory(getMarketNameChoosen(),getResources().getString(R.string.nav_home));
    }

    public void getAllWaresFromCategory(String marketPlace, String category) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<List<WareResponse>> call = apiWare.getAllWaresFromCategory(marketPlace,category);
        call.enqueue(new Callback<List<WareResponse>>() {
            @Override
            public void onResponse(Call<List<WareResponse>> call, Response<List<WareResponse>> response) {
                List<WareResponse> results = response.body();
                wareResponses = new ArrayList<>();
                wareResponses.addAll(results);
                handleHomeView(wareResponses);

            }

            @Override
            public void onFailure(Call<List<WareResponse>> call, Throwable t) {
                Log.d("Ware category Call", "Errorr");
            }
        });
    }
    public void handleHomeView(List<WareResponse> wareResponses){
        RecyclerView recyclerView = getView().findViewById(R.id.content_main_recyclerview);
        Log.d("RESULT", wareResponses.toString());
        WaresAdapter adapter = new WaresAdapter(wareResponses,this.getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),4));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    public String getMarketNameChoosen(){
        SharedPreferences mPrefs = getActivity().getSharedPreferences("saveMarketName", Context.MODE_PRIVATE);
        String marketName = mPrefs.getString("marketName", "");
        if(marketName == null){
            return getResources().getString(R.string.marche_de_biyem_assi);
        }
        return marketName;
    }





}
