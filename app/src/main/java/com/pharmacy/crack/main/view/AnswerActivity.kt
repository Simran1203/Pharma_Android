package com.pharmacy.crack.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_answer.*

class AnswerActivity : AppCompatActivity(),View.OnClickListener {

    var correctOption : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_answer)
        correctOption = intent.getIntExtra("option",0)
//       when(correctOption){
//           1 -> txtAns1.alpha.tex = 10
//       }
        txtNextQuest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtNextQuest){
            onBackPressed()
        }
    }
}