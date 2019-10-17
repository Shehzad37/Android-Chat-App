package com.example.shehzad.finaltabchat;;import android.graphics.Bitmap;

/**
 * Created by Shehzad on 17-9-2016.
 */
public class ChatMessage {

    public boolean left;
    public String message;
    public Bitmap icon;
    public String time;

    public ChatMessage()
    {
         super();
    }

    public ChatMessage(boolean left, String message,Bitmap icon,String time)
    {
        super();
        this.left = left;
        this.message = message;
        this.icon = icon;
        this.time=time;
    }

    public ChatMessage(boolean left, String message,String time)
    {
        super();
        this.left = left;
        this.message = message;
        this.time=time;
    }

    public boolean isLeft() {return left;}

    public void setMessage(String message) {
        this.message = message;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLeft(boolean left) {this.left = left;}

    public String getMessage(){return message;}

}
