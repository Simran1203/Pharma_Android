package com.pharmacy.crack.utils

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import com.pharmacy.crack.R

class PrefHelper(internal var context: Context) {
    private val prefrence: SharedPreferences =
        context.getSharedPreferences("PharmacyPref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefrence.edit()
    lateinit var mDialog: Dialog

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

    var userId: Int
    get() = prefrence.getInt("id",0)
    set(value) {
        editor.putInt("id",value)
        editor.commit()
    }

    var gametype: String
    get() =  prefrence.getString("gameType","").toString()
    set(value) {
        editor.putString("gameType",value)
        editor.commit()
    }

    var clearData: Boolean = false
        set(value) {
            field = value
            editor.clear()
            editor.commit()
        }


    fun hideProgress() {
        while (mDialog != null && mDialog.isShowing) {
            mDialog.dismiss()
        }
    }
    fun showProgress(context: Context){
        if (context != null) {
            mDialog = Dialog(context)
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog.setContentView(R.layout.dialog_progress)
            mDialog.findViewById<View>(R.id.progressBar).visibility = View.VISIBLE
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.setCancelable(false)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.show()

        }

    }
}