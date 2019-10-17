package com.example.shehzad.finaltabchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/

public class Login extends AppCompatActivity {

    String pass,email;
    boolean flag = false;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    EditText txt_email,txt_pass;
    Button btn_login;
    String status,message;
    CheckBox checkBox,admin;
    TextView SignUp,forgot;
    Window window;
    boolean b;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.rgb(128, 85, 0));


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        MainActivity.f = false;

        //    this.setTitle(getResources().getString(R.string.ep));




      sp = getSharedPreferences("My",MODE_PRIVATE);
      editor = sp.edit();

        b= sp.getBoolean("bool", false);



//        p = sp.getString("PIN","");




        checkBox = (CheckBox)findViewById(R.id.check_remember);
        //admin = (CheckBox)findViewById(R.id.check_admin);
        txt_email = (EditText)findViewById(R.id.input_email);
      txt_pass = (EditText)findViewById(R.id.input_password);
       btn_login = (Button)findViewById(R.id.btn_login);
        SignUp=(TextView)findViewById(R.id.signUp);
        //forgot = (TextView)findViewById(R.id.txt_forgot);
        SpannableString content = new SpannableString("Sign up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        SignUp.setText(content);
//        SpannableString content1 = new SpannableString("Forgot Password?");
//        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
//        forgot.setText(content1);


        if(b){

            txt_email.setText(sp.getString("username", ""));
            txt_pass.setText(sp.getString("password", ""));

        }






//        forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://www.google.com"));
//                startActivity(intent);
//            }
//        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://users.guftagu.net/register.php"));
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act();
            }
        });

  checkBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          if (((CheckBox) v).isChecked()) {

              flag = true;

          }

      }
  });


//       admin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (((CheckBox) v).isChecked()) {
//
//                   MainActivity.f = true;
//
//                }
//
//            }
//        });

    }

    public void act() {

        email = txt_email.getText().toString();
        pass = txt_pass.getText().toString();


        final ProgressDialog dialog = ProgressDialog.show(this, "", "Please Wait...",
                true);


//        Handler refresh = new Handler(Looper.getMainLooper());
//
//        refresh.post(new Runnable()
//        {
//            public void run()
//            {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {


//
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    JsonMethods jm = new JsonMethods();
                    JsonMethods.connect();
                    jm.postLoginData(email, pass);
                    jm.getResponse();
                    status = jm.getString("status");
                    message = jm.getString("status_message");

                    //Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                    e.getMessage();
                }

            }
        });


        //        }
////        }).start();


        //  }
        //});
        try {
            if (status.isEmpty()) {

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "No Connection " + status, Toast.LENGTH_SHORT).show();

                // txt_email.setFocusable(true);
                //  txt_pass.setText("");
            } else {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        dialog.dismiss();

                        if (!validate() || status.equals("false")) {


                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            // txt_email.setFocusable(true);
                            //  txt_pass.setText("");
                        } else {


                            editor.putBoolean("bool", flag);
                            editor.putString("username", email);
                            editor.putString("password", pass);
                            editor.commit();


                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }


                }, 3000);
            }
        } catch (NullPointerException e) {

            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();

        }
    }
    public boolean validate() {

        boolean valid = true;


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            txt_email.setError("enter a valid email address");
            txt_email.selectAll();
           txt_email.setFocusableInTouchMode(true);
            txt_email.setFocusable(true);
            txt_email.requestFocus();
            txt_email.setSelection(txt_email.length());

            valid = false;
        } else {
            txt_email.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {



            txt_pass.setError("between 4 and 10 alphanumeric characters");
            txt_pass.selectAll();
            txt_pass.setFocusableInTouchMode(true);
            txt_pass.setFocusable(true);
            txt_pass.requestFocus();
            txt_pass.setSelection(txt_pass.length());
            valid = false;

        } else {
            txt_pass.setError(null);
        }

        return valid;
    }

}
