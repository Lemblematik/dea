package com.cmr.project.mymarket.UI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cmr.project.mymarket.Boundary.Carrier.ApiCarrier;
import com.cmr.project.mymarket.Boundary.Carrier.ApiClientCarrier;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.RequestModell.CarrierRequestModell;
import com.cmr.project.mymarket.ResponseModel.CarrierResponseModel;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Adapter.GalleryItemAdapter;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Adapter.ImageSelectedAdapter;
import com.cmr.project.mymarket.UI.Activities.InsertWare.InsertNewWare;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Picture;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNewCarrier extends AppCompatActivity {
    Spinner spinner_market;
    Spinner spinner_function;
    RecyclerView recyclerViewImageSelected;
    ImageSelectedAdapter adapter;

    public TextInputEditText lastname_registrate;
    public  TextInputEditText firstname_registrate;
    public  TextInputEditText phone_registrate;
    public  TextInputEditText old_carrier ;
    public  TextInputEditText position_registrate;
    public  TextInputEditText  owndescription_registrate;
    public CheckBox checkBox_condition;
    public EditText service_price;



    public static boolean isCallFromCarrier = false;
    public void initialiseView(){
        checkBox_condition = findViewById(R.id.checkBox_condition_carrier);
        lastname_registrate  = findViewById(R.id.lastname_registrate);
        firstname_registrate = findViewById(R.id.firstname_registrate);
        phone_registrate = findViewById(R.id.phone_registrate);
        old_carrier = findViewById(R.id.old_carrier);
        position_registrate = findViewById(R.id.position_registrate);
        owndescription_registrate = findViewById(R.id.owndescription_registrate);
        service_price = findViewById(R.id.service_price);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_carrier);

        handleSpinnerMarketName();
        initialiseView();
        handleSpinnerCarrier();



        final Intent intent = getIntent();
        final List<Picture> picturesSelected = intent.getParcelableArrayListExtra("listpicture");
        //change layout
        if(picturesSelected != null){

            recyclerViewImageSelected = findViewById(R.id.recyclerViewSelected_carrier);
            RelativeLayout iconDownloadLayout = findViewById(R.id.foto_download_layout_carrier);
            RelativeLayout iconFotoAfterClickedLayout = findViewById(R.id.head_foto_carrier);
            iconDownloadLayout.setVisibility(View.GONE);
            iconFotoAfterClickedLayout.setVisibility(View.VISIBLE);
            recyclerViewImageSelected.setHasFixedSize(true);
            adapter = new ImageSelectedAdapter(this,picturesSelected);
            recyclerViewImageSelected.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            recyclerViewImageSelected.setAdapter(adapter);

            Button btn_bearbeiten = findViewById(R.id.foto_bearbeiten_carrier);
            btn_bearbeiten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_gallery = new Intent(AddNewCarrier.this, InsertNewWare.class);
                    isCallFromCarrier = true;
                    startActivity(intent_gallery);
                }
            });

        }


    }

    //handle button foto
    public void clickUploadCarrier(View view){
        //check run time permission
        Intent intent1 = new Intent(this, InsertNewWare.class);
        isCallFromCarrier = true;
        startActivity(intent1);
    }

    public List<String> getAllImages(){
        List<String> results = new ArrayList<>();

        for(Picture picture: GalleryItemAdapter.getAllPictureSelected()){
            results.add(picture.getPath());
        }
        List<String> encodedBase64Images = new ArrayList<>();
        for (String path: results){
            File imagefile = new File(path);
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(imagefile);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b = baos.toByteArray();
            String encImage = Base64.encodeToString(b, Base64.DEFAULT);
            encodedBase64Images.add(encImage);
        }
        return encodedBase64Images;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            recyclerViewImageSelected=null;
            adapter=null;
            Runtime.getRuntime().gc();
        }
        Log.d("destroy","destroyy new Carrier");
    }

    public void setInputFeldEmpty(){
        lastname_registrate.setText("");
        firstname_registrate.setText("");
        phone_registrate.setText("");
        old_carrier.setText("");
        position_registrate.setText("");
        owndescription_registrate.setText("");
        service_price.setText("");
    }
    public String getActuelUserClientId(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel.getClientId();
    }

    public CarrierRequestModell getInputField(){
        final CarrierRequestModell carrierRequest = new CarrierRequestModell();
        carrierRequest.setCarrierId(getActuelUserClientId());
        carrierRequest.setFirstName(firstname_registrate.getText().toString());
        carrierRequest.setLastName(lastname_registrate.getText().toString());
        carrierRequest.setMarketPosition(position_registrate.getText().toString());
        carrierRequest.setMarketName(spinner_market.getSelectedItem().toString());
        carrierRequest.setOwnDescription(owndescription_registrate.getText().toString());
        carrierRequest.setPhoneNbr(phone_registrate.getText().toString());
        carrierRequest.setPrice(service_price.getText().toString());
        carrierRequest.setAge(old_carrier.getText().toString());
        carrierRequest.setFunction(spinner_function.getSelectedItem().toString());
        //for pictures
        carrierRequest.setFoto(getAllImages().get(0));

        //checkbox
        this.checkBox_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(checkBox_condition.isChecked()){
                    carrierRequest.setRightInfos(true);
                }else{
                    carrierRequest.setRightInfos(false);
                }
            }
        });
        return carrierRequest;
    }

    public void saveNewCarrier(View view){
        createWare(getInputField());
        setInputFeldEmpty();
    }
    public void createWare(CarrierRequestModell wareRequest) {
        ApiCarrier api = ApiClientCarrier.getClient().create(ApiCarrier.class);
        Call<CarrierResponseModel> call = api.createCarrier(wareRequest);
        call.enqueue(new Callback<CarrierResponseModel>() {
            @Override
            public void onResponse(Call<CarrierResponseModel> call, Response<CarrierResponseModel> response) {
                Log.d("Post Carrier call", "gut");

            }

            @Override
            public void onFailure(Call<CarrierResponseModel> call, Throwable t) {
                Log.d("Carrier post Call", "Errorr");
            }
        });
    }



    public void handleSpinnerMarketName(){
        ArrayList<String> listDropdownItems = new ArrayList<>();
        listDropdownItems.add(getResources().getString(R.string.marche_mokolo));
        listDropdownItems.add(getResources().getString(R.string.marche_mfoundi));
        listDropdownItems.add(getResources().getString(R.string.marche_central));
        listDropdownItems.add(getResources().getString(R.string.marche_mvog_bi));
        listDropdownItems.add(getResources().getString(R.string.marche_mvog_betsi));
        listDropdownItems.add(getResources().getString(R.string.marche_de_huitieme));
        listDropdownItems.add(getResources().getString(R.string.marche_de_nsam));
        listDropdownItems.add(getResources().getString(R.string.marche_de_nkol_eton));
        listDropdownItems.add(getResources().getString(R.string.marche_de_mendong));
        listDropdownItems.add(getResources().getString(R.string.marche_de_melen));
        listDropdownItems.add(getResources().getString(R.string.marche_de_ekounou));
        listDropdownItems.add(getResources().getString(R.string.marche_de_biyem_assi));

        ArrayAdapter<String> adapterDropdown = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listDropdownItems);
        spinner_market = findViewById(R.id.market_name_dropdown);
        spinner_market.setAdapter(adapterDropdown);
    }

    public void handleSpinnerCarrier(){
        ArrayList<String> listDropdownItems = new ArrayList<>();
        listDropdownItems.add(getResources().getString(R.string.apacheur));
        listDropdownItems.add(getResources().getString(R.string.pousseur));
        listDropdownItems.add(getResources().getString(R.string.brouette));
        listDropdownItems.add(getResources().getString(R.string.livreur));


        ArrayAdapter<String> adapterDropdown = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listDropdownItems);
        spinner_function = findViewById(R.id.function_carrier_dropdown);
        spinner_function.setAdapter(adapterDropdown);
    }

}