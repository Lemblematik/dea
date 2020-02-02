package com.cmr.project.mymarket.Boundary.Mail;

import com.cmr.project.mymarket.RequestModell.MailRequest;
import com.cmr.project.mymarket.ResponseModel.MailResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiMail {
    String BASE_URL = "http://10.0.2.2:8011/mails-ms/";



    @POST("mails")
    Call<MailResponse> createNewMail(@Body MailRequest mailRequest);

    @GET("mails/receivers/{receiverId}")
    Call<List<MailResponse>> getAllMyMails(@Path("receiverId") String receiverId);

    @DELETE("mails/{receiverId}/{mailId}")
    Call<Void> deleteMyMailFromReceiver(@Path("receiverId") String receiverId,@Path("mailId") String mailId);

    @DELETE("mails/{receiverId}/{mailId}")
    Call<Void> deleteMyMailFromSender(@Path("receiverId") String receiverId,@Path("mailId") String mailId);

    @GET("mails/{mailId}")
    Call<MailResponse> getMailContent(@Path("mailId") String mailId);







}
