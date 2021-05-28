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
import com.hbb20.CountryCodePicker
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
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), View.OnClickListener , CountryCodePicker.OnCountryChangeListener{

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
    val player = arrayOf(
        "Pharmacist","Physician","Nurse","Nurse Practitioner","Physician Assistant",
        "Paramedic", "Dentist", "Optometrist", "Pharmacy Technician",
        "Student – Pharmacy", "Student – Nurse", "Student – Medical", "Student - Other","Other"
    )
    val speciality = arrayOf(
        "Specialist", "Clinical Pharmacist", "Retail", "Hospital", "Ambulatory",
        "Administrative", "Family Practice", "Surgeon", "Bedside Nurse",
        "Non-Bedside Nurse", "Clinic", "Other"
    )

    var listYear: ArrayList<Int> = ArrayList()
    var listDay: ArrayList<Int> = ArrayList()
    var listState: ArrayList<String> = ArrayList()
    var listPlayer: ArrayList<String> = ArrayList()
    var termsAndCon: Boolean = false

    var dobYear: Int = 0
    var dobDay: Int = 0
    var dobMonth: String = ""
//    val picker = CountryPicker.newInstance("Select Country")

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_sign_up)


        txtCountry.text = applicationContext.resources.configuration.locale.displayCountry
        initBackground()
        initlistner()

        initYear()
        listMonthInit()
        listDayInit()
        initPlayer()
        initSpeciality()
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

    private fun initSpeciality() {
        val specialityAdapter = ArrayAdapter<CharSequence>(this, R.layout.age_spinner_text, speciality)
        specialityAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerSpecialty.adapter = specialityAdapter

        spinnerSpecialty.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
//                Toast.makeText(
//                    this@SignUpActivity,
//                    player[position], Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun initPlayer() {
        val playerAdapter = ArrayAdapter<CharSequence>(this, R.layout.age_spinner_text, player)
        playerAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerPlayer.adapter = playerAdapter

        spinnerPlayer.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#A2511F"))
//                Toast.makeText(
//                    this@SignUpActivity,
//                    player[position], Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }



    private fun initOther() {

        constarintSignup.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
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
                dobDay = listDay.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        listState.add("UP")
        listState.add("MP")
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
                dobMonth = month.get(position)
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
        countrPickerSignup!!.setOnCountryChangeListener(this)
//        relCont.setOnClickListener(this)


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
            }  else if (editPasswordSignUp.text.toString().trim().isEmpty()) {
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
                Toast.makeText(this, "Confirm Password should be same as Password.", Toast.LENGTH_SHORT)
                    .show()
            }
            else if (editUserName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Username.", Toast.LENGTH_SHORT).show()
                editUserName.text?.clear()
            }
            else if ((getAge(dobYear, dobMonth, dobDay)) < 5) {
                Toast.makeText(this, "You must be at least 5 years old.", Toast.LENGTH_SHORT).show()

            }
            else if (!termsAndCon) {
                Toast.makeText(
                    this,
                    "Please agree to our Terms and Conditions.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(Intent(this, StoryActivity::class.java))
            }

        }
//        if (v == tvCountryCode) {
//            picker.show(supportFragmentManager, "COUNTRY_PICKER")
//        }
        if (v == imgBackSignup) {
            super.onBackPressed()
        }


    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }

    override fun onCountrySelected() {
        txtCountry.text = countrPickerSignup?.selectedCountryName
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
            "nov" -> monthInt = 11
            "Dev" -> monthInt = 12
        }

        return Period.between(
            LocalDate.of(year, monthInt, dayOfMonth),
            LocalDate.now()
        ).years
    }
}