package com.example.shehzad.finaltabchat;


import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;


public class AddMember extends AppCompatActivity {

    EditText ed;
    GroupInfo gi = new GroupInfo();
    Button btn;
    ListView mems;
   static ArrayList<UserData> domainUsers;
    String status,msg;
    DrawerListAdapter adpt;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(MainActivity.selectedColorRGB);

        domainUsers = new ArrayList<>();

//        ed = (EditText)findViewById(R.id.grp_mem_name);
//        btn = (Button)findViewById(R.id.button);
        mems = (ListView)findViewById(R.id.mem_listView);

        mems.setOnTouchListener(new OnswipeTouchListener(AddMember.this) {

            public void onSwipeRight() {

                finish();
            }


        });


        Handler refresh = new Handler(Looper.getMainLooper());

        refresh.post(new Runnable() {
            public void run() {
                try {
                    JsonMethods jm = new JsonMethods();
                    JsonMethods.connect();
                    jm.fetchUsersByDomain();
                    jm.getResponse();
                    domainUsers = jm.getGroupUserInfo();
                    adpt = new DrawerListAdapter(getApplicationContext(), domainUsers);
                    mems.setAdapter(adpt);
                    adpt.notifyDataSetChanged();
                } catch (IOException | JSONException|NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        mems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

               final  String name =  domainUsers.get(position).getName();
                final  String mem_id = domainUsers.get(position).getGroupId();

                Handler refresh = new Handler(Looper.getMainLooper());

                refresh.post(new Runnable() {
                    public void run() {


                        try {



                            JsonMethods jm = new JsonMethods();
                            JsonMethods.connect();
                            jm.insertUsersInGroup(GroupFragment.grp_id, mem_id);
                            jm.getResponse();
                            status = jm.getGrpUserInfo("status");
                            msg = jm.getGrpUserInfo("status_message");

                            if (status.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                            } else {
                                if (status.equals("true")) {
                                    gi.addUser(name, mem_id);
                                    domainUsers.remove(position);
                                    adpt.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                } else  {


                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                            }catch(JSONException | IOException |NullPointerException e){
                            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }


                        }

                });




            }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        GroupInfo.adapter.notifyDataSetChanged();

    }
}
