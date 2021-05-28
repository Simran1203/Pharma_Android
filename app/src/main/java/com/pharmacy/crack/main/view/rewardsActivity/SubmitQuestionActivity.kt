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
import com.pharmacy.crack.utils.editTextBackground
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import com.ybs.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_submit_question.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*


class SubmitQuestionActivity : AppCompatActivity(),View.OnClickListener, CountryCodePicker.OnCountryChangeListener {
    var listCategory: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_submit_question)
        txtCountryQuest.text = applicationContext.resources.configuration.locale.displayCountry

        constraintQuest.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        initCategory()
        clickListner()

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
        listCategory.add("Infectious Disease & Immunology")
        listCategory.add("Womens & Pediatrics")
        listCategory.add("New Rx")
        listCategory.add("Law")
        listCategory.add("Abused Substances")
        listCategory.add("Fun Facts")
        listCategory.add("OTC & Herbals")
        listCategory.add("Endocrinology & Toxicology")
        listCategory.add("Cardiology & Hematology")
        listCategory.add("Neurology & Psychology")
        listCategory.add("Oncology & Miscellaneous")

        val catAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.age_spinner_text,
            listCategory
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
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    override fun onClick(v: View?) {
        if(v==txtSubmitQueston){
            if(edtTypeQue.text.toString().length<20){
                Toast.makeText(this, "Please enter at least 20 min chars long Question.", Toast.LENGTH_SHORT).show()
                edtTypeQue.requestFocus()
            }
            else if(edtCorrectAns.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter in Correct Ans Field.", Toast.LENGTH_SHORT).show()
                edtCorrectAns.requestFocus()
            }
            else if(edtCorrectAns1.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter in Wrong Ans 01 Field.", Toast.LENGTH_SHORT).show()
                edtCorrectAns1.requestFocus()
            }
            else if(edtCorrectAns2.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter in Wrong Ans 02 Field.", Toast.LENGTH_SHORT).show()
                edtCorrectAns2.requestFocus()
            }
            else if(edtCorrectAns3.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter in Wrong Ans 03 Field.", Toast.LENGTH_SHORT).show()
                edtCorrectAns3.requestFocus()
            }else{
                hideKeyBoard(this)
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
        }
       else if(v==imgBackQuest){
            super.onBackPressed()
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