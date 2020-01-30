package com.cmr.project.mymarket.UI.Activities.Login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cmr.project.mymarket.R;
import com.google.android.material.tabs.TabLayout;

public class LoginView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_account);

        PageAdapterLogin tabPager = new PageAdapterLogin(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.login_viewPager);
        viewPager.setAdapter(tabPager);
        TabLayout tabLayout = findViewById(R.id.login_tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_business_black_24dp);
        tabLayout.getTabAt(0).setText(getString(R.string.sign_in));
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_shopping_cart_black_24dp);
        tabLayout.getTabAt(1).setText(getString(R.string.registrate_view));
    }

}
