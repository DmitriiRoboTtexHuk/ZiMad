package com.example.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static String FRAGMENT_INSTANCE_NAME_second = "SecondFragment";
    private static String FRAGMENT_INSTANCE_NAME_first = "FirstFragment";
    SecondFragment fragment = null;
    FirstFragment firstFragment=null;
    FragmentManager manager;
    int TabClicked=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState!=null) {
            TabClicked = savedInstanceState.getInt("TabwasClicked");
        }
        manager= getSupportFragmentManager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                       firstfragment();
                       TabClicked=0;
                        break;
                    case 1:
                       secondfragment();
                        TabClicked=1;
                       break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(TabClicked);
        tab.select();

        fragment=(SecondFragment)manager.findFragmentByTag(FRAGMENT_INSTANCE_NAME_second);
        // Если фрагмент не сохранен, создаем новый экземпляр
        if(TabClicked==1) {
            if (fragment == null) {
                fragment = new SecondFragment();
                manager.beginTransaction().add(R.id.fragment_container, fragment, FRAGMENT_INSTANCE_NAME_second).commit();

            }
        }

            firstFragment=(FirstFragment) manager.findFragmentByTag(FRAGMENT_INSTANCE_NAME_first);
        if(TabClicked==0) {
            if (firstFragment == null) {
                firstFragment = new FirstFragment();
                manager.beginTransaction().add(R.id.fragment_container, firstFragment, FRAGMENT_INSTANCE_NAME_first).commit();


            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("TabwasClicked", TabClicked);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);


    }
    public void firstfragment() {

        manager.beginTransaction().replace(R.id.fragment_container, new FirstFragment(),"FirstFragment").commit();

    }
    public void secondfragment() {

        manager.beginTransaction().replace(R.id.fragment_container, new SecondFragment(), "SecondFragment").commit();

    }

}



