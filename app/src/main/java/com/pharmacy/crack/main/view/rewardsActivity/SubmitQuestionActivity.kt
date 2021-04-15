package com.pharmacy.crack.main.view.rewardsActivity

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.editTextBackground
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_submit_question.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*


class SubmitQuestionActivity : AppCompatActivity(),View.OnClickListener {
    var listCategory: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_submit_question)

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

        edtTypeQue.movementMethod = ScrollingMovementMethod()
        ScrollingMovementMethod.getInstance()
        edtTypeQue.setOnTouchListener(OnTouchListener { v, event ->
            edtTypeQue.parent.requestDisallowInterceptTouchEvent(true)
            false
        })
    }

    private fun initCategory() {

        editTextBackground(relCat, "#FFFC7C", "#FF48AF")
        listCategory.add("Oncology & misc")
        listCategory.add("Women & Pediatrics")
        listCategory.add("Endocrinology")
        listCategory.add("infectious Disease")
        listCategory.add("Casey")
        listCategory.add("Abused Substances")
        listCategory.add("Cardiology & Hematology")
        listCategory.add("Infection")
        listCategory.add("OTC & Herbal")
        listCategory.add("Fun Facts")
        listCategory.add("Neurology")
        listCategory.add("New Rx")
        listCategory.add("Law")

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
                Toast.makeText(this, "Please enter 20 chars as minimum.", Toast.LENGTH_SHORT).show()
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
        if(v==imgBackQuest){
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }

}