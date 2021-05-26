package com.pharmacy.crack.utils

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(internal var context: Context) {
    private val prefrence: SharedPreferences =
        context.getSharedPreferences("PharmacyPref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefrence.edit()

    var isLoggedIn: Boolean
        get() = prefrence.getBoolean("isLoggedIn", false)
        set(value) {
            editor.putBoolean("isLoggedIn", value)
        }

    var authToken: String
        get() = prefrence.getString("token", "").toString()
        set(value) {
            editor.putString("token", value)
            editor.commit()
        }

    var gametype: String
    get() =  prefrence.getString("gameType","").toString()
    set(value) {
        editor.putString("gameType",value)
        editor.commit()
    }

    var wrongQuestion:Int
    get() = prefrence.getInt("wrong",0)
    set(value) {
        editor.putInt("wrong",value)
        editor.commit()
    }
    var clearData: Boolean = false
        set(value) {
            field = value
            editor.clear()
            editor.commit()
        }
}