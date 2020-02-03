package com.cmr.project.mymarket.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Mail.ApiClientMail;
import com.cmr.project.mymarket.Boundary.Mail.ApiMail;

import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.CarrierOrder;
import com.cmr.project.mymarket.ResponseModel.MailResponse;
import com.cmr.project.mymarket.UI.Adapters.TableOrdersMailAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MailItem extends AppCompatActivity {
    TextView mail_From;

    TextView mail_Subject;
    TextView mail_Message;
    MailResponse mailResponse;
    RecyclerView mail_commands_recyclerview;
    TableOrdersMailAdapter tableOrdersMailAdapter;
    Button answerMsg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_item);

        initializeView();
        final String mailId = getIntent().getStringExtra("mailId");
        getEmailContent(mailId);

    }

    public void getEmailContent(String mailId) {

        ApiMail api = ApiClientMail.getClient().create(ApiMail.class);
        Call<MailResponse> call = api.getMailContent(mailId);
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                MailResponse result = response.body();
                setViewContentMail(result);
                Log.d("Mail Get Call", "Gut");
            }

            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });

    }

    public void setViewContentMail(final MailResponse mailResponse) {
        mail_From.append(mailResponse.getNameSender());
        mail_Message.append(mailResponse.getMessage());
        mail_Subject.append(mailResponse.getSubject());
        //handleMailCommands(mailResponse.getCarrierOrderResponse());


        answerMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MailItem.this,ResponseMailView.class);
                intent.putExtra("senderId",mailResponse.getSenderId());
                intent.putExtra("receiverId",mailResponse.getReceiverId());
                intent.putExtra("nameSender",mailResponse.getNameSender());
                startActivity(intent);
            }
        });


    }

    private void initializeView() {
        mail_From = findViewById(R.id.mail_From);
        mail_Subject = findViewById(R.id.mail_Subject);
        mail_Message = findViewById(R.id.mail_Message);
        //mail_commands_recyclerview = findViewById(R.id.mail_commands_recyclerview);
        answerMsg = findViewById(R.id.answerMsg);
    }


    private void handleMailCommands(List<CarrierOrder> carrierOrders){

        //Get the right width size
        TextView mail_market_place = findViewById(R.id.mail_market_place);
        int with_mail_market_place= mail_market_place.getMeasuredWidth();

        TextView mail_position_ware = findViewById(R.id.mail_position_ware);
        int with_mail_position_ware = mail_position_ware.getMeasuredWidth();

        TextView mail_seller_name = findViewById(R.id.mail_seller_name);
        int with_mail_seller_name = mail_seller_name.getMeasuredWidth();

        TextView mail_subcategory_ware = findViewById(R.id.mail_subcategory_ware);
        int with_mail_subcategory_ware = mail_subcategory_ware.getMeasuredWidth();

        TextView mail_category_ware = findViewById(R.id.mail_category_ware);
        int with_mail_category_ware = mail_category_ware.getMeasuredWidth();

        //orders_phone
        TextView mail_name_ware = findViewById(R.id.mail_name_ware);
        int with_mail_name_ware= mail_name_ware.getMeasuredWidth();





        mail_commands_recyclerview.setHasFixedSize(true);
        tableOrdersMailAdapter = new TableOrdersMailAdapter(carrierOrders,this,with_mail_name_ware,with_mail_category_ware,with_mail_subcategory_ware,with_mail_seller_name,with_mail_position_ware,with_mail_market_place);
        mail_commands_recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mail_commands_recyclerview.setAdapter(tableOrdersMailAdapter);
    }

}