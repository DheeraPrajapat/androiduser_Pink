package com.marius.valeyou.data.local;

import com.marius.valeyou.data.beans.signup.SignupData;

public interface SharedPref {
    /**
     *
     * @param value setUser profile bean
     */
    void putUserData(SignupData value);

    /**
     *
     * @return user profile bean
     */
    SignupData getUserData();
    /**
     *
     * @param key unique key
     * @param value integer value
     */

    void put(String key, int value);

    /**
     * Get integer
     *
     * @param key
     * @param defaultValue
     * @return
     */
    int get(String key, int defaultValue);

    /**
     * Put float
     *
     * @param key
     * @param value
     */
    void put(String key, float value);

    /**
     * Get float
     *
     * @param key
     * @param defaultValue
     * @return
     */
    float get(String key, float defaultValue);

    /**
     * Put boolean
     *
     * @param key
     * @param value
     */
    void put(String key, boolean value);

    /**
     * Get boolean
     *
     * @param key
     * @param defaultValue
     * @return
     */
    boolean get(String key, boolean defaultValue);

    /**
     * Put long
     *
     * @param key
     * @param value
     */
    void put(String key, long value);


    /**
     * Get long
     *
     * @param key
     * @param defaultValue
     * @return
     */
    long get(String key, long defaultValue);

    /**
     * Put string
     *
     * @param key
     * @param value
     */
    void put(String key, String value);

    /**
     * Get string
     *
     * @param key
     * @param defaultValue
     * @return
     */
    String get(String key, String defaultValue);

    /**
     * Delete data specified by key
     *
     * @param key
     */
    void delete(String key);

    /**
     * Delete all data from shared preferences
     */
    void deleteAll();

}
