package ru.vsu.savina.tablegame.view.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.view.fragment.ItemShopDialog;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    private Map<Item, Integer> itemMap;
    private List<Item> itemList;

    private ItemShopDialog dialog;

    public ItemListAdapter(Map<Item, Integer> itemMap, ItemShopDialog dialog) {
        this.itemMap = itemMap;
        itemList = itemMap.keySet().stream().sorted(Comparator.comparing(Chooseable::optionInfo)).collect(Collectors.toList());

        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.button_item, parent, false);

        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.getLayout().setOnClickListener(v -> holder.addItemToPlayer(itemList.get(position)));
        holder.setDialog(dialog);
        TextView itemName = holder.getItemName();
        itemName.setText(itemList.get(position).optionInfo());

        TextView itemCost = holder.getItemCost();
        itemCost.setText(String.valueOf(itemList.get(position).getCost()));

        TextView itemAmount = holder.getItemAmount();
        itemAmount.setText(String.valueOf(itemMap.get(itemList.get(position))));
    }

    @Override
    public int getItemCount() {
        return itemMap.size();
    }


    public List<Item> getItemList() {
        return itemList;
    }


    public static class ItemListViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final TextView itemName;
        private final TextView itemCost;
        private final TextView itemAmount;

        private ItemShopDialog dialog;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.layout_item_button);
            itemName = (TextView) itemView.findViewById(R.id.text_itemname);
            itemCost = (TextView) itemView.findViewById(R.id.text_itemcost);
            itemAmount = (TextView) itemView.findViewById(R.id.text_itemamount);
        }

        public LinearLayout getLayout() {
            return layout;
        }

        public void addItemToPlayer(Item item) {
            dialog.addItemToPlayer(item);
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemCost() {
            return itemCost;
        }

        public TextView getItemAmount() {
            return itemAmount;
        }

        public void setDialog(ItemShopDialog dialog) {
            this.dialog = dialog;
        }
    }

}
