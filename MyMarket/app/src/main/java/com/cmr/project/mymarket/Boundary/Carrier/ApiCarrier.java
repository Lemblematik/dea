package com.cmr.project.mymarket.Boundary.Carrier;

import com.cmr.project.mymarket.RequestModell.CarrierRequestModell;
import com.cmr.project.mymarket.RequestModell.ClientRequestModel;
import com.cmr.project.mymarket.ResponseModel.CarrierResponseModel;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.MailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

//TODO
public interface ApiCarrier {
    String BASE_URL = "http://10.0.2.2:8011/carriers-ms/";



    @POST("carriers")
    Call<CarrierResponseModel> createCarrier(@Body CarrierRequestModell carrierRequestModell);
    @GET("carriers/marketName/{marketName}")
    Call<List<CarrierResponseModel>> getCarriers(@Path("marketName") String marketName);

    @GET("carriers/{carrierId}")
    Call<CarrierResponseModel> getCarrier(@Path("carrierId") String carrierId);

    @DELETE("carriers/{carrierId")
    Call<Void> deleteAccount(@Path("carrierId") String carrierId);


}
