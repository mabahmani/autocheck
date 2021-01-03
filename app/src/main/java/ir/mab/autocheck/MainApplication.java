package ir.mab.autocheck;

import android.app.Application;

import androidx.room.Room;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.mab.autocheck.db.AutoCheckDB;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/irlight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())).build());
    }
}
