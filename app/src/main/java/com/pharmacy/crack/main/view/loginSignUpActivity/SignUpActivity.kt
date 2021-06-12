package com.pharmacy.crack.main.view.loginSignUpActivity

import android.app.DatePickerDialog
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
import org.apache.commons.lang3.StringEscapeUtils
import org.json.JSONObject
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
    var dobMonth: Int = 0
    lateinit var stateName: String
    lateinit var pref: PrefHelper
    val myCalendar = Calendar.getInstance();
    lateinit var dpd: DatePickerDialog

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_sign_up)

        txtCountry.text = countrPickerSignup.defaultCountryName
        pref = PrefHelper(this)
        initBackground()
        initlistner()

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


        CoroutineScope(Dispatchers.IO).launch {

            try {
                fetchStateList(countrPickerSignup.defaultCountryNameCode.toString())
                fetchClassification()
                fetchSpeciality()
            } catch (e: java.lang.Exception) {
                withContext(Main) {
                    showToasts(e.message.toString())
                }
            }
        }

        initDatePicker()


    }


    private suspend fun fetchStateList(countrystateId: String) {

        val res = RetrofitFactory.api.getState(countrystateId)
        if (res.isSuccessful) {
            res.body()?.let {
                listState = it.states
                withContext(Main) {
                    initState()
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


    private fun initlistner() {
        editPasswordSignUp.transformationMethod = AsteriskPasswordTransformationMethod()
        editCnfmPasswordSignUp.transformationMethod = AsteriskPasswordTransformationMethod()
        txtSignup.setOnClickListener(this)
        relDate.setOnClickListener(this)
        relMonth.setOnClickListener(this)
        relYear.setOnClickListener(this)
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
                    var dob = "$dobYear-$dobMonth-$dobDay"
                    pref.showProgress(this)
                    CoroutineScope(IO).launch {
                        try {
                            submitRegisterData(dob)
                        }
                        catch (e: java.lang.Exception) {
                        withContext(Main) {
                            pref.hideProgress()
                            showToasts(e.message.toString())
                        }
                    }
                    }
                }
            }
        } else if (v == relDate) {
            dpd.show()
        } else if (v == relMonth) {
            dpd.show()
        } else if (v == relYear) {
            dpd.show()
        } else if (v == imgBackSignup) {
            super.onBackPressed()
        }
    }

    private fun initDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dobDay = day
        dobMonth = month + 1
        dobYear = year
        txtDay.setText("" + day)
        txtMonth.setText("" + (month + 1))
        txtYear.setText("" + year)

        dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                dobDay = dayOfMonth
                dobMonth = monthOfYear + 1
                dobYear = year
                txtDay.setText("" + dayOfMonth)
                txtMonth.setText("" + dobMonth)
                txtYear.setText("" + year)

            },
            year,
            month,
            day
        )
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
                CoroutineScope(Main).launch {
                    pref.hideProgress()
                    pref.authToken = it.auth_token
                    startActivity(Intent(this@SignUpActivity, StoryActivity::class.java))
                    finishAffinity()
                }

            }
        } else {
            CoroutineScope(Main).launch {
                pref.hideProgress()
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts("${jObjError.getString("message")}")
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
        if (!isNetworkAvailable(this)) {
            showToast(this, "Please check your internet connection and try again.")
        } else {
            CoroutineScope(IO).launch {
                fetchStateList(countrPickerSignup.selectedCountryNameCode)
            }
        }
    }

    fun getAge(year: Int, monthInt: Int, dayOfMonth: Int): Int {
        return Period.between(
            LocalDate.of(year, monthInt, dayOfMonth),
            LocalDate.now()
        ).years
    }

}
