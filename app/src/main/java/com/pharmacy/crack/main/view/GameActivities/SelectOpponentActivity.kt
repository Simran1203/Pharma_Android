package com.pharmacy.crack.main.view.GameActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.FriendsAdapter
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_select_opponent.*
import kotlinx.android.synthetic.main.activity_start_game.*
import kotlinx.android.synthetic.main.toolbar.*

class SelectOpponentActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var list: ArrayList<String>
    lateinit var adapter : FriendsAdapter
    companion object{
        var selectedOpponent:Int = -1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_select_opponent)

        initAll()
        listner()
    }



    private fun listner() {
        imgBackToolbar.setOnClickListener(this)
        txtPlay.setOnClickListener(this)
        txtRandomOpp.setOnClickListener(this)
        constraintOpponent.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        })

    }

    private fun initAll() {
        txtToolbar.text = "Select Opponent"

        list = ArrayList()
        list.add("Michel")
        list.add("Rassel")

        adapter = FriendsAdapter(this,list){pos ->
            onItemClick(pos)
        }
        rvFriends.adapter = adapter
    }

    private fun onItemClick(pos: Int) {
        selectedOpponent = pos
        adapter.notifyDataSetChanged()

    }

    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
        if(v==txtPlay){
            if(selectedOpponent==-1){
                showToast(this,"Please select your Opponent")
            }else{
                startActivity(Intent(this,TwoPlayerBattleActivity::class.java))
            }

        }
        if(v==txtRandomOpp){
            startActivity(Intent(this,TwoPlayerBattleActivity::class.java))
        }
    }
}