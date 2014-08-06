package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 更新列表单项
 */
public class UpdateItem {
    private int memberId; //用户id
    private int act;//执行的操作
    private int dateline;//时间戳
    private String meberName; //成员名
    private ArrayList<FileData> list; //相关文件列表

    public String getMeberName() {
        return meberName;
    }

    public void setMeberName(String meberName) {
        this.meberName = meberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public int getDateline() {
        return dateline;
    }

    public void setDateline(int dateline) {
        this.dateline = dateline;
    }

    public ArrayList<FileData> getList() {
        return list;
    }

    public void setList(ArrayList<FileData> list) {
        this.list = list;
    }

    public static UpdateItem create(JSONObject jsonObject) {
        if (jsonObject == null) return null;

        UpdateItem data = new UpdateItem();
        data.setAct(jsonObject.optInt("act"));
        data.setMemberId(jsonObject.optInt("member_id"));
        data.setDateline(jsonObject.optInt("dateline"));
        data.setMeberName(jsonObject.optString("member_name"));

        JSONArray array=jsonObject.optJSONArray("files");
        ArrayList<FileData> items = new ArrayList<FileData>();
        if(array!=null){
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonItem = array.optJSONObject(i);
                FileData item = FileData.create(jsonItem);
                if (item != null) {
                    items.add(item);
                }
            }

        }
        data.setList(items);

        return  data;
    }

}
