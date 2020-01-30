package com.cmr.project.mymarket.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmr.project.mymarket.Boundary.Ware.ApiClientWare;
import com.cmr.project.mymarket.Boundary.Ware.ApiWare;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.WareItem;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWareAdapter extends BaseAdapter implements Filterable {
    public static int allResult_nbr = 0;
    public Context mContext;
    public List<WareResponse> products;
    public List<WareResponse> wareResponses;



    public SearchWareAdapter(Context mContext, List<WareResponse> produitList) {
        super();
        this.mContext = mContext;
        this.products = produitList;
    }

    public class ProductHolder {
        TextView name;
        ImageView imageView;
    }


    @Override
    public int getCount() {
        if(products.size() < 7) {
            return products.size();
        }
        return 6;

    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ProductHolder holder;
        if (view == null) {
            holder = new ProductHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.search_listview_ware_item, null);
            holder.name = (TextView) view.findViewById(R.id.name_alaUne);
            holder.imageView = view.findViewById(R.id.foto_search);



            view.setTag(holder);
        } else {
            holder = (ProductHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(products.get(i).getName());

        //handle img
        loadBitmapByPicasso(mContext,setImagesToaBitMap(products.get(i).getFotos().get(0)),holder.imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WareItem.class);
                intent.putExtra("wareId",products.get(i).getWareId());
                mContext.startActivity(intent);
            }
        });

        return view;
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
    private Bitmap setImagesToaBitMap(String fotos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(fotos, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;


    }



    @Override
    public Filter getFilter() {
        return productsfiler;
    }

    public void getAllSearchProducts(String wareId) {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<List<WareResponse>> call = api.getSearchWare(wareId,wareId,wareId);
        call.enqueue(new Callback<List<WareResponse>>() {
            @Override
            public void onResponse(Call<List<WareResponse>> call, Response<List<WareResponse>> response) {
                List<WareResponse> result = response.body();
                wareResponses = new ArrayList<>();
                wareResponses = result;
                products.addAll(wareResponses);
            }

            @Override
            public void onFailure(Call<List<WareResponse>> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });

    }

    private Filter productsfiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            if (charSequence != null || charSequence.length()>1){
                String filterPattern = charSequence.toString().toLowerCase().trim();
                getAllSearchProducts(filterPattern);
            }else{
                products = new ArrayList<>();
            }
            FilterResults results = new FilterResults();
            results.values = products;
            return results;


        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            products.clear();
            products.addAll((Collection<? extends WareResponse>) filterResults.values);
        }
    };


}
