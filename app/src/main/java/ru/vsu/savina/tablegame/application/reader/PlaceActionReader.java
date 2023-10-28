package ru.vsu.savina.tablegame.application.reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import ru.vsu.savina.tablegame.game.engine.action.IPlaceAction;

public class PlaceActionReader {
    public static Map<Type, IPlaceAction> getPlaceActionMap(String text, String placeDirName, String actionDirName) {
        Map<Type, IPlaceAction> placeActionMap = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String placeClassName = obj.getString("placeType");
                String actionClassName = obj.getString("actionType");


                Class<?> c = Class.forName(actionDirName + actionClassName);
                Constructor<?> cons = c.getConstructor();
                Object object = cons.newInstance();

                placeActionMap.put(Class.forName(placeDirName + placeClassName), (IPlaceAction) object);
            }
        } catch (JSONException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return placeActionMap;
    }
}
