package com.example.ap2_ex3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.entities.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    List<Contact> myList;

    private class ViewHolder {
        TextView name;
        TextView lastMess;
        TextView date;
        ImageView profilePic;
    }

    public ContactAdapter(List<Contact> myList) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.displaynameTextView);
            viewHolder.date = convertView.findViewById(R.id.dateTextView);
            viewHolder.lastMess = convertView.findViewById(R.id.lastMessTextView);
            viewHolder.profilePic = convertView.findViewById(R.id.ProfileImageView);
            convertView.setTag(viewHolder);
        }

        Contact myContact = myList.get(position);
        ContactAdapter.ViewHolder viewHolder = (ContactAdapter.ViewHolder) convertView.getTag();
        viewHolder.name.setText(myContact.getDisplayName());
        viewHolder.date.setText(myContact.getDate());
        String cleanImage = myContact.getProfilePicture().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
        byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.profilePic.setImageBitmap(decodedByte);
        viewHolder.lastMess.setText(myContact.getLastMess());
        return convertView;
    }
}

