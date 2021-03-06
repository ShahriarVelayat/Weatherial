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
package paweltypiak.weatherial.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;
import paweltypiak.weatherial.R;

public class UsefulFunctions {

    public static String getFormattedString(String string){
        //cut empty characters before and after string and set upper case
        if(string.length()!=0){
            string=getStringWithUpperCase(string);
            string=getStringWithoutLastSpace(string);
        }
        return string;
    }

    private static String getStringWithoutLastSpace(String string){
        if(string.length()!=0){
            while(string.substring(string.length()-1).equals(" ")){
                string=string.substring(0,string.length()-1);
            }
        }
        return string;
    }

    private static String getStringWithoutFirstSpace(String string){
        while(string.substring(0,1).equals(" ")){
            string=string.substring(1,string.length());
            if(string.length()==0) break;
        }
        return string;
    }

    private static String getStringWithUpperCase(String string){
        string=getStringWithoutFirstSpace(string);
        if(string.length()!=0) string=string.substring(0, 1).toUpperCase() + string.substring(1);
        return string;
    }

    public static int dpToPixels(int dp,Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        int pixels = (int) Math.ceil(dp * logicalDensity);
        return pixels;
    }

    @SuppressWarnings("deprecation")
    public static void setLocale(Context context,int type){
        String language=null;
        if(type==0) language="en";
        else if(type==1) language="pl";
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);

        } else {
            config.locale = locale;
        }
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    @SuppressWarnings("deprecation")
    public static int getLocale(Context context){
        String languageString;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            languageString=context.getResources().getConfiguration().getLocales().get(0).toString();

        } else {
            languageString=context.getResources().getConfiguration().locale.toString();
        }
        languageString=languageString.substring(0,2);
        int language;
        if(languageString.equals("pl")) language=1;
        else language=0;
        return language;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static Drawable getColoredDrawable(Activity activity, int drawableId, int color){
        Drawable drawable = ContextCompat.getDrawable(activity, drawableId).mutate();
        drawable.setColorFilter(new
                PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        return drawable;
    }

    public static int getScreenHeight(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        return screenHeight;
    }

    public static int getScreenWidth(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        return screenWidth;
    }

    public static int getStatusBarHeight(Activity activity){
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int getTextViewHeight(Activity activity,
                                        String text,
                                        Typeface typeface,
                                        float textSize,
                                        int paddingLeft,
                                        int paddingTop,
                                        int paddingRight,
                                        int paddingBottom) {
        int textViewHeight=getTextViewSize(
                activity,
                text,
                typeface,
                textSize,
                paddingLeft,
                paddingTop,
                paddingRight,
                paddingBottom)[1];
        return textViewHeight;
    }
    public static int getTextViewWidth(Activity activity,
                                       String text,
                                       Typeface typeface,
                                       float textSize,
                                       int paddingLeft,
                                       int paddingTop,
                                       int paddingRight,
                                       int paddingBottom) {
        int textViewWidth=getTextViewSize(
                activity,
                text,
                typeface,
                textSize,
                paddingLeft,
                paddingTop,
                paddingRight,
                paddingBottom)[0];
        return textViewWidth;
    }

    private static int[] getTextViewSize(Activity activity,
                                        String text,
                                        Typeface typeface,
                                        float textSize,
                                        int paddingLeft,
                                        int paddingTop,
                                        int paddingRight,
                                        int paddingBottom){
        TextView textView = new TextView(activity);
        textView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
        textView.setTypeface(typeface);
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int deviceWidth = displayMetrics.widthPixels;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        int [] textViewSize={textView.getMeasuredWidth(),textView.getMeasuredHeight()};
        return textViewSize;
    }

    public static void setTaskDescription(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String title = activity.getString(R.string.app_name);
            Bitmap icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.app_icon);
            int color = ContextCompat.getColor(activity,R.color.colorPrimaryDark);
            try {
                Class<?> taskDescriptionClass = Class.forName("android.app.ActivityManager$TaskDescription");
                Constructor<?> taskDescriptionConstructor
                        = taskDescriptionClass.getConstructor(String.class, Bitmap.class, int.class);
                Object taskDescription = taskDescriptionConstructor.newInstance(title, icon, color);
                Method method = ((Object) activity).getClass().getMethod("setTaskDescription", taskDescriptionClass);
                method.invoke(activity, taskDescription);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
