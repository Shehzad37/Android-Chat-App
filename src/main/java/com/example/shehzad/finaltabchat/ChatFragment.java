
package com.example.shehzad.finaltabchat;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;



        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AbsListView;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Toast;
        import com.example.shehzad.finaltabchat.ChatMessage;
import com.example.shehzad.finaltabchat.R;
import com.example.shehzad.finaltabchat.UserFragment;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;


public class ChatFragment extends Fragment {

    EditText msg;
    ListView msgList;
    String time;
    static String name="";
    UserFragment ud=new UserFragment();
    static MediaPlayer mp1;
    static  ChatArrayAdapter chatArrayAdapter;
    static Button btnSend;
    WebsocketClient web;
    static DBHanlder db;
    boolean side = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  System.out.println("IN Chat FRANGMENT ON CREATE");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy  hh:mm a");
        time = sdf.format(new Date()).toString();

        mp1 = MediaPlayer.create(getActivity(), R.raw.beep);

        web = new WebsocketClient();

        db = new DBHanlder(getActivity());

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Toast.makeText(getActivity(),"Web Socket Thread Running",Toast.LENGTH_SHORT).show();
                System.out.println("Web Socket Thread Running");

                web.connectWebSocket();

            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //System.out.println("IN chat FRANGMENT ON CREATEVIEW");

        View root = inflater.inflate(R.layout.chat_fragment, container, false);

        msg = (EditText)root.findViewById(R.id.txtMsg);
        msgList = (ListView)root.findViewById(R.id.msgList);
        btnSend = (Button)root.findViewById(R.id.btnSend);
        btnSend.setBackgroundColor(MainActivity.selectedColorRGB);

        msg.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {


                    return sendChatMessage();


                }
                return false;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                sendChatMessage();



            }
        });

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){

            chatArrayAdapter = new ChatArrayAdapter(getActivity(), R.layout.right,UserFragment.ud.getList());
            msgList.setAdapter(chatArrayAdapter);
            msgList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            chatArrayAdapter.notifyDataSetChanged();

                         }

    }

    private boolean sendChatMessage() {

        try {

          web.sendMessage(msg.getText().toString(), UserFragment.ud.getName());

        }catch (Exception e){

          Toast.makeText(getActivity(),"Server Closed",Toast.LENGTH_SHORT).show();

        }

         ChatMessage  c = new ChatMessage(false, msg.getText().toString(),MainActivity.thePic,time);

        db.addMsg(c);

        UserFragment.ud.addToList(c);

        chatArrayAdapter.notifyDataSetChanged();

        msg.setText("");

        side = !side;

        return true;
    }

    public void newMsgToUI( final String msg)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy  hh:mm a");

        final String time = sdf.format(new Date()).toString();

        MainActivity.mesg = msg;

        Handler refresh = new Handler(Looper.getMainLooper());

        refresh.post(new Runnable()
        {
            public void run()
            {
                ud.makeList(name);

                UserFragment.name = name;

                playBeep();

                if(MainActivity.flag){MainActivity.getInstance().createNotification(name);}

                ChatMessage  c = new ChatMessage(true,msg,time);

                UserFragment.ud.addToList(c);

                chatArrayAdapter.notifyDataSetChanged();

                db.addMsg(c);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {

           web.onCloseWindow();
        }
        catch (Exception e){

            //Toast.makeText(getActivity(),"Closing Client",Toast.LENGTH_SHORT).show();
        }
    }

public  void playBeep(){


    mp1.start();

}

}