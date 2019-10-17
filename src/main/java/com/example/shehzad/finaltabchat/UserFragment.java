package com.example.shehzad.finaltabchat;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.shehzad.finaltabchat.UserFragment;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;


public class UserFragment extends Fragment {

    ListView userList ;
    static String name;
    static int pos;
    public static DrawerListAdapter adapter = new DrawerListAdapter();
    static UserData ud = new UserData()  ;
    static ArrayList<UserData> users = new ArrayList<UserData>();
    DBHanlder db;

    public  static UserFragment newInstance()
    {

        UserFragment f = new UserFragment();
        return f;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
db = new DBHanlder(getActivity());
          //  System.out.println("IN USER FRANGMENT ON CREATE");

         users.add(new UserData("Waqas",R.drawable.boy,R.drawable.dot));
         users.add(new UserData("khuram",R.drawable.boy,R.drawable.dot));
//        users.add(new UserData("Bilal", R.drawable.ic_launcher, R.drawable.dot));
//        Toast.makeText(getActivity(),ud.getName().toString(),Toast.LENGTH_SHORT).show();

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     //   System.out.println("IN USER FRANGMENT ON CREATEView");

        View root = inflater.inflate(R.layout.user_fragment, container, false);

        userList = (ListView)root.findViewById(R.id.listUsers);
        adapter = new DrawerListAdapter(getActivity(),users);
        userList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                pos = i;
                name = users.get(i).getName();
                getActivity().setTitle(name);
               // Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                makeList(name);
                 MainActivity.viewPager.setCurrentItem(1);
               // ud.clearList();
                //db.getAllChatsByName();
            }
        });

        return root;
    }

public void makeList(String name)
{

    for (UserData u : users) {

        if (u.getName().equals(name)) {

            //Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            ud = u;
                                     }
                            }
}

    @Override
    public void onDestroy() {
        super.onDestroy();
       // Toast.makeText(getActivity(),"in destroy", Toast.LENGTH_SHORT).show();
        ud.clearList();
        users.clear();
    }

    public void removeUser(final String name){

       int posi = 0;

        for (UserData u : users) {

            if (u.getName().equals(name)) {

               users.remove(posi);
                ud.clearList();
            }

            posi++;

        }

        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run()
            {

               // users.add(new UserData(name, R.drawable.ic_launcher, R.drawable.dot));
                adapter.notifyDataSetChanged();
            }
        });


    }

   public void addNewUser( final String name)
   {

       boolean flag = false;


       Handler refresh = new Handler(Looper.getMainLooper());
       refresh.post(new Runnable() {
           public void run()
           {

               users.add(new UserData(name, R.drawable.boy, R.drawable.dot));
               adapter.notifyDataSetChanged();
           }
                                 });

//       for (UserData u : users) {
//
//           if (u.getName().equals(name)) {
//
//                flag = true;
//               Toast.makeText(getActivity(), "User with similar Name Already Exist", Toast.LENGTH_LONG).show();
//               // Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
//               //users.remove(posi);
//               //adapter.notifyDataSetChanged();
//           }
//       }
//      if(!flag){

//          users.add(new UserData(name, R.drawable.ic_launcher, R.drawable.dot));
//          System.out.println("Simple Lsit USer");

//          adapter = new DrawerListAdapter(getActivity(),users);
//          userList.setAdapter(adapter);
       // adapter.notifyDataSetChanged();
//}
    }



    public String getname()

    {
        return name;

    }



}
