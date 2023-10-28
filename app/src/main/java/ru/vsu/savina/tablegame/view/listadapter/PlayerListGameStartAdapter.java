package ru.vsu.savina.tablegame.view.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;
import ru.vsu.savina.tablegame.view.fragment.ItemShopDialog;

public class PlayerListGameStartAdapter extends RecyclerView.Adapter<PlayerListGameStartAdapter.PlayerListStartViewHolder> {
    private List<TableGamePlayer> playerNameList;
    private ItemShopDialog dialog;

    public PlayerListGameStartAdapter(List<TableGamePlayer> playerNameList, ItemShopDialog dialog) {
        this.playerNameList = playerNameList;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public PlayerListStartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.button_player, parent, false);

        return new PlayerListStartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListStartViewHolder holder, int position) {
        holder.getLayout().setOnClickListener(v -> holder.showPlayerItems(playerNameList.get(position)));

        holder.setDialog(dialog);
        TextView playerName = holder.getPlayerName();
        playerName.setText(playerNameList.get(position).optionInfo());

        TextView playerMoney = holder.getPlayerMoney();
        playerMoney.setText(String.valueOf(playerNameList.get(position).getMoney()));
    }

    @Override
    public int getItemCount() {
        return playerNameList.size();
    }

    public static class PlayerListStartViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final TextView playerName;
        private final TextView playerMoney;

        private ItemShopDialog dialog;

        public PlayerListStartViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = (LinearLayout) itemView.findViewById(R.id.layout_player_button);

            playerName = (TextView) itemView.findViewById(R.id.text_playername);


            playerMoney = (TextView) itemView.findViewById(R.id.player_money);
        }

        public LinearLayout getLayout() {
            return layout;
        }

        public void showPlayerItems(TableGamePlayer player) {
            dialog.showPlayerItems(player);
        }

        public TextView getPlayerName() {
            return playerName;
        }

        public TextView getPlayerMoney() {
            return playerMoney;
        }

        public void setDialog(ItemShopDialog dialog) {
            this.dialog = dialog;
        }
    }
}
