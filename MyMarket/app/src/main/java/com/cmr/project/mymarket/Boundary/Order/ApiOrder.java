package com.cmr.project.mymarket.Boundary.Order;

import com.cmr.project.mymarket.RequestModell.OrdersRequestModell;
import com.cmr.project.mymarket.ResponseModel.OrdersResponseModell;
import com.cmr.project.mymarket.ResponseModel.SellerResponseModell;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

//TODO
public interface ApiOrder {
    String BASE_URL = "http://10.0.2.2:8011/orders-ms/";



    @POST("orders")
    Call<OrdersResponseModell> createNewOrder(@Body OrdersRequestModell ordersRequestModell);

    @GET("orders/wareSellerId/{wareSellerId}")
    Call<List<OrdersResponseModell>> getAllOrdersFromWareSellerId(@Path("wareSellerId") String wareSellerId);

    @DELETE("orders/{ordersId}")
    Call<Void> deleteOrders(@Path("ordersId") String wareId);








}
