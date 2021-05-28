package com.pharmacy.crack.main.view

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.QuestionAdapter
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToasts
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.activity_got_category_spin.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question.txtQue
import kotlinx.android.synthetic.main.toolbar.*

class QuestionActivity : AppCompatActivity() {
    lateinit var category: String
    private var correctAnsNo: Int = 1
    private var level: Int = 1
    private var totalLevel: Int = 3
    private lateinit var listOption: ArrayList<String>
    lateinit var countDownTimer: CountDownTimer
    lateinit var countDownTimer24Hour: CountDownTimer
    lateinit var dialogCorrect: Dialog
    lateinit var dialogInCorrect: Dialog
    lateinit var imgDialogCorrect: ImageView
    lateinit var txtCorrectAnsw: TextView
    lateinit var txtPointAnsw: TextView
    lateinit var txtIncAns: TextView
    var coundInterval: Long = 1000
    var isRunning24Hrs: Boolean = false
    companion object{
        var wrongAns=0;
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
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

        rvOption.adapter = QuestionAdapter(this, listOption) { pos ->
            onItemClick(pos)
        }
        initAll()
    }

    private fun onItemClick(pos: Int) {
        if(isRunning24Hrs ==true){
            isRunning24Hrs=false
            countDownTimer24Hour.cancel()
        }else{
            countDownTimer.cancel()
        }

        if (pos == 0) {
            if (correctAnsNo == 3 && level == totalLevel) {
                wrongAns = 0
                startActivity(Intent(this, WinActivity::class.java))
            } else if (correctAnsNo == 3) {
                val intents = Intent(this, CongratulationActivity::class.java).apply {
                    putExtra("cat", category)
                    putExtra("level", level)
                }
                startActivityForResult(intents, 3)
            } else {
                txtToolbar.visibility = View.INVISIBLE
                dialogCorrect.show()
                imageCategoryWithAnimCorrectAns()

            }
        } else {
            if (wrongAns <2) {
                txtToolbar.visibility = View.INVISIBLE
                dialogInCorrectAnim()
            }
            else {
                wrongAns=0
                startActivity(Intent(this, LoseActivity::class.java))
            }
        }
    }

    private fun dialogInCorrectAnim() {
        dialogInCorrect.show()
        val animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom
        )
        txtIncAns.startAnimation(animation)

        Handler().postDelayed({
            wrongAns = wrongAns + 1
            startActivity(
                Intent(this, IncorrectActivity::class.java)
                    .putExtra("optionNo", 4)
                    .putExtra("que", txtQue.text.toString())
                    .putExtra("correct", correctAnsNo)
                    .putExtra("time", txtTimeQue.text.toString())
                    .putStringArrayListExtra("option", listOption)
            )
            dialogInCorrect.dismiss()
        },2000)
    }

    private fun imageCategoryWithAnimCorrectAns() {
        when (category) {
            "Oncology & misc" -> imgDialogCorrect.setImageResource(R.drawable.oncology_misc_large)
            "Women & Pediatrics" -> imgDialogCorrect.setImageResource(R.drawable.women_health_pediatrics_large)
            "Endrocrinolgy" -> imgDialogCorrect.setImageResource(R.drawable.endocrinology_toxicology_large)
            "Infectious Disease" -> imgDialogCorrect.setImageResource(R.drawable.infectious_disease_immunology_large)
            "Casey" -> imgDialogCorrect.setImageResource(R.drawable.casey_capsule_large)
            "Abused Substances" -> imgDialogCorrect.setImageResource(R.drawable.illegal_abused_large)
            "Cardiology & Hematology" -> imgDialogCorrect.setImageResource(R.drawable.cardiology_hematology_large)
            "Infection" -> imgDialogCorrect.setImageResource(R.drawable.infectious_disease_immunology_large)
            "OTC & Herbal" -> imgDialogCorrect.setImageResource(R.drawable.otc_drugs_herbals_large)
            "Fun Facts" -> imgDialogCorrect.setImageResource(R.drawable.history_large)
            "Neurology" -> imgDialogCorrect.setImageResource(R.drawable.neurology_psychiatry_large)
            "New Rx" -> imgDialogCorrect.setImageResource(R.drawable.new_drugs_large)
            "Law" -> imgDialogCorrect.setImageResource(R.drawable.pharmacy_law_large)
        }

        val animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom
        )
        imgDialogCorrect.startAnimation(animation)
        txtCorrectAnsw.startAnimation(animation)
        txtPointAnsw.startAnimation(animation)

        Handler().postDelayed({

            val intents = Intent(this, AnswerActivity::class.java).apply {
                putExtra("optionNo", 1)
                putExtra("que", txtQue.text.toString())
                putExtra("correct", correctAnsNo)
                putStringArrayListExtra("option", listOption)
            }
            startActivityForResult(intents, 2)
            dialogCorrect.dismiss()
        },2000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2) {
            txtToolbar.visibility = View.VISIBLE
            if(wrongAns==2){
                rel_fifty.alpha = 0.5F
            }
            correctAnsNo = data?.getIntExtra("correct", 0)!!
            txtToolbar.text = "Question $correctAnsNo"
            ratngLevel.rating = (correctAnsNo).toFloat()
        }
        if (requestCode == 3) {
            txtToolbar.visibility = View.VISIBLE
            if(wrongAns==2){
                rel_fifty.alpha = 0.5F
            }
            correctAnsNo = 1
            level = data?.getIntExtra("level", 0)!!
            txtTimeLevel.text = "Level $level"
            txtToolbar.text = "Question $correctAnsNo"
            ratngLevel.rating = (correctAnsNo).toFloat()

        }

    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "Question 1"
        dialogCorrect = Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialogCorrect.setContentView(R.layout.dialog_correct_ans)
        imgDialogCorrect = dialogCorrect.findViewById(R.id.imgDialogCorrect)
        txtCorrectAnsw = dialogCorrect.findViewById(R.id.txtCorrectAnsw)
        txtPointAnsw = dialogCorrect.findViewById(R.id.txtPointAnsw)
        dialogCorrect.setCanceledOnTouchOutside(false)
        dialogCorrect.setCancelable(false)

        dialogInCorrect = Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialogInCorrect.setContentView(R.layout.dialog_incorrect_ans)
        txtIncAns = dialogInCorrect.findViewById(R.id.txtIncAns)
        dialogInCorrect.setCanceledOnTouchOutside(false)
        dialogInCorrect.setCancelable(false)

        countDownTimer = object : CountDownTimer(21000, coundInterval) {
            override fun onTick(millisUntilFinished: Long) {
                txtTimeQue.text =  String.format("%02d:%02d",
                    ((millisUntilFinished/1000) % 3600) / 60, ((millisUntilFinished/1000) % 60));
            }

            override fun onFinish() {
                countDownTimer.cancel()
                if (wrongAns <2) {
                    txtToolbar.visibility = View.INVISIBLE
                  dialogInCorrectAnim()
                } else {
                    wrongAns=0
                    startActivity(Intent(this@QuestionActivity, LoseActivity::class.java))
                }
            }
        }
        countDownTimer.start()

        countDownTimer24Hour = object : CountDownTimer(86400000,1000){
            override fun onTick(millisUntilFinished: Long) {

                txtTimeQue.text =  String.format("%02d:%02d:%02d", (millisUntilFinished/1000) / 3600,
                    ((millisUntilFinished/1000) % 3600) / 60, ((millisUntilFinished/1000) % 60));
            }

            override fun onFinish() {
                countDownTimer24Hour.cancel()
                isRunning24Hrs = false
                if (wrongAns <2) {
                    txtToolbar.visibility = View.INVISIBLE
                    dialogInCorrectAnim()
                } else {
                    wrongAns=0
                    startActivity(Intent(this@QuestionActivity, LoseActivity::class.java))
                }
            }

        }

        relTimeQue.setOnClickListener {
            isRunning24Hrs = true
            countDownTimer.cancel()
            countDownTimer24Hour.start()
            // stop timer for 24 hours
        }
    }

    override fun onRestart() {
        super.onRestart()
        txtToolbar.visibility = View.VISIBLE
        countDownTimer.start()
        if(wrongAns==2){
            rel_fifty.alpha = 0.5F
        }
    }

    override fun onBackPressed() {
        wrongAns=0
        countDownTimer.cancel()
        super.onBackPressed()
    }

}