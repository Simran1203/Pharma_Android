package com.pharmacy.crack.main.view.SettingActivities

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityEmailSupportBinding
import com.pharmacy.crack.main.model.EmailsupportDataModel
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_email_support.*
import kotlinx.android.synthetic.main.activity_submit_question.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EmailSupportActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEmailSupportBinding
    lateinit var pref: PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_email_support)
        binding.lifecycleOwner = this
        pref = PrefHelper(this)

        initAll()

        binding.txtSubmitContact.setOnClickListener(this)
    }

    private fun initAll() {

        txtToolbar.text = "Contact Us"

        binding.constantEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }
        imgBackToolbar.setOnClickListener {
            super.onBackPressed()
        }

        binding.edtmessage.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, p1: MotionEvent?): Boolean {
                if (binding.edtmessage.hasFocus()) {
                    v?.parent?.requestDisallowInterceptTouchEvent(true)
                    when (p1?.action!! and MotionEvent.ACTION_MASK) {
                        MotionEvent.ACTION_SCROLL -> {
                            v!!.parent.requestDisallowInterceptTouchEvent(false)
                            return true
                        }
                    }
                }
                return false
            }

        })
    }

    override fun onClick(v: View?) {
        if (v == binding.txtSubmitContact) {
            if (binding.edtmessage.text.toString().trim().isEmpty()) {
                showToasts("Please write something.")
            } else {
                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                } else {
                    CoroutineScope(Main).launch {
                        submitQueries()
                    }
                }
            }
        }
        if (v == imgBackToolbar) {
            imgBackToolbar.setOnClickListener(this)
        }
    }

    private suspend fun submitQueries() {
        pref.showProgress(this)
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate: String = df.format(c)

       val model = EmailsupportDataModel(pref.userId.toString(),binding.edtmessage.text.toString(),formattedDate)

        val res = RetrofitFactory.api.submitQueries("Bearer "+pref.authToken,model)
        if (res.isSuccessful){
            pref.hideProgress()
            res.body()?.let {
                showToasts(it.message)
                binding.edtmessage.text?.clear()
            }

        }else{
            pref.hideProgress()
            try {
                val jObjError = JSONObject(res.errorBody()?.string())
                showToasts("${jObjError.getString("message")}")
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}