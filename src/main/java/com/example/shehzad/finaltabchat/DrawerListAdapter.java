package com.example.shehzad.finaltabchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class DrawerListAdapter extends BaseAdapter {

    Context mContext;

    ArrayList<UserData> mNavItems;

    DrawerListAdapter(){

        super();

    }

    public DrawerListAdapter(ArrayList<UserData> navItems) {
       // mContext = context;
        this.mNavItems = navItems;

    }


    public DrawerListAdapter(Context context, ArrayList<UserData> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, parent,false);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
//        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        ImageView dotView = (ImageView) view.findViewById(R.id.dot);

        titleView.setText( mNavItems.get(position).name );
        iconView.setImageResource(mNavItems.get(position).mIcon);
        dotView.setImageResource(mNavItems.get(position).icon);

        return view;
    }

}