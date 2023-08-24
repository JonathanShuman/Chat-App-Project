package com.example.ap2_ex3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ap2_ex3.api.OneMessage;
import com.example.ap2_ex3.entities.Contact;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    List<OneMessage> myList;

    private class ViewHolder {
        TextView message;

    }

    public ChatAdapter(List<OneMessage> myList) {
        this.myList = myList;
    }


    @Override
    public int getCount() {
        return this.myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (myList.get(position).isiSend()) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_send, parent, false);
        } else {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_recieve, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.message = convertView.findViewById(R.id.sendMessage);
        convertView.setTag(viewHolder);

        OneMessage myMessage = myList.get(position);
        ChatAdapter.ViewHolder viewHolder2 = (ChatAdapter.ViewHolder) convertView.getTag();
        viewHolder2.message.setText(myMessage.getMessage());
        return convertView;
    }
}

