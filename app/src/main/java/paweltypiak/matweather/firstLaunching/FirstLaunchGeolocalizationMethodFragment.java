package paweltypiak.matweather.firstLaunching;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import paweltypiak.matweather.R;

public class FirstLaunchGeolocalizationMethodFragment extends Fragment{

    private int choosenGeolocalizationMethod=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_launch_geolocalization_methods_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroupListener();
    }

    private void radioGroupListener(){
        RadioGroup radioGroup=(RadioGroup)getActivity().findViewById(R.id.first_launch_geolocalization_methods_fragment_radio_group);
        radioGroup.check(getActivity().findViewById(R.id.first_launch_geolocalization_methods_fragment_gps_radio_button).getId());
        choosenGeolocalizationMethod =0;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.first_launch_geolocalization_methods_fragment_gps_radio_button) {
                    Log.d("geolocalization method", "gps");
                    choosenGeolocalizationMethod =0;
                } else if (i == R.id.first_launch_geolocalization_methods_fragment_network_radio_button) {
                    Log.d("geolocalization method", "network");
                    choosenGeolocalizationMethod =1;
                }
            }
        });
    }
    public int getChoosenGeolocalizationMethod() {
        return choosenGeolocalizationMethod;
    }
}
