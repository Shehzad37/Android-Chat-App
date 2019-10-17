package com.example.shehzad.finaltabchat;

import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class AddGroup extends AppCompatActivity {

    Button done;
    ImageView pic;
    EditText name;
    String sts,msg,id;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        done  = (Button)findViewById(R.id.btn_DONE);
        done.setBackgroundColor(MainActivity.selectedColorRGB);
        name = (EditText)findViewById(R.id.input_grp_name);
        pic = (ImageView)findViewById(R.id.grp_img);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(MainActivity.selectedColorRGB);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String name1  = name.getText().toString();



//                Handler refresh = new Handler(Looper.getMainLooper());
//
//                refresh.post(new Runnable() {
//                    public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JsonMethods jm = new JsonMethods();
                            JsonMethods.connect();
                            jm.createGroup(name1);
                            jm.getResponse();
                            sts = jm.getCreateGrpInfo("status");
                            msg = jm.getCreateGrpInfo("status_message");
                            id = jm.getCreateGrpInfo("group_id");


                        } catch (IOException|JSONException|NullPointerException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"No Connection", Toast.LENGTH_LONG).show();
                        }

                        try {
                            if (sts.equals("true")) {

                                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                                GroupFragment.users.add(new UserData(name1, R.drawable.boy, id));
                                GroupFragment.adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                finish();
                            } else {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e){


                            Toast.makeText(getApplicationContext(),"No Connection", Toast.LENGTH_LONG).show();
                        }




                    }
                });




                    }
               // });





           // }
        });



    }
}
