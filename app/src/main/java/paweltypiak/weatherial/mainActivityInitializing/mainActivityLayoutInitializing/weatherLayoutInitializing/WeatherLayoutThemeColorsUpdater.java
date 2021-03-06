/*******************************************************************************
 * Copyright 2017 Paweł Typiak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package paweltypiak.weatherial.mainActivityInitializing.mainActivityLayoutInitializing.weatherLayoutInitializing;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import paweltypiak.weatherial.R;

class WeatherLayoutThemeColorsUpdater {

    private int backgroundColor;
    private int textPrimaryColor;
    private int textSecondaryColor;
    private int textDisabledColor;
    private int dividerColor;
    private int iconColor;

    WeatherLayoutThemeColorsUpdater(Activity activity, boolean isDay){
        updateThemeColors(activity,isDay);
    }

    private void updateThemeColors(Activity activity, boolean isDay){
        if(isDay==true) {
            backgroundColor= ContextCompat.getColor(activity, R.color.backgroundLight);
            textPrimaryColor=ContextCompat.getColor(activity,R.color.textPrimaryLightBackground);
            textSecondaryColor=ContextCompat.getColor(activity,R.color.textSecondaryLightBackground);
            textDisabledColor=ContextCompat.getColor(activity,R.color.textDisabledLightBackground);
            dividerColor=ContextCompat.getColor(activity,R.color.dividerLightBackground);
            iconColor=ContextCompat.getColor(activity,R.color.iconLightBackground);
        }
        else {
            backgroundColor=ContextCompat.getColor(activity,R.color.backgroundDark);
            textPrimaryColor=ContextCompat.getColor(activity,R.color.textPrimaryDarkBackground);
            textSecondaryColor=ContextCompat.getColor(activity,R.color.textSecondaryDarkBackground);
            textDisabledColor=ContextCompat.getColor(activity,R.color.textDisabledDarkBackground);
            dividerColor=ContextCompat.getColor(activity,R.color.dividerDarkBackground);
            iconColor=ContextCompat.getColor(activity,R.color.iconDarkBackground);
        }
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextPrimaryColor() {
        return textPrimaryColor;
    }

    public int getTextSecondaryColor() {
        return textSecondaryColor;
    }

    public int getTextDisabledColor() {
        return textDisabledColor;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public int getIconColor() {
        return iconColor;
    }
}
