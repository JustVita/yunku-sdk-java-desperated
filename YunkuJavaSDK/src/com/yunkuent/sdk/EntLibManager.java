package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.Util;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 企业库管理http请求类
 */
public class EntLibManager extends ParentEngine {

    private static final String URL_API_CREATE_LIB = LIB_HOST + "/1/org/create";
    private static final String URL_API_GET_LIB_LIST = LIB_HOST + "/1/org/ls";
    private static final String URL_API_BIND = LIB_HOST + "/1/org/bind";
    private static final String URL_API_UNBIND = LIB_HOST + "/1/org/unbind";

    private static final String URL_API_GET_MEMBERS = LIB_HOST + "/1/org/get_members";
    private static final String URL_API_ADD_MEMBERS = LIB_HOST + "/1/org/add_member";
    private static final String URL_API_SET_MEMBER_ROLE = LIB_HOST + "/1/org/set_member_role";
    private static final String URL_API_DEL_MEMBER = LIB_HOST + "/1/org/del_member";
    private static final String URL_API_GET_GROUPS = LIB_HOST + "/1/org/get_groups";
    private static final String URL_API_ADD_GROUP = LIB_HOST + "/1/org/add_group";
    private static final String URL_API_DEL_GROUP = LIB_HOST + "/1/org/del_group";
    private static final String URL_API_SET_GROUP_ROLE = LIB_HOST + "/1/org/set_group_role";
    private static final String URL_API_DESTROY = LIB_HOST + "/1/org/destroy";
    private static final String URL_API_GET_MEMBER = LIB_HOST + "/1/org/get_member";
    private static final String URL_API_SET = LIB_HOST + "/1/org/set";


    //    @Deprecated
    public EntLibManager(String username, String password, String clientId, String clientSecret) {
        super(username, password, clientId, clientSecret);
    }

//    public EntLibManager(){
//        super(Config.UESRNAME, Config.PASSWORD, Config.CLIENT_ID, Config.CLIENT_SECRET);
//    }

    private EntLibManager(String username, String password, String clientId, String clientSecrect, String token) {
        super(username, password, clientId, clientSecrect);
        mToken = token;

    }

    /**
     * 创建库
     *
     * @param orgName
     * @param orgCapacity
     * @param storagePointName
     * @param orgDesc
     * @return
     */
    public String create(String orgName, String orgCapacity, String storagePointName, String orgDesc) {
        String method = "POST";
        String url = URL_API_CREATE_LIB;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_name", orgName));
        params.add(new BasicNameValuePair("org_capacity", String.valueOf(orgCapacity)));
        params.add(new BasicNameValuePair("storage_point_name", storagePointName));
        params.add(new BasicNameValuePair("org_desc", orgDesc));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));

        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 获取库列表
     *
     * @return
     */
    public String getLibList() {
        String method = "GET";
        String url = URL_API_GET_LIB_LIST;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));

        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 获取库授权
     *
     * @param orgId
     * @param title
     * @param linkUrl 可以不传
     * @return
     */
    public String bind(int orgId, String title, String linkUrl) {
        String method = "POST";
        String url = URL_API_BIND;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", String.valueOf(orgId)));
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("url", linkUrl));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 取消库授权
     *
     * @param orgClientId
     * @return
     */
    public String unBind(String orgClientId) {
        String method = "POST";
        String url = URL_API_UNBIND;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


//    public String getRoles() {
//        String method = "GET";
//        String url = URL_API_GET_ROLES;
//        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("token", mToken));
//        params.add(new BasicNameValuePair("token_type", "ent"));
//        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
//        return NetConnection.sendRequest(url, method, params, null);
//    }

    /**
     * 获取库成员列表
     *
     * @param start
     * @param size
     * @param orgId
     * @return
     */
    public String getMembers(int start, int size, int orgId) {
        String method = "GET";
        String url = URL_API_GET_MEMBERS;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("start", start + ""));
        params.add(new BasicNameValuePair("size", size + ""));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 添加库成员
     *
     * @param orgId
     * @param roleId
     * @param memberIds
     * @return
     */
    public String addMembers(int orgId, int roleId, int[] memberIds) {
        String method = "POST";
        String url = URL_API_ADD_MEMBERS;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("role_id", roleId + ""));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("member_ids", Util.intArrayToString(memberIds, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 修改库成员角色
     *
     * @param orgId
     * @param roleId
     * @param memberIds
     * @return
     */
    public String setMemberRole(int orgId, int roleId, int[] memberIds) {
        String method = "POST";
        String url = URL_API_SET_MEMBER_ROLE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("role_id", roleId + ""));
        params.add(new BasicNameValuePair("member_ids", Util.intArrayToString(memberIds, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 删除库成员
     *
     * @param orgId
     * @param memberIds
     * @return
     */
    public String delMember(int orgId, int[] memberIds) {
        String method = "POST";
        String url = URL_API_DEL_MEMBER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("member_ids", Util.intArrayToString(memberIds, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 获取库分组列表
     *
     * @param orgId
     * @return
     */
    public String getGroups(int orgId) {
        String method = "GET";
        String url = URL_API_GET_GROUPS;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 库上添加分组
     *
     * @param orgId
     * @param groupId
     * @param roleId
     * @return
     */
    public String addGroup(int orgId, int groupId, int roleId) {
        String method = "POST";
        String url = URL_API_ADD_GROUP;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("group_id", groupId + ""));
        params.add(new BasicNameValuePair("role_id", roleId + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 删除库上的分组
     *
     * @param orgId
     * @param groupId
     * @return
     */
    public String delGroup(int orgId, int groupId) {
        String method = "POST";
        String url = URL_API_DEL_GROUP;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("group_id", groupId + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 修改库上分组的角色
     *
     * @param orgId
     * @param groupId
     * @param roleId
     * @return
     */
    public String setGroupRole(int orgId, int groupId, int roleId) {
        String method = "POST";
        String url = URL_API_SET_GROUP_ROLE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        params.add(new BasicNameValuePair("group_id", groupId + ""));
        params.add(new BasicNameValuePair("role_id", roleId + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 删除库
     *
     * @param orgClientId
     * @return
     */
    public String destroy(String orgClientId) {
        String method = "POST";
        String url = URL_API_DESTROY;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_client_id", orgClientId + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 修改库信息
     *
     * @param orgId
     * @param orgName
     * @param orgCapacity
     * @param orgDes
     * @param orgLogo
     * @return
     */
    public String set(int orgId, String orgName, String orgCapacity, String orgDes, String orgLogo) {
        String method = "POST";
        String url = URL_API_SET;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgId + ""));
        if (orgName != null && !orgName.isEmpty()) {
            params.add(new BasicNameValuePair("org_name", orgName));
        }
        if (orgCapacity != null && !orgCapacity.isEmpty()) {
            params.add(new BasicNameValuePair("org_capacity", orgCapacity + ""));
        }
        if (orgDes != null && !orgDes.isEmpty()) {
            params.add(new BasicNameValuePair("org_desc", orgDes));
        }

        if (orgDes != null && !orgDes.isEmpty()) {
            params.add(new BasicNameValuePair("org_logo", orgLogo));
        }
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 查询库成员信息
     *
     * @param orgid
     * @param type
     * @param ids
     * @return
     */
    public String getMember(int orgid, MemberType type, String[] ids) {
        String method = "GET";
        String url = URL_API_GET_MEMBER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("org_id", orgid + ""));
        params.add(new BasicNameValuePair("type", type.toString().toLowerCase()));
        params.add(new BasicNameValuePair("ids", Util.strArrayToString(ids, ",") + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 复制EntLibManager对象
     *
     * @return
     */
    public EntLibManager clone() {
        return new EntLibManager(mUsername, mPassword, mClientId, mClientSecret, mToken);
    }


}
