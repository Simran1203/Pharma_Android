package com.pharmacy.crack.main.view.loginSignUpActivity

import android.content.Intent
import android.graphics.Color
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
import com.hbb20.CountryCodePicker
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.classificationModels.Getclassification
import com.pharmacy.crack.data.model.specialityModels.Getspeciality
import com.pharmacy.crack.data.model.statesModels.State
import com.pharmacy.crack.main.model.RegisterDataModel
import com.pharmacy.crack.main.view.TermsConditionActivity
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.collections.ArrayList


class SignUpActivity : AppCompatActivity(), View.OnClickListener,
    CountryCodePicker.OnCountryChangeListener {

    var listYear: ArrayList<Int> = ArrayList()
    var listDay: ArrayList<Int> = ArrayList()
    var listState: ArrayList<State> = ArrayList()
    var listClassification: ArrayList<Getclassification> = ArrayList()
    var listSpeciality: ArrayList<Getspeciality> = ArrayList()
    var termsAndCon: Boolean = false

    var dobYear: Int = 0
    var classificationID: Int = 0
    var specialityID: Int = 0
    var dobDay: Int = 0
    var totalMonthDay: Int = 0
    var dobMonth: String = ""
    lateinit var stateName: String
    lateinit var pref: PrefHelper


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_sign_up)

        txtCountry.text = countrPickerSignup.defaultCountryName
        pref = PrefHelper(this)
        initBackground()
        initlistner()

        initYear()
        listMonthInit()

        initOther()


        val ss =
            SpannableString("By clicking on the Next button, you agree to our Terms & Conditions.")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(this@SignUpActivity, TermsConditionActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 49, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF0091")),
            49,
            ss.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        txtTerm.text = ss
        txtTerm.movementMethod = LinkMovementMethod.getInstance()


        CoroutineScope(
            Dispatchers.IO
        ).launch {
            fetchStateList(countrPickerSignup.defaultCountryNameCode.toString())
            fetchClassification()
            fetchSpeciality()
        }

    }

    private suspend fun fetchStateList(countrystateId: String) {
        val res = RetrofitFactory.api.getState(countrystateId)
        if (res.isSuccessful) {
            res.body()?.let {
                listState = it.states
                if (!listState.isNullOrEmpty()) {
                    withContext(Main) {
                        initState()
                    }
                }
            }
        } else {
            withContext(Main) {
                showToasts(res.message())
            }

        }
    }

    suspend fun fetchSpeciality() {
        val res = RetrofitFactory.api.getSpeciality()
        if (res.isSuccessful) {
            res.body()?.let {
                listSpeciality = it.getspeciality
                if (!listSpeciality.isNullOrEmpty()) {
                    withContext(Main) {
                        initSpeciality()
                    }
                }
            }
        } else {
            withContext(Main) {
                showToasts(res.message())
            }
        }
    }

    private suspend fun fetchClassification() {
        val res = RetrofitFactory.api.getClassification()
        if (res.isSuccessful) {
            res.body()?.let {
                listClassification = it.getclassification
                if (!listClassification.isNullOrEmpty()) {
                    withContext(Main) {
                        initClassification()
                    }
                }
            }
        } else {
            withContext(Main) {
                showToasts(res.message())
            }
        }
    }


    private fun initSpeciality() {
        val specialityAdapter = ArrayAdapter<CharSequence>(
            this, R.layout.age_spinner_text,
            listSpeciality as List<CharSequence>
        )
        specialityAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerSpecialty.adapter = specialityAdapter

        spinnerSpecialty.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                specialityID = listSpeciality.get(position).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initClassification() {

        val playerAdapter = ArrayAdapter<CharSequence>(
            this, R.layout.age_spinner_text,
            listClassification as List<CharSequence>
        )
        playerAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerPlayer.adapter = playerAdapter

        spinnerPlayer.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                classificationID = listClassification.get(position).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initOther() {
        constarintSignup.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }
    }

    private fun listDayInit() {
        for (i in 1 until totalMonthDay) {
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
                dobDay = listDay.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initState() {
        val stateAdapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            this@SignUpActivity,
            R.layout.age_spinner_text,
            listState as List<CharSequence>
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
                stateName = listState.get(position).name
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initYear() {
        val sdf = SimpleDateFormat("yyyy")
        var currentYear = Integer.parseInt(sdf.format(Date()))
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
                dobYear = listYear.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun listMonthInit() {
        val res = resources
        val monthAdapter = ArrayAdapter<CharSequence>(
            this,
            R.layout.age_spinner_text,
            res.getStringArray(R.array.month)
        )
        monthAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerMonth.adapter = monthAdapter

        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
                dobMonth = res.getStringArray(R.array.month).get(position)
                if (position == 0) {
                    totalMonthDay = 32
                } else if (position == 1) {
                    if(dobYear%4!=0){
                        totalMonthDay = 29
                    }
                    else{
                        totalMonthDay = 30
                    }

                } else if (position == 2) {
                    totalMonthDay = 32
                } else if (position == 3) {
                    totalMonthDay = 31
                } else if (position == 4) {
                    totalMonthDay = 32
                } else if (position == 5) {
                    totalMonthDay = 31
                } else if (position == 6) {
                    totalMonthDay = 32
                } else if (position == 7) {
                    totalMonthDay = 32
                } else if (position == 8) {
                    totalMonthDay = 31
                } else if (position == 9) {
                    totalMonthDay = 32
                }else if (position == 10) {
                    totalMonthDay = 31
                } else if (position == 11) {
                    totalMonthDay = 32
                }

                listDay.clear()
                listDayInit()
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
        countrPickerSignup!!.setOnCountryChangeListener(this)

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
        editTextBackground(relPlayer, "#F5ED7E", "#ADADAD")
        editTextBackground(relSpecialty, "#EFD671", "#ADADAD")
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
                Toast.makeText(
                    this,
                    "Confirm Password should be same as Password.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (editUserName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Username.", Toast.LENGTH_SHORT).show()
                editUserName.text?.clear()
            } else if ((getAge(dobYear, dobMonth, dobDay)) < 5) {
                Toast.makeText(this, "You must be at least 5 years old.", Toast.LENGTH_SHORT).show()

            } else if (!termsAndCon) {
                Toast.makeText(
                    this,
                    "Please agree to our Terms and Conditions.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                } else {
                    val dobMonthInt = getMonthInteger(dobMonth)
                    var dob = "$dobYear-$dobMonthInt-$dobDay"

                    pref.showProgress(this)
                    CoroutineScope(IO).launch {
                        submitRegisterData(dob)
                    }
                }

            }
        }

        if (v == imgBackSignup) {
            super.onBackPressed()
        }
    }

    private suspend fun submitRegisterData(dob: String) {

        val model = RegisterDataModel(
            edtName.text.toString(),
            editEmail.text.toString(),
            editPasswordSignUp.text.toString(),
            editUserName.text.toString(),
            dob,
            txtCountry.text.toString(),
            stateName,
            classificationID,
            specialityID,
            editCollege.text.toString()
        )

        val res = RetrofitFactory.api.submitSignUp(model)
        if (res.isSuccessful) {
            res.body()?.let {
                var msg = it.message
                if (msg.equals("User Register Successfully")) {
                    CoroutineScope(Main).launch {
                        pref.hideProgress()
                        pref.authToken = it.token
                        startActivity(Intent(this@SignUpActivity, StoryActivity::class.java))
                        finishAffinity()
                    }
                } else {
                    CoroutineScope(Main).launch {
                        pref.hideProgress()
                        showToasts("$msg")
                    }
                }
            }
        } else {
            CoroutineScope(Main).launch {
                pref.hideProgress()
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts("${jObjError.getString("msg")}")
                } catch (e: Exception) {
                    Toast.makeText(this@SignUpActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }

    override fun onCountrySelected() {
        txtCountry.text = countrPickerSignup?.selectedCountryName
        CoroutineScope(IO).launch {
            fetchStateList(countrPickerSignup.selectedCountryNameCode)
        }
    }

    fun getAge(year: Int, month: String, dayOfMonth: Int): Int {
        var monthInt = 0
        when (month) {
            "Jan" -> monthInt = 1
            "Feb" -> monthInt = 2
            "Mar" -> monthInt = 3
            "Apr" -> monthInt = 4
            "May" -> monthInt = 5
            "Jun" -> monthInt = 6
            "Jul" -> monthInt = 7
            "Aug" -> monthInt = 8
            "Sep" -> monthInt = 9
            "Oct" -> monthInt = 10
            "Nov" -> monthInt = 11
            "Dev" -> monthInt = 12
        }

        return Period.between(
            LocalDate.of(year, monthInt, dayOfMonth),
            LocalDate.now()
        ).years
    }

    fun getMonthInteger(month: String): Int {
        var monthInt = 0
        when (month) {
            "Jan" -> monthInt = 1
            "Feb" -> monthInt = 2
            "Mar" -> monthInt = 3
            "Apr" -> monthInt = 4
            "May" -> monthInt = 5
            "Jun" -> monthInt = 6
            "Jul" -> monthInt = 7
            "Aug" -> monthInt = 8
            "Sep" -> monthInt = 9
            "Oct" -> monthInt = 10
            "Nov" -> monthInt = 11
            "Dev" -> monthInt = 12
        }

        return monthInt
    }
}
