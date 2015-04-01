package com.yunkuent.sdk;

import com.yunkuent.sdk.upload.UploadCallBack;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntFileManager extends SignAbility implements HostConfig {

    private static final int UPLOAD_LIMIT_SIZE = 52428800;
    private static final String URL_API_FILELIST = LIB_HOST + "/1/file/ls";
    private static final String URL_API_UPDATE_LIST = LIB_HOST + "/1/file/updates";
    private static final String URL_API_FILE_INFO = LIB_HOST + "/1/file/info";
    private static final String URL_API_CREATE_FOLDER = LIB_HOST + "/1/file/create_folder";
    private static final String URL_API_CREATE_FILE = LIB_HOST + "/1/file/create_file";
    private static final String URL_API_DEL_FILE = LIB_HOST + "/1/file/del";
    private static final String URL_API_MOVE_FILE = LIB_HOST + "/1/file/move";
    private static final String URL_API_LINK_FILE = LIB_HOST + "/1/file/link";
    private static final String URL_API_SENDMSG = LIB_HOST + "/1/file/sendmsg";
    private static final String URL_API_GET_LINK = LIB_HOST + "/1/file/links";
    private static final String URL_API_UPDATE_COUNT = LIB_HOST + "/1/file/updates_count";


    private String mOrgClientId;

    public EntFileManager(String orgClientId, String orgClientSecret) {
        mOrgClientId = orgClientId;
        mClientSecret = orgClientSecret;//orgClientSecret
    }

    /**
     * 获取文件列表
     *
     * @param dateline
     * @param start
     * @param fullPath
     * @return
     */
    public String getFileList(int dateline, int start, String fullPath) {
        String method = "GET";
        String url = URL_API_FILELIST;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("start", start + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);

    }

    /**
     * 获取更新列表
     *
     * @param dateline
     * @param isCompare
     * @param fetchDateline
     * @return
     */
    public String getUpdateList(int dateline, boolean isCompare, long fetchDateline) {
        String method = "GET";
        String url = URL_API_UPDATE_LIST;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        if (isCompare) {
            params.add(new BasicNameValuePair("mode", "compare"));
        }
        params.add(new BasicNameValuePair("fetch_dateline", fetchDateline + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 获取文件信息
     *
     * @param dateline
     * @param fullPath
     * @return
     */
    public String getFileInfo(int dateline, String fullPath) {
        String method = "GET";
        String url = URL_API_FILE_INFO;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 创建文件夹
     *
     * @param dateline
     * @param fullPath
     * @param opName
     * @return
     */
    public String createFolder(int dateline, String fullPath, String opName) {
        String method = "POST";
        String url = URL_API_CREATE_FOLDER;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 通过文件流上传
     *
     * @param dateline
     * @param fullPath
     * @param opName
     * @param stream
     * @param fileName
     * @return
     */
    public String createFile(int dateline, String fullPath, String opName, FileInputStream stream, String fileName) {
        try {
            if (stream.available() > UPLOAD_LIMIT_SIZE) {
                LogPrint.print("文件大小超过50MB");
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
            params.add(new BasicNameValuePair("dateline", dateline + ""));
            params.add(new BasicNameValuePair("fullpath", fullPath));
            params.add(new BasicNameValuePair("op_name", opName));
            params.add(new BasicNameValuePair("filefield", "file"));

            MsMultiPartFormData multipart = new MsMultiPartFormData(URL_API_CREATE_FILE, "UTF-8");
            multipart.addFormField("org_client_id", mOrgClientId);
            multipart.addFormField("dateline", dateline + "");
            multipart.addFormField("fullpath", fullPath);
            multipart.addFormField("op_name", opName);
            multipart.addFormField("filefield", "file");
            multipart.addFormField("sign", generateSign(paramSorted(params)));

            multipart.addFilePart("file", stream, fileName);

            return multipart.finish();

        } catch (IOException ex) {
            System.err.println(ex);
        }

        return "";
    }


    /**
     * @param dateline
     * @param fullPath
     * @param opName
     * @param opId
     * @param localFilePath
     * @param overWrite
     * @param callBack
     */
    public void uploadByBlock(int dateline, String fullPath, String opName, int opId, String localFilePath,
                              boolean overWrite, UploadCallBack callBack) {
        Thread thread = new Thread(new UploadRunnable(URL_API_CREATE_FILE, localFilePath, fullPath, opName, opId, mOrgClientId, dateline, callBack, mClientSecret, overWrite));
        thread.start();
    }

    /**
     * 通过本地路径上传
     *
     * @param dateline
     * @param fullPath
     * @param opName
     * @param localPath
     * @return
     */
    public String createFile(int dateline, String fullPath, String opName, String localPath) {
        File file = new File(localPath.trim());
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                return createFile(dateline, fullPath, opName, inputStream, Util.getNameFromPath(localPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            LogPrint.print("file not exist");
        }

        return "";

    }

    /**
     * 删除文件
     *
     * @param dateline
     * @param fullPaths
     * @param opName
     * @return
     */
    public String del(int dateline, String fullPaths, String opName) {
        String method = "POST";
        String url = URL_API_DEL_FILE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpaths", fullPaths));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 移动文件
     *
     * @param dateline
     * @param fullPaths
     * @param destFullPath
     * @param opName
     * @return
     */
    public String move(int dateline, String fullPaths, String destFullPath, String opName) {
        String method = "POST";
        String url = URL_API_MOVE_FILE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpaths", fullPaths));
        params.add(new BasicNameValuePair("dest_fullpath", destFullPath));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }

    /**
     * 获取文件链接
     *
     * @param dateline
     * @param fullPath
     * @param deadline
     * @param authType
     * @param password
     * @return
     */
    public String link(int dateline, String fullPath, int deadline, AuthType authType, String password) {
        String method = "POST";
        String url = URL_API_LINK_FILE;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("fullpath", fullPath));

        if (deadline != 0) {
            params.add(new BasicNameValuePair("deadline", deadline + ""));
        }

        if (!authType.equals(AuthType.DEFAULT)) {
            params.add(new BasicNameValuePair("auth", authType.toString().toLowerCase()));
        }
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 发送消息
     *
     * @param dateline
     * @param title
     * @param text
     * @param image
     * @param linkUrl
     * @param opName
     * @return
     */
    public String sendmsg(int dateline, String title, String text, String image, String linkUrl, String opName) {
        String method = "POST";
        String url = URL_API_SENDMSG;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("image", image));
        params.add(new BasicNameValuePair("url", linkUrl));
        params.add(new BasicNameValuePair("op_name", opName));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 获取当前库所有外链
     *
     * @param dateline
     * @return
     */
    public String links(int dateline, boolean fileOnly) {
        String method = "GET";
        String url = URL_API_GET_LINK;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        if (fileOnly) {
            params.add(new BasicNameValuePair("file", "1"));
        }
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 文件更新数量
     *
     * @param dateline
     * @param beginDateline
     * @param endDateline
     * @param showDelete
     * @return
     */
    public String getUpdateCounts(int dateline, long beginDateline, long endDateline, boolean showDelete) {
        String method = "GET";
        String url = URL_API_UPDATE_COUNT;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("org_client_id", mOrgClientId));
        params.add(new BasicNameValuePair("dateline", dateline + ""));
        params.add(new BasicNameValuePair("begin_dateline", beginDateline + ""));
        params.add(new BasicNameValuePair("end_dateline", endDateline + ""));
        params.add(new BasicNameValuePair("showdel", (showDelete ? 1 : 0) + ""));
        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
        return NetConnection.sendRequest(url, method, params, null);
    }


    /**
     * 复制一个EntFileManager对象
     *
     * @return
     */
    public EntFileManager clone() {
        return new EntFileManager(mOrgClientId, mClientSecret);
    }

    public enum AuthType {
        DEFAULT,
        PREVIEW,
        DOWNLOAD,
        UPLOAD
    }

}
