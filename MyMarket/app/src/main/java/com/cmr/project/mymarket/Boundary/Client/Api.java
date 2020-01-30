package com.cmr.project.mymarket.Boundary.Client;

import com.cmr.project.mymarket.RequestModell.ClientRequestModel;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.SellerResponseModell;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

//TODO
public interface Api {
    String BASE_URL = "http://10.0.2.2:8011/clients-ms/";

    @GET("clients/phone/{phone}")
    Call<ClientResponseModel> getSpecialClientWithNumber(@Path("phone") String number);

    @POST("clients")
    Call<ClientResponseModel> createClient(@Body ClientRequestModel wareRequest);


    @GET("clients/{clientId}")
    Call<ClientResponseModel> getSpecialClientInfos(@Path("clientId") String clientId);


}
