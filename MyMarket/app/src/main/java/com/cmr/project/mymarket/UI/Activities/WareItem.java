package com.cmr.project.mymarket.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WareItem extends AppCompatActivity {

    WareResponse wareResponse;
    Button btnIsInCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ware_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnIsInCart = findViewById(R.id.addInCart_btn);
        String wareId = getIntent().getStringExtra("wareId");
        getSpecialWare(wareId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.korb_header) {
            startActivity(new Intent(WareItem.this, Corb_header.class));
        }
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getSpecialWare(String wareId) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = apiWare.getSpecialWare(wareId);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                WareResponse result = response.body();
                wareResponse = new WareResponse();
                wareResponse = result;
                setViewSpecialWare(wareResponse);
            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });
    }

    private void setViewSpecialWare(final WareResponse wareResponse) {
        setTitle(wareResponse.getName());

        TextView nameView = findViewById(R.id.ware_layout_name);
        nameView.setText(wareResponse.getName());

        TextView priceView = findViewById(R.id.ware_layout_price);
        priceView.setText(wareResponse.getPrice() + "  FCFA");

        TextView descriptionView = findViewById(R.id.ware_layout_description);
        descriptionView.setText(wareResponse.getDescription());

        TextView position_ware = findViewById(R.id.ware_position);
        position_ware.append(wareResponse.getMarketPosition());

        TextView category_name_ware = findViewById(R.id.category_name_ware);
        category_name_ware.append(wareResponse.getCategory());

        TextView market_name_ware = findViewById(R.id.market_name_ware);
        market_name_ware.append(wareResponse.getMarketPlace());

        //Caroussel
        final List<Bitmap> allImages = setAllImages(wareResponse.getFotos());


        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {

                loadBitmapByPicasso(WareItem.this,allImages.get(position),imageView);

                /*
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent_img = new Intent(this, WareImages.class);
                        intent_img.putStringArrayListExtra("allFotos", (ArrayList<String>) wareResponse.getFotos());
                        startActivity(intent_img);
                    }
                });
                */
            }
        };



        CarouselView customCarouselView = findViewById(R.id.ware_layout_carouselView);
        customCarouselView.setImageListener(imageListener);
        customCarouselView.setPageCount(wareResponse.getFotos().size());

        //btn
        btnIsInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wareResponse.setCart(true);
                updateWare(wareResponse,wareResponse.getWareId());
                Toast.makeText(WareItem.this, wareResponse.getName() + " added in Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Bitmap> setAllImages(List<String> fotos) {
        List<Bitmap> results = new ArrayList<>();
        for(String base64Photo: fotos){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            results.add(decodedImage);
        }
        return results;

    }
    private void loadBitmapByPicasso(Context pContext, Bitmap pBitmap, ImageView pImageView) {
        try {
            Uri uri = Uri.fromFile(File.createTempFile("temp_file_name", ".jpg", pContext.getCacheDir()));
            OutputStream outputStream = pContext.getContentResolver().openOutputStream(uri);
            pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            Picasso.get().load(uri).into(pImageView);
        } catch (Exception e) {
            Log.e("LoadBitmapByPicasso", e.getMessage());
        }
    }

    public void updateWare(WareResponse wareRequest, String wareId) {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = apiWare.patchWareInfos(wareId,wareRequest);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                Log.d("Update Ware Call", "gut");
            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Update Ware Call", "failed");
            }
        });
    }



}
