package com.pharmacy.crack.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.viewUtils.RegularEditText
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


private const val TAG = "AndroidUtils"

fun showToast(context: Context, message: String){
    var toast: Toast? = null
    toast?.cancel()
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    val tf = Typeface.createFromAsset(
        context.assets,
        "fonts/" + context.getString(R.string.regular_font)
    )
    if(toast?.view != null) {
        val toastLayout = toast.view as LinearLayout?
        val toastText = toastLayout?.getChildAt(0) as TextView
        toastText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
        toastText.typeface = tf
    }
    toast?.show()
}
fun Context.showToasts(message: String){
    var toast: Toast? = null
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    val tf = Typeface.createFromAsset(
        this.assets,
        "fonts/" + this.getString(R.string.regular_font)
    )
    if(toast?.view != null) {
        val toastLayout = toast.view as LinearLayout?
        val toastText = toastLayout?.getChildAt(0) as TextView
        toastText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
        toastText.typeface = tf
    }
    toast?.show()
}


fun editTextBackground(v: View?, colors: String, stroke: String) {

    val border = GradientDrawable()
    border.setColor(Color.parseColor(colors))
    border.cornerRadius = 30F
    border.setStroke(3, Color.parseColor(stroke)) //

    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        v?.setBackgroundDrawable(border);
    } else {
        v?.setBackground(border);
    }
}

fun drawableColor(context: Context, v: View?, colors: String){
    val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.custom_progress_bar)
    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
    DrawableCompat.setTint(wrappedDrawable, Color.parseColor(colors))
}

fun fonts(v: RegularEditText, context: Context){
    var tf: Typeface? = Typeface.createFromAsset(
        context.assets, "fonts/" + context.getString(
            R.string.regular_font
        )
    )

    v.setTypeface(tf)

}

fun visibiltiyGone(view: View?){
    view!!.visibility = View.GONE
}
fun visibiltiyVisible(view: View?){
    view!!.visibility = View.VISIBLE
}


fun setStatusBarColorFromActivity(context: Context, pActivity: Activity, color: Int){
    setSystemBarTheme(pActivity, false)
    pActivity.window.statusBarColor = ContextCompat.getColor(context, color)
}


fun setSystemBarTheme(pActivity: Activity, pIsDark: Boolean) {
    val lFlags = pActivity.window.decorView.systemUiVisibility
    pActivity.window.decorView.systemUiVisibility =
        if (pIsDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}



fun getRootDirPath(context: Context): String {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val file: File = ContextCompat.getExternalFilesDirs(
            context.applicationContext,
            null
        )[0]
        file.absolutePath
    } else {
        context.applicationContext.filesDir.absolutePath
    }
}


    @RequiresApi(Build.VERSION_CODES.O_MR1)
    fun setFullScreen(context: Context) {
        setWindowFlag(context as Activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(context, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        context.window.statusBarColor = Color.TRANSPARENT
        setWindowFlag(context, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false)
    }




fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
    val win = activity.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}


fun getAppVersionName(context: Context): String {
    try {
        return context.packageManager
            .getPackageInfo(context.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e(
            TAG,
            "Error :" + e.message + " at line number : " + e.stackTrace[2].lineNumber
        )
    }
    return ""
}


fun hideKeyBoard(context: Context) {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if ((context as Activity).currentFocus != null && context.currentFocus!!
            .windowToken != null) {
        manager.hideSoftInputFromWindow(
            context.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}


fun isNetworkAvailable(context: Context): Boolean {
    try {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
    } catch (e: Exception) {
        Log.e(TAG, "unable to check is internet Available", e)
    }
    return false
}


fun isAppIsInBackground(context: Context): Boolean {
    var isInBackground = true
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningProcesses = am.runningAppProcesses
    for (processInfo in runningProcesses) {
        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            for (activeProcess in processInfo.pkgList) {
                if (activeProcess == context.packageName) {
                    isInBackground = false
                }
            }
        }
    }
    return isInBackground
}


fun openPlayStoreLink(context: Context, url: String?) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}


fun getKeyHash(context: Context) {
    try {
        val info = context.packageManager.getPackageInfo(
            context.packageName, PackageManager.GET_SIGNATURES
        )
        //4B:D4:52:55:B6:2F:95:6D:54:16:B9:66:12:1D:EF:41:81:FC:B1:CE
        for (signature in info.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KEY_HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }
        /*val sha1 = byteArrayOf(
            0x4B,
            0xD4.toByte(), 0x52, 0x55,
            0xB6.toByte(), 0x2F, 0x95.toByte(), 0x6D, 0x54,
            0x16, 0xB9.toByte(), 0x66, 0x12, 0x1D, 0xEF.toByte(), 0x41,
            0x81.toByte(), 0xFC.toByte(), 0xB1.toByte(), 0xCE.toByte()
        )
        Log.e("keyHashGooglePlaySignIn", Base64.encodeToString(sha1, Base64.NO_WRAP))*/
    } catch (e: PackageManager.NameNotFoundException) {
    } catch (e: NoSuchAlgorithmException) {
    }
}


fun rotateAnimation(imageView: View) {
    val rotate = RotateAnimation(
        0f, 360f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    rotate.duration = 30000
    rotate.interpolator = LinearInterpolator()
    rotate.repeatCount = RotateAnimation.INFINITE
    imageView.startAnimation(rotate)
}


fun showSnackBar(view: View?, message: String?) {
    val snackbar = Snackbar
        .make(view!!, message!!, Snackbar.LENGTH_SHORT)
    snackbar.show()
}


fun showCustomSnackBar(
    view: View?,
    initialMsg: String?,
    actionTxt: String?,
    finalMsg: String?
) {
    val snackbar = Snackbar.make(view!!, initialMsg!!, Snackbar.LENGTH_LONG)
        .setAction(actionTxt) { view ->
            val snackbar1 = Snackbar.make(view, finalMsg!!, Snackbar.LENGTH_SHORT)
            snackbar1.show()
        }
    snackbar.show()
}


fun shareApp(context: Context, subject: String?, extraText: String, playStoreLink: String) {
    try {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, subject)
        var sAux = """
                    
                    $extraText
                    
                    """.trimIndent()
        sAux = """
                    $sAux$playStoreLink
                    
                    """.trimIndent()
        i.putExtra(Intent.EXTRA_TEXT, sAux)
        context.startActivity(Intent.createChooser(i, "Choose one"))
    } catch (e: Exception) {
        e.toString()
    }
}


fun applyFontsToTextInputLayout(
    context: Context,
    textInputLayouts: Array<TextInputLayout>
) {
    for (textInputLayout in textInputLayouts) {
        textInputLayout.typeface = Typeface.createFromAsset(
            context.assets,
            "fonts/WorkSans-Medium.ttf"
        )
    }

}