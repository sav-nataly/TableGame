package ru.vsu.savina.tablegame.view.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.action.Chooseable;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;
import ru.vsu.savina.tablegame.view.fragment.ItemShopDialog;

public class PlayerItemsListAdapter extends RecyclerView.Adapter<PlayerItemsListAdapter.PlayerItemsListViewHolder> {
    private TableGamePlayer player;
    private List<Item> itemList;
    private ItemShopDialog dialog;

    public PlayerItemsListAdapter(TableGamePlayer player, ItemShopDialog dialog) {
        this.player = player;
        itemList = player.getItemMap().keySet().stream().sorted(Comparator.comparing(Chooseable::optionInfo)).collect(Collectors.toList());

        this.dialog = dialog;
    }

    @NonNull
    @Override
    public PlayerItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.added_item_button, parent, false);

        return new PlayerItemsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerItemsListViewHolder holder, int position) {
        holder.getDeleteButton().setOnClickListener(v -> holder.deleteItemFromPlayer(itemList.get(position)));
        holder.setDialog(dialog);

        TextView itemName = holder.getItemName();
        itemName.setText(itemList.get(position).optionInfo());

        TextView itemCost = holder.getItemCost();
        itemCost.setText(String.valueOf(itemList.get(position).getCost()));

        TextView itemAmount = holder.getItemAmount();
        itemAmount.setText(String.valueOf(player.getItemMap().get(itemList.get(position))));
    }

    @Override
    public int getItemCount() {
        return (int) player.getItemMap().size();
    }

    public void notifyNewItemAdded(Item item) {
        itemList = player.getItemMap().keySet().stream().sorted(Comparator.comparing(Chooseable::optionInfo)).collect(Collectors.toList());
        notifyItemInserted(itemList.indexOf(item));
    }

    public void notifyItemRemoved(Item item) {
        notifyItemRemoved(itemList.indexOf(item));
        itemList = player.getItemMap().keySet().stream().sorted(Comparator.comparing(Chooseable::optionInfo)).collect(Collectors.toList());
    }

    public void notifyItemChanged(Item item) {
        notifyItemChanged(itemList.indexOf(item));
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setPlayer(TableGamePlayer player) {
        this.player = player;
    }

    public static class PlayerItemsListViewHolder extends RecyclerView.ViewHolder {
        private final Button deleteButton;
        private final TextView itemName;
        private final TextView itemCost;
        private final TextView itemAmount;

        private ItemShopDialog dialog;

        public PlayerItemsListViewHolder(@NonNull View itemView) {
            super(itemView);

            deleteButton = (Button) itemView.findViewById(R.id.delete_player_button2);
            itemName = (TextView) itemView.findViewById(R.id.text_added_itemname);
            itemCost = (TextView) itemView.findViewById(R.id.text_added_itemcost);
            itemAmount = (TextView) itemView.findViewById(R.id.text_added_itemamount);
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

        public void deleteItemFromPlayer(Item item) {
            dialog.deleteItemFromPlayer(item);
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
