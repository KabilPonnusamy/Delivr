package com.delivr.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.delivr.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.delivr.ui.fragments.Frag_MyJobs;
import com.delivr.ui.fragments.Frag_MyJobsQueue;
import com.delivr.ui.fragments.Frag_MyProfile;

import static android.view.View.VISIBLE;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    Activity activity = this;
    ImageView img_hamburg;
    DrawerLayout drawer;
    BottomNavigationView bottom_nav;
    Toolbar dash_toolbar;

    RelativeLayout myjobs_layout, jobqueue_layout, comp_jobs_layout, profile_layout,
            logout_layout;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));

        toolbar_init();
        initView();

    }

    private void initView() {
        myjobs_layout = findViewById(R.id.myjobs_layout);
        jobqueue_layout = findViewById(R.id.jobqueue_layout);
        comp_jobs_layout = findViewById(R.id.comp_jobs_layout);
        profile_layout = findViewById(R.id.profile_layout);
        logout_layout = findViewById(R.id.logout_layout);

        bottom_nav = findViewById(R.id.bottom_nav);
        drawer = findViewById(R.id.drawer_layout);
        img_hamburg = (ImageView) findViewById(R.id.img_hamburg);

        setListeners();
        navigation_listener();
    }

    private void setListeners() {
        myjobs_layout.setOnClickListener(this);
        jobqueue_layout.setOnClickListener(this);
        comp_jobs_layout.setOnClickListener(this);
        profile_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        img_hamburg.setOnClickListener(this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void navigation_listener() {
        bottom_nav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment fragment = null;
                        switch (menuItem.getItemId()) {
                            case R.id.menu_job:
                                fragment = new Frag_MyJobs();
                                if (fragment != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout, fragment);
                                    ft.commit();
                                }
                                return true;

                            case R.id.menu_profile:
                                fragment = new Frag_MyProfile();
                                if (fragment != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout, fragment);
                                    ft.commit();
                                }
                                return true;

                            case R.id.menu_queue:
                                fragment = new Frag_MyJobsQueue();
                                if (fragment != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout, fragment);
                                    ft.commit();
                                }
                                return true;
                        }
                        return false;
                    }
                });
            }

    private void toolbar_init() {
        dash_toolbar = (Toolbar) findViewById(R.id.dash_toolbar);
        dash_toolbar.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_hamburg:
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                } else {
                    drawer.closeDrawer(GravityCompat.END);
                }
                break;

            case R.id.myjobs_layout:
                Fragment jobsfragment = new Frag_MyJobs();
                FragmentTransaction ftjobs = getSupportFragmentManager().beginTransaction();
                ftjobs.replace(R.id.frame_layout, jobsfragment);
                ftjobs.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.jobqueue_layout:
                Fragment queuefragment = new Frag_MyJobsQueue();
                FragmentTransaction ftqueue = getSupportFragmentManager().beginTransaction();
                ftqueue.replace(R.id.frame_layout, queuefragment);
                ftqueue.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.comp_jobs_layout:
                Fragment compfragment = new Frag_MyJobs();
                FragmentTransaction ftcomp = getSupportFragmentManager().beginTransaction();
                ftcomp.replace(R.id.frame_layout, compfragment);
                ftcomp.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.profile_layout:
                Fragment profilefragment = new Frag_MyProfile();
                FragmentTransaction ftprofile = getSupportFragmentManager().beginTransaction();
                ftprofile.replace(R.id.frame_layout, profilefragment);
                ftprofile.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout_layout:
                Toast.makeText(getApplicationContext(), "Will implement Later", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }
}
