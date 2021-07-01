package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.categoryModels.Category
import com.pharmacy.crack.main.adapter.CategorySpinAdapter
import com.pharmacy.crack.main.adapter.LandedCaseyCapsulAdapter
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_category_spin.*
import kotlinx.android.synthetic.main.activity_category_spin.rvCatSpin
import kotlinx.android.synthetic.main.activity_landed_cassey_capsule.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.ArrayList

class LandedCasseyCapsuleActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var networkConnectivity: NetworkConnectivity
    var listCategory: ArrayList<Category> = ArrayList()
    lateinit var pref: PrefHelper
    lateinit var adapter: LandedCaseyCapsulAdapter

    companion object{
        var selectionLandedcasey:Int = 4
    }
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_landed_cassey_capsule)
        pref = PrefHelper(this)

        txtStartGameLanded.setOnClickListener(this)

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
                            pref.showProgress(this@LandedCasseyCapsuleActivity)
                            fetchcategory()
                        } catch (e: Exception) {

                            if(pref.mDialog.isShowing){
                                pref.hideProgress()
                            }
                            Log.d("initfetchcategory:",e.message.toString())
                            showToast(
                                this@LandedCasseyCapsuleActivity,
                                "Please check your internet connection and try again."
                            )
                        }
                    }
                }
                false -> showToast(
                    this@LandedCasseyCapsuleActivity,
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
                    val modelCasey = Category("",-1,"Casey")
                    listCategory.add(4,modelCasey)
                    adapter = LandedCaseyCapsulAdapter(this, listCategory){ pos->
                        onItemClick(pos)
                    }
                    rvCatLand.adapter = adapter
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
        selectionLandedcasey = pos
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        if(v==txtStartGameLanded){
            if(selectionLandedcasey==4){
                showToast(this,"Please select category.")
            }else{
                startActivity(Intent(this,QuestionActivity::class.java)
                    .putExtra("cat", listCategory[selectionLandedcasey].name))
            }

        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}