package com.cmr.project.mymarket.UI.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Adapters.SearchWareAdapter;

import java.util.ArrayList;

public class DoMarket extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static String word_input = "";
    private SearchView mSearchView;
    SearchWareAdapter wareAdapter;
    ListView listView_produit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_market_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        generateWareList();

        handleSearchView();

    }

    public void handleSearchView() {
        //search item
        mSearchView =(SearchView) findViewById(R.id.search);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here and click >");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.wtf("onQueryTextSubmit s  = ", s + "");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filter filterProduct = wareAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filterProduct.filter("");
        } else {
            word_input =  newText;
            filterProduct.filter(newText);
        }
        return true;
    }



    private void generateWareList() {
        listView_produit = findViewById(R.id.search_listview_cart);
        wareAdapter = new SearchWareAdapter(this,new ArrayList<WareResponse>());
        listView_produit.setAdapter(wareAdapter);
        listView_produit.setTextFilterEnabled(true);
    }

}

