package com.pharmacy.crack.main.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.categoryModels.Category
import com.pharmacy.crack.data.model.questionModels.Getquestion
import com.pharmacy.crack.databinding.ActivityReportQuestionBinding
import com.pharmacy.crack.main.model.ReportQuestionDataModel
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_email_support.*
import kotlinx.android.synthetic.main.activity_report_question.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_submit_question.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ReportQuestionActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var que: String
    lateinit var networkConnectivity: NetworkConnectivity
    var listCategory: ArrayList<Category> = ArrayList()
    var listQuestion: ArrayList<Getquestion> = ArrayList()
    lateinit var pref: PrefHelper
    private lateinit var binding: ActivityReportQuestionBinding
    var queId = 0
    var catId = 0

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_question)
        binding.lifecycleOwner = this
        pref = PrefHelper(this)


        initBackground()

        binding.edtReportAns.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, p1: MotionEvent?): Boolean {
                if (edtReportAns.hasFocus()) {
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

        binding.consReportQue.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        txtSubmitReportQue.setOnClickListener(this)
        imgBackreportQuest.setOnClickListener(this)

        if (!isNetworkAvailable(this)) {
            showToast(
                this,
                "Please check your internet connection and try again."
            )
        }
        initfetchcategory()

    }

    private fun initfetchcategory() {

        networkConnectivity = NetworkConnectivity(application)
        networkConnectivity.observe(this, androidx.lifecycle.Observer { isAvailable ->
            when (isAvailable) {
                true -> if (listCategory.isEmpty()) {
                    Thread.sleep(1_000)
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            fetchcategory()
                        } catch (e: Exception) {
                            showToast(
                                this@ReportQuestionActivity,
                                "Please check your internet connection and try again."
                            )
                        }
                    }
                }
                false -> showToast(
                    this,
                    "Please check your internet connection and try again."
                )
            }
        })
    }

    private suspend fun fetchcategory() {

        val res = RetrofitFactory.api.getcategory("Bearer " + pref.authToken)
        if (res.isSuccessful) {
            res.body()?.let {
                listCategory = it.category
                if (!listCategory.isNullOrEmpty()) {
                    initCategory()
                }
            }
        } else {
            try {
                val jObjError = JSONObject(res.errorBody()?.string())
                showToasts("${jObjError.getString("message")}")
            } catch (e: Exception) {
                showToasts(e.message.toString())
            }

        }
    }

    private fun initCategory() {
        val catAdapter = ArrayAdapter<CharSequence>(
            this,
            R.layout.age_spinner_text,
            listCategory as List<CharSequence>
        )
        catAdapter.setDropDownViewResource(R.layout.age_spinner)
        binding.spnCatReport.adapter = catAdapter

        binding.spnCatReport.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                catId = listCategory[position].id
                hitQuestionApi(catId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun hitQuestionApi(id: Int) {
        pref.showProgress(this@ReportQuestionActivity)

        CoroutineScope(Main).launch {
            val res = RetrofitFactory.api.getQuestion("Bearer " + pref.authToken, id)

            pref.hideProgress()
            if (res.isSuccessful) {
                res.body()?.let {
                    listQuestion = it.getquestion
                    if (!listQuestion.isNullOrEmpty()) {
                        initQuestion()
                    }
                }
            } else {
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts("${jObjError.getString("message")}")
                } catch (e: Exception) {
                    showToasts(e.message.toString())
                }
            }
        }
    }

    private fun initQuestion() {
        val catAdapter = ArrayAdapter<CharSequence>(
            this,
            R.layout.age_spinner_text,
            listQuestion as List<CharSequence>
        )
        catAdapter.setDropDownViewResource(R.layout.age_spinner)
        binding.spnQueReport.adapter = catAdapter

        binding.spnQueReport.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                queId = listQuestion[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initBackground() {
        editTextBackground(binding.relCatReport, "#FFFFA4", "#ADADAD")
        editTextBackground(binding.relQueReport, "#FFFFA4", "#ADADAD")
    }

    override fun onClick(v: View?) {
        if (v == binding.txtSubmitReportQue) {
            if (binding.edtReportAns.text.toString().trim().isEmpty()) {
                showToasts("Please write something.")
            } else {
                if ((!isNetworkAvailable(this))) {
                    showToast(this, "Please check your internet connection and try again.")
                } else {
                    try {
                            submitReportQuestion()
                    }
                    catch (e: java.lang.Exception) {

                          if(pref.mDialog.isShowing){
                              pref.hideProgress()
                          }
                            showToast(this, "Please check your internet connection and try again.")
                    }

                }
            }
        } else if (v == binding.imgBackreportQuest) {
            super.onBackPressed()
        }
    }

    private fun submitReportQuestion() {
        pref.showProgress(this@ReportQuestionActivity)
        val model = ReportQuestionDataModel(catId,queId,binding.edtReportAns.text.toString())
        CoroutineScope(Main).launch {
            val res = RetrofitFactory.api.submitreportQuestion("Bearer " + pref.authToken, model)
            pref.hideProgress()
            if (res.isSuccessful) {
                res.body()?.let {
                    showToasts(it.message)
                    super.onBackPressed()
                }
            } else {
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts("${jObjError.getString("message")}")
                } catch (e: Exception) {
                    showToasts(e.message.toString())
                }
            }
        }
    }

}