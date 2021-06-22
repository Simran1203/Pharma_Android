package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_got_category_spin.*
import kotlinx.android.synthetic.main.row_catspin.view.*


class GotCategorySpinActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var category:String

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_got_category_spin)

        
        category = intent.getStringExtra("cat").toString()
        txtGot.text = "You Landed on ${category}!"


        when (category) {
            "Oncology & misc" -> imgGotNeuro.setImageResource(R.drawable.oncology_misc_large)
            "Women & Pediatrics" -> imgGotNeuro.setImageResource(R.drawable.women_health_pediatrics_large)
            "Endocrinology & Toxicology" -> imgGotNeuro.setImageResource(R.drawable.endocrinology_toxicology_large)
            "Infectious Disease & Immunology" -> imgGotNeuro.setImageResource(R.drawable.infectious_disease_immunology_large)
            "Casey" -> imgGotNeuro.setImageResource(R.drawable.casey_capsule_large)
            "Abused Substances" -> imgGotNeuro.setImageResource(R.drawable.illegal_abused_large)
            "Cardiology & Hematology" -> imgGotNeuro.setImageResource(R.drawable.cardiology_hematology_large)
            "Infection" -> imgGotNeuro.setImageResource(R.drawable.infectious_disease_immunology_large)
            "OTC & Herbal" -> imgGotNeuro.setImageResource(R.drawable.otc_drugs_herbals_large)
            "Fun Facts" -> imgGotNeuro.setImageResource(R.drawable.history_large)
            "Neurology & Psychology" -> imgGotNeuro.setImageResource(R.drawable.neurology_psychiatry_large)
            "New Rx" -> imgGotNeuro.setImageResource(R.drawable.new_drugs_large)
            "Law" -> imgGotNeuro.setImageResource(R.drawable.pharmacy_law_large)
        }

        val animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom
        )
        imgGotNeuro.startAnimation(animation)

        listner()
    }

    private fun listner() {
        linearUseSpin.setOnClickListener(this)
        txtstartGme.setOnClickListener(this)
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")
    override fun onClick(v: View?) {
        if(v==linearUseSpin){
            super.onBackPressed()
        }
        else if(v==txtstartGme){
            startActivity(
                Intent(this, QuestionActivity::class.java)
                    .putExtra("cat", category)
            )
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}