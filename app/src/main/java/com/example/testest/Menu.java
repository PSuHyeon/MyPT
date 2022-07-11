package com.example.testest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;


public class Menu extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ListFragment listFragment = new ListFragment();
    private trainee_chat trainer = new trainee_chat();
    private CommunityFragment communityFragment = new CommunityFragment();
    private TimerFragment timerFragment = new TimerFragment();
    public static String key_id = "";
    public static String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Log.d("keyid", ""+ intent.getStringExtra("keyid"));
        key_id = intent.getStringExtra("keyid");
        name = intent.getStringExtra("name");

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, listFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new Menu.ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_list:
                    transaction.replace(R.id.frame, listFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_chat:
                    transaction.replace(R.id.frame, trainer).commitAllowingStateLoss();
                    break;
                case R.id.menu_community:
                    transaction.replace(R.id.frame, communityFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_timer:
                    transaction.replace(R.id.frame, timerFragment).commitAllowingStateLoss();
            }
                return true;
        }
    }
}