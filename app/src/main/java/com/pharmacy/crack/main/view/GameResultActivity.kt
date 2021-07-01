package com.pharmacy.crack.main.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.main.view.mainActivities.StartGameActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_game_result.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.UnsupportedEncodingException
import java.lang.String
import java.net.URLEncoder

class GameResultActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var pref: PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_game_result)

        initAll()
    }

    private fun initAll() {
        pref = PrefHelper(this)
        txtToolbar.text = "Game Result"
        imgBackToolbar.setOnClickListener(this)
        txtPlayAgainGame.setOnClickListener(this)
        cardFacebook.setOnClickListener(this)
        cardInsta.setOnClickListener(this)
        cardTwit.setOnClickListener(this)
        cardMsg.setOnClickListener(this)

        txtFirstPlyrName.text = pref.fullName
        txtsecondPlyrName.text = pref.opponentName

        if((!pref.profilePic.isNullOrEmpty())&&(pref.profilePic != "null")){
            Glide.with(this).load(pref.profilePic).placeholder(R.drawable.profile_img).into(imgFirstPlyr)
        }
    }

    override fun onClick(v: View?) {
        if(v==txtPlayAgainGame){
            startActivity(Intent(this,StartGameActivity::class.java))
            finishAffinity()
        }
        if(v==imgBackToolbar){
           startActivity(Intent(this,DashboardActivity::class.java))
            finishAffinity()
        }
        if(v==cardFacebook){
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello from Pharma\nhttps://www.agicent.com/")
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.facebook.orca")
            try {
                startActivity(sendIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Please Install Facebook Messenger", Toast.LENGTH_LONG)
                    .show()
            }
        }

        if(v==cardInsta){
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello from Pharma\nhttps://www.agicent.com/")
            shareIntent.setPackage("com.instagram.android")
            startActivity(shareIntent)
        }

        if(v==cardTwit){
            val tweetUrl = String.format(
                "https://twitter.com/intent/tweet?text=%s&url=%s",
                urlEncode("Hello From Pharma"),
                urlEncode("https://www.agicent.com/")
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl))
            val matches = packageManager.queryIntentActivities(intent, 0)
            for (info in matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                    intent.setPackage(info.activityInfo.packageName)
                }
            }
            startActivity(intent)
        }

        if(v==cardMsg){
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.putExtra("sms_body", "From Pharma\nhttps://www.agicent.com/")
            sendIntent.type = "vnd.android-dir/mms-sms"
            startActivity(sendIntent)
        }

    }

    private fun urlEncode(s: kotlin.String): kotlin.String? {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Log.wtf("kjasdsa", "UTF-8 should always be supported", e)
            throw RuntimeException("URLEncoder.encode() failed for $s")
        }
    }
}