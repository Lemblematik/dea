package com.cmr.project.mymarket.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.ResponseModel.MailResponse;
import com.cmr.project.mymarket.UI.Activities.MailItem;

import java.util.ArrayList;
import java.util.List;

public class MailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MailResponse> mailResponses = new ArrayList<>();
    private Context mContext;
    protected LayoutInflater inflater;


    public MailAdapter(List<MailResponse> mailResponses, Context mContext) {
        this.mailResponses = mailResponses;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.mail_postfach_view, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.mail_senderName.setText(mailResponses.get(position).getNameSender());
        itemHolder.mail_subject.setText(mailResponses.get(position).getSubject());
        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MailItem.class);
                intent.putExtra("mailId",mailResponses.get(position).getMailId());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mailResponses.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView mail_senderName;
        public TextView mail_subject;
        public CardView cardView;

        public ItemHolder(View itemView) {
            super(itemView);
            mail_senderName = (TextView) itemView.findViewById(R.id.mail_senderName);
            cardView = itemView.findViewById(R.id.mail_layout);
            mail_subject = itemView.findViewById(R.id.mail_subject);
        }

    }
}