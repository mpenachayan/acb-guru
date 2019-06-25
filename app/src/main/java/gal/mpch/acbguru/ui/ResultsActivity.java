package gal.mpch.acbguru.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import gal.mpch.acbguru.Config;
import gal.mpch.acbguru.R;

public class ResultsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.results_toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindString(R.string.open_source_licenses)
    String openSourceLicenses;

    @BindString(R.string.results_title)
    String fragmentResultsTitle;

    @BindView(R.id.result_view_pager)
    ViewPager viewPager;

    @BindView(R.id.result_tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(fragmentResultsTitle);
        setSupportActionBar(toolbar);
        viewPager.setAdapter(new ResultsPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void selectTab(int position) {
        viewPager.setCurrentItem(position);
    }

    public int getSelectedTab() {
        return viewPager.getCurrentItem();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_results:
                if(getSupportActionBar()!=null) {
                    getSupportActionBar().setTitle(fragmentResultsTitle);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_standings:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_send_comments:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Config.EMAIL_URL});
                startActivity(Intent.createChooser(emailIntent, ""));
                finish();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_open_source:
                if(getSupportActionBar()!=null) {
                    getSupportActionBar().setTitle(openSourceLicenses);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
            default:
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
