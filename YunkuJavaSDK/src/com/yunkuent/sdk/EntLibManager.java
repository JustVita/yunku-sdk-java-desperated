package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Brandon on 2014/8/6.
 */
public class EntLibManager extends ParentEngine {

    private static final String URL_API_CREATE_LIB = LIB_HOST + "/1/org/create";
    private static final String URL_API_GET_LIB_LIST = LIB_HOST + "/1/org/ls";
    private static final String URL_API_BIND = LIB_HOST + "/1/org/bind";
    private static final String URL_API_UNBIND = LIB_HOST + "/1/org/unbind";
    private static final String URL_API_FILELIST = LIB_HOST + "/1/file/ls";
    private static final String URL_API_UPDATE_LIST = LIB_HOST + "/1/file/updates";
    private static final String URL_API_FILE_INFO = LIB_HOST + "/1/file/info";
    private static final String URL_API_CREATE_FOLDER = LIB_HOST + "/1/file/create_folder";
    private static final String URL_API_CREATE_FILE = LIB_HOST + "/1/file/create_file";
    private static final String URL_API_DEL_FILE = LIB_HOST + "/1/file/del";
    private static final String URL_API_MOVE_FILE = LIB_HOST + "/1/file/move";
    private static final String URL_API_LINK_FILE = LIB_HOST + "/1/file/link";
    private static final String URL_API_SENDMSG = LIB_HOST + "/1/file/sendmsg";
    private static final String URL_API_GET_ROLES = LIB_HOST + "/1/org/get_roles";
    private static final String URL_API_GET_MEMBERS = LIB_HOST + "/1/org/get_members";
    private static final String URL_API_ADD_MEMBERS = LIB_HOST + "/1/org/add_member";
    private static final String URL_API_SET_MEMBER_ROLE = LIB_HOST + "/1/org/set_member_role";
    private static final String URL_API_DEL_MEMBER = LIB_HOST + "/1/org/del_member";
    private static final String URL_API_GET_GROUPS = LIB_HOST + "/1/org/get_groups";
    private static final String URL_API_ADD_GROUP = LIB_HOST + "/1/org/add_group";
    private static final String URL_API_DEL_GROUP = LIB_HOST + "/1/org/del_group";
    private static final String URL_API_SET_GROUP_ROLE = LIB_HOST + "/1/org/set_group_role";


    public EntLibManager(String username, String password, String clientId, String clientSecrect) {
        super(username, password, clientId, clientSecrect);
    }

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
    public String create(String orgName, int orgCapacity, String storagePointName, String orgDesc) {
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


    public String getLibList() {
        String method = "GET";
        String url = URL_API_GET_LIB_LIST;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));

        return NetConnection.sendRequest(url, method, params, null);
    }

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

    public String getFileList(String orgClientId, String orgClientSecret, int dateline, int start, String fullPath) {
        String method = "GET";
        String url = URL_API_FILELIST;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("start", start + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);

    }

    public String getUpdateList(String orgClientId, String orgClientSecret, int dateline, boolean isCompare, long fetchDateline) {
        String method = "GET";
        String url = URL_API_UPDATE_LIST;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        if (isCompare) {
            params.add(new BasicNameValuePair("mode", "compare"));
        }
        params.add(new BasicNameValuePair("fetch_dateline", fetchDateline + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }

    public String getFileInfo(String orgClientId, String orgClientSecret, int dateline, String fullPath) {
        String method = "GET";
        String url = URL_API_FILE_INFO;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }

    public String createFolder(String orgClientId, String orgClientSecret, int dateline, String fullPath, String opName) {
        String method = "POST";
        String url = URL_API_CREATE_FOLDER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 通过文件流上传
     *
     * @param orgClientId
     * @param orgClientSecret
     * @param dateline
     * @param fullPath
     * @param opName
     * @param stream
     * @param fileName
     * @return
     */
    public String createFile(String orgClientId, String orgClientSecret, int dateline, String fullPath, String opName, FileInputStream stream, String fileName) {
        try {
            if (stream.available() > 51200) {
                LogPrint.print("文件大小超过50MB");
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("org_client_id", orgClientId));
            params.add(new BasicNameValuePair("dateline", dateline + ""));
            params.add(new BasicNameValuePair("fullpath", fullPath));
            params.add(new BasicNameValuePair("op_name", opName));
            params.add(new BasicNameValuePair("token", mToken));
            params.add(new BasicNameValuePair("filefield", "file"));

            MsMultiPartFormData multipart = new MsMultiPartFormData(URL_API_CREATE_FILE, "UTF-8");
            multipart.addFormField("org_client_id", orgClientId);
            multipart.addFormField("dateline", dateline + "");
            multipart.addFormField("fullpath", fullPath);
            multipart.addFormField("op_name", opName);
            multipart.addFormField("token", mToken);
            multipart.addFormField("filefield", "file");
            multipart.addFormField("sign", generateSign(paramSorted(params), orgClientSecret));

            multipart.addFilePart("file", stream, fileName);

            return multipart.finish();

        } catch (IOException ex) {
            System.err.println(ex);
        }

        return "";

    }

    /**
     * 通过本地路径上传
     *
     * @param orgClientId
     * @param orgClientSecret
     * @param dateline
     * @param fullPath
     * @param opName
     * @param localPath
     * @return
     */
    public String createFile(String orgClientId, String orgClientSecret, int dateline, String fullPath, String opName, String localPath) {
        File file = new File(localPath.trim());
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                return createFile(orgClientId, orgClientSecret, dateline, fullPath, opName, inputStream, Util.getNameFromPath(localPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            LogPrint.print("file not exist");
        }

        return "";

    }

    public String del(String orgClientId, String orgClientSecret, int dateline, String fullPath, String opName) {
        String method = "POST";
        String url = URL_API_DEL_FILE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }

    public String move(String orgClientId, String orgClientSecret, int dateline, String fullPath, String destFullPath, String opName) {
        String method = "POST";
        String url = URL_API_MOVE_FILE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("dest_fullpath", destFullPath));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }

    public String link(String orgClientId, String orgClientSecret, int dateline, String fullPath) {
        String method = "POST";
        String url = URL_API_LINK_FILE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }


    public String sendmsg(String orgClientId, String orgClientSecret, int dateline, String title, String text, String image, String linkUrl, String opName) {
        String method = "POST";
        String url = URL_API_SENDMSG;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", orgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("image", image));
        params.add(new BasicNameValuePair("url", linkUrl));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params), orgClientSecret)));
        return NetConnection.sendRequest(url, method, params, null);
    }

    public String getRoles() {
        String method = "GET";
        String url = URL_API_GET_ROLES;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("token_type", "ent"));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

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

    public EntLibManager clone() {
        return new EntLibManager(mUsername, mPassword, mClientId, mClientSecret, mToken);
    }


}
