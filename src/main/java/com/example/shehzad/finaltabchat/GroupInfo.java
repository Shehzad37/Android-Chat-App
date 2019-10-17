package com.example.shehzad.finaltabchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class GroupInfo extends AppCompatActivity {

    ListView infoList;
    Window window;
   static ArrayList<UserData> users = new ArrayList<UserData>();
    static DrawerListAdapter adapter = new DrawerListAdapter();
    TextView tv;
    String stats,msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(MainActivity.selectedColorRGB);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(MainActivity.selectedColorRGB);

        tv = (TextView)findViewById(R.id.tetx_grp);
        tv.setBackgroundColor(MainActivity.selectedColorRGB);

        this.setTitle(GroupFragment.grp_name);

        infoList = (ListView)findViewById(R.id.info_listView);

        //layout = (RelativeLayout)findViewById(R.id.Grp_chat_layout);

        infoList.setOnTouchListener(new OnswipeTouchListener(GroupInfo.this) {

            public void onSwipeRight() {

                finish();
            }


        });


//
//        Handler refresh = new Handler(Looper.getMainLooper());
//
//        refresh.post(new Runnable() {
//            public void run() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    JsonMethods jm = new JsonMethods();
                    JsonMethods.connect();
                    jm.fetchUsersByGroup(GroupFragment.grp_id);
                    jm.getResponse();
                    users = jm.getGroupUserInfo();


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "No Connection ", Toast.LENGTH_LONG).show();
                }
            }
                });


                GroupFragment.ud.setMemList(users);
                adapter = new DrawerListAdapter(getApplicationContext(), GroupFragment.ud.getMemList());
                infoList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                registerForContextMenu(infoList);

//
//           }
//        });


//        users.add(new UserData("Shehzad", R.drawable.boy));
//        users.add(new UserData("Luqman", R.drawable.boy));
//        users.add(new UserData("Shehzad", R.drawable.boy));
//        users.add(new UserData("Luqman", R.drawable.boy));
//        users.add(new UserData("Shehzad", R.drawable.boy));
//        users.add(new UserData("Luqman", R.drawable.boy));
//        users.add(new UserData("Shehzad", R.drawable.boy));
//        users.add(new UserData("Luqman", R.drawable.boy));
//

            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_members) {

            Intent i = new Intent(this,AddMember.class);
            startActivity(i);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void addUser(String name,String id){

        GroupFragment.ud.addUserToGroup(new UserData(name,R.drawable.boy,id));
        adapter.notifyDataSetChanged();


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

       if (v.getId()==R.id.info_listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.use, menu);


        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int poss = info.position;
       final String pos = GroupFragment.grp_id;
        final String posss = GroupInfo.users.get(poss).getGroupId();
        System.out.println(poss + " "+GroupInfo.users.get(poss).getGroupId() );

        switch(item.getItemId()) {

            case R.id.remove:

                Handler refresh = new Handler(Looper.getMainLooper());

                refresh.post(new Runnable() {
                    public void run() {

                        try {
                            JsonMethods jm = new JsonMethods();
                            JsonMethods.connect();
                            jm.removeUser(pos, posss);
                            System.out.println(poss + " "+GroupInfo.users.get(poss).getGroupId() );
                            jm.getResponse();
                            stats = jm.getGrpUserInfo("status");
                            msg = jm.getGrpUserInfo("status_message");

                            if(stats.equals("true")) {

                                GroupInfo.users.remove(poss);
                                GroupInfo.adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                            }

                            else{

                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                    return true;

            case R.id.block:

                Handler refresh1 = new Handler(Looper.getMainLooper());

                refresh1.post(new Runnable() {
                    public void run() {

                        try {
                            JsonMethods jm = new JsonMethods();
                            JsonMethods.connect();
                            jm.blockGrpUser(posss, GroupFragment.grp_id);
                            System.out.println(poss + " " + GroupInfo.users.get(poss).getGroupId());
                            jm.getResponse();
                            stats = jm.getGrpUserInfo("status");
                            msg = jm.getGrpUserInfo("status_message");

                            if(stats.equals("true")) {

                                GroupInfo.users.remove(poss);
                                GroupInfo.adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                            }

                            else{

                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                 return true;




            default:
                return super.onContextItemSelected(item);
        }
    }

}
