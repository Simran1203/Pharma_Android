package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_incorrect.*

class IncorrectActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_incorrect)

        initAll()
    }

    private fun initAll() {
        txtReportQuest.setOnClickListener(this)
        txtendGame.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtReportQuest){
            startActivity(Intent(this,ReportQuestionActivity::class.java)
                .putExtra("que",txtQueInCorr.text.toString())
            )
        }
        else if(v==txtendGame){
            startActivity(Intent(this,WinActivity::class.java))

            //  or  //
//            startActivity(Intent(this,LoseActivity::class.java))
        }
    }
}