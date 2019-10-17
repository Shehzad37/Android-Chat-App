package com.example.shehzad.finaltabchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatView extends AppCompatActivity {

    EditText msg;
    ListView msgList;
    String time;
    static String name="";
    UserFragment ud=new UserFragment();
    static MediaPlayer mp1;
    static  ChatArrayAdapter chatArrayAdapter;
    Button btnSend;
    static Toolbar toolbar;
    WebsocketClient web;
    Window window;
    static DBHanlder db;
    boolean side = false;
    int Threshhold = 100;
   private float  downX;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_view);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(MainActivity.selectedColorRGB);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(MainActivity.selectedColorRGB);

        this.setTitle(GroupFragment.grp_name);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy  hh:mm a");
        time = sdf.format(new Date()).toString();

        mp1 = MediaPlayer.create(this, R.raw.beep);
        msg = (EditText)findViewById(R.id.txtMsg);
        msgList = (ListView)findViewById(R.id.msgList);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setBackgroundColor(MainActivity.selectedColorRGB);

        chatArrayAdapter = new ChatArrayAdapter(this, R.layout.right,GroupFragment.ud.getList());
        msgList.setAdapter(chatArrayAdapter);
        msgList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatArrayAdapter.notifyDataSetChanged();

        layout = (RelativeLayout)findViewById(R.id.Grp_chat_layout);

        msgList.setOnTouchListener(new OnswipeTouchListener(GroupChatView.this) {

            public void onSwipeRight() {

                finish();
            }


        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                sendChatMessage();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grp_menu, menu);

        if(JsonMethods.role_id.equals("1")) {

            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_view) {

         Intent i = new Intent(this,GroupInfo.class);
            startActivity(i);

            return true;
        }


        if (id == R.id.action_viewblock) {

            Intent i = new Intent(this,BlockedUsers.class);
            startActivity(i);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private boolean sendChatMessage() {

        try {

            web.sendMessage(msg.getText().toString(), UserFragment.ud.getName());

            ChatMessage  c = new ChatMessage(false, msg.getText().toString(),MainActivity.thePic,time);

            //  db.addMsg(c);

            GroupFragment.ud.addToList(c);

            chatArrayAdapter.notifyDataSetChanged();

        }catch (Exception e){

            Toast.makeText(this, "Server Closed", Toast.LENGTH_SHORT).show();

        }



        msg.setText("");

      //  side = !side;

        return true;
    }


}
