package com.unilever.go.wallsopsi1.ViewHolders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unilever.go.wallsopsi1.CustomView.CircleImageView;
import com.unilever.go.wallsopsi1.R;

public class RightMessageViewHolder extends RecyclerView.ViewHolder{
    public TextView textMessage;
    public TextView messageTimeStamp;
    public CircleImageView messageStatus;

    public RightMessageViewHolder(View itemView) {
        super(itemView);
        textMessage = itemView.findViewById(R.id.textViewMessage);
        messageStatus = itemView.findViewById(R.id.img_message_status);
        messageTimeStamp = itemView.findViewById(R.id.timestamp);
    }

}
