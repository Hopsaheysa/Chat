package com.stranskymarek.chat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stranskymarek.chat.Model.Chat;
import com.stranskymarek.chat.Model.Users;
import com.stranskymarek.chat.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final Context context;
    private final List<Chat> mChat;
    private final String imgURL;

    FirebaseUser fuser;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public MessageAdapter(Context context, List<Chat> mChat, String imgURL) {
        this.context = context;
        this.mChat = mChat;
        this.imgURL = imgURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,
                    parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,
                    parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
            Chat chat = mChat.get(position);
            holder.show_message.setText(chat.getMessage());

            if (imgURL.equals("default")) {
                holder.profile_image.setImageResource(R.mipmap.ic_launcher);
            } else {
                Glide.with(context).load(imgURL).into(holder.profile_image);
            }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.message_text);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        return  MSG_TYPE_LEFT;
    }
}
