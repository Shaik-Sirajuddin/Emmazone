package com.live.emmazone.extensionfuncton

import com.google.gson.Gson
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.base.AppController


fun savePreference(key: String, value: Any) {
    val preference = AppController.instance!!.getSharedPreferences(AppConstants.SHARED_NAME, 0)
    val editor = preference.edit()

    when (value) {
        is String -> editor.putString(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Int -> editor.putInt(key, value)
    }
    editor.apply()
}

inline fun <reified T> getPreference(key: String, deafultValue: T): T {
    val preference = AppController.instance!!.getSharedPreferences(AppConstants.SHARED_NAME, 0)
    return when (T::class) {
        String::class -> preference.getString(key, deafultValue as String) as T
        Boolean::class -> preference.getBoolean(key, deafultValue as Boolean) as T
        Int::class -> preference.getInt(key, deafultValue as Int) as T
        else -> {
            " " as T
        }
    }

}

inline fun <reified T> savePrefObject(key: String, obj: T) {
    savePreference(key, Gson().toJson(obj))
}

inline fun <reified T> getPrefObject(key: String): T {
    return Gson().fromJson(getPreference(key, ""), T::class.java)
}


fun clearPreferences() {
    val preference = AppController.instance!!.getSharedPreferences(AppConstants.SHARED_NAME, 0)
    val editor = preference.edit()
    editor.clear()
    editor.apply()
}