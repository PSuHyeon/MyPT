//package com.example.testest;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class TrainerMenu extends AppCompatActivity {
//
//    private FragmentManager fragmentManager = getSupportFragmentManager();
//    private Trainer1 trainerFragment1 = new Trainer1();
//    private Trainer2 trainerFragment2 = new Trainer2();
//    private Trainer3 trainerFragment3 = new Trainer3();
////    public static String key_id = "";
////    public static String name = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trainer_menu);
//
//        Intent intent = getIntent();
//        //Log.d("keyid", ""+ intent.getStringExtra("keyid"));
//        //key_id = intent.getStringExtra("keyid");
//        //name = intent.getStringExtra("name");
//
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.trainer_frame, trainerFragment1).commitAllowingStateLoss();
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.trainer_menu_bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new TrainerMenu);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new TrainerMenu.ItemSelectedListener());
//
//        class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//                switch (menuItem.getItemId()) {
//                    case R.id.trainer_menu_list:
//                        transaction.replace(R.id.frame, trainerFragment1).commitAllowingStateLoss();
//                        break;
//                    case R.id.trainer_menu_chat:
//                        transaction.replace(R.id.frame, trainerFragment2).commitAllowingStateLoss();
//                        break;
//                    case R.id.trainer_menu_community:
//                        transaction.replace(R.id.frame, trainerFragment3).commitAllowingStateLoss();
//                        break;
//                }
//                return true;
//            }
//        }
//    }
//}

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


public class TrainerMenu extends AppCompatActivity {

    private FragmentManager trainerFragmentManager = getSupportFragmentManager();
    private Trainer1 trainerFragment1 = new Trainer1();
    private Trainer2 trainerFragment2 = new Trainer2(); //테스트용으로 바궈둠
    private CommunityFragment communityFragment = new CommunityFragment();
    FragmentTransaction transaction;
    Menu menu = new Menu();
//    public static String key_id = "";
//    public static String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_menu);

        Intent intent = getIntent();
        Log.d("keyid", ""+ intent.getStringExtra("keyid"));
        menu.key_id = intent.getStringExtra("keyid");
        menu.name = intent.getStringExtra("name");

        transaction = trainerFragmentManager.beginTransaction();

        getSupportFragmentManager().beginTransaction().add(R.id.trainer_frame, new Trainer1()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.trainer_menu_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction = trainerFragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.trainer_menu_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame, new Trainer1()).commit();
                        break;
                    case R.id.trainer_menu_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame, new Trainer2()).commit();
                        break;
                    case R.id.trainer_menu_community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame, communityFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}