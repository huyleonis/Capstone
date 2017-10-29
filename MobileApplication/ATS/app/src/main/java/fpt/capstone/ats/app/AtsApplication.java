package fpt.capstone.ats.app;

import android.app.Application;
import com.estimote.coresdk.common.config.EstimoteSDK;


public class AtsApplication extends Application {

    private static boolean isVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        EstimoteSDK.initialize(getApplicationContext(), "demo-7at", "2236dfcc5c0f0d3e6dcf3c29ab4eb63c");
    }

    public static void onPausedApp() {
        isVisible = false;
    }

    public static void onResumeApp() {
        isVisible = true;
    }

    public static boolean isVisibleApp() {
        return isVisible;
    }



}
