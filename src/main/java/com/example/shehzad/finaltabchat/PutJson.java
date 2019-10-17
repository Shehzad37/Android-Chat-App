package com.example.shehzad.finaltabchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shehzad on 10/21/2016.
 */
public class PutJson {

    JSONObject data ;
    String username,password,tokken,user_id,login_id,domain_id,group_id,group_name;

    public  PutJson(){


        data = new JSONObject();
    }



    public JSONObject putsNamePassJson(String username,String password) throws JSONException {

        data = new JSONObject();
        data.put("username",username);
        data.put("password",password);

        return data;
    }


    public JSONObject putCreateGrpJson(String tokken,String domain_id,String login_id,String grp_name) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("domain_id",domain_id);
        data.put("login_id", login_id);
        data.put("group_name",grp_name);

        return data;
    }

    public JSONObject putLogoutJson(String tokken,String login_id) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("login_id", login_id);
        return data;
    }

    public JSONObject putFtchGrpByAdminJson(String tokken,String user_id,String admin_id) throws JSONException {

        data = new JSONObject();
       data.put("tokken", tokken);
       data.put("login_id",user_id);
        //  myUserJson.put("domain_id",domain_id);
        data.put("admin_id",admin_id);

        return data;
    }


    public JSONObject putFtchGrpByUserJson(String tokken,String user_id,String admin_id) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("login_id",user_id);
        //  myUserJson.put("domain_id",domain_id);
        data.put("user_id",admin_id);

        return data;
    }

    public JSONObject putFtchGrpByDomainJson(String tokken,String user_id,String domain_id) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("login_id",user_id);
         data.put("domain_id",domain_id);

        return data;
    }

    public JSONObject putAddUserInGrpJson(String tokken,String login_id,String user_id,String Group_id) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("login_id",login_id);
        data.put("user_id",user_Ids(user_id));
        data.put("group_id",Group_id);

        return data;
    }
    public JSONArray user_Ids(String id) throws JSONException {

        JSONArray array = new JSONArray();
        JSONObject iid = new JSONObject();
        iid.put("id", id);
        array.put(iid);


        return array;
    }

    public JSONObject putFtchUsersByDomainJson(String tokken,String login_id,String domain_id) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("login_id",login_id);
        data.put("domain_id",domain_id);


        return data;
    }

    public JSONObject putFtchUsersByGroupJson(String tokken,String login_id,String group_id) throws JSONException {

        data = new JSONObject();
        data.put("tokken", tokken);
        data.put("login_id",login_id);
        data.put("group_id",group_id);


        return data;
    }

    public JSONObject putRmvGrpJson(String group_id,String tokken,String login_id) throws JSONException {

        data = new JSONObject();
        data.put("group_id",group_id);
        data.put("tokken", tokken);
        data.put("login_id",login_id);
        return data;
    }

    public JSONObject putRmvUserJson(String group_id,String tokken,String login_id,String userId) throws JSONException {

        data = new JSONObject();
        data.put("group_id",group_id);
        data.put("tokken", tokken);
        data.put("user_id",login_id);
        data.put("login_id",userId);
        return data;
    }

    public JSONObject putBlockUserJson(String group_id,String tokken,String login_id) throws JSONException {

        data = new JSONObject();
        data.put("login_id",group_id);
        data.put("tokken", tokken);
        data.put("user_id",login_id);
        return data;
    }

    public JSONObject putRenameGrpJson(String group_id,String tokken,String login_id,String newGrpNme) throws JSONException {

        data = new JSONObject();
        data.put("login_id",group_id);
        data.put("tokken", tokken);
        data.put("group_id",login_id);
        data.put("new_group_name",newGrpNme);
        return data;
    }

    public JSONObject putUnBlockUserJson(String group_id,String tokken,String login_id,String groupp_id) throws JSONException {

        data = new JSONObject();
        data.put("login_id",group_id);
        data.put("tokken", tokken);
        data.put("user_id",login_id);
        data.put("group_id",groupp_id);
        return data;
    }


    public JSONObject putGrpBlockUsersJson(String group_id,String tokken,String login_id,String UsrId) throws JSONException {

        data = new JSONObject();
        data.put("login_id",group_id);
        data.put("tokken", tokken);
        data.put("user_id",login_id);
        data.put("group_id",UsrId);
        return data;
    }

    public JSONObject putFtchGrpBlockUsersJson(String group_id,String tokken,String UsrId) throws JSONException {

        data = new JSONObject();
        data.put("login_id",group_id);
        data.put("tokken", tokken);
        data.put("group_id",UsrId);
        return data;
    }

}
