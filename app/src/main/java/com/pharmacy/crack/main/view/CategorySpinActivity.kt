package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.CategorySpinAdapter
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToast
import kotlinx.android.synthetic.main.activity_category_spin.*
import java.util.*


class CategorySpinActivity : AppCompatActivity() {

    lateinit var listCat : ArrayList<String>
    lateinit var adapter: CategorySpinAdapter
    lateinit var countDownTimer: CountDownTimer
    var coundInterval: Long = 300
    var catName = arrayOf("Oncology & misc","Women & Pediatrics","Endocrinology & Toxicology","Infectious Disease & Immunology",
        "Casey","Abused Substances","Cardiology & Hematology","Infection","OTC & Herbal","Fun Facts",
    "Neurology & Psychology","New Rx","Infection","Law","Casey")

    companion object{
         var selection:Int = 0
    }
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_category_spin)
        listCat = ArrayList()

        for(i in 0..14){
            listCat.add(catName[i])
        }

        adapter = CategorySpinAdapter(this, listCat){ pos->
            onItemClick(pos)
        }

        rvCatSpin.adapter = adapter

        countDownTimer = object : CountDownTimer(30000, coundInterval) {
            override fun onTick(millisUntilFinished: Long) {

                val randomNumber = listOf(0, 1, 2,3,4,5,6,7,8,9,10,11,12,13,14).random()
                selection = randomNumber
                adapter.notifyDataSetChanged()
            }

            override fun onFinish() {
                if(selection==7||selection==12){
                    startActivity(Intent(this@CategorySpinActivity,EndGameActivity::class.java))
                }
                else if(selection==4||selection==14){
                    startActivity(Intent(this@CategorySpinActivity,LandedCasseyCapsuleActivity::class.java))
                }
                else{
                    startActivity(Intent(this@CategorySpinActivity,GotCategorySpinActivity::class.java)
                        .putExtra("cat",listCat.get(selection)))
                }

            }
        }

        countDownTimer.start()

        imgStopCatspin.setOnClickListener {
            countDownTimer.cancel()
            if(selection==7||selection==12){
                startActivity(Intent(this@CategorySpinActivity,EndGameActivity::class.java))
            }
            else if(selection==4||selection==14){
                startActivity(Intent(this@CategorySpinActivity,LandedCasseyCapsuleActivity::class.java))
            }
            else{
                startActivity(Intent(this@CategorySpinActivity,GotCategorySpinActivity::class.java)
                    .putExtra("cat",listCat.get(selection)))
            }
        }


    }

    private fun onItemClick(pos: Int) {
//        Log.d("TAGasd", "onItemClick: " + pos)
    }

    override fun onRestart() {
        super.onRestart()
        countDownTimer.start()
    }
    override fun onPause() {
        countDownTimer.cancel()
        super.onPause()
    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }

    override fun onBackPressed() {
        countDownTimer.cancel()
        super.onBackPressed()
    }
}