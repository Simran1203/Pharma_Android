package com.pharmacy.crack.main.view.mainActivities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.activity_i_q_map.*

class IQMapActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var pref: PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_q_map)
        setFullScreen(this)
        initAll()
        initClick()
    }

    private fun initAll() {
        pref = PrefHelper(this)
        if((!pref.profilePic.isNullOrEmpty())&&(pref.profilePic != "null")){
            Glide.with(this).load(pref.profilePic).placeholder(R.drawable.user_img).into(userImagelevel3)
        }
    }

    private fun initClick() {
        btnContinueIQMap.setOnClickListener(this)
        imgBackIQMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==btnContinueIQMap){
            super.onBackPressed()
        }
        else if(v==imgBackIQMap){
            super.onBackPressed()
        }
    }
}