package ru.vsu.savina.tablegame.application.reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.situation.ItemGiveSituation;
import ru.vsu.savina.tablegame.game.impl.situation.ItemTakeSituation;
import ru.vsu.savina.tablegame.game.impl.situation.ItemsFoundSituation;
import ru.vsu.savina.tablegame.game.impl.situation.ShootSituation;
import ru.vsu.savina.tablegame.game.impl.situation.Situation;

public class SituationsReader {
    public static List<Situation> getSituationCards(String text, String itemsClassName) {
        List<Situation> situationList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String className = obj.getString("type");
                Situation situation;
                switch (className) {
                    case "ItemsFoundSituation":
                        situation = getItemsFoundSituation(obj, itemsClassName);
                        break;
                    case "ShootSituation":
                        situation = getShootSituation(obj);
                        break;
                    case "ItemTakeSituation":
                        situation = getItemTakeSituation(obj, itemsClassName);
                        break;
                    default:
                        situation = getItemGiveSituation(obj, itemsClassName);
                }

                if (situation != null)
                    situationList.add(situation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return situationList;
    }


    private static ItemsFoundSituation getItemsFoundSituation(JSONObject jsonObject, String itemsClassName) {
        try {
            List<Item> itemList = ItemsReader.getItemList(jsonObject.getJSONArray("items"), itemsClassName);
            return new ItemsFoundSituation(itemList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ItemTakeSituation getItemTakeSituation(JSONObject jsonObject, String itemsClassName) {
        try {
            Item item = ItemsReader.getItem(jsonObject.getJSONObject("item"), itemsClassName);
            return new ItemTakeSituation(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ItemGiveSituation getItemGiveSituation(JSONObject jsonObject, String itemsClassName) {
        try {
            Item item = ItemsReader.getItem(jsonObject.getJSONObject("item"), itemsClassName);
            return new ItemGiveSituation(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ShootSituation getShootSituation(JSONObject jsonObject) {
        try {
            boolean canUseSmokeBomb = jsonObject.getBoolean("canUseSmokeBomb");
            int shootNumber = jsonObject.getInt("shootNumber");
            return new ShootSituation(shootNumber, canUseSmokeBomb);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
