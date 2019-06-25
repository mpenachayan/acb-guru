package gal.mpch.acbguru.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import gal.mpch.acbguru.Config;
import gal.mpch.acbguru.R;

public class ResultsPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    private boolean firstTime;

    public ResultsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.firstTime = true;
    }

    @Override
    public Fragment getItem(int position) {
        ResultsFragment resultsFragment = new ResultsFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        if (firstTime) {
            firstTime = false;
            args.putString(Config.ARGS, Config.CURRENT_DAY_WS);
        } else {
            args.putString(Config.ARGS, String.valueOf(position + 1));
        }
        resultsFragment.setArguments(args);
        return resultsFragment;
    }

    @Override
    public int getCount() {
        return 34;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(R.string.title_match_day) + " " + (position + 1);
    }
}