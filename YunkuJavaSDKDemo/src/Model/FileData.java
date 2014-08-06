package Model;

import org.json.JSONObject;

/**
 *解析文件列表
 */
public class FileData extends BaseData {
    private final static String KEY_HASH = "hash";
    private final static String KEY_DIR = "dir";
    private final static String KEY_FULLPATH = "fullpath";
    private final static String KEY_FILENAME = "filename";
    private final static String KEY_LAST_MEMBER_NAME = "last_member_name";
    private final static String KEY_LAST_DATELINE = "last_dateline";
    private final static String KEY_FILEHASH = "filehash";
    private final static String KEY_FILESIZE = "filesize";
    private final static String KEY_URI = "uri";
    private final static String KEY_PREVIEW = "preview";
    private final static String KEY_THUMBNAIL = "thumbnail";

    private String hash; //文件hash
    private int dir; //文件夹 1是 0 否
    private String fullpath;//文件全路径
    private String filename;  //文件名
    private String lastMemberName;  //最后修改人
    private long lastDateline; //最后修改时间
    private String filehash; //文件hahsh
    private long filesize; //文件大小
    private String uri;     //文件下载地址
    private String preview;     //文件预览地址
    private String thumbNail;  //文件缩略图
    private int orgShare;

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLastMemberName() {
        return lastMemberName;
    }

    public void setLastMemberName(String lastMemberName) {
        this.lastMemberName = lastMemberName;
    }

    public long getLastDateline() {
        return lastDateline;
    }

    public void setLastDateline(long lastDateline) {
        this.lastDateline = lastDateline;
    }

    public String getFilehash() {
        return filehash;
    }

    public void setFilehash(String filehash) {
        this.filehash = filehash;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }


    public static FileData create(String jsonString) {
        FileData data = new FileData();
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
            data.setDir(json.optInt(KEY_DIR));
            data.setFilehash(json.optString(KEY_FILEHASH));
            data.setFilename(json.optString(KEY_DIR));
            data.setFilesize(json.optLong(KEY_FILESIZE));
            data.setFullpath(json.optString(KEY_FULLPATH));
            data.setLastDateline(json.optLong(KEY_LAST_DATELINE));
            data.setLastMemberName(json.optString(KEY_LAST_MEMBER_NAME));
            data.setPreview(json.optString(KEY_PREVIEW));
            data.setUri(json.optString(KEY_URI));
            data.setThumbNail(json.optString(KEY_THUMBNAIL));
            data.setOrgShare(json.optInt("org_share"));

        } catch (Exception e) {
            json = null;
        }
        if (json == null) {
            return null;
        }
        data.setErrorCode(json.optInt(KEY_ERRORCODE));
        data.setErrorMsg(json.optString(KEY_ERRORMSG));
        return data;

    }

    public static FileData create(JSONObject obj) {
        if (obj == null) return null;

        FileData data=new FileData();
        data.setHash(obj.optString(KEY_HASH));
        data.setLastDateline(obj.optInt(KEY_LAST_DATELINE));
        data.setFullpath(obj.optString(KEY_FULLPATH));
        data.setFilename(obj.optString(KEY_FILENAME));
        data.setLastMemberName(obj.optString(KEY_LAST_MEMBER_NAME));
        data.setLastDateline(obj.optLong(KEY_LAST_DATELINE));
        data.setFilehash(obj.optString(KEY_FILEHASH));
        data.setFilesize(obj.optLong(KEY_FILESIZE));
        return data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getOrgShare() {
        return orgShare;
    }

    public void setOrgShare(int orgShare) {
        this.orgShare = orgShare;
    }
}
