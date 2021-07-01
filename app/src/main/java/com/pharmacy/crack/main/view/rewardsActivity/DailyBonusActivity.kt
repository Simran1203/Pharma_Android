package com.pharmacy.crack.main.view.rewardsActivity

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.masterBonus.Getbonu
import com.pharmacy.crack.main.adapter.DailyBonusAdapter
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_daily_bonus.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class DailyBonusActivity : AppCompatActivity(),View.OnClickListener {

    var list: ArrayList<String> = ArrayList()
    var listGetBonus: ArrayList<Getbonu> = ArrayList()
    lateinit var adapter : DailyBonusAdapter
    lateinit var networkConnectivity: NetworkConnectivity
    lateinit var pref: PrefHelper

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_daily_bonus)

        initAll()
        clickListener()

        setTopLayout()

    }

    @SuppressLint("SetTextI18n")
    private fun initAll() {
        txtToolbar.text = "Daily Bonus"
        pref = PrefHelper(this)

        if (!isNetworkAvailable(this)) {
            showToast(
                this,
                "Please check your internet connection and try again."
            )
        }
        initGetBonus()

        list.add("Day 1")
        list.add("Day 2")
        list.add("Day 3")
        list.add("Day 4")
        list.add("Day 5")
        list.add("Day 6")
        list.add("Day 7")
        list.add("Day 7")
        list.add("Day 7")

    }

    private fun initGetBonus() {

        networkConnectivity = NetworkConnectivity(application)
        networkConnectivity.observe(this, androidx.lifecycle.Observer { isAvailable ->
            when (isAvailable) {
                true -> if (listGetBonus.isEmpty()) {
                    Thread.sleep(1_000)
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            pref.showProgress(this@DailyBonusActivity)
                            fetchBonus()
                        } catch (e: Exception) {
                            showToast(
                                this@DailyBonusActivity,
                                "Please check your internet connection and try again."
                            )
                        }
                    }
                }
                false -> showToast(
                    this,
                    "Please check your internet connection and try again."
                )
            }
        })

    }

    private suspend fun fetchBonus() {

        val res = RetrofitFactory.api.getBonus("Bearer " + pref.authToken)
        pref.hideProgress()
        if (res.isSuccessful) {
            res.body()?.let {
                listGetBonus = it.getbonus
                if (!listGetBonus.isNullOrEmpty()) {

                    adapter = DailyBonusAdapter(this,list,listGetBonus)
                    rvBonuss.adapter = adapter
                }
            }
        } else {
            try {
                val jObjError = JSONObject(res.errorBody()?.string())
                showToasts("${jObjError.getString("message")}")
            } catch (e: Exception) {
                showToasts(e.message.toString())
            }
        }
    }


    private fun clickListener() {
        imgBackToolbar.setOnClickListener(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setTopLayout() {
        bonusFifty.title = "0"
        bonusFifty.icon = getDrawable(R.drawable.fifty_fifty)

        bonusStopWatch.title = "0"
        bonusStopWatch.icon = getDrawable(R.drawable.stopwatch)

        bonusSpin.title = "0"
        bonusSpin.icon = getDrawable(R.drawable.respin)
    }

    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
    }
}