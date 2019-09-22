package com.example.chatapp;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter  extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder> {

    private FirebaseUser mUser;
    private DatabaseReference mReference;

    private ArrayList<String> sendersList;
    public static final int MSG_RIGHT = 0;
    public static final int MSG_LEFT = 1;




    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options , ArrayList<String> sendersList ) {
        super(options);
        this.sendersList = sendersList;
    }




    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int i, @NonNull Message message) {


        holder.messageContent.setText(message.getMessage());
        holder.messageTime.setText(message.getTime());
        holder.messageSenderName.setText(message.getName());

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_LEFT){

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_layout, parent, false);
            return new MessageViewHolder(view);
        }else{


            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_layout_right, parent, false);
            return new MessageViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {




        if (sendersList.get(position).equals(UsersActivity.clientName)){

            return MSG_RIGHT;
        }else{
            return MSG_LEFT;
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView messageContent;
        TextView messageTime;
        TextView messageSenderName;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.txtMessage);
            messageTime = itemView.findViewById(R.id.txtTime);
            messageSenderName = itemView.findViewById(R.id.txtName);
        }
    }
}
