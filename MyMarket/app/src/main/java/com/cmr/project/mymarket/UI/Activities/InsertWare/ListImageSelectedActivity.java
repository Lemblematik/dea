package com.cmr.project.mymarket.UI.Activities.InsertWare;
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

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.RequestModell.WareRequest;
import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Adapter.GalleryItemAdapter;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Adapter.ImageSelectedAdapter;
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

public class ListImageSelectedActivity  extends AppCompatActivity {
    RecyclerView recyclerViewImageSelected;
    ImageSelectedAdapter adapter;
    WareResponse wareResponse;

    public  EditText insert_name_ware;
    public  EditText insert_price;
    public  EditText description1;
    public  EditText description2 ;
    public  EditText description3;
    public EditText position_market_Ware;
    public Spinner spinner;
    public Spinner spinnerSubCategory;
    public Spinner ware_market_name_dropdown;
    public CheckBox checkBox_condition;


    public void initialiseView(){
        checkBox_condition = findViewById(R.id.checkBox_condition);
        insert_name_ware  = findViewById(R.id.name_ware);
        insert_price = findViewById(R.id.insert_price);
        description1 = findViewById(R.id.description1);
        description2 = findViewById(R.id.description2);
        description3 = findViewById(R.id.description3);
        position_market_Ware = findViewById(R.id.position_market_Ware);
        spinnerSubCategory = findViewById(R.id.ware_subCategory_spinner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image_selected);

        initialiseView();

        fillDropdownItem();

        fillDropdownSubCategory();

        handleSpinnerMarketName();

        final Intent intent = getIntent();
        final List<Picture> picturesSelected = intent.getParcelableArrayListExtra("listpicture");
        //change layout
        if(picturesSelected != null){

            recyclerViewImageSelected = findViewById(R.id.recyclerViewSelected);
            RelativeLayout iconDownloadLayout = findViewById(R.id.foto_download_layout);
            RelativeLayout iconFotoAfterClickedLayout = findViewById(R.id.head_foto);
            iconDownloadLayout.setVisibility(View.GONE);
            iconFotoAfterClickedLayout.setVisibility(View.VISIBLE);
            recyclerViewImageSelected.setHasFixedSize(true);
            adapter = new ImageSelectedAdapter(this,picturesSelected);
            recyclerViewImageSelected.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            recyclerViewImageSelected.setAdapter(adapter);

            Button btn_bearbeiten = findViewById(R.id.foto_bearbeiten);
            btn_bearbeiten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_gallery = new Intent(ListImageSelectedActivity.this, InsertNewWare.class);
                    startActivity(intent_gallery);
                }
            });

        }


        //check if start activity for update
        if(getIntent().getStringExtra("wareId") != null){
            getWareForUpdateInfos(getIntent().getStringExtra("wareId"));
        }
    }

    private void getWareForUpdateInfos(String wareId) {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = api.getSpecialWare(wareId);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                WareResponse result = response.body();
                wareResponse = new WareResponse();
                wareResponse = result;
                setViewForSpecialWare(wareResponse);
            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });
    }

    private void setViewForSpecialWare(WareResponse wareResponse) {
        insert_name_ware.setText(wareResponse.getName());
        description1.setText(wareResponse.getDescription());
        insert_price.setText(wareResponse.getPrice());
        position_market_Ware.setText("");
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
        ware_market_name_dropdown = findViewById(R.id.ware_market_name_dropdown);
        ware_market_name_dropdown.setAdapter(adapterDropdown);
    }


    public void clickUpload(View view){
        //check run time permission
        Intent intent1 = new Intent(this, InsertNewWare.class);
        startActivity(intent1);
    }

    //get Images
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
        Log.d("destroy","destroy_______________________");
    }

    public void setInputFeldEmpty(){
        insert_name_ware.setText("");
        description1.setText("");
        description2.setText("");
        description3.setText("");
        insert_price.setText("");
        position_market_Ware.setText("");

    }

    public WareRequest getInputField(){
        final WareRequest wareRequest = new WareRequest();
        wareRequest.setName(insert_name_ware.getText().toString());
        wareRequest.setPrice(insert_price.getText().toString());
        wareRequest.setCategory(spinner.getSelectedItem().toString());
        wareRequest.setSubCategory(spinnerSubCategory.getSelectedItem().toString());
        wareRequest.setMarketPlace(ware_market_name_dropdown.getSelectedItem().toString());

        //description
        String desc1 = "-> " + description1.getText() + "\n";
        String desc2 = "-> " + description2.getText() + "\n";
        String desc3 = "-> " + description3.getText() + "\n";
        String res = desc1.concat(desc2);
        String result = res.concat(desc3);
        wareRequest.setDescription(result);
        wareRequest.setMarketPosition(position_market_Ware.getText().toString());
        wareRequest.setWareSellerId(getActuelUser().getClientId());
        wareRequest.setWareSellerName(getActuelUser().getFirstName() +" " + getActuelUser().getLastName());

        //checkbox
        this.checkBox_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(checkBox_condition.isChecked()){
                    wareRequest.setRightAgree(true);
                }else{
                    wareRequest.setRightAgree(false);
                }
            }
        });

        //for pictures
        wareRequest.setFotos(getAllImages());
        return wareRequest;
    }
    public ClientResponseModel getActuelUser(){
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        SharedPreferences mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        return clientResponseModel;
    }



    public void saveNewWare(View view){
        if (getIntent().getStringExtra("wareId") != null){
            updateDataInfos(getIntent().getStringExtra("wareId"), wareResponse);
            setInputFeldEmpty();
            return;
        }
        createWare(getInputField());
        setInputFeldEmpty();
    }

    private void updateDataInfos(String wareId, WareResponse wareResponse) {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = api.patchWareInfos(wareId,wareResponse);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                Log.d("Update Ware Call", "gut");
            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Update Ware Call", "Errorr");
            }
        });
    }

    public void createWare(WareRequest wareRequest) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = apiWare.createSpecialWare(wareRequest);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                Log.d("POST", "gutt");

            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Ware Call", "Errorr");
            }
        });
    }

    public void fillDropdownItem(){
        //price dropdown
        ArrayList<String> listDropdownItems = new ArrayList<>();
        listDropdownItems.add(getResources().getString(R.string.shops_nav_offer_service));
        listDropdownItems.add(getResources().getString(R.string.shops_nav_alimentation));
        listDropdownItems.add(getResources().getString(R.string.shops_nav_electronic));
        listDropdownItems.add(getResources().getString(R.string.shops_nav_shoes));
        listDropdownItems.add(getResources().getString(R.string.shops_nav_parfumerie));
        listDropdownItems.add(getResources().getString(R.string.shops_nav_vetement));
        listDropdownItems.add(getResources().getString(R.string.nav_home));


        ArrayAdapter<String> adapterDropdown = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listDropdownItems);
        spinner = findViewById(R.id.category_dropdown);
        spinner.setAdapter(adapterDropdown);
    }

    public void fillDropdownSubCategory(){
        ArrayList<String> listDropdownItems = new ArrayList<>();
        listDropdownItems.add(getResources().getString(R.string.electronic_computer));
        listDropdownItems.add(getResources().getString(R.string.electronic_menager));
        listDropdownItems.add(getResources().getString(R.string.electronic_phone));
        listDropdownItems.add(getResources().getString(R.string.electronic_piece));
        listDropdownItems.add(getResources().getString(R.string.alimentation_new_arrivals));
        listDropdownItems.add(getResources().getString(R.string.alimentation_promotion));
        listDropdownItems.add(getResources().getString(R.string.alimentation_ventes_en_gros));
        listDropdownItems.add(getResources().getString(R.string.parfumerie_beauty));
        listDropdownItems.add(getResources().getString(R.string.parfumerie_coiffure));
        listDropdownItems.add(getResources().getString(R.string.parfumerie_sales));
        listDropdownItems.add(getResources().getString(R.string.clothes_kids));
        listDropdownItems.add(getResources().getString(R.string.clothes_mann));
        listDropdownItems.add(getResources().getString(R.string.clothes_woemen));
        listDropdownItems.add(getResources().getString(R.string.clothes_new_arrivals));

        listDropdownItems.add(getResources().getString(R.string.shoes_baby));
        listDropdownItems.add(getResources().getString(R.string.shoes_femme));
        listDropdownItems.add(getResources().getString(R.string.shoes_hommes));
        listDropdownItems.add(getResources().getString(R.string.shoes_sales));



        ArrayAdapter<String> adapterDropdown = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listDropdownItems);
        spinnerSubCategory.setAdapter(adapterDropdown);
    }





}
