package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.categoryModels.Category
import com.pharmacy.crack.main.adapter.CategorySpinAdapter
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_category_spin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*


class CategorySpinActivity : AppCompatActivity() {


    lateinit var adapter: CategorySpinAdapter
    lateinit var countDownTimer: CountDownTimer
    var coundInterval: Long = 300
    var catName = arrayOf("Oncology & misc","Women & Pediatrics","Endocrinology & Toxicology","Infectious Disease & Immunology",
        "Casey","Abused Substances","Cardiology & Hematology","Infection","OTC & Herbal","Fun Facts",
    "Neurology & Psychology","New Rx","Infection","Law","Casey")

    lateinit var networkConnectivity: NetworkConnectivity
    var listCategory: ArrayList<Category> = ArrayList()
    lateinit var pref: PrefHelper

    companion object{
         var selection:Int = 0
    }
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_category_spin)
        pref = PrefHelper(this)

        countDownTimer = object : CountDownTimer(30000, coundInterval) {
            override fun onTick(millisUntilFinished: Long) {

                val randomNumber = listOf(0, 1, 2,3,4,5,6,7,8,9,10).random()
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
                        .putExtra("cat", listCategory[selection].name))
                }

            }
        }



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
                    .putExtra("cat", listCategory[selection].name))
            }
        }

        initfetchcategory()
    }

    private fun initfetchcategory() {

        networkConnectivity = NetworkConnectivity(application)
        networkConnectivity.observe(this, androidx.lifecycle.Observer { isAvailable ->
            when (isAvailable) {
                true -> if (listCategory.isEmpty()) {
                    Thread.sleep(1_000)
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            pref.showProgress(this@CategorySpinActivity)
                            fetchcategory()
                        } catch (e: Exception) {
                            if(pref.mDialog.isShowing){
                                pref.hideProgress()
                            }
                            showToast(
                                this@CategorySpinActivity,
                                "Please check your internet connection and try again."
                            )
                        }
                    }
                }
                false -> showToast(
                    this@CategorySpinActivity,
                    "Please check your internet connection and try again."
                )
            }
        })
    }

    private suspend fun fetchcategory() {

        val res = RetrofitFactory.api.getcategory("Bearer " + pref.authToken)

        pref.hideProgress()
        if (res.isSuccessful) {
            res.body()?.let {
                listCategory = it.category
                if (!listCategory.isNullOrEmpty()) {
                    imgStopCatspin.visibility = View.VISIBLE
                    val modelCasey = Category("",-1,"Casey")
                    val modelCasey1 = Category("",-2,"Casey")
                    val modelInfection = Category("",-3,"Infection")
                    val modelInfection1 = Category("",-4,"Infection")
                    listCategory.add(4,modelCasey)
                    listCategory.add(7,modelInfection)
                    listCategory.add(12,modelInfection1)
                    listCategory.add(14,modelCasey1)
                    adapter = CategorySpinAdapter(this, listCategory){ pos->
                        onItemClick(pos)
                    }
                    rvCatSpin.adapter = adapter
                    countDownTimer.start()
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

    }
}