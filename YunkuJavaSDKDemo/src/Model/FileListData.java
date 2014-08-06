package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 解析文件列表
 */
public class FileListData extends BaseData {
    private static final String LOG_TAG = "FileListData";
    private static final String KEY_LIST = "list";
    private static final String KEY_COUNT = "count";

    private ArrayList<FileData> list;//文件列表
    private int count;//文件数量

    public static FileListData create(String jsonString) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (Exception e) {
            json = null;
        }
        if (json == null) {
            return null;
        }
        FileListData data = new FileListData();
        data.setErrorCode(json.optInt(KEY_ERRORCODE));
        data.setErrorMsg(json.optString(KEY_ERRORMSG));
        data.setCount(json.optInt(KEY_COUNT));

        JSONArray array = json.optJSONArray(KEY_LIST);
        ArrayList<FileData> items = new ArrayList<FileData>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonItem = array.optJSONObject(i);
            FileData item = FileData.create(jsonItem);
            if (item != null) {
                items.add(item);
            }
        }
        data.setList(items);
        return data;
    }

    public ArrayList<FileData> getList() {
        return list;
    }

    public void setList(ArrayList<FileData> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
