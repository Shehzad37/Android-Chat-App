package com.example.shehzad.finaltabchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Shehzad on 10/18/2016.
 */
public class JsonMethods {

   static URLConnection con;
   static URL url;
   static String tokken;
    static String domain_id;
   static String role_id,user_id;
    PutJson pj = new PutJson();
    String jsonResponse;

    JsonMethods() throws IOException {

       url = new URL("http://api.guftagu.net/v3/");

    }

  static public void connect() throws IOException {

        con = url.openConnection();
        con.setDoOutput(true);
      System.out.println("connected");

    }

    public void postLoginData(String username,String password) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put("order", "login");
        obj.put("type", "user");
        obj.put("data",pj.putsNamePassJson(username, password));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();
    }

    public void postLogoutData() throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put("order", "logout");
        obj.put("type", "user");
        obj.put("data",pj.putLogoutJson(tokken, user_id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();
    }

    public void createGroup(String grpName) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put("order", "Insert");
        obj.put("type", "add_group");
        obj.put("data", pj.putCreateGrpJson(tokken, domain_id, user_id, grpName));

      //  obj.put("domain_id",domain_id);

        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();
    }

    public void getResponse()

    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  String jsonResponse;

        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonResponse = sb.toString();

        System.out.println("Response: " + jsonResponse);

    }

public String getCreateGrpInfo(String type) throws JSONException {

    String statusOfLogin = "";
    String statusMessage = "";
    String groupId = "";


    JSONObject   temp = new JSONObject(jsonResponse);
    statusOfLogin = temp.get("status").toString();
    statusMessage = temp.get("status_message").toString();

    if(statusOfLogin.equals("true")) {

        JSONObject data = temp.getJSONObject("data");
        groupId = data.get("group_id").toString();
        System.out.println(groupId);
    }


    if(type.equals("status"))
        return statusOfLogin;
    if(type.equals("status_message"))
        return statusMessage;
    if(type.equals("group_id"))
        return groupId;


    return null;
}
    public String getString(String dataType){

      String statusOfLogin = "";
      String statusMessage = "";

      try {

          JSONObject   temp = new JSONObject(jsonResponse);
          statusOfLogin = temp.get("status").toString();
          statusMessage = temp.get("status_message").toString();

          if(statusOfLogin.equals("true")) {

              JSONObject data = temp.getJSONObject("data");
              tokken = data.get("tokken").toString();
              domain_id = data.get("domain_id").toString();
              role_id = data.get("role_id").toString();
              user_id = data.get("user_id").toString();
          }
          System.out.println(tokken +" "+ domain_id+" "+role_id+" "+user_id);

      } catch (JSONException e) {
          e.printStackTrace();
      }

      if(dataType.equals("status"))
          return statusOfLogin;
     if(dataType.equals("status_message"))
          return statusMessage;

        return null;

    }

    public void fetchGroups() throws IOException, JSONException{


        JSONObject obj = new JSONObject();
        obj.put("order", "Fetch");
        obj.put("type", "get_domain_groups");
        obj.put("data",pj.putFtchGrpByDomainJson(tokken,user_id,domain_id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted="+obj.toString());
        ps.close();

    }

    public ArrayList<UserData> getGrpInfo() {

        ArrayList<UserData> users = new ArrayList<>();

        String statusOfLogin = "";
        String statusMessage = "";
        String GroupName = "";
        String Group_id;

        try {

            JSONObject temp = new JSONObject(jsonResponse);
            statusOfLogin = temp.get("status").toString();
            statusMessage = temp.get("status_message").toString();




            if (statusOfLogin.equals("true")) {

                    JSONArray data = temp.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject nam_grp = data.getJSONObject(i);
                    GroupName = nam_grp.get("Group_Name").toString();
                    Group_id = nam_grp.get("Group_Id").toString();
                    System.out.println(GroupName);
                   // GroupFragment.users.add(new UserData(GroupName, R.drawable.boy, Group_id));
                    users.add(new UserData(GroupName, R.drawable.boy, Group_id));   // allNames.add(name);
                }

            } else {

                System.out.println("### SSSORRRRRRRRRRRYY ###");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }


    public void fetchGroupByAdmin() throws JSONException, IOException {


            JSONObject obj = new JSONObject();
            obj.put("order","Fetch");
            obj.put("type", "get_groups_by_admin");
            obj.put("data", pj.putFtchGrpByAdminJson(tokken, user_id, user_id));
            System.out.println(obj.toString());
            PrintStream ps = new PrintStream(con.getOutputStream());
            ps.print("posted=" + obj.toString());
            ps.close();

        }



    public void insertUsersInGroup(String grp_Id,String us_id) throws JSONException, IOException {


        JSONObject obj = new JSONObject();
        obj.put("order", "Insert");
        obj.put("type", "add_user_in_group");
        obj.put("data",pj.putAddUserInGrpJson(tokken, user_id, us_id, grp_Id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();


    }

    public String getGrpUserInfo(String type){

        String statusOfLogin = "";
        String statusMessage = "";

        try {

            JSONObject temp = new JSONObject(jsonResponse);
            statusOfLogin = temp.get("status").toString();
            statusMessage = temp.get("status_message").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(type.equals("status"))
            return statusOfLogin;
        if(type.equals("status_message"))
            return statusMessage;

        return null;

    }

    public void fetchUsersByDomain() throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "Fetch");
        obj.put("type", "get_domain_users");
        obj.put("data",pj.putFtchUsersByDomainJson(tokken, user_id, domain_id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();
    }

//    public ArrayList<UserData> getDomainUserInfo(){
//
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        String userName = "";
//        String user_id;
//        ArrayList<UserData>  domainUsers= new ArrayList<>();
//
//        try {
//
//            JSONObject temp = new JSONObject(jsonResponse);
//            statusOfLogin = temp.get("status").toString();
//            statusMessage = temp.get("status_message").toString();
//
//            if (statusOfLogin.equals("true")) {
//
//                JSONArray data = temp.getJSONArray("data");
//                for (int i = 0; i < data.length(); i++) {
//                    JSONObject nam_grp = data.getJSONObject(i);
//                    userName = nam_grp.get("user_name").toString();
//                    user_id = nam_grp.get("user_id").toString();
//                    System.out.println(user_id+ " "+userName);
//                    domainUsers.add(new UserData(userName,R.drawable.boy,user_id));
//                    // allNames.add(name);
//                }
//
//            } else {
//
//                System.out.println("### SSSORRRRRRRRRRRYY ###");
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//return domainUsers;
//    }

    public void fetchUsersByGroup(String grpId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "Fetch");
        obj.put("type", "get_group_users");
        obj.put("data",pj.putFtchUsersByGroupJson(tokken, user_id, grpId));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();
    }

    public ArrayList<UserData> getGroupUserInfo(){

        String statusOfLogin = "";
        String statusMessage = "";
        String userName = "";
        String user_id;
        ArrayList<UserData>  domainUsers= new ArrayList<>();

        try {

            JSONObject temp = new JSONObject(jsonResponse);
            statusOfLogin = temp.get("status").toString();
            statusMessage = temp.get("status_message").toString();

            if (statusOfLogin.equals("true")) {

                JSONArray data = temp.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject nam_grp = data.getJSONObject(i);
                    userName = nam_grp.get("user_name").toString();
                    user_id = nam_grp.get("user_id").toString();
                    System.out.println(user_id+ " "+userName);
                    domainUsers.add(new UserData(userName,R.drawable.boy,user_id));
                    // allNames.add(name);
                }

            } else {

                System.out.println("### SSSORRRRRRRRRRRYY ###");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return domainUsers;
    }

    public void removeGroup(String grpId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "Delete");
        obj.put("type", "remove_group");
        obj.put("data",pj.putRmvGrpJson(grpId, tokken, user_id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public String getremoveGroupInfo(String type) throws JSONException {
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        JSONObject temp = new JSONObject(jsonResponse);
//        statusOfLogin = temp.get("status").toString();
//        statusMessage = temp.get("status_message").toString();
//
//        if(type.equals("status"))
//            return statusOfLogin;
//        if(type.equals("status_message"))
//            return statusMessage;
//
//        return null;
//    }
// Remove user


    public void removeUser(String grpId,String userId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "Delete");
        obj.put("type", "remove_user_from_group");
        obj.put("data", pj.putRmvUserJson(grpId, tokken, userId, user_id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public String getremoveUserInfo(String type) throws JSONException {
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        JSONObject temp = new JSONObject(jsonResponse);
//        statusOfLogin = temp.get("status").toString();
//        statusMessage = temp.get("status_message").toString();
//
//        if(type.equals("status"))
//            return statusOfLogin;
//        if(type.equals("status_message"))
//            return statusMessage;
//
//        return null;
//    }


    public void blockUser(String userId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "block_user");
        obj.put("type", "block_user");
        obj.put("data", pj.putBlockUserJson(user_id, tokken, userId));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public String getrBlockUserInfo(String type) throws JSONException {
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        JSONObject temp = new JSONObject(jsonResponse);
//        statusOfLogin = temp.get("status").toString();
//        statusMessage = temp.get("status_message").toString();
//
//        if(type.equals("status"))
//            return statusOfLogin;
//        if(type.equals("status_message"))
//            return statusMessage;
//
//        return null;
//    }

    public void renameGrp(String grpName,String grpId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "Update");
        obj.put("type", "update_group_name");
        obj.put("data", pj.putRenameGrpJson(user_id, tokken, grpId, grpName));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public String getrRenameGrpInfo(String type) throws JSONException {
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        JSONObject temp = new JSONObject(jsonResponse);
//        statusOfLogin = temp.get("status").toString();
//        statusMessage = temp.get("status_message").toString();
//
//        if(type.equals("status"))
//            return statusOfLogin;
//        if(type.equals("status_message"))
//            return statusMessage;
//
//        return null;
//    }

    public void fetchGroupByUser() throws JSONException, IOException {


        JSONObject obj = new JSONObject();
        obj.put("order","Fetch");
        obj.put("type", "get_groups_by_user");
        obj.put("data", pj.putFtchGrpByUserJson(tokken, user_id, user_id));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

    public ArrayList<UserData> getGrpByUSerInfo() {

    ArrayList<UserData> users = new ArrayList<>();

        String statusOfLogin = "";
        String statusMessage = "";
        String GroupName = "";
        String Group_id;

        try {

            JSONObject temp = new JSONObject(jsonResponse);
            statusOfLogin = temp.get("status").toString();
            statusMessage = temp.get("status_message").toString();




            if (statusOfLogin.equals("true")) {

                JSONArray data = temp.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject nam_grp = data.getJSONObject(i);
                    GroupName = nam_grp.get("group_name").toString();
                    Group_id = nam_grp.get("group_id").toString();
                    System.out.println(GroupName);
                   // GroupFragment.users.add(new UserData(GroupName,R.drawable.boy,Group_id));
                    // allNames.add(name);
                    users.add(new UserData(GroupName,R.drawable.boy,Group_id));
                }

            } else {

                System.out.println("### SSSORRRRRRRRRRRYY ###");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void unblockUser(String userId,String GroupId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "unblock_user_from_group");
        obj.put("type", "unblock_user_from_group");
        obj.put("data", pj.putUnBlockUserJson(user_id, tokken, userId,GroupId));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public String getUnBlockUserInfo(String type) throws JSONException {
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        JSONObject temp = new JSONObject(jsonResponse);
//        statusOfLogin = temp.get("status").toString();
//        statusMessage = temp.get("status_message").toString();
//
//        if(type.equals("status"))
//            return statusOfLogin;
//        if(type.equals("status_message"))
//            return statusMessage;
//
//        return null;
//    }


    public void blockGrpUser(String userId,String grpId) throws JSONException, IOException {

        JSONObject obj = new JSONObject();
        obj.put("order", "block_user_from_group");
        obj.put("type", "block_user_from_group");
        obj.put("data", pj.putGrpBlockUsersJson(user_id, tokken, userId,grpId));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public String getrGrpBlockUserInfo(String type) throws JSONException {
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        JSONObject temp = new JSONObject(jsonResponse);
//        statusOfLogin = temp.get("status").toString();
//        statusMessage = temp.get("status_message").toString();
//
//        if(type.equals("status"))
//            return statusOfLogin;
//        if(type.equals("status_message"))
//            return statusMessage;
//
//        return null;
//    }
    public void fetchGrpBlockedUser(String grpId) throws JSONException, IOException {


        JSONObject obj = new JSONObject();
        obj.put("order","Fetch");
        obj.put("type","get_group_blocked_users");
      obj.put("data", pj.putFtchGrpBlockUsersJson(user_id, tokken,grpId));
        System.out.println(obj.toString());
        PrintStream ps = new PrintStream(con.getOutputStream());
        ps.print("posted=" + obj.toString());
        ps.close();

    }

//    public ArrayList<UserData> getBlockedUSerInfo() {
//
//        ArrayList<UserData> users = new ArrayList<>();
//
//        String statusOfLogin = "";
//        String statusMessage = "";
//        String GroupName = "";
//        String Group_id;
//
//        try {
//
//            JSONObject temp = new JSONObject(jsonResponse);
//            statusOfLogin = temp.get("status").toString();
//            statusMessage = temp.get("status_message").toString();
//
//
//
//
//            if (statusOfLogin.equals("true")) {
//
//                JSONArray data = temp.getJSONArray("data");
//                for (int i = 0; i < data.length(); i++) {
//                    JSONObject nam_grp = data.getJSONObject(i);
//                    GroupName = nam_grp.get("user_name").toString();
//                    Group_id = nam_grp.get("user_id").toString();
//                    System.out.println(GroupName);
//                    // GroupFragment.users.add(new UserData(GroupName,R.drawable.boy,Group_id));
//                    // allNames.add(name);
//                    users.add(new UserData(GroupName,R.drawable.boy,Group_id));
//                }
//
//            } else {
//
//                System.out.println("### SSSORRRRRRRRRRRYY ###");
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }




}
