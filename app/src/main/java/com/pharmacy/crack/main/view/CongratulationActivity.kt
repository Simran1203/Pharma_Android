package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_congratulation.*

class CongratulationActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var category:String
     var level:Int = 0
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_congratulation)
        category = intent.getStringExtra("cat").toString()
        level = intent.getIntExtra("level",0)
        txtIncreasedDrug.text = "You Increased Your Drug IQ\nIn\n$category"
        txtLevelCong.text = "Level $level"

        txtConCongrats.setOnClickListener(this)
    }

    override fun onBackPressed() {

//            val intents = Intent()
//            intents.putExtra("level",level+1)
//            setResult(3,intents)
//            finish()

    }

    override fun onClick(v: View?) {
        if(v==txtConCongrats){
            // if all Levels clear go to You win In case of Solo
//                startActivity(Intent(this,WinActivity::class.java))

           // else go to back - next question

    val intents = Intent()
            intents.putExtra("level",level+1)
            setResult(3,intents)
            finish()
        }
    }
}