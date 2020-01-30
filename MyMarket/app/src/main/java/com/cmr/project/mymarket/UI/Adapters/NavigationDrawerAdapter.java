package com.cmr.project.mymarket.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.MainActivity;
import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.UI.Activities.About;
import com.cmr.project.mymarket.UI.Activities.Account;
import com.cmr.project.mymarket.UI.Activities.Corb_header;
import com.cmr.project.mymarket.UI.Activities.Mail;
import com.cmr.project.mymarket.UI.Activities.MyOrders;
import com.cmr.project.mymarket.UI.Activities.DoMarket;
import com.cmr.project.mymarket.UI.Activities.Feedback;
import com.cmr.project.mymarket.UI.Activities.MyWares;
import com.cmr.project.mymarket.UI.Activities.AnacondaInfos;
import com.cmr.project.mymarket.UI.Activities.Post;
import com.cmr.project.mymarket.UI.UI_Model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<DrawerItem> drawerItems = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;



    public NavigationDrawerAdapter(List<DrawerItem> drawerItems, Context mContext) {
        this.drawerItems = drawerItems;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.drawer_item, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.nameTextView.setText(drawerItems.get(position).getName());
        itemHolder.imageView.setImageResource(drawerItems.get(position).getIcon());




        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int result = drawerItems.get(position).getName();
                switch (result){
                    case R.string.nav_home:
                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                        break;
                    case R.string.nav_about:
                        mContext.startActivity(new Intent(mContext, About.class));
                        break;
                    case R.string.nav_my_order:
                        mContext.startActivity(new Intent(mContext, MyOrders.class));
                        break;
                    case R.string.nav_doMarket:
                        mContext.startActivity(new Intent(mContext, DoMarket.class));
                        break;
                    case R.string.nav_anaconda:
                        mContext.startActivity(new Intent(mContext, Feedback.class));
                        break;
                    case R.string.nav_myCorb:
                        mContext.startActivity(new Intent(mContext, Corb_header.class));
                        break;
                    case R.string.nav_post_apacheur:
                        mContext.startActivity(new Intent(mContext, Post.class));
                        break;
                    case R.string.nav_my_account:
                        mContext.startActivity(new Intent(mContext, Account.class));
                        break;
                    case R.string.nav_feeddback:
                        mContext.startActivity(new Intent(mContext, AnacondaInfos.class));
                        break;
                    case R.string.my_wares:
                        mContext.startActivity(new Intent(mContext, MyWares.class));
                        break;
                    case R.string.nav_mail:
                        mContext.startActivity(new Intent(mContext, Mail.class));
                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return drawerItems.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView imageView;
        public CardView cardView;
        public ItemHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nav_title);
            imageView = (ImageView) itemView.findViewById(R.id.nav_image);
            cardView = itemView.findViewById(R.id.drawer_item);
        }

    }
}
