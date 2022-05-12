package edu.volstate.flickrliker.controllers;

import android.app.Activity;
import android.app.PictureInPictureUiState;
import android.content.Context;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.volstate.flickrliker.MainActivity;
import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.views.FavoritesFragment;
import edu.volstate.flickrliker.views.PicturesFragment;
import edu.volstate.flickrliker.views.SettingsFragment;

public class BottomNavController extends FragmentActivity implements NavigationBarView.OnItemSelectedListener  {
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    public BottomNavController(FragmentManager fragmentManager, BottomNavigationView bottomNavigationView) {
        this.fragmentManager = fragmentManager;
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottomNavFavorites:
                item.setChecked(true);
                replaceFragment(new FavoritesFragment());
                break;
            case R.id.bottomNavPictures:
                item.setChecked(true);
                replaceFragment(new PicturesFragment());
                break;
            case R.id.bottomNavSettings:
                item.setChecked(true);
                replaceFragment(new SettingsFragment());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return false;
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();

    }

}
