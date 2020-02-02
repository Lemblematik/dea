package com.cmr.project.mymarket.UI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.Boundary.Mail.ApiClientMail;
import com.cmr.project.mymarket.Boundary.Mail.ApiMail;
import com.cmr.project.mymarket.Boundary.Order.ApiClientOrder;
import com.cmr.project.mymarket.Boundary.Order.ApiOrder;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.RequestModell.MailRequest;
import com.cmr.project.mymarket.RequestModell.OrdersRequestModell;
import com.cmr.project.mymarket.ResponseModel.CarrierOrder;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.MailResponse;
import com.cmr.project.mymarket.ResponseModel.OrdersResponseModell;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Adapters.CartAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Corb_header extends AppCompatActivity {
    public List<WareResponse> wareResponses;
    RecyclerView recyclerView;
    CartAdapter adapter;
    TextInputEditText corb_date;
    public String resultDate;
    public static RadioGroup radioGroup;

    //For mail
    List<CarrierOrder> carrierOrders;
    MailRequest mailRequest;
    EditText corb_mail_subject;
    EditText corb_mail_message;

    String carrierId;


    public static EditText carrier_infos;
    public static LinearLayout livraison_linearlayout;




    private void initialiseView() {
        corb_date = findViewById(R.id.corb_date);
        corb_mail_subject = findViewById(R.id.corb_mail_subject);
        corb_mail_message = findViewById(R.id.corb_mail_message);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corb_layout);
        //Linearlayout
        livraison_linearlayout = findViewById(R.id.carrier_input);
        carrier_infos = findViewById(R.id.carrier_infos);

        carrierId = getIntent().getStringExtra("carrier_id");
        if(carrierId != null ){
            livraison_linearlayout.setVisibility(View.VISIBLE);
            carrier_infos.setText(getIntent().getStringExtra("carrier_infos"));
        }
        initialiseView();


        handleCarrier();
        editHandleCarrier();


        final ClientResponseModel clientResponseModel = getInfosForUserApp();
        getAllWaresInCart(clientResponseModel.getClientId());



        //handle btn send
        Button btn = findViewById(R.id.sendOrdersBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resultDate = corb_date.getText().toString();
                for(WareResponse wareResponse: wareResponses){
                    OrdersRequestModell ordersRequestModell = new OrdersRequestModell();
                    ordersRequestModell.setNameClient(clientResponseModel.getFirstName() + " " + clientResponseModel.getLastName());
                    ordersRequestModell.setNameProduct(wareResponse.getName());
                    ordersRequestModell.setNumberProduct(wareResponse.getCount());
                    ordersRequestModell.setPhoneNbr(clientResponseModel.getPhone());
                    ordersRequestModell.setRecoveryDate(resultDate);
                    ordersRequestModell.setWareSellerId(wareResponse.getWareSellerId());

                    //FOR POST
                    CarrierOrder carrierOrder = new CarrierOrder();
                    carrierOrder.setWareName(wareResponse.getName());
                    carrierOrder.setCategory(wareResponse.getCategory());
                    carrierOrder.setMarketPlace(wareResponse.getMarketPlace());
                    carrierOrder.setPosition(wareResponse.getMarketPosition());
                    carrierOrder.setNameSeller(clientResponseModel.getFirstName() + " " + clientResponseModel.getLastName());


                    carrierOrders.add(carrierOrder);
                    createOrders(ordersRequestModell);
                }
                restartCorbView();
            }
        });

        //hdle button send mail
        Button btnSendEmail = findViewById(R.id.sendOrdersCarrierBtn);

        carrierOrders = new ArrayList<>();
        mailRequest = new MailRequest();

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDate = corb_date.getText().toString();
                for(WareResponse wareResponse: wareResponses){
                    //FOR POST
                    CarrierOrder carrierOrder = new CarrierOrder();
                    carrierOrder.setWareName(wareResponse.getName());
                    carrierOrder.setCategory(wareResponse.getCategory());
                    carrierOrder.setMarketPlace(wareResponse.getMarketPlace());
                    carrierOrder.setPosition(wareResponse.getMarketPosition());
                    carrierOrder.setNameSeller(wareResponse.getWareSellerName());
                    carrierOrders.add(carrierOrder);

                }
                mailRequest.setCarrierOrderRespons(carrierOrders);
                mailRequest.setDate(resultDate);
                mailRequest.setMessage(corb_mail_message.getText().toString());
                mailRequest.setNameSender(clientResponseModel.getFirstName() + " " + clientResponseModel.getLastName());
                mailRequest.setSubject(corb_mail_subject.getText().toString());

                //get id from carrier
                mailRequest.setReceiverId(carrierId);

                sendEmailToCarrier(mailRequest);
            }
        });



    }

    private void sendEmailToCarrier(MailRequest mailRequest) {
        //TODO  wareSellerName in responseWare-- wareapi
        ApiMail api = ApiClientMail.getClient().create(ApiMail.class);
        Call<MailResponse> call = api.createNewMail(mailRequest);
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                Log.d("Mail Post Call", "gut");
                Toast.makeText(Corb_header.this, "Mail is sended to " + getIntent().getStringExtra("carrier_infos"), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                Log.d("Mail Post Call", "Errorr");
            }
        });

    }


    private void createOrders(OrdersRequestModell ordersRequestModell) {
        ApiOrder api = ApiClientOrder.getClient().create(ApiOrder.class);
        Call<OrdersResponseModell> call = api.createNewOrder(ordersRequestModell);
        call.enqueue(new Callback<OrdersResponseModell>() {
            @Override
            public void onResponse(Call<OrdersResponseModell> call, Response<OrdersResponseModell> response) {
                Log.d("Order Post Call", "gut");

            }

            @Override
            public void onFailure(Call<OrdersResponseModell> call, Throwable t) {
                Log.d("Order Post Call", "Errorr");
            }
        });
    }


    public void getAllWaresInCart(String sellerId) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<List<WareResponse>> call = apiWare.getWaresInCart(sellerId);
        call.enqueue(new Callback<List<WareResponse>>() {
            @Override
            public void onResponse(Call<List<WareResponse>> call, Response<List<WareResponse>> response) {
                List<WareResponse> results = response.body();
                wareResponses = new ArrayList<>();
                wareResponses.addAll(results);
                handleCartView(wareResponses);
            }
            @Override
            public void onFailure(Call<List<WareResponse>> call, Throwable t) {
                Log.d("All Wares In Cart Call", "Errorr");
            }
        });



    }

    private void handleCartView(List<WareResponse> wareResponses) {
        recyclerView = findViewById(R.id.cart_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new CartAdapter(wareResponses,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void restartCorbView(){
        corb_date.setVisibility(View.GONE);
        startActivity(new Intent(this, Corb_header.class));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            recyclerView=null;
            adapter=null;
            Runtime.getRuntime().gc();
        }
        Log.d("destroy","destroy_______________________");
    }

    public ClientResponseModel getInfosForUserApp(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel;
    }

    public void handleCarrier(){
        radioGroup = (RadioGroup) findViewById(R.id.delivery_radio_btn);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                if(index == 0){
                    // Toast.makeText(ListImageSelectedActivity.this, "Open ", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Corb_header.this, SeeCarrier.class);
                    startActivity(intent1);
                }

            }
        });
    }

    public void editHandleCarrier(){
        final RadioButton radioButton = findViewById(R.id.no_btn);
        Button btnClear = findViewById(R.id.clear_btn);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                livraison_linearlayout.setVisibility(View.GONE);
                radioButton.setChecked(true);
            }
        });

        Button btnRdit = findViewById(R.id.edit_btn);
        btnRdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLivraison = new Intent(Corb_header.this, SeeCarrier.class);
                startActivity(intentLivraison);
            }
        });
    }

}
