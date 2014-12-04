package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.Util;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntManager extends ParentEngine {

    private static final String URL_API_GET_GROUPS = LIB_HOST + "/1/ent/get_groups";
    private static final String URL_API_GET_MEMBERS = LIB_HOST + "/1/ent/get_members";
    private static final String URL_API_GET_ROLES = LIB_HOST + "/1/ent/get_roles";
    private static final String URL_API_SYNC_MEMBER = LIB_HOST + "/1/ent/sync_member";
    private static final String URL_API_GET_MEMBER_FILE_LINK = LIB_HOST + "/1/ent/get_member_file_link";
    private static final String URL_API_GET_MEMBER_BY_OUT_ID = LIB_HOST + "/1/ent/get_member_by_out_id";

    private static final String URL_API_ADD_SYNC_MEMBER = LIB_HOST + "/1/ent/add_sync_member";
    private static final String URL_API_DEL_SYNC_MEMBER = LIB_HOST + "/1/ent/del_sync_member";
    private static final String URL_API_ADD_SYNC_GROUP = LIB_HOST + "/1/ent/add_sync_group";
    private static final String URL_API_DEL_SYNC_GROUP = LIB_HOST + "/1/ent/del_sync_group";
    private static final String URL_API_ADD_SYNC_GROUP_MEMBER = LIB_HOST + "/1/ent/add_sync_group_member";
    private static final String URL_API_DEL_SYNC_GROUP_MEMBER = LIB_HOST + "/1/ent/del_sync_group_member";
    private static final String URL_API_GET_GROUP_MEMBERS = LIB_HOST + "/1/ent/get_group_members";

    //    @Deprecated
    public EntManager(String username, String password, String clientId, String clientSecret) {
        super(username, password, clientId, clientSecret);
    }

//    public EntManager(){
//        super(Config.UESRNAME, Config.PASSWORD, Config.CLIENT_ID, Config.CLIENT_SECRET);
//    }

    protected EntManager(String username, String password, String clientId, String clientSecrect, String token) {
        super(username, password, clientId, clientSecrect);
        mToken = token;
    }

//    /**
//     * 同步成员和组织架构
//     * @param membersJsonStr
//     * @return
//     */
//
//    public String syncMembers(String membersJsonStr) {
//        String method = "POST";
//        String url = URL_API_SYNC_MEMBER;
//        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("token", mToken));
//        params.add(new BasicNameValuePair("token_type", "ent"));
//        params.add(new BasicNameValuePair("members", membersJsonStr));
//        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
//        return NetConnection.sendRequest(url, method, params, null);
//    }

    /**
     * 获取角色
     *
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
     *
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
     *
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

    /**
     * 根据成员id获取成员个人库外链
     *
     * @param memberId
     * @return
     */

    public String getMemberFileLink(int memberId, boolean fileOnly) {
        String method = "GET";
        String url = URL_API_GET_MEMBER_FILE_LINK;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("member_id", memberId + ""));
        if (fileOnly) {
            params.add(new BasicNameValuePair("file", "1"));
        }
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 根据外部成员id获取成员信息
     *
     * @return
     */
    public String getMemberByOutid(String outIds[]) {
        if (outIds == null) {
            throw new NullPointerException("outIds is null");
        }
        return getMemberByIds(null, outIds);

    }

    /**
     * 根据外部成员登录帐号获取成员信息
     *
     * @return
     */
    public String getMemberByUserId(String[] userIds) {
        if (userIds == null) {
            throw new NullPointerException("userIds is null");
        }
        return getMemberByIds(userIds, null);
    }

    private String getMemberByIds(String[] userIds, String[] outIds) {
        String method = "GET";
        String url = URL_API_GET_MEMBER_BY_OUT_ID;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        if (outIds != null) {
            params.add(new BasicNameValuePair("out_ids", Util.strArrayToString(outIds, ",") + ""));
        } else {
            params.add(new BasicNameValuePair("user_ids", Util.strArrayToString(userIds, ",") + ""));
        }
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 添加或修改同步成员
     *
     * @param oudId
     * @param memberName
     * @param account
     * @param memberEmail
     * @param memberPhone
     * @param password    如果需要由够快验证帐号密码,密码为必须参数
     * @return
     */

    public String addSyncMember(String oudId, String memberName, String account, String memberEmail, String memberPhone, String password) {
        String method = "POST";
        String url = URL_API_ADD_SYNC_MEMBER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("out_id", oudId));
        params.add(new BasicNameValuePair("member_name", memberName));
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("member_email", memberEmail));
        params.add(new BasicNameValuePair("member_phone", memberPhone));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 删除同步成员
     *
     * @param members
     * @return
     */
    public String delSyncMember(String[] members) {
        String method = "POST";
        String url = URL_API_DEL_SYNC_MEMBER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("members", Util.strArrayToString(members, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 添加或修改同步分组
     *
     * @param outId
     * @param name
     * @param parentOutId
     * @return
     */
    public String addSyncGroup(String outId, String name, String parentOutId) {

        String method = "POST";
        String url = URL_API_ADD_SYNC_GROUP;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("out_id", outId));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("parent_out_id", parentOutId));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 删除同步分组
     *
     * @param groups
     * @return
     */
    public String delSyncGroup(String[] groups) {
        String method = "POST";
        String url = URL_API_DEL_SYNC_GROUP;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("groups", Util.strArrayToString(groups, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 添加同步分组的成员
     *
     * @param groupOutId
     * @param members
     * @return
     */
    public String addSyncGroupMember(String groupOutId, String[] members) {
        String method = "POST";
        String url = URL_API_ADD_SYNC_GROUP_MEMBER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("group_out_id", groupOutId));
        params.add(new BasicNameValuePair("members", Util.strArrayToString(members, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 删除同步分组的成员
     *
     * @param groupOutId
     * @param members
     * @return
     */
    public String delSyncGroupMember(String groupOutId, String[] members) {
        String method = "POST";
        String url = URL_API_DEL_SYNC_GROUP_MEMBER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("group_out_id", groupOutId));
        params.add(new BasicNameValuePair("members", Util.strArrayToString(members, ",")));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 分组成员列表
     *
     * @param groupId
     * @param start
     * @param size
     * @param showChild
     * @return
     */
    public String getGroupMembers(int groupId, int start, int size, boolean showChild) {
        String method = "GET";
        String url = URL_API_GET_GROUP_MEMBERS;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("group_id", groupId + ""));
        params.add(new BasicNameValuePair("start", start + ""));
        params.add(new BasicNameValuePair("size", size + ""));
        params.add(new BasicNameValuePair("show_child", (showChild ? 1 : 0) + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 复制一个EntManager对象
     *
     * @return
     */
    public EntManager clone() {
        return new EntManager(mUsername, mPassword, mClientId, mClientSecret, mToken);
    }

}
