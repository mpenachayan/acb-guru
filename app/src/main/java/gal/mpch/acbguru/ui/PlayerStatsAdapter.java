package gal.mpch.acbguru.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gal.mpch.acbguru.R;
import gal.mpch.acbguru.model.PlayerStatsItem;

class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsAdapter.ViewHolder> {

    private List<PlayerStatsItem> dataSet;

    PlayerStatsAdapter(List<PlayerStatsItem> list) {
        this.dataSet = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_player_stats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlayerStatsItem playerStatsItem = dataSet.get(position);
        holder.playerNumber.setText(playerStatsItem.getNumber());
        holder.playerName.setText(playerStatsItem.getName());
        holder.minutes.setText(playerStatsItem.getTime());
        holder.points.setText(playerStatsItem.getPoints());
        holder.rebounds.setText(playerStatsItem.getRebounds());
        holder.assists.setText(playerStatsItem.getAssists());
        holder.fouls.setText(playerStatsItem.getFoulsCommitted());
        holder.valorization.setText(playerStatsItem.getValorization());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<PlayerStatsItem> list) {
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.player_stats_number)
        TextView playerNumber;

        @BindView(R.id.player_stats_name)
        TextView playerName;

        @BindView(R.id.player_stats_minutes)
        TextView minutes;

        @BindView(R.id.player_stats_points)
        TextView points;

        @BindView(R.id.player_stats_rebounds)
        TextView rebounds;

        @BindView(R.id.player_stats_assists)
        TextView assists;

        @BindView(R.id.player_stats_fouls)
        TextView fouls;

        @BindView(R.id.player_stats_val)
        TextView valorization;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
