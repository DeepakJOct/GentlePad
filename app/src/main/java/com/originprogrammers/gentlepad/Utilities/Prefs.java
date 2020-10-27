package com.example.gentlepad.Utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.support.annotation.Nullable;


import java.util.Map;
import java.util.Set;


/**
 * The type Prefs.
 */
public class Prefs {


    public static final String IS_SWIPE_FIRST_TIME = "isSwipeFirstTime";
    public static final String IS_TUTORIAL_SHOWN = "is_tutorial_shown";
    /**
     * Initialize the Prefs helper class to keep a reference to the
     * SharedPreference for this application the SharedPreference will use the
     * package name of the application as the Key.
     *
     * @param context
     * the Application context.
     */

    private static SharedPreferences mPrefs;
    private static final String key = "LoginPref";

    /**
     * The constant IS_LOGGED.
     */
    public static final String IS_LOGGED = "IsLoggedIn";
    /**
     * The constant PIN_CODE.
     */
    public static final String PIN_CODE = "pincode";
    /**
     * The constant CITY.
     */
    public static final String CITY = "city";
    /**
     * The constant STATE.
     */
    public static final String STATE = "state";
    /**
     * The constant REFER_CODE.
     */
    public static final String REFER_CODE = "refer_code";
    /**
     * The constant FIRST_NAME.
     */
    public static final String FIRST_NAME = "first_name";
    /**
     * The constant LAST_NAME.
     */
    public static final String LAST_NAME = "last_name";
    /**
     * The constant AUTHORIZATION.
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * The constant EMAIL.
     */
    public static final String EMAIL = "email";
    /**
     * The constant MOBILE_NO.
     */
    public static final String MOBILE_NO = "mobile_no";
    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "user_id";
    /**
     * The constant GROUP_ID.
     */
    public static final String GROUP_ID = "group_id";
    /**
     * The constant GROUP.
     */
    public static final String GROUP = "group";
    public static final String INIT_CUST_GROUP = "initial_custgroup";


    /**
     * The constant EMAIL_KEY.
     */
    public static final String EMAIL_KEY = "email_key";
    /**
     * The constant STREET.
     */
    public static final String STREET = "street";
    /**
     * The constant PRIMARY_OCCUPATION.
     */
    public static final String PRIMARY_OCCUPATION = "primary_occupation";
    /**
     * The constant IDENTITY_NUMBER.
     */
    public static final String IDENTITY_NUMBER = "identity_no";//pan number
    /**
     * The constant MASTER_DATA.
     */
    public static final String MASTER_DATA = "master_data";

    /**
     * The constant RECOMNED_UPDATE.
     */
    public static final String RECOMNED_UPDATE = "recomend_update";


    /**
     * The constant GENERAL_CHAT.
     */
//RevChat Pres
    public static final String GENERAL_CHAT = "general_chat";
    /**
     * The constant RFQ_CHAT.
     */
    public static final String RFQ_CHAT = "rfq_chat";

    /**
     * The constant REFERRAL_CODE.
     */
    public static final String REFERRAL_CODE = "referral_code";

    /**
     * The constant AADHAR_NUMBER.
     */
    public static final String AADHAR_NUMBER = "aadharNumber";

    /**
     * The constant IDENTITY_OPTION.
     */
    public static final String IDENTITY_OPTION = "identityOption";

    /**
     * The constant GSTIN.
     */
    public static final String GSTIN = "gstin";

    /**
     * The constant BUSINESS_TYPE.
     */
    public static final String BUSINESS_TYPE = "businessType";

    /**
     * The constant GST_OPTION.
     */
    public static final String GST_OPTION = "gstOption";

    public static final String DASHBOARD_RESPONSE = "dashboard_response";

    public static final String CART_COUNT = "cart_count";

    public static final String NOTIFICATION_COUNT = "notification_count";

    public static final String RFP_COUNT = "rfp_count";

    public static final String REFRESH_HOME = "refresh_home";

    /**
     * The constant HAS_DEFALUT_ADDRESS.
     */
    public static final String HAS_DEFALUT_ADDRESS = "has_defalut_address";

    public static final String VISITOR_ID = "visitor_id";

    public static final String CART_QUOTE_ID = "cart_quote_id";

    public static final String RFP_QUOTE_ID = "rfq_quote_id";

    public static final String WISHLIST_ID = "wishlist_id";

    public static final String DELIVERY_PIN = "delivery_pin";

    public static final String COMPARE_COUNT = "compare_count";
    public static final String IS_DEALER_VERIFIED = "is_dealer_verified";

    public static final String COMPANY_NAME = "company_name";

    public static final String COMPANY_MAIL = "company_mail";

    public static final String PREF_PAYMENT = "pref_payment";

    public static final String SHOP_NAME = "shop_name";

    public static final String OTHERS_PRIMARY = "others_primary";

    public static final String CUS_ADDR_ENTITY_ID = "cus_add_entity_id";
    public static final String CUS_ADDR_CITY= "cus_add_city";
    public static final String CUS_ADDR_COMPANY = "cus_add_company";
    public static final String CUS_ADDR_COUNTRY_ID = "cus_add_country_id";
    public static final String CUS_ADDR_FIRSTNAME = "cus_add_firstname";
    public static final String CUS_ADDR_LASTNAME = "cus_add_firstname";
    public static final String CUS_ADDR_POSTCODE = "cus_add_postcode";
    public static final String CUS_ADDR_REGION= "cus_add_region";
    public static final String CUS_ADDR_REGION_ID = "cus_add_region_id";
    public static final String CUS_ADDR_STREET = "cus_add_street";
    public static final String CUS_ADDR_STREET_0 = "cus_add_street_0";
    public static final String CUS_ADDR_STREET_1 = "cus_add_street_1";
    public static final String CUS_ADDR_TELEPHONE = "cus_add_telephone";
    public static final String INTRESTED_CATEGORIES = "intrested_categories";
    public static final String RFP_CART_IDS = "rfp_cart_ids";

    public static final String IS_IMAGE_SHOW = "is_image_show";
    public static final String PRAMOTIONAL_IMAGE = "pramotional_image";
    public static final String PRAMOTIONAL_TYPE = "pramotional_type";
    public static final String PRAMOTIONAL_TITLE = "pramotional_title";
    public static final String COMPARE_IDS = "compare_ids";
    public static final String WISH_LIST_ADDED_IDS = "wish_list_removed_ids";
    public static final String WISH_LIST_REMOVED_IDS = "wish_list_added_ids";
    public static final String SIGNUP_TUTORIAL_VIDEOS = "signup_tutorial_videos";
    public static final String PROFILE_IMAGE = "profile_image";
    public static final String CART_ITEMS_CLEAR="cart_items_cleared";
    public static final String IS_FROM_SIGNUP = "is_from_signup";
    public static final String REFER_TEXT= "refer_text";

    /**
     * Init prefs.
     *
     * @param context the context
     */
    public static void initPrefs(Context context) {
        if (mPrefs == null) {
            mPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
    }

    /**
     * Clearall prefs.
     *
     * @param ctx the ctx
     */
    public static void clearallPrefs(Context ctx) {

        getPreferences().edit().clear().apply();

        // / App specific


        // =========

    }

    /**
     * Returns an instance of the shared preference for this app.
     *
     * @return an Instance of the SharedPreference
     */
    public static SharedPreferences getPreferences() {
        if (mPrefs != null) {
            return mPrefs;
        }
        throw new RuntimeException(
                "Prefs class not correctly instantiated please call Prefs.iniPrefs(context) in the Application class onCreate.");
    }

    /**
     * Gets all.
     *
     * @return Returns a map containing a list of pairs key/value representing the preferences.
     * @see SharedPreferences#getAll() SharedPreferences#getAll()SharedPreferences#getAll()SharedPreferences#getAll()SharedPreferences#getAll()
     */
    public static Map<String, ?> getAll() {
        return getPreferences().getAll();
    }

    /**
     * Gets int.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     * @see SharedPreferences#getInt(String, int) SharedPreferences#getInt(String, int)SharedPreferences#getInt(String, int)SharedPreferences#getInt(String, int)SharedPreferences#getInt(String, int)
     */
    public static int getInt(final String key, final int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    /**
     * Gets boolean.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     * @see SharedPreferences#getBoolean(String, boolean) SharedPreferences#getBoolean(String, boolean)SharedPreferences#getBoolean(String, boolean)SharedPreferences#getBoolean(String, boolean)SharedPreferences#getBoolean(String, boolean)
     */
    public static boolean getBoolean(final String key, final boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    /**
     * Gets long.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a long.
     * @see SharedPreferences#getLong(String, long) SharedPreferences#getLong(String, long)SharedPreferences#getLong(String, long)SharedPreferences#getLong(String, long)SharedPreferences#getLong(String, long)
     */
    public static long getLong(final String key, final long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * Gets float.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a float.
     * @see SharedPreferences#getFloat(String, float) SharedPreferences#getFloat(String, float)SharedPreferences#getFloat(String, float)SharedPreferences#getFloat(String, float)SharedPreferences#getFloat(String, float)
     */
    public static float getFloat(final String key, final float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    /**
     * Gets string.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a String.
     * @see SharedPreferences#getString(String, String) SharedPreferences#getString(String, String)SharedPreferences#getString(String, String)SharedPreferences#getString(String, String)SharedPreferences#getString(String, String)
     */
    public static String getString(final String key, @Nullable final String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /**
     * Gets string set.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference values if they exist, or defValues. Throws ClassCastException if there is a preference with this name that is not a Set.
     * @see SharedPreferences#getStringSet(String,
     * Set) SharedPreferences#getStringSet(String,
     * Set)SharedPreferences#getStringSet(String, Set)SharedPreferences#getStringSet(String, Set)SharedPreferences#getStringSet(String, Set)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(final String key,
                                           final Set<String> defValue) {
        SharedPreferences prefs = getPreferences();
        return prefs.getStringSet(key, defValue);
    }

    /**
     * Put long.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see Editor#putLong(String, long) Editor#putLong(String, long)Editor#putLong(String, long)Editor#putLong(String, long)Editor#putLong(String, long)
     */
    public static void putLong(final String key, final long value) {
        final Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Put int.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see Editor#putInt(String, int) Editor#putInt(String, int)Editor#putInt(String, int)Editor#putInt(String, int)Editor#putInt(String, int)
     */
    public static void putInt(final String key, final int value) {
        final Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Put float.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see Editor#putFloat(String, float) Editor#putFloat(String, float)Editor#putFloat(String, float)Editor#putFloat(String, float)Editor#putFloat(String, float)
     */
    public static void putFloat(final String key, final float value) {
        final Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Put boolean.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see Editor#putBoolean(String, boolean) Editor#putBoolean(String, boolean)Editor#putBoolean(String, boolean)Editor#putBoolean(String, boolean)Editor#putBoolean(String, boolean)
     */
    public static void putBoolean(final String key, final boolean value) {
        final Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Put string.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see Editor#putString(String, String) Editor#putString(String, String)Editor#putString(String, String)Editor#putString(String, String)Editor#putString(String, String)
     */
    public static void putString(final String key, final String value) {
        final Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Put string set.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see Editor#putStringSet(String,
     * Set) Editor#putStringSet(String,
     * Set)Editor#putStringSet(String, Set)Editor#putStringSet(String, Set)Editor#putStringSet(String, Set)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(final String key, final Set<String> value) {
        final Editor editor = getPreferences().edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * Remove.
     *
     * @param key The name of the preference to remove.
     * @see Editor#remove(String) Editor#remove(String)Editor#remove(String)Editor#remove(String)Editor#remove(String)
     */
    public static void remove(final String key) {
        SharedPreferences prefs = getPreferences();
        final Editor editor = prefs.edit();
        if (prefs.contains(key + "#LENGTH")) {
            // Workaround for pre-HC's lack of StringSets
            int stringSetLength = prefs.getInt(key + "#LENGTH", -1);
            if (stringSetLength >= 0) {
                editor.remove(key + "#LENGTH");
                for (int i = 0; i < stringSetLength; i++) {
                    editor.remove(key + "[" + i + "]");
                }
            }
        }
        editor.remove(key);

        editor.apply();
    }

    /**
     * Contains boolean.
     *
     * @param key The name of the preference to check.
     * @return the boolean
     * @see SharedPreferences#contains(String) SharedPreferences#contains(String)SharedPreferences#contains(String)SharedPreferences#contains(String)SharedPreferences#contains(String)
     */
    public static boolean contains(final String key) {
        return getPreferences().contains(key);
    }

    /**
     * Clear session details
     *
     * @param context the context
     */
    public static void logoutUser(Context context) {
        getPreferences().edit().clear().commit();
        getPreferences().edit().clear().apply();

    }


    /**
     * Clear session details
     *
     * @param context the context
     */
    public static void clearPref(Context context) {
        getPreferences().edit().clear().apply();
    }
}