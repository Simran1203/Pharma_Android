package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.toolbar.*

class QuestionActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var category:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_question)

        category = intent.getStringExtra("cat").toString()
        txtCategory.text = category

        initAll()
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text  = "Question 1"

        txtQue1.setOnClickListener(this)
        txtQue2.setOnClickListener(this)
        txtQue3.setOnClickListener(this)
        txtQue4.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtQue1){
            startActivity(Intent(this,AnswerActivity::class.java)
                .putExtra("option",1))
        }
        else if(v==txtQue2){
            startActivity(Intent(this,AnswerActivity::class.java)
                .putExtra("option",2))
        }
        else if(v==txtQue3){
            startActivity(Intent(this,AnswerActivity::class.java)
                .putExtra("option",3))
        }
        else if(v==txtQue4){
            startActivity(Intent(this,AnswerActivity::class.java)
                .putExtra("option",4))
        }
    }
}