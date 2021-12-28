package com.stranskymarek.chat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stranskymarek.chat.MessageActivity;
import com.stranskymarek.chat.Model.Users;
import com.stranskymarek.chat.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<Users> mUsers;
    private boolean isChat;

    // Constructor
    public UserAdapter() {
    }

    public UserAdapter(Context context, List<Users> users, boolean isChat) {
        this.context = context;
        this.mUsers = users;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = mUsers.get(position);
        holder.username.setText(users.getUsername());

        if (users.getImageURL() == null || users.getImageURL().equals("default")) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            //Adding Glide Library
            Glide.with(context)
                    .load(users.getImageURL())
                    .into(holder.imageView);
        }

        if (isChat) {
            if (users.getStatus().equals("online")) {
                holder.statusOn.setVisibility(View.VISIBLE);
                holder.statusOff.setVisibility(View.GONE);
            } else {
                holder.statusOn.setVisibility(View.GONE);
                holder.statusOff.setVisibility(View.VISIBLE);
            }
        } else {
            holder.statusOn.setVisibility(View.GONE);
            holder.statusOff.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("userid", users.getId());
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView imageView;
        public ImageView statusOn;
        public ImageView statusOff;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameItem);
            imageView = itemView.findViewById(R.id.userImageItem);
            statusOn = itemView.findViewById(R.id.status_imageON);
            statusOff = itemView.findViewById(R.id.status_imageOFF);
        }

    }

}
