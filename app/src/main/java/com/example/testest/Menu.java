package com.example.testest;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    public ArrayList<Exercise> exercise_list = new ArrayList<Exercise>();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ListFragment listFragment = new ListFragment();
    private ChatFragment chatFragment = new ChatFragment();
    private CommunityFragment communityFragment = new CommunityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
                    transaction.replace(R.id.frame, chatFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_community:
                    transaction.replace(R.id.frame, communityFragment).commitAllowingStateLoss();
                    break;
            }
                return true;
        }
    }
}