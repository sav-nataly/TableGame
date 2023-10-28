package ru.vsu.savina.tablegame.game.engine.player;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vsu.savina.tablegame.game.engine.item.Item;

public class Player {
    private String name;
    private Map<Item, Integer> itemMap;
    private double money;

    public Player(String name) {
        this.name = name;
        itemMap = new HashMap<>();
        money = 0;
    }

    public boolean hasItem(Item item) {
        if (itemMap.containsKey(item)){
            return itemMap.get(item) > 0;
        }

        return false;
    }

    public void addItem(Item item, int count) {
        if (count > 0) {
            if (itemMap.containsKey(item)) {
                itemMap.replace(item, itemMap.get(item) + count);
            } else {
                itemMap.put(item, count);
            }
        }
    }

    public void deleteItem(Item item, int count) {
        if (itemMap.containsKey(item)) {
            if (itemMap.get(item) > count) {
                itemMap.replace(item,  itemMap.get(item) - count);
            }
            else
                itemMap.remove(item);
        }
    }

    public void deleteItemsOfType(Type type) {
        if (hasItemOfType(type)) {
            List<Item> items = getItemsOfType(type);
            deleteAllItems(items);
        }
    }

    public void deleteAllItems(List<Item> itemList) {
        for (Item item : itemList)
            deleteItem(item, itemMap.get(item));
    }

    public void deleteAllItemsExceptType(Type type) {
        List<Item> deleteItems = new ArrayList<>();
        for (Item item: itemMap.keySet()) {
            if (!item.getClass().equals(type))
                deleteItems.add(item);
        }

        for (Item item : deleteItems) {
            deleteItem(item, itemMap.get(item));
        }
    }


    public List<Item> getItemsOfType(Type type) {
        return itemMap.keySet().stream().filter(i -> i.getClass().equals(type) && itemMap.get(i) > 0).collect(Collectors.toList());
    }

    public boolean hasItemOfType(Type type) {
        return itemMap.keySet().stream().anyMatch(i -> i.getClass().equals(type) && itemMap.get(i) > 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Item, Integer> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Item, Integer> itemMap) {
        this.itemMap = itemMap;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
