package gal.mpch.acbguru.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import gal.mpch.acbguru.Config;
import gal.mpch.acbguru.R;

public class MatchDetailsActivity extends AppCompatActivity {

    @BindView(R.id.match_details_view_pager)
    ViewPager viewPager;

    @BindView(R.id.match_details_toolbar)
    Toolbar toolbar;

    @BindView(R.id.match_details_tab_layout)
    TabLayout tabLayout;

    private String localTeam;

    private String visitorTeam;

    private String matchId;

    private int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(new MatchDetailsPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        localTeam = intent.getStringExtra(Config.LOCAL_TEAM);
        visitorTeam = intent.getStringExtra(Config.VISITOR_TEAM);
        matchId = intent.getStringExtra(Config.MATCH_ID);
    }

    public String getLocalTeam() {
        return localTeam;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public String getMatchId() {
        return matchId;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class MatchDetailsPagerAdapter extends FragmentPagerAdapter {

        MatchDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PlayerStatsFragment();
                case 1:
                    return new PlayByPlayFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.stats_tab);
                case 1:
                    return getString(R.string.plays_tab);
            }
            return null;
        }
    }
}
