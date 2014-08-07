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
public class YunkuEngine extends ParentEngine {
    private static final String OAUTH_HOST = "http://a.goukuai.cn";
    private static final String LIB_HOST = "http://a-lib.goukuai.cn";
    private static final String URL_API_TOKEN = OAUTH_HOST + "/oauth2/token";
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


    public YunkuEngine(String username, String password, String clientId, String clientSecrect) {
        super(username, password, clientId, clientSecrect);
    }

    private YunkuEngine(String username, String password, String clientId, String clientSecrect, String token, String refreshToken) {
        super(username, password, clientId, clientSecrect);
        mToken = token;
        mRefreshToken = refreshToken;

    }

    /**
     * 获取token
     *
     * @return
     */
    public String accessToken(boolean isEnt) {

        String method = "POST";
        String url = URL_API_TOKEN;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", mUsername));
        params.add(new BasicNameValuePair("password", Util.convert2MD532(mPassword)));
        params.add(new BasicNameValuePair("client_id", mClientId));
        params.add(new BasicNameValuePair("client_secret", mClientSecret));
        params.add(new BasicNameValuePair("grant_type", isEnt ? "ent_password" : "password"));

        String result = NetConnection.sendRequest(url, method, params, null);
        ReturnResult returnResult = ReturnResult.create(result);
        LogPrint.print("accessToken:==>result:" + result);

        if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
            LogPrint.print("accessToken:==>StatusCode:200");
            OauthData data = OauthData.create(returnResult.getResult());
            mToken = data.getToken();
        }
        return result;
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
    public String createLib(String orgName, int orgCapacity, String storagePointName, String orgDesc) {
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

    public String bindLib(int orgId, String title, String linkUrl) {
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

    public String unBindLib(String orgClientId) {
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

    public YunkuEngine clone() {
        return new YunkuEngine(mUsername, mPassword, mClientId, mClientSecret, mToken, mRefreshToken);
    }

}
