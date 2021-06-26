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


    var storyCount: Int
        get() = prefrence.getInt("story", 0)
        set(value) {
            editor.putInt("story", value)
            editor.commit()
        }
    var tutorialCount: Int
        get() = prefrence.getInt("tutorial", 0)
        set(value) {
            editor.putInt("tutorial", value)
            editor.commit()
        }
    var gametype: String
        get() = prefrence.getString("gameType", "").toString()
        set(value) {
            editor.putString("gameType", value)
            editor.commit()
        }

    var profilePic: String
        get() = prefrence.getString("pic", "").toString()
        set(value) {
            editor.putString("pic", value)
            editor.commit()
        }

    var userName: String
        get() = prefrence.getString("userName", "").toString()
        set(value) {
            editor.putString("userName", value)
            editor.commit()
        }

    var fullName: String
        get() = prefrence.getString("fullName", "").toString()
        set(value) {
            editor.putString("fullName", value)
            editor.commit()
        }

    var opponentName: String
        get() = prefrence.getString("opponentName", "").toString()
        set(value) {
            editor.putString("opponentName", value)
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

    fun showProgress(context: Context) {
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