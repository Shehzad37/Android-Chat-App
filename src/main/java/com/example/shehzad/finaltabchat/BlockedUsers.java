package com.example.shehzad.finaltabchat;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class BlockedUsers extends AppCompatActivity {

    EditText ed;
    GroupInfo gi = new GroupInfo();
    Button btn;
    ListView mems;
    static ArrayList<UserData> domainUsers;
    String status,msg;
    DrawerListAdapter adpt;
    Window window;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_users);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(MainActivity.selectedColorRGB);

        domainUsers = new ArrayList<>();

        mems = (ListView) findViewById(R.id.blockmem_listView);

       mems.setOnTouchListener(new OnswipeTouchListener(BlockedUsers.this) {

            public void onSwipeRight() {

                finish();
            }


        });

    tv = (TextView)findViewById(R.id.textView_block);

        Handler refresh = new Handler(Looper.getMainLooper());

        refresh.post(new Runnable() {
            public void run() {
                try {
                    JsonMethods jm = new JsonMethods();
                    JsonMethods.connect();
                    jm.fetchGrpBlockedUser(GroupFragment.grp_id);
                    jm.getResponse();
                    domainUsers = jm.getGroupUserInfo();
                    adpt = new DrawerListAdapter(getApplicationContext(), domainUsers);
                    mems.setAdapter(adpt);
                    registerForContextMenu(mems);
                    adpt.notifyDataSetChanged();
                } catch (IOException | JSONException|NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"No Connection",Toast.LENGTH_LONG).show();
                }

            }
        });

    }



        @Override
        public void onBackPressed () {
            super.onBackPressed();

            GroupInfo.adapter.notifyDataSetChanged();

        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.blockmem_listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.block, menu);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int poss = info.position;
        final String pos = domainUsers.get(poss).getGroupId();
//        String posss = GroupInfo.users.get(poss).getGroupId();

        switch(item.getItemId()) {

            case R.id.Unblock  :

                Handler refresh1 = new Handler(Looper.getMainLooper());

                refresh1.post(new Runnable() {
                    public void run() {

                        try {

//                            final  String name =  domainUsers.get(position).getName();
//                            final  String mem_id = domainUsers.get(position).getGroupId();


                            Log.i("Add mem", "");
                            JsonMethods jm = new JsonMethods();
                            JsonMethods.connect();
                            jm.unblockUser(pos,GroupFragment.grp_id);
                            jm.getResponse();
                            status = jm.getGrpUserInfo("status");
                            msg = jm.getGrpUserInfo("status_message");

                            //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                            if (status.equals("true")) {
//                                gi.addUser(name, mem_id);
//
//
                            domainUsers.remove(poss);
                                adpt.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                            } else {


                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }


                    }
                });






            default:
                return super.onContextItemSelected(item);
        }
    }
    }
