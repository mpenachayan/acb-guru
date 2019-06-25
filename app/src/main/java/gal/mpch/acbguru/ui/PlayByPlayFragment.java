package gal.mpch.acbguru.ui;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import gal.mpch.acbguru.R;
import gal.mpch.acbguru.model.PlayByPlayItem;

public class PlayByPlayFragment extends Fragment {

    @BindView(R.id.play_by_play_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_play_by_play)
    SwipeRefreshLayout swipeRefreshLayout;

    class FetchPlayByPlayTask extends AsyncTask<String, Void, List<PlayByPlayItem>> {

        @Override
        protected List<PlayByPlayItem> doInBackground(String... params) {
            return null;
        }
    }
}
