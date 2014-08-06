package Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 */
public class UpdateDataList extends BaseData {

    private ArrayList<UpdateItem> list;//更新

    public ArrayList<UpdateItem> getList() {
        return list;
    }

    public void setList(ArrayList<UpdateItem> list) {
        this.list = list;
    }

    public static UpdateDataList create(String jsonString) {
        if (jsonString == null) return null;
        UpdateDataList data = new UpdateDataList();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            ArrayList<UpdateItem> items = new ArrayList<UpdateItem>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonItem = jsonArray.optJSONObject(i);
                UpdateItem item = UpdateItem.create(jsonItem);
                if (item != null) {
                    items.add(item);
                }
            }
            data.setList(items);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return data;
    }
}
