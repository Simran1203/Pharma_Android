package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.model.countryModel.Geoname
import com.pharmacy.crack.main.view.TermsConditionActivity
import com.pharmacy.crack.utils.*
import com.ybs.countrypicker.CountryPicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    val month = arrayOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
    )

    var listYear: ArrayList<Int> = ArrayList()
    var listDay: ArrayList<Int> = ArrayList()
    var listCountry: ArrayList<Geoname> = ArrayList()
    var listState: ArrayList<String> = ArrayList()
    var listStateModel: ArrayList<com.pharmacy.crack.main.model.stateModel.Geoname> = ArrayList()
    var termsAndCon: Boolean = false
    val picker = CountryPicker.newInstance("Select Country")
    var urlCountry: String = "http://api.geonames.org/countryInfoJSON?username=savej123"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_sign_up)

        if (!isNetworkAvailable(this)) {
            showToast(this, "No Internet Connection")
            coordinatorSignup.visibility = View.INVISIBLE
        } else {
            coordinatorSignup.visibility = View.VISIBLE
            hitCountryApi(applicationContext.resources.configuration.locale.displayCountry)
        }

        initBackground()
        initlistner()

        initYear()
        listMonthInit()
        listDayInit()
        initOther()

//        txtTerm.setOnClickListener {
//            showToasts("click")
//        }


        val ss = SpannableString("By clicking on the Next button, you agree to our Terms & Conditions.")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(this@SignUpActivity,TermsConditionActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 49, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(ForegroundColorSpan(Color.parseColor("#FF0091")), 49, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        txtTerm.text = ss
        txtTerm.movementMethod = LinkMovementMethod.getInstance()
//        txtTerm.highlightColor = Color.TRANSPARENT
    }

    private fun hitCountryApi(countryName: String) {
//        val dialog : Dialog =  showProgress(this)
        GlobalScope.launch(Dispatchers.IO) {
//            dialog.dismiss()

            val response = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.geonames.org/countryInfoJSON/").build().create<MyApi>()
                .getCountry(urlCountry)
            if (response.isSuccessful) {
                listCountry = response.body()?.geonames!!

                for (i in 1..listCountry.size) {
                    val name = listCountry[i].countryName
                    if (name.equals(countryName)) {
                        txtCountry.text = name
                       val geonameId = listCountry[i].geonameId
                        hitStateApi(geonameId)
                        break
                    }
                }
            }else{
                showToast(this@SignUpActivity, "" + response.message())
            }
        }
    }

    private fun hitStateApi(geonameId: Int) {
        listStateModel.clear()
        listState.clear()


        GlobalScope.launch(Dispatchers.IO) {
            val response = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.geonames.org/").build().create<MyApi>()
                .getState(geonameId, "savej123")

            if (response.isSuccessful) {
                listStateModel = response.body()?.geonames!!
                listStateModel.forEach {
                    listState.add(it.name)
                }

                withContext(Dispatchers.Main){
                    val stateAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        this@SignUpActivity,
                        R.layout.age_spinner_text,
                        listState
                    )
                    stateAdapter.setDropDownViewResource(R.layout.age_spinner)
                    spinnerState.setAdapter(stateAdapter)

                    spinnerState.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View, position: Int, id: Long
                        ) {
                            (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // write code to perform some action
                        }
                    }

                }

//                Log.d("hitStateApi:", listStateModel.size.toString())
            }
        }
    }

    private fun initOther() {

        constarintSignup.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        picker.setListener { name, code, dialCode, flagDrawableResID ->
            for (i in 0 until listCountry.size) {
                val name1 = listCountry[i].countryName
                if (name1.equals(name)){
                    txtCountry.text = name1
                    val geonameId = listCountry[i].geonameId
                    hitStateApi(geonameId)
                    break
                }
            }
            picker.dismiss()
        }
    }

    private fun listDayInit() {

        for (i in 1 until 32) {
            listDay.add(i)
        }

        val dayAdapter: ArrayAdapter<Int> = ArrayAdapter<Int>(
            this,
            R.layout.age_spinner_text,
            listDay
        )
        dayAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerDay.setAdapter(dayAdapter)

        spinnerDay.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initYear() {
        val sdf = SimpleDateFormat("yyyy")
        val currentYear = Integer.parseInt(sdf.format(Date()))
        for (i in 1970..currentYear) {
            listYear.add(i)
        }

        val yearAdapter: ArrayAdapter<Int> = ArrayAdapter<Int>(
            this,
            R.layout.age_spinner_text,
            listYear
        )
        yearAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerYear.adapter = yearAdapter

        spinnerYear.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun listMonthInit() {

        val monthAdapter = ArrayAdapter<CharSequence>(this, R.layout.age_spinner_text, month)
        monthAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerMonth.adapter = monthAdapter

        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
//                Toast.makeText(
//                    this@SignUpActivity,
//                    month[position], Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }


    private fun initlistner() {
        editPasswordSignUp.transformationMethod = AsteriskPasswordTransformationMethod()
        editCnfmPasswordSignUp.transformationMethod = AsteriskPasswordTransformationMethod()
        txtSignup.setOnClickListener(this)
        imgBackSignup.setOnClickListener(this)
        relCont.setOnClickListener(this)

        chkTerm.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                termsAndCon = isChecked

            }

        })
    }


    private fun initBackground() {
        editTextBackground(relYear, "#FFFFA4", "#ADADAD")
        editTextBackground(relMonth, "#FFFFCF", "#ADADAD")
        editTextBackground(relDate, "#FFFFA7", "#ADADAD")
        editTextBackground(relCont, "#FBFB95", "#ADADAD")
        editTextBackground(relState, "#FBFB95", "#ADADAD")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v == txtSignup) {
            hideKeyBoard(this)
            if (edtName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Name.", Toast.LENGTH_SHORT).show()
                edtName.text?.clear()
            } else if (editEmail.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                editEmail.text?.clear()
            } else if (!(Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim())
                    .matches())
            ) {
                Toast.makeText(this, "Please enter valid Email Address.", Toast.LENGTH_SHORT).show()
            } else if (editEmail.getText().toString().startsWith(" ")) {
                Toast.makeText(this, "Please enter valid Email Address.", Toast.LENGTH_SHORT).show()
            } else if (editPasswordSignUp.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                editPasswordSignUp.text?.clear()
            } else if (editPasswordSignUp.getText().toString().trim().length < 6) {
                Toast.makeText(
                    this,
                    "Please enter at least 6 chars long password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (editCnfmPasswordSignUp.text.toString().trim().isEmpty()) {
                editCnfmPasswordSignUp.text?.clear()
                Toast.makeText(this, "Please enter Confirm Password.", Toast.LENGTH_SHORT).show()
            } else if (!editCnfmPasswordSignUp.text.toString().equals(
                    editPasswordSignUp.getText().toString()
                )
            ) {
                Toast.makeText(this, "Confirm Password should be same as Password", Toast.LENGTH_SHORT)
                    .show()
            } else if (editUserName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Username.", Toast.LENGTH_SHORT).show()
                editUserName.text?.clear()
            } else if (editCollege.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter College/University name.", Toast.LENGTH_SHORT)
                    .show()
            } else if (!termsAndCon) {
                Toast.makeText(
                    this,
                    "Please agree to our Terms and Conditions.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(Intent(this, StoryActivity::class.java))
            }

        }
        if (v == relCont) {
            picker.show(supportFragmentManager, "COUNTRY_PICKER")
        }
        if (v == imgBackSignup) {
            super.onBackPressed()
        }


    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }
}