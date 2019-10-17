package com.example.shehzad.finaltabchat;

import android.os.Build;
import android.util.Log;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by Shehzad on 10/10/2016.
 */
public class WebsocketClient {

    String name= MainActivity.username;
    private WebSocketClient mWebSocketClient;
    UserFragment us=new UserFragment();
    ChatFragment cf = new ChatFragment();
    MainActivity main;



   public void WebSocketClient() {
    }

    public void connectWebSocket() {

        Log.i("name", name);

        URI uri;
        try {
            uri = new URI("ws://191.168.6.12:8787");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri,new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("LIST," + name);
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String tokken){

                if (tokken.startsWith("LIST")) {
                    System.out.println("list returned");
                    getUserList(tokken);
                }
                if (tokken.startsWith("NEWUSER")) {
                    String names[] = tokken.split(",");
                    us.addNewUser(names[1]);
                    UserFragment.newInstance();
                }

                if (tokken.startsWith("REMOVE")) {
                    System.out.println("Removed");
                    String names[] = tokken.split(",");
                    us.removeUser(names[1]);
                }
                if (tokken.startsWith("NEW_MESSAGE")) {
                    String msg[] = tokken.split(",");
                    ChatFragment.name = msg[1];
                    System.out.println(ChatFragment.name);
                    cf.newMsgToUI(msg[2]);
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    public void sendMessage(String msg,String rcvr) {

        mWebSocketClient.send("MESSAGE,"+name+","+rcvr+","+msg);

    }


    public void onCloseWindow() {
        System.out.println("Close window");
        mWebSocketClient.send("LOGOUT,"+name);

    }


    public void getUserList(String tokken) {
        String names[] = tokken.split(",");
        for (int i = 1; i < names.length; i++) {
            //gui.addUser(new UserData(names[i]));
            us.addNewUser(names[i]);
//			UserFragment.adapter.notifyDataSetChanged();
            System.out.println("Simple List echo");
            //UserFragment.adapter.notifyDataSetChanged();
        }
    }
}
