package com.pharmacy.crack.main.view.mainActivities

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.AccuracyStatsAdapter
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.activity_game_result.*
import kotlinx.android.synthetic.main.activity_stats_iq.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class StatsIqActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var listImage: ArrayList<Int>
    lateinit var listPercent: ArrayList<Int>
    private lateinit var networkConnectivity: NetworkConnectivity
    private lateinit var pref: PrefHelper
    private var historyApiCalled: Boolean = false
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_stats_iq)

        initAll()
        listner()
    }

    private fun listner() {
        imgBackToolbar.setOnClickListener(this)
    }

    private fun initAll() {
        pref = PrefHelper(this)
        txtToolbar.text = "Accuracy\nStatistics"


        listPercent = ArrayList()
        listPercent.add(90)
        listPercent.add(80)
        listPercent.add(94)
        listPercent.add(71)
        listPercent.add(78)
        listPercent.add(69)
        listPercent.add(51)
        listPercent.add(42)
        listPercent.add(46)
        listPercent.add(76)
        listPercent.add(31)

        listImage = ArrayList()
        listImage.add(R.drawable.history)
        listImage.add(R.drawable.illegal_abused)
        listImage.add(R.drawable.otc_drugs_herbals)
        listImage.add(R.drawable.cardiology_hematology)
        listImage.add(R.drawable.infectious_disease_immunology)
        listImage.add(R.drawable.neurology_psychiatry)
        listImage.add(R.drawable.women_health_pediatrics)
        listImage.add(R.drawable.endocrinology_toxicology)
        listImage.add(R.drawable.new_drugs)
        listImage.add(R.drawable.pharmacy_law)
        listImage.add(R.drawable.oncology_misc)


        rvStats.adapter = AccuracyStatsAdapter(this, listPercent, listImage)

        txtStatsPlayername.text = pref.fullName

        if((!pref.profilePic.isNullOrEmpty())&&(pref.profilePic != "null")){
            Glide.with(this).load(pref.profilePic).placeholder(R.drawable.profile_img).into(imgPlayerStats)
        }

        initHistory()
    }
    private fun initHistory() {
        networkConnectivity = NetworkConnectivity(application)
        networkConnectivity.observe(this, androidx.lifecycle.Observer { isAvailable->
            when(isAvailable){
                true -> if (!historyApiCalled){
                    pref.showProgress(this)
                    Thread.sleep(1_000)
                    CoroutineScope(Dispatchers.IO).launch {
                        try{
                            fetchHistory()
                        }catch (e:Exception){
                            withContext(Dispatchers.Main) {
                                showToast(this@StatsIqActivity, "Please check your internet connection and try again.")
                            }
                        }
                    }
                }
            }
        })
    }

    private suspend fun fetchHistory() {

        val res = RetrofitFactory.api.getmatchHistory("Bearer "+pref.authToken)
        if (res.isSuccessful) {
            historyApiCalled = true

            res.body()?.let {

                withContext(Dispatchers.Main) {
                    pref.hideProgress()
                    txtWinStats.text = it.win.Wins.toString()
                    txtLossStats.text = it.loss.Losses.toString()
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                pref.hideProgress()
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts("${jObjError.getString("message")}")
                } catch (e: Exception) {
                    Toast.makeText(this@StatsIqActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }


    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
    }

}