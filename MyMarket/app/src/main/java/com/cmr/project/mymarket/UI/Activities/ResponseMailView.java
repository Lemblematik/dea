package com.cmr.project.mymarket.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cmr.project.mymarket.Boundary.Mail.ApiClientMail;
import com.cmr.project.mymarket.Boundary.Mail.ApiMail;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.RequestModell.MailRequest;
import com.cmr.project.mymarket.ResponseModel.CarrierOrder;
import com.cmr.project.mymarket.ResponseModel.MailResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//should receive senderId, receiverId, nameSenderr


public class ResponseMailView extends AppCompatActivity {
    TextView mail_From;
    EditText corb_mail_subject;
    EditText corb_mail_message;
    Button sendOrdersCarrierBtn_response;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_mail_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialiseView();

        //Set sender
        mail_From.append(getIntent().getStringExtra("nameSender"));
        String mailId = getIntent().getStringExtra("mailId");

        //set Mail Request


        sendOrdersCarrierBtn_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MailRequest mailRequest = getMailRequest();
                sendEmailToSender(mailRequest);
                startActivity(new Intent(ResponseMailView.this, Mail.class));
            }
        });


    }

    private MailRequest getMailRequest() {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject(corb_mail_subject.getText().toString());
        mailRequest.setSenderId(getIntent().getStringExtra("senderId"));
        mailRequest.setReceiverId(getIntent().getStringExtra("receiverId"));
        mailRequest.setMessage(corb_mail_message.getText().toString());
        mailRequest.setDate("date");
        mailRequest.setNameSender(getIntent().getStringExtra("nameSender"));
        mailRequest.setCarrierOrderRespons(new ArrayList<CarrierOrder>());
        return mailRequest;
    }

    private void sendEmailToSender(MailRequest mailRequest) {
        //TODO  wareSellerName in responseWare-- wareapi
        ApiMail api = ApiClientMail.getClient().create(ApiMail.class);
        Call<MailResponse> call = api.createNewMail(mailRequest);
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                Log.d("Mail Post Call", "gut");
                Toast.makeText(ResponseMailView.this, "Mail is sended to " + getIntent().getStringExtra("nameSender"), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                Log.d("Mail Post Call", "Errorr");
            }
        });

    }

    private void initialiseView() {
        mail_From = findViewById(R.id.mailTo_response);
        corb_mail_subject = findViewById(R.id.corb_mail_subject_response);
        corb_mail_message = findViewById(R.id.corb_mail_message_response);
        sendOrdersCarrierBtn_response = findViewById(R.id.sendOrdersCarrierBtn_response);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        return true;
    }

    private void sendEmailToCarrier(MailRequest mailRequest) {
        //TODO  wareSellerName in responseWare-- wareapi
        ApiMail api = ApiClientMail.getClient().create(ApiMail.class);
        Call<MailResponse> call = api.createNewMail(mailRequest);
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                Log.d("Mail Post Call", "gut");
                Toast.makeText(ResponseMailView.this, "Mail is sended to " + getIntent().getStringExtra("nameSender"), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                Log.d("Mail Post Call", "Errorr");
            }
        });

    }


}