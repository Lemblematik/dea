package com.cmr.project.mymarket.UI.UI_Model;

import com.cmr.project.mymarket.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrawerItem implements Serializable {
    private int name;
    private int icon;

    public DrawerItem(int name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }



}
