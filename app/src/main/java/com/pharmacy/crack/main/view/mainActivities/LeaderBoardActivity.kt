package com.pharmacy.crack.main.view.mainActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.LeaderBoardAdapter
import com.pharmacy.crack.main.view.ShareActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_leader_board.*
import kotlinx.android.synthetic.main.toolbar.*

class LeaderBoardActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var list: ArrayList<String>
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        list = ArrayList()
        list.add("Elizebth")
        list.add("Dennis Reynolds")
        list.add("Laurel Schneider")
        list.add("Debra Carpenter")
        list.add("Beverly Barnett")

        rvLeaderBoard.adapter = LeaderBoardAdapter(this,list)

        initAll()
        clickListners()
    }

    private fun clickListners() {
        imgBackToolbar.setOnClickListener(this)
        txtSharePillsLeader.setOnClickListener(this)
    }

    private fun initAll() {
        txtToolbar.setText("Weekly Ranking")

    }

    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
        else if(v==txtSharePillsLeader){
            startActivity(Intent(this,ShareActivity::class.java))
        }
    }
}