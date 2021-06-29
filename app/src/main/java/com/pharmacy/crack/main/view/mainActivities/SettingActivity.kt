package com.pharmacy.crack.main.view.mainActivities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.loginSignUpActivity.LoginActivity
import com.pharmacy.crack.main.view.SettingActivities.ChangeProfileActivity
import com.pharmacy.crack.main.view.SettingActivities.EmailSupportActivity
import com.pharmacy.crack.main.view.SettingActivities.TutorialScreenActivity
import com.pharmacy.crack.main.view.TermsConditionActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToasts
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var pref : PrefHelper
    var mGoogleSignInClient: GoogleSignInClient? = null
    lateinit var dialogLogout: Dialog
    lateinit var txtYesLogout : RegularTextView
    lateinit var txtNoLogout : RegularTextView
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_setting)

        pref = PrefHelper(this)

        imgBackSetting.setOnClickListener(this)
        txtChangeProfile.setOnClickListener(this)
        txtEmaiSupport.setOnClickListener(this)
        txtTutorial.setOnClickListener(this)
        txtTermsCondition.setOnClickListener(this)
        txtLogout.setOnClickListener(this)


        initDialog()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("319985004088-hc8ec1iog2etbrmkvtrvctt2prr1n98c.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun initDialog() {
        dialogLogout = Dialog(this, android.R.style.Theme_Light)
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLogout.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogLogout.setContentView(R.layout.dialog_logout)
        txtYesLogout = dialogLogout.findViewById(R.id.txtYesLogout)
        txtNoLogout = dialogLogout.findViewById(R.id.txtNoLogout)
        txtNoLogout.setOnClickListener {
            dialogLogout.dismiss()
        }
        txtYesLogout.setOnClickListener {
            pref.clearData = true
            mGoogleSignInClient?.signOut()
            LoginManager.getInstance().logOut()
            showToasts("Logout Successfully")
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }

    override fun onClick(v: View?) {
        if(v==txtChangeProfile){
            startActivity(Intent(this,ChangeProfileActivity::class.java))
        }
        if(v==txtEmaiSupport){
            startActivity(Intent(this,EmailSupportActivity::class.java))
        }
        if(v==txtTutorial){
            startActivity(Intent(this, TutorialScreenActivity::class.java))
        }
        if(v==txtTermsCondition){
            startActivity(Intent(this, TermsConditionActivity::class.java))
        }
        if(v==txtLogout){
            dialogLogout.show()
        }
        if(v==imgBackSetting){
            super.onBackPressed()
        }
    }

}