package com.samwolfand.movieland.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import java.util.Map;

/**
 * Created by wkh176 on 9/25/15.
 */
public final class Prefs {

    private static final String DEFAULT_SUFFIX = "_preferences";
    private static final String LENGTH = "#LENGTH";
    private static SharedPreferences mPref;
    private static String mDefStringValue = null;
    private static int mDefIntValue = -1;
    private static boolean mDefBooleanValue = false;

    /**
     * Initialize the prefs helper class to keep a reference to the SharedPreference for this
     * application.
     */
    private static void initPrefs(Context context, String name, int mode) {
        mPref = context.getSharedPreferences(name, mode);
    }

    /**
     * Returns and instance of the shared preference for this app
     *
     * @return an instance of the SharedPreference
     * @throws RuntimeException if SharedPreference instance is null
     */
    public static SharedPreferences getPreferences() {
        if (mPref != null) {
            return mPref;
        }
        throw new RuntimeException(
                "Prefs class is null!"
        );
    }

    public static Map<String, ?> getAll() {
        return getPreferences().getAll();
    }

    /**
     * Return JUST the int value you want default will be -1
     */
    public static int getInt(final String key) {
        return getPreferences().getInt(key, -1);
    }

    /**
     * Override the default value
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(final String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    /**
     * default value is false
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(final String key) {
        return getPreferences().getBoolean(key, mDefBooleanValue);
    }


    /**
     * @param key
     * @param defValue override the default value
     * @return
     */
    public static boolean getBoolean(final String key, final boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }


    /**
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     * @see SharedPreferences#getLong(String, long)
     */
    public static long getLong(final String key, final long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * DEFAULT VALUE IS -1
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     * @see SharedPreferences#getLong(String, long)
     */
    public static long getLong(final String key) {
        return getPreferences().getLong(key, mDefIntValue);
    }


    /**
     * Returns the double that has been saved as a long raw bits value in the long preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue the double Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     * @see SharedPreferences#getLong(String, long)
     */
    public static double getDouble(final String key, final double defValue) {
        return Double.longBitsToDouble(getPreferences().getLong(key, Double.doubleToLongBits(defValue)));
    }

    /**
     * DEFAULT IS -1
     * Returns the double that has been saved as a long raw bits value in the long preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     * @see SharedPreferences#getLong(String, long)
     */
    public static double getDouble(final String key) {
        return Double.longBitsToDouble(getPreferences().getLong(key, Double.doubleToLongBits(mDefIntValue)));
    }

    /**
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     * @see SharedPreferences#getFloat(String, float)
     */
    public static float getFloat(final String key, final float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    /**
     * DEFAULT IS -1
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     * @see SharedPreferences#getFloat(String, float)
     */
    public static float getFloat(final String key) {
        return getPreferences().getFloat(key, mDefIntValue);
    }

    /**
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     * @see SharedPreferences#getString(String, String)
     */
    public static String getString(final String key, final String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /**
     * DEFAULT VALUE IS NULL
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     * @see SharedPreferences#getString(String, String)
     */
    public static String getString(final String key) {
        return getPreferences().getString(key, mDefStringValue);
    }


    /**
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see SharedPreferences.Editor#putLong(String, long)
     */
    public static void putLong(final String key, final long value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see SharedPreferences.Editor#putInt(String, int)
     */
    public static void putInt(final String key, final int value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * Saves the double as a long raw bits inside the preferences.
     *
     * @param key   The name of the preference to modify.
     * @param value The double value to be save in the preferences.
     * @see SharedPreferences.Editor#putLong(String, long)
     */
    public static void putDouble(final String key, final double value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see SharedPreferences.Editor#putFloat(String, float)
     */
    public static void putFloat(final String key, final float value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see SharedPreferences.Editor#putBoolean(String, boolean)
     */
    public static void putBoolean(final String key, final boolean value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see SharedPreferences.Editor#putString(String, String)
     */
    public static void putString(final String key, final String value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * @param key The name of the preference to remove.
     * @see SharedPreferences.Editor#remove(String)
     */
    public static void remove(final String key) {
        SharedPreferences prefs = getPreferences();
        final SharedPreferences.Editor editor = prefs.edit();
        if (prefs.contains(key + LENGTH)) {
            // Workaround for pre-HC's lack of StringSets
            int stringSetLength = prefs.getInt(key + LENGTH, -1);
            if (stringSetLength >= 0) {
                editor.remove(key + LENGTH);
                for (int i = 0; i < stringSetLength; i++) {
                    editor.remove(key + "[" + i + "]");
                }
            }
        }
        editor.remove(key);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * @param key The name of the preference to check.
     * @return true if preference contains this key value.
     * @see SharedPreferences#contains(String)
     */
    public static boolean contains(final String key) {
        return getPreferences().contains(key);
    }

    /**
     * @return the for chaining. The changes have already been committed/applied through the execution of this method.
     * @see SharedPreferences.Editor#clear()
     */
    public static SharedPreferences.Editor clear() {
        final SharedPreferences.Editor editor = getPreferences().edit().clear();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
        return editor;
    }

    public static SharedPreferences.Editor edit() {
        return getPreferences().edit();
    }

    /**
     * Builder class for the EasyPrefs instance. You only have to call this once in the Application onCreate. And in the rest of the code base you can call Prefs.method name.
     */
    public final static class Builder {
        private String mKey;
        private Context mContext;
        private int mMode = -1;
        private boolean mUseDefault = false;

        /**
         * Set the filename of the sharedprefence instance  usually this is the applications packagename.xml but for migration purposes or customization.
         *
         * @param prefsName the filename used for the sharedpreference
         * @return the object.
         */
        public Builder setPrefsName(final String prefsName) {
            mKey = prefsName;
            return this;
        }

        /**
         * Set the context used to instantiate the sharedpreferences
         *
         * @param context the application context
         * @return
         */
        public Builder setContext(final Context context) {
            mContext = context;
            return this;
        }

        /**
         * Set the mode of the sharedpreference instance.
         *
         * @param mode Operating mode.  Use 0 or {@link Context#MODE_PRIVATE} for the
         *             default operation, {@link Context#MODE_WORLD_READABLE}
         * @return the
         * @see Context#getSharedPreferences
         */


        public Builder setMode(final int mode) {
            if (mode == ContextWrapper.MODE_PRIVATE || mode == ContextWrapper.MODE_WORLD_READABLE || mode == ContextWrapper.MODE_WORLD_WRITEABLE || mode == ContextWrapper.MODE_MULTI_PROCESS) {
                mMode = mode;
            } else {
                throw new RuntimeException("The mode in the sharedpreference can only be set too ContextWrapper.MODE_PRIVATE, ContextWrapper.MODE_WORLD_READABLE, ContextWrapper.MODE_WORLD_WRITEABLE or ContextWrapper.MODE_MULTI_PROCESS");
            }

            return this;
        }

        /**
         * Set the default sharedpreference file name. Often the package name of the application is used, but if the {@link android.preference.PreferenceActivity} or {@link android.preference.PreferenceFragment} is used android append that with _preference.
         *
         * @param defaultSharedPreference true if default sharedpreference name should used.
         * @return the builder object.
         */
        public Builder setUseDefaultSharedPreference(boolean defaultSharedPreference) {
            mUseDefault = defaultSharedPreference;
            return this;
        }


        /**
         * Set the default int to use for getInt() calls
         *
         * @param defaultIntValue default int value see {@link SharedPreferences}
         * @return
         */
        public Builder setDefaultIntValue(final int defaultIntValue) {
            mDefIntValue = defaultIntValue;
            return this;
        }


        /**
         *
         * @param defaultBooleanValue
         * @return
         */
        public Builder setDefaultBooleanValue(final boolean defaultBooleanValue) {
            mDefBooleanValue = defaultBooleanValue;
            return this;
        }

        public Builder setDefaultStringValue(final String defaultStringValue) {
            mDefStringValue = defaultStringValue;
            return this;
        }

        /**
         * Initialize the sharedpreference instance to used in the application.
         *
         * @throws RuntimeException if context has not been set.
         */
        public void build() {
            if (mContext == null) {
                throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
            }

            if (TextUtils.isEmpty(mKey)) {
                mKey = mContext.getPackageName();
            }

            if (mUseDefault) {
                mKey += DEFAULT_SUFFIX;
            }

            if (mMode == -1) {
                mMode = ContextWrapper.MODE_PRIVATE;
            }

            Prefs.initPrefs(mContext, mKey, mMode);
        }
    }
}
