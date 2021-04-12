package com.pharmacy.crack.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_answer.*

class AnswerActivity : AppCompatActivity(), View.OnClickListener {

    var correctOptionNO: Int = 0
    lateinit var optionList: ArrayList<String>
    lateinit var question: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_answer)
        correctOptionNO = intent.getIntExtra("optionNo", 0)
        question = intent.getStringExtra("que").toString()
        optionList = intent.getStringArrayListExtra("option") as ArrayList<String>
        txtAns1.text = optionList[0]
        txtAns2.text = optionList[1]
        txtAns3.text = optionList[2]
        txtAns4.text = optionList[3]
        txtQue.text = question
        when (correctOptionNO) {
            1 -> txtAns1.alpha = 1.0F
            2 -> txtAns2.alpha = 1.0F
            3 -> txtAns3.alpha = 1.0F
            4 -> txtAns4.alpha = 1.0F
        }
        txtNextQuest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == txtNextQuest) {
            onBackPressed()
        }
    }
}