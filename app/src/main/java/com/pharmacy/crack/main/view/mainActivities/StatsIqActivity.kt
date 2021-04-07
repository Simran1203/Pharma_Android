package com.pharmacy.crack.main.view.mainActivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.AccuracyStatsAdapter
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_stats_iq.*
import kotlinx.android.synthetic.main.toolbar.*


class StatsIqActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var listImage: ArrayList<Int>
    lateinit var listPercent: ArrayList<Int>
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

//        val layoutManager = GridLayoutManager(this, 2)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                when (position) {
//                    10 -> return 2
//                    else -> return 1
//                }
//            }
//        }
//        rvStats.layoutManager = layoutManager
        rvStats.adapter = AccuracyStatsAdapter(this, listPercent, listImage)

    }

    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
    }

}