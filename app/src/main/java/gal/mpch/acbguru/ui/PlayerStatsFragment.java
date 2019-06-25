package gal.mpch.acbguru.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gal.mpch.acbguru.Config;
import gal.mpch.acbguru.R;
import gal.mpch.acbguru.model.PlayerStatsItem;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerStatsFragment extends Fragment {

    @BindView(R.id.player_stats_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_players_stats)
    SwipeRefreshLayout swipeRefreshLayout;

    private PlayerStatsAdapter playerStatsAdapter;

    private MatchDetailsActivity activity;

    public PlayerStatsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlayerStatsFragment newInstance() {
        return new PlayerStatsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_stats, container, false);
        ButterKnife.bind(this, rootView);
        activity = (MatchDetailsActivity) getActivity();

        playerStatsAdapter = new PlayerStatsAdapter(new ArrayList<PlayerStatsItem>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(playerStatsAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        refreshData();
        return rootView;
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
        new FetchPlayerStatsTask().execute(activity.getMatchId(), activity.getLocalTeam(), activity.getVisitorTeam());
    }

    private class FetchPlayerStatsTask extends AsyncTask<String, Void, List<PlayerStatsItem>> {

        @Override
        protected List<PlayerStatsItem> doInBackground(String... params) {
            String matchId = params[0];
            String localTeam = params[1];
            String visitorTeam = params[2];
            List<PlayerStatsItem> statsList = new ArrayList<>();
            try {
                statsList = parseTeamStats(Config.URL_WS_MATCH_STATS.replace(Config.MATCH_ID, matchId).replace(Config.TEAM_ID, localTeam));
                statsList.addAll(parseTeamStats(Config.URL_WS_MATCH_STATS.replace(Config.MATCH_ID, matchId).replace(Config.TEAM_ID, visitorTeam)));
            } catch (IOException e) {
                Log.e(getTag(), e.getLocalizedMessage());
            }

            return statsList;
        }

        @Override
        protected void onPostExecute(List<PlayerStatsItem> statsList) {
            recyclerView.setAdapter(new PlayerStatsAdapter(statsList));
            swipeRefreshLayout.setRefreshing(false);
        }

        private List<PlayerStatsItem> parseTeamStats(String urlString) throws IOException {
            List<PlayerStatsItem> playerStatsList = new ArrayList<>();
            Document teamStatsDocument = Jsoup.connect(urlString).get();
            for (Element playersTable : teamStatsDocument.select("table.cols-fijas")) {
                for (Element playerRow : playersTable.getElementsByTag("tr")) {
                    Elements playerColumns = playerRow.getElementsByTag("td");
                    if (!playerColumns.isEmpty()) {
                        PlayerStatsItem playerStatsItem = new PlayerStatsItem();
                        playerStatsItem.setPlaying("*".equals(playerColumns.get(0).text()));
                        playerStatsItem.setNumber(playerColumns.get(1).text());
                        playerStatsItem.setName(playerColumns.get(2).text());
                        if (!"EQ".equalsIgnoreCase(playerStatsItem.getNumber())) {
                            playerStatsList.add(playerStatsItem);
                        }
                    }
                }
            }
            Elements statsTables = teamStatsDocument.select("div.cols-scroll table");
            for (Element statsTable : statsTables) {
                int rowNumber = 0;
                for (Element statsRow : statsTable.getElementsByTag("tr")) {
                    Elements statsColumns = statsRow.getElementsByTag("td");
                    if (!statsColumns.isEmpty() && rowNumber < playerStatsList.size()) {
                        playerStatsList.get(rowNumber).setTime(statsColumns.get(0).text());
                        playerStatsList.get(rowNumber).setPoints(statsColumns.get(1).text());
                        playerStatsList.get(rowNumber).setRebounds(statsColumns.get(8).text());
                        playerStatsList.get(rowNumber).setAssists(statsColumns.get(10).text());
                        playerStatsList.get(rowNumber).setBlocks(statsColumns.get(13).text());
                        playerStatsList.get(rowNumber).setFoulsCommitted(statsColumns.get(16).text());
                        playerStatsList.get(rowNumber).setValorization(statsColumns.get(18).text());
                        rowNumber++;
                    }
                }
            }
            return playerStatsList;
        }
    }
}