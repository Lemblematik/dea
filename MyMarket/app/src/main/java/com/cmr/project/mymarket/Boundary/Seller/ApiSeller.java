package com.cmr.project.mymarket.Boundary.Seller;

import com.cmr.project.mymarket.RequestModell.WareRequest;
import com.cmr.project.mymarket.ResponseModel.SellerResponseModell;
import com.cmr.project.mymarket.ResponseModel.WareResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

//TODO
public interface ApiSeller {
    String BASE_URL = "http://10.0.2.2:8011/sellers-ms/";



    @GET("sellers/{sellerId}")
    Call<SellerResponseModell> getSpecialSeller(@Path("sellerId") String sellerId);





}
