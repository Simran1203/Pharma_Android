package com.pharmacy.crack.main.view.rewardsActivity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.categoryModels.Category
import com.pharmacy.crack.main.model.CategoryDataModel
import com.pharmacy.crack.main.model.QuestionSubmitDataModel
import com.pharmacy.crack.utils.*
import com.ybs.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_submit_question.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class SubmitQuestionActivity : AppCompatActivity(), View.OnClickListener,
    CountryCodePicker.OnCountryChangeListener {
    var listCategory: ArrayList<Category> = ArrayList()
    lateinit var pref: PrefHelper
    lateinit var networkConnectivity: NetworkConnectivity
     var catId: Int =0

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_submit_question)
        txtCountryQuest.text = applicationContext.resources.configuration.locale.displayCountry
        pref = PrefHelper(this)

        constraintQuest.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }


        clickListner()
        if (!isNetworkAvailable(this)) {
            showToast(
                this@SubmitQuestionActivity,
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
                                this@SubmitQuestionActivity,
                                "Please check your internet connection and try again."
                            )
                        }
                    }
                }
                false -> showToast(
                    this@SubmitQuestionActivity,
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

    private fun clickListner() {
        imgBackQuest.setOnClickListener(this)
        txtSubmitQueston.setOnClickListener(this)
        countrPickerQuest!!.setOnCountryChangeListener(this)


        edtTypeQue.movementMethod = ScrollingMovementMethod()
        ScrollingMovementMethod.getInstance()
        edtTypeQue.setOnTouchListener(OnTouchListener { v, event ->
            edtTypeQue.parent.requestDisallowInterceptTouchEvent(true)
            false
        })
    }

    private fun initCategory() {
        editTextBackground(relContQuest, "#FBFB95", "#FF48AF")
        editTextBackground(relCat, "#FFFC7C", "#FF48AF")

        val catAdapter = ArrayAdapter<CharSequence>(
            this,
            R.layout.age_spinner_text,
            listCategory as List<CharSequence>
        )
        catAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerCategory.adapter = catAdapter

        spinnerCategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                catId = listCategory[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    override fun onClick(v: View?) {
        if (v == txtSubmitQueston) {
            if (edtTypeQue.text.toString().length < 20) {
                Toast.makeText(
                    this,
                    "Please enter at least 20 min chars long Question.",
                    Toast.LENGTH_SHORT
                ).show()
                edtTypeQue.requestFocus()
            } else if (edtCorrectAns.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter in Correct Ans Field.", Toast.LENGTH_SHORT)
                    .show()
                edtCorrectAns.requestFocus()
            } else if (edtCorrectAns1.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter in Wrong Ans 01 Field.", Toast.LENGTH_SHORT)
                    .show()
                edtCorrectAns1.requestFocus()
            } else if (edtCorrectAns2.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter in Wrong Ans 02 Field.", Toast.LENGTH_SHORT)
                    .show()
                edtCorrectAns2.requestFocus()
            } else if (edtCorrectAns3.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter in Wrong Ans 03 Field.", Toast.LENGTH_SHORT)
                    .show()
                edtCorrectAns3.requestFocus()
            } else {
                hideKeyBoard(this)
                if(isNetworkAvailable(this)){
                    hitSubmitQuestApi()
                }else{
                    showToast(this, "Please check your internet connection and try again.")
                }

            }
        } else if (v == imgBackQuest) {
            super.onBackPressed()
        }

    }

    private fun hitSubmitQuestApi() {
        CoroutineScope(Main).launch {
         pref.showProgress(this@SubmitQuestionActivity)
          val model = QuestionSubmitDataModel("1",edtTypeQue.text.toString(),catId.toString(),
              edtCorrectAns.text.toString(),edtCorrectAns1.text.toString(),edtCorrectAns2.text.toString(),
              edtCorrectAns3.text.toString(),"14")

            val res = RetrofitFactory.api.submitQuestion("Bearer "+pref.authToken,model)
            pref.hideProgress()

            if(res.isSuccessful){
                res.body()?.let {
                    showToasts(it.message)
                    super.onBackPressed()
                }

            }else{
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts("${jObjError.getString("message")}")
                } catch (e: Exception) {
                    Toast.makeText(this@SubmitQuestionActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }

    override fun onCountrySelected() {
        txtCountryQuest.text = countrPickerQuest?.selectedCountryName
    }

}