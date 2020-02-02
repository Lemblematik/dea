package com.cmr.project.mymarket.Boundary.Ware;

import com.cmr.project.mymarket.RequestModell.WareRequest;
import com.cmr.project.mymarket.ResponseModel.MailResponse;
import com.cmr.project.mymarket.ResponseModel.WareResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiWare {
    String BASE_URL = "http://10.0.2.2:8011/wares-ms/";

    @GET("wares/all")
    Call<List<WareResponse>> getWares();

    @GET("wares/{wareId}")
    Call<WareResponse> getSpecialWare(@Path("wareId") String wareId);

    @POST("wares")
    Call<WareResponse> createSpecialWare(@Body WareRequest wareRequest);

    @DELETE("wares/{wareId}")
    Call<Void> deleteWare(@Path("wareId") String wareId);

    /*
    @PUT("wares/{wareId}")
    Call<WareResponse> updateWareInfos(@Path("wareId") String wareId, @Body WareRequest wareRequest);
    */

    @PATCH("wares/{wareId}")
    Call<WareResponse> patchWareInfos(@Path("wareId") String wareId, @Body WareResponse wareResponse);

    @GET("wares/carts/{wareSellerId}")
    Call<List<WareResponse>> getWaresInCart(@Path("wareSellerId")String wareSellerId);

    @GET("wares/sellers/{wareSellerId}")
    Call<List<WareResponse>> getAllWaresFromSpecialSeller(@Path("wareSellerId")String wareSellerId);


    @GET("wares/categories/{marketPlace}/{category}")
    Call<List<WareResponse>> getAllWaresFromCategory(@Path("marketPlace") String marketPlace,@Path("category") String category);

    @GET("wares/search/{marketPlace}/{category}/{name}")
    Call<List<WareResponse>> getSearchWare(@Path("marketPlace") String marketPlace,@Path("category") String category,@Path("name") String name);



}
