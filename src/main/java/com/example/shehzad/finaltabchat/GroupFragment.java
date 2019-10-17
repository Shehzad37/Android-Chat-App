package com.example.shehzad.finaltabchat;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Shehzad on 9/26/2016.
 */
public class GroupFragment extends Fragment {

    ListView groupList;
  static ArrayList<UserData> users = new ArrayList<UserData>();
   static  DrawerListAdapter adapter;
    static String grp_id,grp_name;
    UserFragment uf = new UserFragment();
    static UserData ud = new UserData();
    static  int pos;
    String grpName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

      //  if (JsonMethods.role_id.equals("2")) {

//            Handler refresh1 = new Handler(Looper.getMainLooper());
//
//            refresh1.post(new Runnable() {
//                public void run() {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        if (JsonMethods.role_id.equals("2")) {

                            try {
                                JsonMethods jm = new JsonMethods();
                                JsonMethods.connect();
                                jm.fetchGroupByAdmin();
                                jm.getResponse();
                                users = jm.getGrpInfo();

                            } catch (IOException | JSONException |NullPointerException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_SHORT).show();
                            }
                        } else if (JsonMethods.role_id.equals("1"))

                        {
                            try {
                                JsonMethods jm = new JsonMethods();
                                JsonMethods.connect();
                                jm.fetchGroupByUser();
                                jm.getResponse();
                                users = jm.getGrpByUSerInfo();

                            } catch (IOException | JSONException|NullPointerException e) {
                                Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception e){

                        Toast.makeText(getActivity(),"No Connection",Toast.LENGTH_SHORT).show();

                    }

                }
            });


//                }
//            });
       // }

//        else if (JsonMethods.role_id.equals("1")) {
//
//            Handler refresh1 = new Handler(Looper.getMainLooper());
//
//            refresh1.post(new Runnable() {
//                public void run() {
//
//                    try {
//                        JsonMethods jm = new JsonMethods();
//                        JsonMethods.connect();
//                        jm.fetchGroupByUser();
//                        jm.getResponse();
//                        jm.getGrpByUSerInfo();
//
//                    } catch (IOException | JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        users.add(new UserData("Gujratians",R.drawable.boy));
//        users.add(new UserData("Dosti", R.drawable.boy));
       // System.out.println("IN Group FRANGMENT ON CREATEVIEW");
       View root = inflater.inflate(R.layout.group_fragment, container, false);


        groupList = (ListView)root.findViewById(R.id.listGroup);
       adapter = new DrawerListAdapter(getActivity(),users);
        groupList.setAdapter(adapter);
try {
    if (JsonMethods.role_id.equals("2")) {
        registerForContextMenu(groupList);
    }
}catch (Exception e){
    Toast.makeText(getActivity(),"No Connection",Toast.LENGTH_SHORT).show();


}

       groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               pos = position;
               Toast.makeText(getActivity(), users.get(position).getGroupId(), Toast.LENGTH_SHORT).show();
               grp_id = users.get(position).getGroupId();
               grp_name = users.get(position).getName();
               makeList(grp_id);
               Intent i = new Intent(getActivity(), GroupChatView.class);
               startActivity(i);
           }
       });


        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        users.clear();

try {

    try {
        JsonMethods jm = new JsonMethods();
        JsonMethods.connect();
        jm.postLogoutData();
        jm.getResponse();
    } catch (IOException | JSONException e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), "No Connection ", Toast.LENGTH_LONG).show();
    }
}
catch (Exception e){

    Toast.makeText(getActivity(),"No Connection",Toast.LENGTH_LONG).show();
}




    }

    public void makeList(String name)
    {

        for (UserData u : users) {

            if (u.getGroupId().equals(name)) {

                //Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                ud = u;
            }
        }
    }

}