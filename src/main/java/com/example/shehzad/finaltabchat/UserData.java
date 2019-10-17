package com.example.shehzad.finaltabchat;



import java.util.ArrayList;

public class UserData {



         String name;
    ArrayList<ChatMessage> chatList = new ArrayList<>();
    //String mSubTitle;
    ArrayList<UserData> usr  = new ArrayList<>();
    int mIcon;
    int icon;
    String id;



    ChatMessage  cm = new ChatMessage();
   // NavItem ni = new NavItem() ;
//	private boolean myMessage;

        public UserData() {
           // super();
           chatList = new ArrayList<ChatMessage>();
        }

    public UserData(String name,int mIcon,int icon){
        this.name=name;
        this.mIcon=mIcon;
        this.icon=icon;
        chatList = new ArrayList<ChatMessage>();
    }

    public UserData(String name,int mIcon){
        this.name=name;
        this.mIcon=mIcon;
        chatList = new ArrayList<ChatMessage>();
    }


        public void setName(String name){this.name=name;}

        public String getName(){
            return name;
        }

        public UserData(String name) {
            this.name=name;
            usr = new ArrayList<UserData>();
          //  chatList = new ArrayList<ChatMessage>();
                                    }

        public void addToList(ChatMessage lv){
            chatList.add(lv);
        }

        public void clearList(){
            chatList.clear();
        }

        public ArrayList<ChatMessage> getList(){
            return chatList;
        }

        public String toString()
        {
            return this.name;
        }

    public String getNameAtPos(ArrayList<UserData> user,int pos){

       String name = user.get(pos).getName();
        return name;
    }

    public void addUserToGroup(UserData ud)
    {

     usr.add(ud);

    }

    public UserData(String name,int icon,String id){


        this.name = name;
        this.mIcon  =icon;
        this.id = id;

    }

    public ArrayList<UserData> getMemList(){


        return usr;


    }

    public String getGroupId(){
        

        return id;
    }

   public void setMemList(ArrayList<UserData> list){


        this.usr = list;


    }

}


