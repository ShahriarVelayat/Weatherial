package paweltypiak.weatherial.dialogsInitializing.dialogInitializers.editFavouritesDialogInitializing;

import android.app.Activity;

import paweltypiak.weatherial.mainActivityInitializing.MainActivity;
import paweltypiak.weatherial.mainActivityInitializing.mainActivityLayoutInitializing.layoutUpdating.OnWeatherDataChangeLayoutUpdater;
import paweltypiak.weatherial.usefulClasses.FavouritesEditor;
import paweltypiak.weatherial.usefulClasses.SharedPreferencesModifier;

class DeleteFromFavouritesRunnable implements Runnable{

    private Activity activity;

    DeleteFromFavouritesRunnable(Activity activity){
        this.activity=activity;
    }

    @Override
    public void run() {
        FavouritesEditor.deleteFavouritesItem(activity);
        updateAppBarLayout();
        updateDefaultLocation();
    }

    private void updateAppBarLayout(){
        setFloatingActionButtonOnClickIndicator();
        uncheckNavigationDrawerMenuItem();
        updateLocationName();
    }

    private void setFloatingActionButtonOnClickIndicator(){
        ((MainActivity)activity).getMainActivityLayoutInitializer()
                .getAppBarLayoutInitializer()
                .getAppBarLayoutButtonsInitializer()
                .getFloatingActionButtonInitializer()
                .setFloatingActionButtonOnClickIndicator(0);
    }

    private void uncheckNavigationDrawerMenuItem(){
        ((MainActivity)activity).getMainActivityLayoutInitializer()
                .getAppBarLayoutInitializer()
                .getAppBarLayoutButtonsInitializer()
                .getNavigationDrawerInitializer()
                .uncheckNavigationDrawerMenuItem(1);
    }

    private void updateLocationName(){
        String city= OnWeatherDataChangeLayoutUpdater.getCurrentWeatherDataParser().getCity();
        String region=OnWeatherDataChangeLayoutUpdater.getCurrentWeatherDataParser().getRegion();
        String country=OnWeatherDataChangeLayoutUpdater.getCurrentWeatherDataParser().getCountry();
        ((MainActivity)activity).getMainActivityLayoutInitializer().getAppBarLayoutInitializer().
                getAppBarLayoutDataInitializer().
                updateLocationName(city,region+", "+ country);
    }

    private void updateDefaultLocation(){
        if(FavouritesEditor.isDefaultLocationEqual(activity,null)) {
            SharedPreferencesModifier.setDefaultLocationGeolocalization(activity);
        }
    }
}