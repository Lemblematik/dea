package com.cmr.project.mymarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;
import com.cmr.project.mymarket.ResponseModel.WareResponse;
import com.cmr.project.mymarket.UI.Activities.Corb_header;
import com.cmr.project.mymarket.UI.Activities.Login.LoginView;
import com.cmr.project.mymarket.UI.Adapters.NavigationDrawerAdapter;
import com.cmr.project.mymarket.UI.Activities.Tabs.OfferService;
import com.cmr.project.mymarket.UI.Activities.Tabs.Alimentation;
import com.cmr.project.mymarket.UI.Activities.Tabs.Parfumerie;
import com.cmr.project.mymarket.UI.Activities.Tabs.Clothes;
import com.cmr.project.mymarket.UI.Activities.Tabs.Home;
import com.cmr.project.mymarket.UI.Activities.Tabs.Shoes;
import com.cmr.project.mymarket.UI.Activities.Tabs.Electronic;
import com.cmr.project.mymarket.UI.UI_Model.DrawerItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static List<WareResponse> wareResponses;
    public static WareResponse wareResponse;
    public static WareResponse wareResponsePost;
    public static WareResponse wareResponsePut;
    public static List<DrawerItem> drawerItems;
    NavigationView navigationView;
    public static String phoneAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //first use app
        SharedPreferences userInfo = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean logedIn =  userInfo.getBoolean("firstStart", true);
        if(!logedIn){
            showLoginView();
        }



        //handle nav
        //TODO
        //ArrayList<DrawerItem> drawerItems =(ArrayList<DrawerItem>)getIntent().getExtras().getSerializable("list_drawer_item");
        handleNavigationDrawer(getAllDrawerItem());



        //GET ALL
        //getAllWares();


 /*
        //GET WARE
        getSpecialWare("31336bbc-ca53-4d5e-abe2-de7111b9d5dd");

        //POST
        WareRequest wareRequest = new WareRequest();
        wareRequest.setDescription("delicieux");
        wareRequest.setName("patate");
        wareRequest.setPrice("888888");
        wareRequest.setWareSellerId("vendeur 1");
        List<String> fotos = new ArrayList<>();
        fotos.add("foto1");
        fotos.add("foto2");
        wareRequest.setFotos(fotos);
        createWare(wareRequest);


        //UPDATE
        WareRequest ware2 = new WareRequest();
        ware2.setDescription("delicieux");
        ware2.setName("test");
        ware2.setPrice("77777777");
        ware2.setWareSellerId("vendeur 1");
        List<String> fotos2 = new ArrayList<>();
        fotos2.add("foto1");
        fotos2.add("foto2");
        ware2.setFotos(fotos2);
        updateWare(ware2, "c683f59a-ddf9-4599-87c6-9d2c6e5cc02d");

        //DELETE
        deleteWare("2ed1bb83-7f84-493d-8cc4-06f7f0b51c3d");

        */


        handleTabView();

        //handle marche name
        /*
        FloatingActionButton fab = findViewById(R.id.fab_main);
        fab.setContentDescription("Start Chat");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListImageSelectedActivity.class));
            }
        });
*/



    }

    private void showLoginView() {

        startActivity(new Intent(this,LoginView.class));
        SharedPreferences userInfo = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor  editor = userInfo.edit();
        editor.putBoolean("firstStart",true);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void handleTabView(){
        MainActivity.MyTabPagerAdapter tabPager = new MainActivity.MyTabPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(0).setText(getString(R.string.ads_accueil));
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        tabLayout.getTabAt(1).setText(getString(R.string.shops_nav_electronic));
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        tabLayout.getTabAt(2).setText(getString(R.string.shops_nav_alimentation));
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        tabLayout.getTabAt(3).setText(getString(R.string.shops_nav_offer_service));
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        tabLayout.getTabAt(4).setText(getString(R.string.shops_nav_parfumerie));
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        tabLayout.getTabAt(5).setText(getString(R.string.shops_nav_vetement));
        tabLayout.getTabAt(6).setIcon(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        tabLayout.getTabAt(6).setText(getString(R.string.shops_nav_shoes));
    }

    /*

    public void getAllWares() {
        ApiWare apiWare = ApiClientWare.getClient().create(ApiWare.class);
        Call<List<WareResponse>> call = apiWare.getWares();
        call.enqueue(new Callback<List<WareResponse>>() {
            @Override
            public void onResponse(Call<List<WareResponse>> call, Response<List<WareResponse>> response) {
                List<WareResponse> results = response.body();
                wareResponses = new ArrayList<>();
                wareResponses.addAll(results);
                handleHomeView(wareResponses);

            }

            @Override
            public void onFailure(Call<List<WareResponse>> call, Throwable t) {
                Log.d("Ware Call", "Errorr");
            }
        });
    }
    public void handleHomeView(List<WareResponse> wareResponses){
        RecyclerView recyclerView = findViewById(R.id.content_main_recyclerview);
        Log.d("RESULT", wareResponses.toString());
        WaresAdapter adapter = new WaresAdapter(wareResponses,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }


     */


    public void handleNavigationDrawer(List<DrawerItem> drawerItemList){
        View headerLayout = navigationView.getHeaderView(0);
        RecyclerView recyclerView = headerLayout.findViewById(R.id.nav_recyclerview);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(drawerItemList,this);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }

    public List<DrawerItem> getAllDrawerItem(){
        drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem(R.string.nav_home,R.drawable.logo) );
        drawerItems.add(new DrawerItem(R.string.nav_my_account,R.drawable.logo) );

        //My wares only for admin  phoneAdmin.equals("88888888")

        drawerItems.add(new DrawerItem(R.string.my_wares,R.drawable.logo) );
        drawerItems.add(new DrawerItem(R.string.nav_my_order,R.drawable.logo) );

        drawerItems.add(new DrawerItem(R.string.nav_post_apacheur,R.drawable.logo) );
        drawerItems.add(new DrawerItem(R.string.nav_myCorb,R.drawable.logo) );
        drawerItems.add(new DrawerItem(R.string.nav_doMarket,R.drawable.logo) );
        drawerItems.add(new DrawerItem(R.string.nav_about,R.drawable.logo) );
        drawerItems.add(new DrawerItem(R.string.nav_feeddback, R.drawable.logo));
        drawerItems.add(new DrawerItem(R.string.nav_mail, R.drawable.logo));
        return drawerItems;
    }

    public boolean admin(){
        SharedPreferences  mPrefs = getSharedPreferences("saveUser",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("actuel_user", "");
        ClientResponseModel clientResponseModel =  gson.fromJson(json, ClientResponseModel.class);
        if (clientResponseModel.getPhone().equals("88888888")){
            return true;
        }
        return false;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.korb_header) {
            startActivity(new Intent(MainActivity.this, Corb_header.class));
        }
        return super.onOptionsItemSelected(item);
    }



    //for tabs
    static class MyTabPagerAdapter extends FragmentPagerAdapter {
        MyTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 7; // One for each tab
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new Home();
                case 1:
                    return new Electronic();
                case 2:
                    return new Alimentation();
                case 3:
                    return new OfferService();
                case 4:
                    return new Parfumerie();
                case 5:
                    return new Clothes();
                case 6:
                    return new Shoes();
                default:
                    throw new IllegalArgumentException();
            }
        }
    }




    /*
    //GET ALL
    public void getAllWares() {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<List<WareResponse>> call = api.getWares();
        call.enqueue(new Callback<List<WareResponse>>() {
            @Override
            public void onResponse(Call<List<WareResponse>> call, Response<List<WareResponse>> response) {
                List<WareResponse> results = response.body();
                wareResponses = new ArrayList<>();
                wareResponses.addAll(results);
                setViewALL(wareResponses);

            }

            @Override
            public void onFailure(Call<List<WareResponse>> call, Throwable t) {
                Log.d("Ware Call", "Errorr");
            }
        });
    }

    //GET SPECIAL WAREID
    public void getSpecialWare (String wareId) {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = api.getSpecialWare(wareId);
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

    //POST WARE
    public void createWare(WareRequest wareRequest) {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = api.createSpecialWare(wareRequest);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                WareResponse results = response.body();
                wareResponsePost = new WareResponse();
                wareResponsePost = results;
                setViewPost(wareResponsePost);

            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Ware Call", "Errorr");
            }
        });
    }


    //UPDATE WARE
    public void updateWare(WareRequest wareRequest, String wareId) {
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<WareResponse> call = api.patchWareInfos(wareId,wareRequest);
        call.enqueue(new Callback<WareResponse>() {
            @Override
            public void onResponse(Call<WareResponse> call, Response<WareResponse> response) {
                WareResponse results = response.body();
                wareResponsePut = new WareResponse();
                wareResponsePut = results;
                setViewUpdate(wareResponsePut);

            }

            @Override
            public void onFailure(Call<WareResponse> call, Throwable t) {
                Log.d("Ware Call", "Errorr");
            }
        });
    }

    //DELETE
    public void deleteWare(final String wareId){
        ApiWare api = ApiClientWare.getClient().create(ApiWare.class);
        Call<Void> call = api.deleteWare(wareId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                setViewAfterDeletedWare(wareId);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Ware Get Call", "Failed");
            }
        });
    }


    private void setViewAfterDeletedWare(String wareId) {
        TextView textView = findViewById(R.id.deletedWare);
        textView.append(wareId);
    }

    private void setViewUpdate(WareResponse wareResponsePut) {
        TextView textView = findViewById(R.id.putWare);
        textView.append(wareResponsePut.getName());
    }



    private void setViewPost(WareResponse wareResponsePost) {
        TextView textView = findViewById(R.id.name);
        textView.append(wareResponsePost.getName());
    }

    private void setViewSpecialWare(WareResponse result) {
        TextView textView = findViewById(R.id.oneWare);
        textView.append(result.getName());
    }

    public void setViewALL(List<WareResponse> wareResponses){
        //test Retrofit
        TextView textView = findViewById(R.id.allWare);
        textView.append(wareResponses.get(0).getName());

    }

*/


}
