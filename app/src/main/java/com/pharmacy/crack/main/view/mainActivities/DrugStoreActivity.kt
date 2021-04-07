package com.pharmacy.crack.main.view.mainActivities

import android.app.Dialog
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

    lateinit var dialogUnsuccessful : Dialog
    lateinit var dialogPurchase : Dialog
    lateinit var imgCloseUnsuccess : ImageView
    lateinit var imgClosePurchase : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_drug_store)

        imgBackToolbarMultiColor.setOnClickListener(this)
        constarint1Life.setOnClickListener(this)

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
        if(v== constarint1Life){
            dialogPurchase.show()
        }
        if(v== imgBackToolbarMultiColor){
            onBackPressed()
        }
    }
}