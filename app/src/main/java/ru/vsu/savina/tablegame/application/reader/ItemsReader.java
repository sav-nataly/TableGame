package ru.vsu.savina.tablegame.application.reader;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class ItemsReader {
    public static Map<Item, Integer> getItemMap(String text, String fullClassName) {
        try {
            JSONArray jsonArray = new JSONArray(text);
            return getItemMap(jsonArray, fullClassName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public static Map<Item, Integer> getItemMap(JSONArray jsonArray, String fullClassName) {
        Map<Item, Integer> itemMap = new HashMap<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int number = obj.getInt("number");
                Item item = getItem(obj, fullClassName);
                if (item != null)
                    itemMap.put(item, number);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemMap;
    }

    public static List<Item> getItemList(JSONArray jsonArray, String fullClassName) {
        List<Item> itemList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Item item = getItem(obj, fullClassName);
                if (item != null)
                    itemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public static Item getItem(JSONObject jsonObject, String fullClassName) {
        try {
            String className = jsonObject.getString("type");
            Gson gson = new Gson();

            return (Item) gson.fromJson(String.valueOf(jsonObject), Class.forName(fullClassName + className));
        } catch (ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
