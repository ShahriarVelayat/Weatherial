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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import paweltypiak.weatherial.R;
import paweltypiak.weatherial.utils.UsefulFunctions;
import paweltypiak.weatherial.dataDownloading.weatherDataDownloading.WeatherDataFormatter;

import static paweltypiak.weatherial.utils.UsefulFunctions.getColoredDrawable;

class WeatherForecastLayoutInitializer {

    private View forecastStepperView;
    private LinearLayout[] forecastDayLayout;
    private TextView[] forecastDayDateTextView;
    private TextView[] forecastDayNameTextView;
    private ImageView[] forecastDayConditionsImageView;
    private TextView[] forecastDayConditionsTextView;
    private ImageView[] forecastHighTemperatureImageView;
    private ImageView[] forecastLowTemperatureImageView;
    private TextView[] forecastHighTemperatureTextView;
    private TextView[] forecastLowTemperatureTextView;
    private View[] forecastDividerView;

    WeatherForecastLayoutInitializer(Activity activity){
        findWeatherForecastLayoutViews(activity);
        rotateMinTemperatureImageView();
        setWeatherForecastLayoutSizes(activity);
    }

    private void findWeatherForecastLayoutViews(Activity activity){

        forecastStepperView=activity.findViewById(R.id.weather_forecast_layout_stepper_view);
        forecastDayLayout=new LinearLayout[5];
        forecastDayDateTextView=new TextView[5];
        forecastDayNameTextView=new TextView[5];
        forecastDayConditionsImageView =new ImageView[5];
        forecastDayConditionsTextView=new TextView[5];
        forecastDividerView=new View[5];
        forecastHighTemperatureTextView=new TextView[5];
        forecastLowTemperatureTextView=new TextView[5];
        forecastHighTemperatureImageView=new ImageView[5];
        forecastLowTemperatureImageView=new ImageView[5];
        for(int i=0;i<5;i++){
            forecastDayLayout[i] =(LinearLayout) activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_layout_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastDayDateTextView[i] =(TextView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_date_text_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastDayNameTextView[i] =(TextView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_name_text_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastDayConditionsImageView[i] =(ImageView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_conditions_image_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastDayConditionsTextView[i]=(TextView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_conditions_text_"+(i),
                            "id",
                            activity.getPackageName())
            );
            forecastDividerView[i]=activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_divider_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastHighTemperatureTextView[i]=(TextView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_max_temperature_text_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastLowTemperatureTextView[i]=(TextView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_min_temperature_text_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastHighTemperatureImageView[i]=(ImageView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_max_temperature_image_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
            forecastLowTemperatureImageView[i]=(ImageView)activity.findViewById(
                    activity.getResources().getIdentifier(
                            "weather_forecast_layout_day_min_temperature_image_"+(i),
                            "id",
                            activity.getPackageName()
                    )
            );
        }
    }

    private void rotateMinTemperatureImageView(){
        for(int i=0;i<5;i++){
            forecastLowTemperatureImageView[i].setRotation(180);
        }
    }

    private void setWeatherForecastLayoutSizes(final Activity activity){
       final LinearLayout forecastLayout=(LinearLayout)activity.findViewById(R.id.weather_forecast_layout);
        ViewTreeObserver observer=forecastLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LinearLayout daysLayout=(LinearLayout)activity.findViewById(R.id.weather_forecast_layout_days_layout);
                int daysLayoutHeight=daysLayout.getHeight();
                LinearLayout dayLayout=(LinearLayout)activity.findViewById(R.id.weather_forecast_layout_day_layout_0);
                int dayLayoutHeight=dayLayout.getHeight();
                int stepperHeight=daysLayoutHeight-dayLayoutHeight;
                RelativeLayout dateLayout=(RelativeLayout)activity.findViewById(R.id.weather_forecast_layout_day_date_layout_0);
                int dateLayoutWidth=dateLayout.getWidth();
                int conditionIconSize=(int)activity.getResources().getDimension(R.dimen.forecast_condition_icon_size);
                int conditionIconHorizontalMargin=(int)activity.getResources().getDimension(R.dimen.forecast_condition_icon_horizontal_margin);
                int stepperWidth=(int)activity.getResources().getDimension(R.dimen.forecast_stepper_size);
                int marginLeft=dateLayoutWidth+conditionIconHorizontalMargin+(conditionIconSize-stepperWidth)/2;
                forecastStepperView=activity.findViewById(R.id.weather_forecast_layout_stepper_view);
                RelativeLayout.LayoutParams stepperViewLayoutParams=(RelativeLayout.LayoutParams)forecastStepperView.getLayoutParams();
                stepperViewLayoutParams.height=stepperHeight;
                stepperViewLayoutParams.leftMargin=marginLeft;
                forecastStepperView.setLayoutParams(stepperViewLayoutParams);
                forecastLayout.getViewTreeObserver().removeOnGlobalLayoutListener(
                        this);
            }
        });
    }

    public void updateWeatherForecastLayoutData(Activity activity,WeatherDataFormatter weatherDataFormatter){
        String[] dayName = new String[5];
        String[] dayDate = new String[5];
        for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance();
            if(i!=0){
                calendar.add(Calendar.DATE, i);
            }
            CharSequence dayDateFormat;
            dayDateFormat = DateFormat.format("dd.MM", calendar);
            dayDate[i] = dayDateFormat.toString();
            CharSequence dayNameFormat;
            dayNameFormat = DateFormat.format("EEEE", calendar);
            dayName[i] = dayNameFormat.toString().substring(0, 3).toUpperCase();
            forecastDayDateTextView[i].setText(dayDate[i]);
            forecastDayNameTextView[i].setText(dayName[i]);
            int forecastCode=weatherDataFormatter.getForecastCode()[i];
            int forecastConditionsDrawableId=activity.getResources().getIdentifier("drawable/forecast_conditions_icon_" + forecastCode, null, activity.getPackageName());
            Drawable forecastConditionsIconDrawable= ContextCompat.getDrawable(activity,forecastConditionsDrawableId);
            forecastDayConditionsImageView[i].setImageDrawable(forecastConditionsIconDrawable);
            int forecastConditionsStringId=activity.getResources().getIdentifier("condition_" + forecastCode, "string",activity.getPackageName());
            forecastDayConditionsTextView[i].setText(forecastConditionsStringId);
            String forecastHighTemperature=weatherDataFormatter.getForecastHighTemperature()[i];
            forecastHighTemperatureTextView[i].setText(forecastHighTemperature);
            String forecastLowTemperature=weatherDataFormatter.getForecastLowTemperature()[i];
            forecastLowTemperatureTextView[i].setText(forecastLowTemperature);
        }
        setTemperatureLayoutWidth(activity,weatherDataFormatter);
    }

    private void setTemperatureLayoutWidth(Activity activity,WeatherDataFormatter weatherDataFormatter){
        List<Integer> temperatureTextViewWidthList = new ArrayList<>();
        for(int i=0;i<5;i++){
            String forecastHighTemperature=weatherDataFormatter.getForecastHighTemperature()[i];
            temperatureTextViewWidthList.add(getTemperatureTextViewWidth(activity,forecastHighTemperature));
            String forecastLowTemperature=weatherDataFormatter.getForecastLowTemperature()[i];
            temperatureTextViewWidthList.add(getTemperatureTextViewWidth(activity,forecastLowTemperature));
        }
        int maxWidth= Collections.max(temperatureTextViewWidthList);
        for(int i=0;i<5;i++){
            LinearLayout.LayoutParams highTemperatureTextViewParams=(LinearLayout.LayoutParams)forecastHighTemperatureTextView[i].getLayoutParams();
            highTemperatureTextViewParams.width=maxWidth+2;
            forecastHighTemperatureTextView[i].setLayoutParams(highTemperatureTextViewParams);
            LinearLayout.LayoutParams lowTemperatureTextViewParams=(LinearLayout.LayoutParams)forecastLowTemperatureTextView[i].getLayoutParams();
            lowTemperatureTextViewParams.width=maxWidth+2;
            forecastLowTemperatureTextView[i].setLayoutParams(lowTemperatureTextViewParams);
        }
    }

    private int getTemperatureTextViewWidth(Activity activity,String temperatureString){
        int temperatureTextViewWidth= UsefulFunctions.getTextViewWidth(
                activity,
                temperatureString,
                Typeface.DEFAULT,
                (int)activity.getResources().getDimension(R.dimen.forecast_max_min_temperature_text_size),
                0,
                0,
                0,
                0
        );
        return temperatureTextViewWidth;
    }

    public void updateWeatherForecastLayoutTheme(Activity activity,WeatherLayoutThemeColorsUpdater themeColorsUpdater){
        int dividerColor=themeColorsUpdater.getDividerColor();
        int textPrimaryColor=themeColorsUpdater.getTextPrimaryColor();
        int textSecondaryColor=themeColorsUpdater.getTextSecondaryColor();
        int backgroundColor=themeColorsUpdater.getBackgroundColor();
        int iconColor=themeColorsUpdater.getIconColor();
        forecastStepperView.setBackgroundColor(dividerColor);

        for (int i = 0; i < 5; i++) {
            forecastDayDateTextView[i].setTextColor(textPrimaryColor);
            forecastDayNameTextView[i].setTextColor(textSecondaryColor);
            forecastDayConditionsTextView[i].setTextColor(textPrimaryColor);
            forecastDividerView[i].setBackgroundColor(dividerColor);
            forecastHighTemperatureTextView[i].setTextColor(textPrimaryColor);
            forecastLowTemperatureTextView[i].setTextColor(textPrimaryColor);
            forecastDayLayout[i].setBackgroundColor(backgroundColor);
            Drawable fullArrowIconDrawable=getColoredDrawable(activity,R.drawable.full_arrow_icon,iconColor);
            forecastHighTemperatureImageView[i].setImageDrawable(fullArrowIconDrawable);
            forecastLowTemperatureImageView[i].setImageDrawable(fullArrowIconDrawable);
        }
    }
}
