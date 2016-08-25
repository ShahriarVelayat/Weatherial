package paweltypiak.matweather.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import paweltypiak.matweather.R;
import paweltypiak.matweather.usefulClasses.SharedPreferencesModifier;

public class GeolocalizationMethodDialogPreference extends CustomDialogPreference{

    private int choosenOption=-1;

    public GeolocalizationMethodDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setDialogTitle(getContext().getString(R.string.preferences_language_version_title));
        setPreferenceSummary();
    }

    protected void buildRadioGroup(RadioGroup radioGroup){
        String gpsRadioButtonText=getContext().getString(R.string.geolocalization_method_gps);
        int gpsRadioButtonId=R.id.geolocalization_method_dialog_gps_radio_button_id;
        int gpsRadioButtonBottomMargin=16;
        RadioButton gpsRadioButton=setRadioButtonLayout(gpsRadioButtonText,gpsRadioButtonId,gpsRadioButtonBottomMargin);
        radioGroup.addView(gpsRadioButton);
        String networkRadioButtonText=getContext().getString(R.string.geolocalization_method_network);
        int networkRadioButtonId=R.id.geolocalization_method_dialog_network_radio_button_id;
        int networkRadioButtonBottomMargin=0;
        RadioButton networkRadioButton=setRadioButtonLayout(networkRadioButtonText,networkRadioButtonId,networkRadioButtonBottomMargin);
        radioGroup.addView(networkRadioButton);
        int geolocalizationMethod=SharedPreferencesModifier.getGeolocalizationMethod(getContext());
        if(geolocalizationMethod==0){
            gpsRadioButton.setChecked(true);
            choosenOption=0;
        }
        else if(geolocalizationMethod==1) {
            networkRadioButton.setChecked(true);
            choosenOption=1;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.geolocalization_method_dialog_gps_radio_button_id) {
                    choosenOption=0;
                }
                else if (i == R.id.geolocalization_method_dialog_network_radio_button_id) {
                    choosenOption=1;
                }
            }
        });
    };

    protected void onPositiveResult(){
        if(choosenOption==0){
            SharedPreferencesModifier.setGeolocalizationMethod(getContext(),0);
            setSummary(getContext().getString(R.string.geolocalization_method_gps));
            Log.d("changed_preference",getTitle()+ " preference changed to: "+getSummary());
        }
        else if(choosenOption==1){
            SharedPreferencesModifier.setGeolocalizationMethod(getContext(),1);
            setSummary(getContext().getString(R.string.geolocalization_method_network));
            Log.d("changed_preference",getTitle()+ " preference changed to: "+getSummary());
        }
    }
    protected void setPreferenceSummary(){
        int geolocalizationMethod=SharedPreferencesModifier.getGeolocalizationMethod(getContext());
        if(geolocalizationMethod==0){
            setSummary(getContext().getString(R.string.geolocalization_method_gps));
        }
        else if(geolocalizationMethod==1){
            setSummary(getContext().getString(R.string.geolocalization_method_network));
        }
        else  setSummary(getContext().getString(R.string.preferences_geolocalization_method_defeault_summary));
    }

}