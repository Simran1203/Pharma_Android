package com.pharmacy.crack.main.view.mainActivities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_drug_store.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*
import kotlinx.android.synthetic.main.toolbar_multicolor.imgBackToolbarMultiColor

class DrugStoreActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var dialogUnsuccessful : Dialog
    private lateinit var dialogPurchase : Dialog
    private lateinit var imgCloseUnsuccess : ImageView
    private lateinit var imgClosePurchase : ImageView
    private var fromSource:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_drug_store)

        imgBackToolbarMultiColor.setOnClickListener(this)
        constraint2.setOnClickListener(this)
        constraint3.setOnClickListener(this)

        if(intent.hasExtra("fromSource")){
            fromSource = intent.getStringExtra("fromSource").toString()
        }
        initAll()

    }

    private fun initAll() {
        txtToolLime.setText("Drug")
        txtToolWlidStraw.setText("Store")

        dialogUnsuccessful = Dialog(this@DrugStoreActivity,android.R.style.Theme_Light)
        dialogUnsuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogUnsuccessful.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogUnsuccessful.setCancelable(false)
        dialogUnsuccessful.setContentView(R.layout.dialog_purchase_unsuccessful)
        imgCloseUnsuccess = dialogUnsuccessful.findViewById(R.id.imgCloseUnsuccess)
        imgCloseUnsuccess.setOnClickListener(View.OnClickListener {
            dialogUnsuccessful.dismiss()
        })
        dialogPurchase = Dialog(this@DrugStoreActivity,android.R.style.Theme_Light)
        dialogPurchase.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPurchase.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogPurchase.setCancelable(false)
        dialogPurchase.setContentView(R.layout.dialog_purchase)
        imgClosePurchase = dialogPurchase.findViewById(R.id.imgClosePurchase)
        imgClosePurchase.setOnClickListener(View.OnClickListener {
            dialogPurchase.dismiss()
            dialogUnsuccessful.show()
        })
    }

    override fun onClick(v: View?) {
        if(v== constraint2){
            dialogPurchase.show()
        }
        if(v== constraint3){
            dialogPurchase.show()
        }
        if(v== imgBackToolbarMultiColor){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if(fromSource == "Dashboard"){
            super.onBackPressed()
        }
        else{
            startActivity(Intent(this,DashboardActivity::class.java))
            finishAffinity()
        }
    }
}