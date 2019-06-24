package com.example.a3cuadras.Services

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context : Context) {
    val PREFS_FILENAME = "com.example.a3cuadras.prefs"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    val MAX_DISTANCE = "maxDistance"
    val MAX_RESULTS = "maxResults"
    val CHEAP = "cheap"
    val AVERAGE = "average"
    val EXPENSIVE = "expensive"
    val VERY_EXPENSIVE = "very_expensive"

    var maxDistance: Int
        get() = prefs.getInt(MAX_DISTANCE, 100)
        set(value) = prefs.edit().putInt(MAX_DISTANCE, value).apply()

    var maxResults: Int
        get() = prefs.getInt(MAX_RESULTS, 50)
        set(value) = prefs.edit().putInt(MAX_RESULTS, value).apply()

    var cheap: Boolean
        get() = prefs.getBoolean(CHEAP, true)
        set(value) = prefs.edit().putBoolean(CHEAP, value).apply()

    var average: Boolean
        get() = prefs.getBoolean(AVERAGE, true)
        set(value) = prefs.edit().putBoolean(AVERAGE, value).apply()

    var expensive: Boolean
        get() = prefs.getBoolean(EXPENSIVE, true)
        set(value) = prefs.edit().putBoolean(EXPENSIVE, value).apply()

    var very_expensive: Boolean
        get() = prefs.getBoolean(VERY_EXPENSIVE, true)
        set(value) = prefs.edit().putBoolean(VERY_EXPENSIVE, value).apply()

    fun pricesToString() : String {
        var pricesStringParameter = ""
        var cadenaVacia = true

        if (cheap) {
            if (cadenaVacia){
                pricesStringParameter += "1"
                cadenaVacia = false
            }
        }
        if (average){
            if (cadenaVacia) {
                pricesStringParameter += "2"
                cadenaVacia = false
            }
            else{
                pricesStringParameter += ",2"
            }
        }
        if (expensive){
            if (cadenaVacia){
                pricesStringParameter += "3"
                cadenaVacia = false
            }
            else{
                pricesStringParameter += ",3"
            }
        }
        if (very_expensive){
            if (cadenaVacia){
                pricesStringParameter += "4"
                cadenaVacia = false
            }
            else{
                pricesStringParameter += ",4"
            }
        }
        return pricesStringParameter;
    }
}
