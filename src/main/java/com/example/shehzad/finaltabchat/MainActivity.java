package com.example.shehzad.finaltabchat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static ViewPager viewPager;
    Uri selectedImage;
    static String username,pass;
   int count;

    static String picturePath= "/storage/emulated/0/player.png";
    static Bitmap thePic = (BitmapFactory.decodeFile(picturePath));
    private String UPLOAD_URL ="http://192.168.43.14/upload.php";

    static  boolean flag;
   static boolean f;
    static String mesg;
   static MainActivity main;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    String new_name;
    String stats,msg;
    static int selectedColorRGB;
    int selectedColorR=0;
    int selectedColorG=0 ;
    int  selectedColorB=0;
    Bitmap bitmap;
    Toolbar toolbar;
    Window window;
    TabLayout tabLayout;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  db = new DBHanlder(this);
        System.out.println(selectedColorRGB);

        main=this;

    sp = getSharedPreferences("My", MODE_PRIVATE);
        username  = sp.getString("username", "");
        pass  = sp.getString("password", "");
        selectedColorRGB = sp.getInt("color", 0);



        if(selectedColorRGB == 0){

            selectedColorRGB = Color.rgb(0, 119, 204);


        }

        System.out.println(selectedColorRGB);
        //i.getStringExtra("USERNAME");
       // Toast.makeText(this,username,Toast.LENGTH_SHORT).show();

       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
       tabLayout.addTab(tabLayout.newTab().setText("Groups").setIcon(R.drawable.commandd));
        tabLayout.addTab(tabLayout.newTab().setText("Chat").setIcon(R.drawable.chat));
        tabLayout.addTab(tabLayout.newTab().setText("Online Users").setIcon(R.drawable.useronline));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        toolbar.setBackgroundColor((selectedColorRGB));
        window.setStatusBarColor(selectedColorRGB - 25);
        tabLayout.setBackgroundColor(selectedColorRGB);

       viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        navigationView.setNavigationItemSelectedListener(this);


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        try {

            if (JsonMethods.role_id.equals("1")) {

                menu.getItem(1).setVisible(false);
            }

        }
        catch (Exception e){


            Toast.makeText(getApplicationContext(),"No Connection",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            chooseImage();

            return true;
        }

        if (id == R.id.action_change) {

//                SharedPreferences sp = getSharedPreferences("My", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putBoolean("bool", false);
//                editor.commit();

                Intent intent = new Intent(this,Login.class);
                startActivity(intent);
                finish();
               // this.onStop();

            return true;
        }


        if(id == R.id.action_add){

        Intent i = new Intent(this,AddGroup.class);
            startActivity(i);

        }

        if(id == R.id.action_changr){




            final changecolor cp = new changecolor(MainActivity.this);
            cp.show();



/* On Click listener for the dialog, when the user select the color */
            Button okColor = (Button)cp.findViewById(R.id.okColorButton);

            okColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

        /* You can get single channel (value 0-255) */


                    selectedColorR = cp.getRed();
                    selectedColorB = cp.getBlue();
                    selectedColorG = cp.getGreen();



        /* Or the android RGB Color (see the android Color class reference) */
                    selectedColorRGB = cp.getColor();
                    System.out.println(selectedColorRGB);

//                    toolbar.setBackgroundColor((selectedColorRGB));
//                    window.setStatusBarColor(selectedColorRGB-3);
//                    tabLayout.setBackgroundColor(selectedColorRGB);

                    SharedPreferences.Editor ed = sp.edit();
                    ed.putInt("color",selectedColorRGB);
                    ed.commit();



                    cp.dismiss();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }


    public void chooseImage(){

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }


    public static MainActivity getInstance(){return   main;}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            selectedImage  = data.getData();
            performCrop();}

            else if (requestCode == 2 && resultCode == RESULT_OK && null != data){

            Bundle extras = data.getExtras();
            //get the cropped bitmap
           thePic = extras.getParcelable("data");

            uploadImage();

       //   System.out.println(getStringImage(thePic));

        }
    }

    public void createNotification(String name) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this,UserFragment.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);


        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New Message from " + name)
                .setContentText(mesg).setSmallIcon(R.drawable.gglogo)
                .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        count++;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        noti.number += count;

        notificationManager.notify(0, noti);

    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = false;
    }

    @Override
    protected void onPause() {
        super.onPause();

      //  System.out.println("In on pause");
        flag = true;

    }

    @Override
    protected void onStop() {
        super.onStop();
       // System.out.println("In on stop");

        //   f=false;
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //System.out.println("********" + size + " ************* ");

        bmp.compress(Bitmap.CompressFormat.JPEG,70, baos);
        byte[] imageBytes = baos.toByteArray();
        long size = imageBytes.length;
        //   System.out.println("********" + size/1000 +" kb"+ " ************* ");

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    private void performCrop(){

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(selectedImage, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent,2);

    }


    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(MainActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        try{
                        Toast.makeText(MainActivity.this, volleyError.getMessage() + " Upload Failed", Toast.LENGTH_LONG).show();}
                        catch (Exception e){

                            Toast.makeText(MainActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(thePic);

                //Getting Image Name
           //     String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("image", image);
               // params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listGroup) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }

      else  if (v.getId()==R.id.info_listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.use, menu);


        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

       final int poss = info.position;
        final String pos = GroupFragment.users.get(poss).getGroupId();
//        String posss = GroupInfo.users.get(poss).getGroupId();

        switch(item.getItemId()) {

            case R.id.delete:

                Handler refresh1 = new Handler(Looper.getMainLooper());

                refresh1.post(new Runnable() {
                    public void run() {

                        try {
                            JsonMethods jm = new JsonMethods();
                            JsonMethods.connect();
                            jm.removeGroup(pos);
                            System.out.println(poss + " " + GroupFragment.users.get(poss).getGroupId());
                            jm.getResponse();
                            stats = jm.getGrpUserInfo("status");
                            msg = jm.getGrpUserInfo("status_message");

                            if (stats.equals("true")) {
                                GroupFragment.users.remove(poss);
                                GroupFragment.adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                //return true;
                            } else if (stats.equals("false")) {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Connection ", Toast.LENGTH_LONG).show();
                        }


                    }

                });
                return  true;


            case R.id.rename:

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        this);
                alert.setTitle("Rename");

                final EditText input = new EditText(this);
                alert.setView(input);


                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        new_name = input.getEditableText().toString();

                        Handler refresh = new Handler(Looper.getMainLooper());

                        refresh.post(new Runnable() {
                            public void run() {

//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {

                                    try {
                                        JsonMethods jm = new JsonMethods();
                                        JsonMethods.connect();
                                        jm.renameGrp(new_name, pos);
                                        jm.getResponse();
                                        String status = jm.getGrpUserInfo("status");
                                        String msg = jm.getGrpUserInfo("status_message");

                                        //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                                        if (status.equals("true")) {

                                            GroupFragment.users.get(poss).setName(new_name);
                                            GroupFragment.adapter.notifyDataSetChanged();
                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                        } else if (status.equals("false")) {


                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (IOException | JSONException | NullPointerException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "No Connection ", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                            }
                        });

//                    }
//                });

                alert.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();






            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
