package com.yunkuent.sdk;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntManager extends ParentEngine {

    private static final String URL_API_GET_GROUPS= LIB_HOST + "/1/ent/get_groups";
    private static final String URL_API_GET_MEMBERS = LIB_HOST + "/1/ent/get_members";
    private static final String URL_API_GET_ROLES= LIB_HOST + "/1/ent/get_roles";
    private static final String URL_API_SYNC_MEMBER = LIB_HOST + "/1/ent/sync_member";

    public EntManager(String username, String password, String clientId, String clientSecrect) {
        super(username, password, clientId, clientSecrect);
    }

//    public String syncMembers(String members) {
//        String method = "POST";
//        String url = URL_API_SYNC_MEMBER;
//        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("token", mToken));
//        params.add(new BasicNameValuePair("token_type", "ent"));
//        params.add(new BasicNameValuePair("members", members));
//        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
//        return NetConnection.sendRequest(url, method, params, null);
//    }

    /**
     * 获取角色
     * @return
     */
    public String getRoles() {
        String method = "GET";
        String url = URL_API_GET_ROLES;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 获取成员
     * @param start
     * @param size
     * @return
     */
    public String getMembers(int start, int size) {
        String method = "GET";
        String url = URL_API_GET_MEMBERS;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("start", start + ""));
        params.add(new BasicNameValuePair("size", size + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 获取分组
     * @return
     */
    public String getGroups() {
        String method = "GET";
        String url = URL_API_GET_GROUPS;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


}
