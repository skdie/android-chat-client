package com.example.galab.sudreeshya1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {


    private Context context;
    private List<dataModelChat> list;


    public CardView card;

    public MessageAdapter(Context context, List<dataModelChat> list) {
        this.context = context;
        this.list = list;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userMessage;
        public CardView cv;

        public MyViewHolder(View itemView) {
            super(itemView);
//            this.arrivalDate = (TextView) itemView.findViewById(R.id.arrivalDate);
//            this.arrivalTime = (TextView) itemView.findViewById(R.id.arrivalTime);
            userMessage = itemView.findViewById(R.id.userMessageTxt);
            cv = itemView.findViewById(R.id.cardView);

        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_view, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        dataModelChat dataModelChat = list.get(position);
        holder.userMessage.setText(dataModelChat.getMessage());
        TextView userMessage = holder.userMessage;
        CardView cardView = holder.cv;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
