package com.pharmacy.crack.main.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_share.*
import java.io.UnsupportedEncodingException
import java.lang.String
import java.net.URLEncoder


class ShareActivity : AppCompatActivity(), View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_share)

        listner()
    }

    private fun listner() {
        imgCloseShare.setOnClickListener(this)
        cardMessagss.setOnClickListener(this)
        cardInstagram.setOnClickListener(this)
        cardMessenger.setOnClickListener(this)
        cardSnapchat.setOnClickListener(this)
        cardTwitter.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==imgCloseShare){
            super.onBackPressed()
        }
        else if(v==cardMessagss){
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.putExtra("sms_body", "From Pharma\nhttps://www.agicent.com/")
            sendIntent.type = "vnd.android-dir/mms-sms"
            startActivity(sendIntent)
        }
        else if(v==cardInstagram){
            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.type = "image/*"
            shareIntent.type = "text/plain"
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello from Pharma\nhttps://www.agicent.com/")
            shareIntent.setPackage("com.instagram.android")

            try {
                startActivity(shareIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Please Install Instagram", Toast.LENGTH_LONG)
                    .show()
            }

        }
        else if(v==cardMessenger){
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
        else if(v==cardSnapchat){
            val intent = Intent(Intent.ACTION_SEND)
//            intent.putExtra(Intent.EXTRA_TEXT, "Hello from Pharma")
            intent.putExtra(Intent.EXTRA_TEXT, "Hello from Pharma\nhttps://www.agicent.com/")
            intent.type = "text/plain"
//            intent.type = "*/*"
            intent.setPackage("com.snapchat.android")
            try {
                startActivity(Intent.createChooser(intent, "Open Snapchat"))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Please Install Snapchat", Toast.LENGTH_LONG)
                    .show()
            }

        }
        else if(v==cardTwitter){
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
    }

    fun urlEncode(s: kotlin.String): kotlin.String? {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Log.wtf("kjasdsa", "UTF-8 should always be supported", e)
            throw RuntimeException("URLEncoder.encode() failed for $s")
        }
    }
}