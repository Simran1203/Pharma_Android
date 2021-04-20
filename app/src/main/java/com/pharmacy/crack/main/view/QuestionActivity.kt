package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Dimension
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.QuestionAdapter
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.toolbar.*

class QuestionActivity : AppCompatActivity(){
    lateinit var category:String
    private var correctAnsNo:Int = 1
    private var level:Int = 9
    lateinit var listOption:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_question)

        listOption = ArrayList()
        listOption.add("A) topiramate")
        listOption.add("B) amitryptyline")
        listOption.add("C) propanolol")
        listOption.add("D) valproic acid")

        category = intent.getStringExtra("cat").toString()
        txtCategory.text = category

        rvOption.adapter = QuestionAdapter(this,listOption){
             pos -> onItemClick(pos)
        }
        initAll()
    }

    private fun onItemClick(pos: Int) {
        if(pos==0) {
            if(correctAnsNo==3){
                val intents = Intent(this, CongratulationActivity::class.java).apply {
                    putExtra("cat",category)
                    putExtra("level",level)
                }
                startActivityForResult(intents, 3)
            }else{
                val intents = Intent(this, AnswerActivity::class.java).apply {
                    putExtra("optionNo",1)
                    putExtra("que",txtQue.text.toString())
                    putExtra("correct",correctAnsNo)
                    putStringArrayListExtra("option",listOption)
            }
                startActivityForResult(intents, 2)

        }
            }


            else {
            if (PrefHelper(this).gametype.equals("Solo")) {
                startActivity(Intent(this,LoseActivity::class.java))
            }else{
                startActivity(Intent(this, IncorrectActivity::class.java)
                    .putExtra("optionNo",4)
                    .putExtra("que",txtQue.text.toString())
                    .putExtra("correct",correctAnsNo)
                    .putStringArrayListExtra("option",listOption))
            }

        }



        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2){
           correctAnsNo = data?.getIntExtra("correct",0)!!

        }
        if(requestCode==3){
            correctAnsNo =1
            level = data?.getIntExtra("level",0)!!
        }
    }


    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text  = "Question 1"

    }


}