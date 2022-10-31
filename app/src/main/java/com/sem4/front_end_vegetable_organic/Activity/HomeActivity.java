package com.sem4.front_end_vegetable_organic.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sem4.front_end_vegetable_organic.Activity.ui.account.AccountFragment;
import com.sem4.front_end_vegetable_organic.Activity.ui.cart.CartFragment;
import com.sem4.front_end_vegetable_organic.Activity.ui.home.HomeFragment;
import com.sem4.front_end_vegetable_organic.Activity.ui.menu.MenuFragment;
import com.sem4.front_end_vegetable_organic.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class HomeActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    MenuFragment menuFragment;
    AccountFragment accountFragment;
    CartFragment cartFragment;
    Fragment activeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        accountFragment = new AccountFragment();
        cartFragment = new CartFragment();
        activeFragment = homeFragment;
        addFragment();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        showFragment(homeFragment);
                        break;
                    case R.id.navigation_cart:
                        showFragment(cartFragment);
                    case R.id.navigation_menu:
                        showFragment(menuFragment);
                        break;
                    case R.id.navigation_account:
                        showFragment(accountFragment);
                        break;
                    default:
                        showFragment(homeFragment);
                        break;
                }
                return true;
            }
        });
    }
    private void addFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_layout, menuFragment).hide(menuFragment);
        transaction.add(R.id.content_layout, accountFragment).hide(accountFragment);
        transaction.add(R.id.content_layout, cartFragment).hide(cartFragment);
        transaction.add(R.id.content_layout, homeFragment);
        transaction.commit();
    }
    private void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(activeFragment);
        transaction.show(fragment);

        transaction.commit();
        activeFragment = fragment;
    }
}