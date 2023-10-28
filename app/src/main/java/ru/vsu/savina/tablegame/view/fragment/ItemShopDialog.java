package ru.vsu.savina.tablegame.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.game.engine.item.Item;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;
import ru.vsu.savina.tablegame.view.listadapter.ItemListAdapter;
import ru.vsu.savina.tablegame.view.listadapter.PlayerItemsListAdapter;
import ru.vsu.savina.tablegame.view.listadapter.PlayerListGameStartAdapter;

public class ItemShopDialog extends DialogFragment {
    private List<TableGamePlayer> playerList;
    private Map<Item, Integer> itemMap;

    private PlayerListGameStartAdapter playerAdapter;
    private ItemListAdapter itemListAdapter;
    private PlayerItemsListAdapter playerItemsAdapter;

    private TableGamePlayer activePlayer;

    public interface ItemShopListener {
        void onDialogReadyClick();
    }

    ItemShopListener listener;

    public ItemShopDialog(List<TableGamePlayer> playerList, Map<Item, Integer> itemMap) {
        this.playerList = playerList;
        this.itemMap = itemMap;

        activePlayer = playerList.get(0);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ItemShopListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("ItemShopListener not implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), com.google.android.material.R.style.Widget_Material3_MaterialCalendar_Fullscreen);
        View view = inflater.inflate(R.layout.dialog_start_items, null);

        playerAdapter = new PlayerListGameStartAdapter(playerList, this);
        itemListAdapter = new ItemListAdapter(itemMap, this);
        playerItemsAdapter = new PlayerItemsListAdapter(activePlayer, this);

        RecyclerView playerView = (RecyclerView) view.findViewById(R.id.playerlist_layout);
        playerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        playerView.setAdapter(playerAdapter);

        RecyclerView itemsView = (RecyclerView) view.findViewById(R.id.items_layout);
        itemsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemsView.setAdapter(itemListAdapter);

        RecyclerView playerItemsView = (RecyclerView) view.findViewById(R.id.added_items_layout);
        playerItemsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        playerItemsView.setAdapter(playerItemsAdapter);

        builder.setView(view).setPositiveButton("Готово", (dialog, which) -> listener.onDialogReadyClick());


        return builder.create();
    }

    public void showPlayerItems(TableGamePlayer player) {
        activePlayer = player;
        playerItemsAdapter.setPlayer(player);
        playerItemsAdapter.notifyDataSetChanged();
    }

    public void addItemToPlayer(Item item) {
        if (itemMap.get(item) > 0) {
            itemMap.replace(item, itemMap.get(item) - 1);
            activePlayer.addItem(item, 1);
            activePlayer.setMoney(activePlayer.getMoney() - item.getCost());

            playerAdapter.notifyItemChanged(playerList.indexOf(activePlayer));
            itemListAdapter.notifyItemChanged(itemListAdapter.getItemList().indexOf(item));

            if (activePlayer.getItemMap().get(item) == 1)
                playerItemsAdapter.notifyNewItemAdded(item);
            else
                playerItemsAdapter.notifyItemChanged(item);
        }
    }

    public void deleteItemFromPlayer(Item item) {
        itemMap.replace(item, itemMap.get(item) + 1);
        activePlayer.deleteItem(item, 1);
        activePlayer.setMoney(item.getCost() + activePlayer.getMoney());

        playerAdapter.notifyItemChanged(playerList.indexOf(activePlayer));
        itemListAdapter.notifyItemChanged(itemListAdapter.getItemList().indexOf(item));
        if (activePlayer.getItemMap().containsKey(item)) {
            playerItemsAdapter.notifyItemChanged(item);
        } else {
            playerItemsAdapter.notifyItemRemoved(item);
        }

    }
}
