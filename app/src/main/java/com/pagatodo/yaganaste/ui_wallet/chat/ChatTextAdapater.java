package com.pagatodo.yaganaste.ui_wallet.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.ChatBoxGrayTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.ChatBoxTextViewHolder;

import java.util.ArrayList;

public class ChatTextAdapater extends ArrayAdapter<ChatBoxTextViewHolder.Chatholder> {

    private Context context;
    private ArrayList<ChatBoxTextViewHolder.Chatholder> listChat;

    public ChatTextAdapater(@NonNull Context context) {
        super(context, -1);
        this.context = context;
        this.listChat = new ArrayList<>();
    }

    public void addChatText(ChatBoxTextViewHolder.Chatholder chatholder){
        this.listChat.add(this.listChat.size()-1,chatholder);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;
        if (listChat.get(position).isLeft()) {
            rowView = inflater.inflate(R.layout.chat_box_gray_layout, parent, false);
            ChatBoxGrayTextViewHolder holder = new ChatBoxGrayTextViewHolder(rowView);
            holder.bind(listChat.get(position),null);
        } else {
            rowView = inflater.inflate(R.layout.chat_box_layout, parent, false);
            ChatBoxTextViewHolder holder = new ChatBoxTextViewHolder(rowView);
            holder.bind(listChat.get(position),null);

        }
        return rowView;
    }

    @Override
    public int getCount() {
        return this.listChat.size();
    }
}
