package paweltypiak.matweather.layoutInitializing.appBarInitializing.appBarButtonsInitializing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import paweltypiak.matweather.MainActivity;
import paweltypiak.matweather.R;
import paweltypiak.matweather.settings.Settings;
import paweltypiak.matweather.usefulClasses.DialogInitializer;
import paweltypiak.matweather.usefulClasses.SharedPreferencesModifier;

public class NavigationDrawerInitializer implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
    private DialogInitializer dialogInitializer;
    private SmoothActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AlertDialog geolocalizationMethodsDialog;
    private AlertDialog noFavouritesDialog;
    private AlertDialog aboutDialog;
    private AlertDialog feedbackDialog;
    private AlertDialog authorDialog;


    public NavigationDrawerInitializer(Activity activity, DialogInitializer dialogInitializer){
        this.activity=activity;
        this.dialogInitializer=dialogInitializer;
        initializeToolbar();
        initializeActionBarDrawerToggle();
        setNavigationViewItemSelectedListener();
    }

    private void initializeToolbar(){
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar_layout_collapsed_toolbar);
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
    }

    private void initializeActionBarDrawerToggle(){
        drawerLayout = (DrawerLayout)  activity.findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new SmoothActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close,invalidateOptionsMenuRunnable);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setNavigationViewItemSelectedListener(){
        navigationView = (NavigationView)  activity.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();
        actionBarDrawerToggle.runWhenIdle(new Runnable() {
            @Override
            public void run() {
                if(id==R.id.nav_button_geolocalization){
                    //geolocalization
                    onGeolocalizationButtonClick();
                }
                else if(id==R.id.nav_button_favourites){
                    //favourites
                   onFavouritesButtonClick();
                }
                else if (id == R.id.nav_button_settings) {
                    //settings
                    onSettingsButtonClick();
                }
                else if (id == R.id.nav_button_about) {
                    //about
                   onAboutButtonClick();
                }
                else if (id == R.id.nav_button_feedback) {
                    //send feedback
                    onFeedbackButtonClick();
                }
                else if(id == R.id.nav_button_author){
                    //author
                   onAuthorButtonClick();
                }
            }
        });
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onGeolocalizationButtonClick(){
        int localizationOption= SharedPreferencesModifier.getGeolocalizationMethod(activity);
        if(localizationOption==-1){
            if(geolocalizationMethodsDialog==null){
                geolocalizationMethodsDialog=dialogInitializer.initializeGeolocalizationMethodsDialog(1,startGeolocalizationRunnable);
            }
            geolocalizationMethodsDialog.show();
        }
        else{
            startGeolocalizationRunnable.run();
        }
    }

    Runnable startGeolocalizationRunnable = new Runnable() {
        public void run() {
            onStartGeolocalizationListener geolocalizationListener=((MainActivity)activity).getGeolocalizationListener();
            geolocalizationListener.startCheckingPermissions();
        }
    };

    public interface onStartGeolocalizationListener{
        void startCheckingPermissions();
    }

    private void onFavouritesButtonClick(){
        if(SharedPreferencesModifier.getFavouriteLocationsAddresses(activity).length==0) {
            if(noFavouritesDialog==null){
                noFavouritesDialog=dialogInitializer.initializeNoFavouritesDialog();
            }
            noFavouritesDialog.show();
        }
        else{
            AlertDialog favouritesDialog=dialogInitializer.initializeFavouritesDialog(1,null,null);
            favouritesDialog.show();
        }
    }

    private void onSettingsButtonClick(){
        Intent intent = new Intent(activity, Settings.class);
        activity.startActivity(intent);
    }

    private void onAboutButtonClick(){
        if (aboutDialog == null) {
            aboutDialog=dialogInitializer.initializeAboutDialog();
        }
        aboutDialog.show();
    }

    private void onFeedbackButtonClick(){
        if (feedbackDialog == null) {
            feedbackDialog=dialogInitializer.initializeFeedbackDialog();
        }
        feedbackDialog.show();
    }

    private void onAuthorButtonClick(){
        if (authorDialog == null) {
            authorDialog=dialogInitializer.initializeAuthorDialog();
        }
        authorDialog.show();
    }

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {
        //smooth drawer toggle - action is called after drawer is hide
        private Runnable runnable;
        private Runnable invalidateOptionsMenuRunnable;

        public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes, Runnable invalidateOptionsMenuRunnable) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
            this.invalidateOptionsMenuRunnable=invalidateOptionsMenuRunnable;
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenuRunnable.run();
        }
        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenuRunnable.run();
        }
        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
        }

        public void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private Runnable invalidateOptionsMenuRunnable = new Runnable() {
        public void run() {
            activity.invalidateOptionsMenu();
        }
    };

    public void uncheckAllNavigationDrawerMenuItems(){
        //uncheck all items in navigation drawer
        MenuItem geolocalizationItem=navigationView.getMenu().findItem(R.id.nav_button_geolocalization);
        MenuItem favouritesItem=navigationView.getMenu().findItem(R.id.nav_button_favourites);
        geolocalizationItem.setChecked(false);
        geolocalizationItem.setCheckable(false);
        favouritesItem.setChecked(false);
        favouritesItem.setCheckable(false);
    }

    public void uncheckNavigationDrawerMenuItem(int itemId){
        MenuItem geolocalizationItem=navigationView.getMenu().findItem(R.id.nav_button_geolocalization);;
        MenuItem favouritesItem=navigationView.getMenu().findItem(R.id.nav_button_favourites);
        if(itemId==0) {
            geolocalizationItem.setChecked(false);
        }
        else if(itemId==1) {
            favouritesItem.setChecked(false);
        }
    }

    public void checkNavigationDrawerMenuItem(int itemId){
        MenuItem geolocalizationItem=navigationView.getMenu().findItem(R.id.nav_button_geolocalization);;
        MenuItem favouritesItem=navigationView.getMenu().findItem(R.id.nav_button_favourites);
        if(itemId==0) {
            geolocalizationItem.setCheckable(true);
            geolocalizationItem.setChecked(true);
            favouritesItem.setCheckable(false);
            favouritesItem.setChecked(false);

        }
        else if(itemId==1) {
            favouritesItem.setCheckable(true);
            favouritesItem.setChecked(true);
            geolocalizationItem.setCheckable(false);
            geolocalizationItem.setChecked(false);
        }
    }
    public void openDrawerLayout(){
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public boolean closeDrawerLayout(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }
}

