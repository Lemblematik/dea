package com.cmr.project.mymarket.UI.Activities.Login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PageAdapterLogin extends FragmentPagerAdapter {

    PageAdapterLogin(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2; // One for each tab
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new Einloggen();
            case 1:
                return new Registrieren();
            default:
                throw new IllegalArgumentException();
        }
    }

}
