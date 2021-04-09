package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.CategorySpinAdapter
import com.pharmacy.crack.main.adapter.LandedCaseyCapsulAdapter
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToast
import kotlinx.android.synthetic.main.activity_category_spin.*
import kotlinx.android.synthetic.main.activity_category_spin.rvCatSpin
import kotlinx.android.synthetic.main.activity_landed_cassey_capsule.*
import java.util.ArrayList

class LandedCasseyCapsuleActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var listCat : ArrayList<String>
    lateinit var adapter: LandedCaseyCapsulAdapter
    var catName = arrayOf("Oncology & misc","Women & Pediatrics","Endrocrinolgy","Infectious Disease",
        "Casey","Abused Substances","Cardiology & Hematology","Law","OTC & Herbal","Fun Facts",
        "Neurology","New Rx")

    companion object{
        var selectionLandedcasey:Int = 4
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_landed_cassey_capsule)

        listCat = ArrayList()

        for(i in 0..11){
            listCat.add(catName[i])
        }

        adapter = LandedCaseyCapsulAdapter(this, listCat){ pos->
            onItemClick(pos)
        }

        rvCatLand.adapter = adapter

        txtStartGameLanded.setOnClickListener(this)
    }

    private fun onItemClick(pos: Int) {
        selectionLandedcasey = pos
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        if(v==txtStartGameLanded){
            if(selectionLandedcasey==4){
                showToast(this,"Please Select category")
            }else{
                startActivity(Intent(this,QuestionActivity::class.java)
                    .putExtra("cat",listCat.get(selectionLandedcasey)))
            }

        }
    }
}