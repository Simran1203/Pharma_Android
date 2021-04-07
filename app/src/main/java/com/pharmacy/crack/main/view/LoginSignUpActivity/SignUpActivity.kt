package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    val month = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    var listYear: ArrayList<Int> = ArrayList()
    var listDay: ArrayList<Int> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_sign_up)

        initBackground()
        initlistner()

        initYear()
        listMonthInit()
        listDayInit()

        constarintSignup.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        }

    }

    private fun listDayInit() {

        for (i in 1 until 32){
            listDay.add(i)
        }

        val dayAdapter:ArrayAdapter<Int> = ArrayAdapter<Int>(
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
        for (i in currentYear downTo 1970){
        listYear.add(i)
        }

        val yearAdapter: ArrayAdapter<Int> = ArrayAdapter<Int>(this, R.layout.age_spinner_text, listYear)
        yearAdapter.setDropDownViewResource(R.layout.age_spinner)
        spinnerYear.setAdapter(yearAdapter)

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
        spinnerMonth.setAdapter(monthAdapter)

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
        if(v==txtSignup){
            hideKeyBoard(this)
            if(edtName.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter the Name", Toast.LENGTH_SHORT).show()
            }
            else if(editEmail.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show()
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches())){
                Toast.makeText(this, "Email Address is not valid", Toast.LENGTH_SHORT).show()
            }
            else if(editPasswordSignUp.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter the Password", Toast.LENGTH_SHORT).show()
            }
            else if(editPasswordSignUp.getText().toString().length<6){
                Toast.makeText(this, "Password Should be at least 6 digit", Toast.LENGTH_SHORT).show()
            }
            else if(editCnfmPasswordSignUp.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter the Confirm Password", Toast.LENGTH_SHORT).show()
            }
            else if(!editCnfmPasswordSignUp.text.toString().equals(
                    editPasswordSignUp.getText().toString()
                )){
                Toast.makeText(this, "Confirm Password should same as Password", Toast.LENGTH_SHORT).show()
            }
            else if(editUserName.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter the Username", Toast.LENGTH_SHORT).show()
            }
            else if(editCollege.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter the College Name", Toast.LENGTH_SHORT).show()
            }
            else{
                startActivity(Intent(this, StoryActivity::class.java))
            }

        }


    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }
}