package de.karzek.diettracker.presentation;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import de.karzek.diettracker.ConfigurationManager;
import de.karzek.diettracker.presentation.dependencyInjection.component.AppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.CookbookComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.DaggerAppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.DiaryComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.HomeComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.SettingsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.SettingsModule;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
public class TrackerApplication extends Application {

    private AppComponent appComponent;

    private HomeComponent homeComponent;
    private DiaryComponent diaryComponent;
    private CookbookComponent cookbookComponent;
    private SettingsComponent settingsComponent;

    private RefWatcher refwatcher;

    @Override
    public void onCreate(){
        super.onCreate();

        new ConfigurationManager(this);
        refwatcher = LeakCanary.install(this);

        appComponent = createAppComponent();

    }

    public static TrackerApplication get(Context context) {
        return (TrackerApplication) context.getApplicationContext();
    }

    public void watch(Fragment fragment) {
        refwatcher.watch(fragment);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    //Create Components

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public HomeComponent createHomeComponent() {
        if (homeComponent != null) {
            return homeComponent;
        }
        homeComponent = appComponent.plus(new HomeModule());
        return homeComponent;
    }

    public DiaryComponent createDiaryComponent() {
        if (diaryComponent != null) {
            return diaryComponent;
        }
        diaryComponent = appComponent.plus(new DiaryModule());
        return diaryComponent;
    }

    public CookbookComponent createCookbookComponent() {
        if (cookbookComponent != null) {
            return cookbookComponent;
        }
        cookbookComponent = appComponent.plus(new CookbookModule());
        return cookbookComponent;
    }

    public SettingsComponent createSettingsComponent() {
        if (settingsComponent != null) {
            return settingsComponent;
        }
        settingsComponent = appComponent.plus(new SettingsModule());
        return settingsComponent;
    }

    //Release Components
    public void releaseHomeComponent() {
        homeComponent = null;
    }

    public void releaseDiaryComponent() {
        diaryComponent = null;
    }

    public void releaseCookbookComponent() {
        cookbookComponent = null;
    }

    public void releaseSettingsComponent() {
        settingsComponent = null;
    }

}