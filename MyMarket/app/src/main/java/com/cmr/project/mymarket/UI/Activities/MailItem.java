package com.cmr.project.mymarket.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeView();
        getEmailContent(getIntent().getStringExtra("mailId"));
    }

    private void getEmailContent(String mailId) {
        ApiMail apiWare = ApiClientMail.getClient().create(ApiMail.class);
        Call<MailResponse> call = apiWare.getMailContent(mailId);
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                MailResponse results = response.body();
                mailResponse = new MailResponse();
                mailResponse = results;
                setViewContentMail(mailResponse);
                Log.d("Mail get", "gutt");

            }
            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                Log.d("Mail get Call", "Errorr");
            }
        });
    }

    private void setViewContentMail(MailResponse mailResponse) {
        mail_From.append(mailResponse.getNameSender());
        mail_Message.append(mailResponse.getMessage());
        mail_Subject.append(mailResponse.getSubject());
        handleMailCommands(mailResponse.getCarrierOrderResponse());
    }

    private void initializeView() {
        mail_From = findViewById(R.id.mail_From);
        mail_Subject = findViewById(R.id.mail_Subject);
        mail_Message = findViewById(R.id.mail_Message);
        mail_commands_recyclerview = findViewById(R.id.mail_commands_recyclerview);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        return true;
    }

}