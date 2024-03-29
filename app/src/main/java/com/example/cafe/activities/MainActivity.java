package com.example.cafe.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.cafe.R;
import com.example.cafe.databinding.ActivityMainBinding;
import com.example.cafe.screens.auth.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    public NavController navController;
    private BottomNavigationView mBottomNavigation;
    private Toolbar mToolbar;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @SuppressLint("NonConstantResourceId")
    private void init() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = Objects.requireNonNull(hostFragment).getNavController();
        mToolbar = mBinding.toolbar;
        mBottomNavigation = mBinding.bottomNavigationView;

        BottomNavigationView bottomNavigationView = mBinding.bottomNavigationView;
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(mToolbar, navController);
//        setSupportActionBar(mToolbar);

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            switch (navDestination.getId()) {
                case R.id.authFragment2:
                case R.id.loginFragment:
                case R.id.productFragment:
                case R.id.signInEmailFragment:
                case R.id.signInPhoneFragment: {
                    hideToolbarAndNavBar();
                    break;
                }
                case R.id.categoryFragment:
                case R.id.catalogFragment2:
                case R.id.basketFragment2:
                {
                    mToolbar.setVisibility(View.GONE);
                    mBottomNavigation.setVisibility(View.VISIBLE);
                    break;
                }
                default:
                    mToolbar.setVisibility(View.VISIBLE);
                    mBottomNavigation.setVisibility(View.VISIBLE);
                    break;
            }
        });

        userViewModel.getMsg().observe(this, s -> Snackbar.make(mBinding.getRoot(), s, Snackbar.LENGTH_LONG).show());
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    private void hideToolbarAndNavBar() {
        mBottomNavigation.setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }

    private void setStatusBarColor(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.color_on_primary));
    }
}
