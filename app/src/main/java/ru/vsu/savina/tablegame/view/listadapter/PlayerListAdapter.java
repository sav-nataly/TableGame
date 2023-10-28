package ru.vsu.savina.tablegame.view.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.vsu.savina.tablegame.R;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder> {
    private ArrayList<String> playerNameList;

    @NonNull
    @Override
    public PlayerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_list_player_name, parent, false);

        return new PlayerListViewHolder(view);
    }

    public PlayerListAdapter(ArrayList<String> playerList) {
        this.playerNameList = playerList;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListViewHolder holder, int position) {
        holder.getTextView().setText(playerNameList.get(position));
        holder.getDeleteButton().setOnClickListener(v -> deletePlayer(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return playerNameList.size();
    }

    public static class PlayerListViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Button deleteButton;

        public PlayerListViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.list_player_name);
            this.deleteButton = (Button) itemView.findViewById(R.id.delete_player_button);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public void addPlayer(String name) {
        if (name.length() > 0) {
            playerNameList.add(name);
            notifyItemInserted(playerNameList.size() - 1);
        }
    }

    public void deletePlayer(int position) {
        playerNameList.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<String> getPlayerNameList() {
        return playerNameList;
    }

    public void setPlayerNameList(ArrayList<String> playerNameList) {
        this.playerNameList = playerNameList;
    }
}
