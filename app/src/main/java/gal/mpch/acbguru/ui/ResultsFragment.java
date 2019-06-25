package gal.mpch.acbguru.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import gal.mpch.acbguru.Config;
import gal.mpch.acbguru.R;
import gal.mpch.acbguru.model.ResultHandler;
import gal.mpch.acbguru.model.ResultItem;
import gal.mpch.acbguru.util.Utility;

public class ResultsFragment extends Fragment {

    @BindView(R.id.listview_results)
    ListView listView;
    @BindView(R.id.swipe_refresh_results)
    SwipeRefreshLayout swipeRefreshLayout;
    private ResultAdapter resultAdapter;
    private String matchDayNumber;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        resultAdapter = new ResultAdapter(getActivity(), new ArrayList<ResultItem>());
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        listView.setAdapter(resultAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
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
        new FetchResultsTask().execute(matchDayNumber);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchDayNumber = getArguments().getString(Config.ARGS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listview_results)
    void opendMatchDetails(int position) {
        ResultItem resultItem = (ResultItem) listView.getItemAtPosition(position);
        Intent intent = new Intent(getContext(), MatchDetailsActivity.class);
        intent.putExtra(Config.LOCAL_TEAM, resultItem.getLocalTeamId());
        intent.putExtra(Config.VISITOR_TEAM, resultItem.getVisitorTeamId());
        intent.putExtra(Config.MATCH_ID, String.valueOf(resultItem.getId()));
        startActivity(intent);
    }

    private class FetchResultsTask extends AsyncTask<String, Void, List<ResultItem>> {

        private final String LOG_TAG = FetchResultsTask.class.getSimpleName();

        String matchDayParam;

        @Override
        protected List<ResultItem> doInBackground(String... params) {
            matchDayParam = Config.CURRENT_DAY_WS;
            if (params != null && params.length != 0) {
                matchDayParam = params[0];
            }
            try {
                InputStream resultsStream = Utility.downloadUrl(Config.URL_WS_RESULTS + matchDayParam);
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser saxParser = spf.newSAXParser();
                ResultHandler resultHandler = new ResultHandler();
                saxParser.parse(resultsStream, resultHandler);
                matchDayNumber = resultHandler.getMatchDay();
                return resultHandler.getResults();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ResultItem> resultItems) {
            resultAdapter.clear();
            ResultsActivity resultsActivity = (ResultsActivity) getActivity();
            int positionCurrent = Integer.valueOf(matchDayNumber) - 1;
            if (Config.CURRENT_DAY_WS.equals(matchDayParam)) {
                resultsActivity.selectTab(positionCurrent);
            }
            if (resultItems != null && resultItems.size() > 0) {
                for (ResultItem resultItem : resultItems) {
                    resultAdapter.add(resultItem);
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}


