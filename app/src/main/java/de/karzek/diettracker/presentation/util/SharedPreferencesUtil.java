package de.karzek.diettracker.presentation.util;

import android.content.SharedPreferences;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class SharedPreferencesUtil {
    private final SharedPreferences sharedPreferences;

    public static final String KEY_APP_INITIALIZED = "KEY_APP_INITIALIZED";

    public static final String KEY_SETTING_NUTRITION_DETAILS = "KEY_SETTING_NUTRITION_DETAILS";
    public static final String VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY = "CALORIES_ONLY";
    public static final String VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS = "CALORIES_AND_MACROS";

    public SharedPreferencesUtil(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public boolean initialiseStandardValues(){
        try {
            sharedPreferences
                    .edit()
                    .putBoolean(KEY_APP_INITIALIZED, false)
                    .putString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS)
                    .apply();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getString(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue){
        return sharedPreferences.getFloat(key,defaultValue);
    }

    public int getInt(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue){
        return sharedPreferences.getLong(key, defaultValue);
    }


}