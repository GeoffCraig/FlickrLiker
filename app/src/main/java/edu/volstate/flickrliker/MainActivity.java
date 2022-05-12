package edu.volstate.flickrliker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.volstate.flickrliker.controllers.BottomNavController;
import edu.volstate.flickrliker.dialogs.AboutSettings;
import edu.volstate.flickrliker.dialogs.HelpSettings;
import edu.volstate.flickrliker.views.FavoritesFragment;
import edu.volstate.flickrliker.views.PicturesFragment;
import edu.volstate.flickrliker.views.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY = "NEED_TO_GET_YOUR_OWN_FLICKR_API_KEY";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.mainBottomNavigation);
        FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnItemSelectedListener(new BottomNavController(fragmentManager, bottomNavigationView));
        bottomNavigationView.setSelectedItemId(R.id.bottomNavPictures);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FlickrLiker");
        setSupportActionBar(toolbar);
    }

    // AppToolBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.app_bar_about:
                AboutSettings aboutSettings = new AboutSettings();
                aboutSettings.show(getSupportFragmentManager(), "about_settings");
                return true;
            case R.id.app_bar_help:
                HelpSettings helpSettings = new HelpSettings();
                helpSettings.show(getSupportFragmentManager(), "help_settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}