package com.example.shehzad.finaltabchat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    ImageView imgIcon;
    TextView textView;
    private ArrayList<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId, ArrayList<ChatMessage> chatMessageList)
    {

        super(context, textViewResourceId);
        this.context = context;
        this.chatMessageList = chatMessageList;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }


    public ArrayList<ChatMessage> getList(){return chatMessageList;}


    public ChatMessage getItem(int index) {return this.chatMessageList.get(index);}

    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMessage chatMessageObj = getItem(position);

        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (chatMessageObj.left)
        {
            row = inflater.inflate(R.layout.left, parent, false);
        }
         else
        {

            row = inflater.inflate(R.layout.right,parent,false);
        }

        textView=(TextView)row.findViewById(R.id.time);

        chatText = (TextView) row.findViewById(R.id.msgr);

        imgIcon = (ImageView) row.findViewById(R.id.img);

        chatText.setText(chatMessageObj.message);

        imgIcon.setImageBitmap((chatMessageObj.icon));

        textView.setText(chatMessageObj.time);

       chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.recieve : R.drawable.send);

        return row;
    }
}