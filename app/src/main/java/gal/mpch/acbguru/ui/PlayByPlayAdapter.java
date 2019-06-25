package gal.mpch.acbguru.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import gal.mpch.acbguru.R;
import gal.mpch.acbguru.model.PlayByPlayItem;
import gal.mpch.acbguru.model.PlayerStatsItem;

/**
 * Created by martin on 02/04/2017.
 */

public class PlayByPlayAdapter extends RecyclerView.Adapter<PlayByPlayAdapter.ViewHolder> {

    private List<PlayByPlayItem> dataSet;

    @BindDrawable(R.drawable.rounded_corners)
    Drawable localBackground;

    PlayByPlayAdapter(List<PlayByPlayItem> list){
        dataSet = list;
    }

    @Override
    public PlayByPlayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_play_by_play, parent, false);
        return new PlayByPlayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayByPlayAdapter.ViewHolder holder, int position) {
        PlayByPlayItem item = dataSet.get(position);
        holder.playEvent.setBackground(localBackground);
        holder.playEvent.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.play_by_play_event)
        TextView playEvent;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
