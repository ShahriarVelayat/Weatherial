package paweltypiak.matweather.layoutInitializing.WeatherLayoutInitializing.swipeRefreshLayoutInitializing;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import paweltypiak.matweather.R;
import paweltypiak.matweather.customViews.LockableSmoothNestedScrollView;
import paweltypiak.matweather.usefulClasses.UsefulFunctions;

public class OnSwipeRefreshLayoutPullListenersInitializer {

    private LockableSmoothNestedScrollView nestedScrollView;
    private LinearLayout weatherLayout;
    private LinearLayout onRefreshMessageLayout;
    private boolean isFingerOnScreen;
    private boolean isToolbarExpanded =true;
    private float movedHeight;
    private float startHeight;
    private float fadeOutOffset;
    private float fadeOutRange;
    private Activity activity;

    public OnSwipeRefreshLayoutPullListenersInitializer(Activity activity, SwipeRefreshLayout swipeRefreshLayout){
        this.activity=activity;
        findResources(activity);
        addNestedScrollViewOnScrollListener(swipeRefreshLayout);
        setNestedScrollViewOnTouchListener(swipeRefreshLayout);
    }

    private void findResources(Activity activity){
        findViews(activity);
        findDimensions(activity);
    }

    private void findViews(Activity activity){
        nestedScrollView=(LockableSmoothNestedScrollView)activity.findViewById(R.id.nested_scroll_view);
        weatherLayout=(LinearLayout)activity.findViewById(R.id.weather_inner_layout);
        onRefreshMessageLayout =(LinearLayout)activity.findViewById(R.id.main_content_layout_on_refresh_message_layout);
    }

    private void findDimensions(Activity activity){
        float swipeRefrehLayoutOffset=activity.getResources().getDimension(R.dimen.swipe_refresh_layout_offset);
        fadeOutRange=activity.getResources().getDimension(R.dimen.swipe_refresh_layout_offset)*1.5f;
        fadeOutOffset=swipeRefrehLayoutOffset+ UsefulFunctions.dpToPixels(1,activity);
    }

    private void setNestedScrollViewOnTouchListener(final SwipeRefreshLayout swipeRefreshLayout){
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            //dynamically set transparency depending on the pull
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    if(isFingerOnScreen ==false){
                        startHeight = event.getRawY();
                        isFingerOnScreen =true;
                    }
                    setWeatherLayoutTransparencyOnSwipeRefreshLayoutPull(event,swipeRefreshLayout);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    setWeatherLayoutNonTransparentOnActionUp();
                    refreshSwipeRefreshLayoutAvailabilityAfterActionUp(swipeRefreshLayout);
                    movedHeight=0;
                    isFingerOnScreen =false;
                }
                return false;
            }
        });
    }

    private void setWeatherLayoutTransparencyOnSwipeRefreshLayoutPull(MotionEvent event,SwipeRefreshLayout swipeRefreshLayout){
        movedHeight = event.getRawY() - startHeight;
        if(isSwipeRefreshLayoutPulled(swipeRefreshLayout,movedHeight)==true){
            float transparencyPercentage=movedHeight/fadeOutRange;
            float currentAlpha=1-transparencyPercentage;
            if(currentAlpha<0.25){
                currentAlpha=0.25f;
            }
            weatherLayout.setAlpha(currentAlpha);
        }
    }

    private boolean isSwipeRefreshLayoutPulled(SwipeRefreshLayout swipeRefreshLayout,float movedHeight){
        if(swipeRefreshLayout.isEnabled()==true && movedHeight>0){
            return true;
        }
        else return false;
    }

    private void setWeatherLayoutNonTransparentOnActionUp(){
        if(movedHeight<=fadeOutOffset){
            float alpha=weatherLayout.getAlpha();
            long transitionTime=100;
            if(alpha!=1){
                weatherLayout.animate()
                        .alpha(1f)
                        .setDuration(transitionTime)
                        .setListener(null);
            }
        }
    }

    private void refreshSwipeRefreshLayoutAvailabilityAfterActionUp(SwipeRefreshLayout swipeRefreshLayout){
        if(isToolbarExpanded ==true){
            swipeRefreshLayout.setEnabled(true);
        }
        else{
            swipeRefreshLayout.setEnabled(false);
        }
    }

    private void addNestedScrollViewOnScrollListener(final SwipeRefreshLayout swipeRefreshLayout){
        nestedScrollView.addOnScrollListener(new android.support.v4.widget.NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(android.support.v4.widget.NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                detectExpandedToolbar(scrollY);
                refreshSwipeRefreshLayoutAvailabilityAfterFling(swipeRefreshLayout);
            }
        });
    }

    private void detectExpandedToolbar(int scrollY){
        if(scrollY==0){
            isToolbarExpanded =true;
        }
        else{
            isToolbarExpanded =false;
        }
    }

    private void refreshSwipeRefreshLayoutAvailabilityAfterFling(SwipeRefreshLayout swipeRefreshLayout){
        if(isFingerOnScreen==false){
            if(isToolbarExpanded ==true){
                swipeRefreshLayout.setEnabled(true);
            }
        }
    }

    public void setNestedScrollViewScrollingDisabled(){
        nestedScrollView.setScrollingEnabled(false);
    }

    public void setNestedScrollViewScrollingEnabled(){
        nestedScrollView.setScrollingEnabled(true);
    }
}
