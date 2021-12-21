package com.live.emmazone.utils.helper

import com.live.emmazone.utils.App
import com.live.emmazone.utils.Constants.APP_NAME
import com.live.emmazone.utils.Constants.PROFILE_TYPE
import com.live.emmazone.utils.Constants.USER_ID
import com.live.emmazone.utils.Constants.USER_TOKEN

private fun savePreference(key: String, value: Any) {
    val preference = App.application.applicationContext.getSharedPreferences(APP_NAME, 0)
    val editor = preference.edit()

    when (value) {
        is String -> editor.putString(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is Float -> editor.putFloat(key, value)
    }
    editor.apply()
}

private inline fun <reified T> getPreference(key: String, defaultValue: T): T {
    val preference = App.application.applicationContext.getSharedPreferences(APP_NAME, 0)
    return when (T::class) {
        String::class -> preference.getString(key, defaultValue as String) as T
        Boolean::class -> preference.getBoolean(key, defaultValue as Boolean) as T
        Int::class -> preference.getInt(key, defaultValue as Int) as T
        Long::class -> preference.getLong(key, defaultValue as Long) as T
        Float::class -> preference.getFloat(key, defaultValue as Float) as T
        else -> {
            " " as T
        }
    }
}

fun clearPreferences() {
    val preference = App.application.applicationContext.getSharedPreferences(APP_NAME, 0)
    val editor = preference.edit()
    editor.clear()
    editor.apply()
}

fun getUserId(): String {
    return getPreference(USER_ID, "")
}

fun saveUserId(userId: Int) {
    savePreference(USER_ID, userId)
}

fun getUserToken(): String {
    return getPreference(USER_TOKEN, "")
}

fun saveUserToken(userToken: String) {
    savePreference(USER_TOKEN, userToken)
}

fun getProfileType(): String {
    return getPreference(PROFILE_TYPE, "")
}

fun saveProfileType(type: String) {
    savePreference(PROFILE_TYPE, type)
}