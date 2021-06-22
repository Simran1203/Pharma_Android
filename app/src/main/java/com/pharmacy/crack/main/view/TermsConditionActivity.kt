package com.pharmacy.crack.main.view

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.isNetworkAvailable
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_terms_condition.*


class TermsConditionActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_terms_condition)
        if (!isNetworkAvailable(this)) {
            showToast(this, "Please check your internet connection and try again.")
            webTerm.visibility = View.INVISIBLE
        } else {
            webTerm.visibility = View.VISIBLE
            webTerm.settings.setSupportZoom(true)
            webTerm.settings.javaScriptEnabled = true
            progressBar = ProgressDialog.show(this, "Please Wait!", "Loading...");

            webTerm.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                    view.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {

                    progressBar.dismiss()

                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String,
                    failingUrl: String?
                ) {
                    progressBar.dismiss()
                    Toast.makeText(this@TermsConditionActivity, " $description", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            webTerm.loadUrl("https://www.agicent.com/")
        }

        imgBackterms.setOnClickListener { super.onBackPressed() }
    }
}