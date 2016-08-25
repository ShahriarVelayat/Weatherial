package paweltypiak.matweather.settings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import paweltypiak.matweather.R;
import paweltypiak.matweather.usefulClasses.UsefulFunctions;

public class Settings extends AppCompatPreferenceActivity  implements UsefulFunctions.RefreshFragmentListener{
    private static FragmentManager fragmentManager;
    private static android.support.v7.app.ActionBar actionBar;
    private static boolean languagePreferencesChanged=false;
    private static boolean unitsPreferencesChanged=false;
    private static UsefulFunctions.RefreshFragmentListener refreshSettingsFragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.nav_drawer_settings));
        refreshSettingsFragmentListener=this;
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, new SettingsFragment(),"SettingsFragment")
                .commit();
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName) ||
                unitsSettingsFragment.class.getName().equals(fragmentName);
    }

    @Override
    public void onBackPressed() {
        if(actionBar.getTitle().equals(getString(R.string.preferences_units_title)))
            actionBar.setTitle(getString(R.string.nav_drawer_settings));
        super.onBackPressed();
    }

    public void refreshFragment(){
        recreate();
    }

    public static boolean isLanguagePreferencesChanged() {
        return languagePreferencesChanged;
    }

    public static boolean isUnitsPreferencesChanged() {
        return unitsPreferencesChanged;
    }

    public static void setLanguagePreferencesChanged(boolean languagePreferencesChanged) {
        Settings.languagePreferencesChanged = languagePreferencesChanged;
    }

    public static void setUnitsPreferencesChanged(boolean unitsPreferencesChanged) {
        Settings.unitsPreferencesChanged = unitsPreferencesChanged;
    }

    public static UsefulFunctions.RefreshFragmentListener getRefreshSettingsFragmentListener() {
        return refreshSettingsFragmentListener;
    }

    public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            setHasOptionsMenu(true);
            findPreference("preferences_units").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    fragmentManager.beginTransaction()
                            .replace(android.R.id.content, new unitsSettingsFragment(),"UnitsSettingsFragment")
                            .addToBackStack(null)
                            .commit();
                    actionBar.setTitle(getString(R.string.preferences_units_title));
                    return true;
                }
            });
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = super.onCreateView(inflater, container, savedInstanceState);
            if(v != null) {
                ListView lv = (ListView) v.findViewById(android.R.id.list);
                lv.setPadding(0, 0, 0, 0);
            }
            return v;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                if (!super.onOptionsItemSelected(item)) {
                    getActivity().onBackPressed();
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    public static class unitsSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            addPreferencesFromResource(R.xml.preferences_units);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = super.onCreateView(inflater, container, savedInstanceState);
            if(v != null) {
                ListView lv = (ListView) v.findViewById(android.R.id.list);
                lv.setPadding(0, 0, 0, 0);
            }
            return v;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                if (!super.onOptionsItemSelected(item)) {
                    getActivity().onBackPressed();
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}