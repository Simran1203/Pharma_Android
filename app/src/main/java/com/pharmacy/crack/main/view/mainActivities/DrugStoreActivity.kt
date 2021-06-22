package com.pharmacy.crack.main.view.mainActivities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.activity_drug_store.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*
import kotlinx.android.synthetic.main.toolbar_multicolor.imgBackToolbarMultiColor

class DrugStoreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dialogUnsuccessful: Dialog
    private lateinit var dialogPurchase: Dialog
    private lateinit var dialogProVer: Dialog
    private lateinit var imgCloseUnsuccess: ImageView
    private lateinit var imgClosePurchase: ImageView
    private lateinit var imgCloseProVer: ImageView
    private lateinit var txtBuyDrug: RegularTextView
    private lateinit var txtBuyProVer: RegularTextView
    private var fromSource: String = ""

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_drug_store)

        imgBackToolbarMultiColor.setOnClickListener(this)
        constraint2.setOnClickListener(this)
        constraint3.setOnClickListener(this)

        initAll()

    }

    private fun initAll() {
        txtToolLime.text = "Drug"
        txtToolWlidStraw.text = "Store"

        dialogUnsuccessful = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogUnsuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogUnsuccessful.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogUnsuccessful.setCancelable(false)
        dialogUnsuccessful.setContentView(R.layout.dialog_purchase_unsuccessful)
        imgCloseUnsuccess = dialogUnsuccessful.findViewById(R.id.imgCloseUnsuccess)
        imgCloseUnsuccess.setOnClickListener {
            dialogUnsuccessful.dismiss()
        }
        dialogPurchase = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogPurchase.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPurchase.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogPurchase.setCancelable(false)
        dialogPurchase.setContentView(R.layout.dialog_purchase)
        imgClosePurchase = dialogPurchase.findViewById(R.id.imgClosePurchase)
        txtBuyDrug = dialogPurchase.findViewById(R.id.txtBuyDrug)
        imgClosePurchase.setOnClickListener {
            dialogPurchase.dismiss()
            dialogUnsuccessful.show()
        }
        txtBuyDrug.setOnClickListener {
            super.onBackPressed()
        }

        dialogProVer = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogProVer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogProVer.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogProVer.setCancelable(false)
        dialogProVer.setContentView(R.layout.dialog_purchase_proversion)
        imgCloseProVer = dialogProVer.findViewById(R.id.imgCloseProVer)
        txtBuyProVer = dialogProVer.findViewById(R.id.txtBuyProVer)
        imgCloseProVer.setOnClickListener {
            dialogProVer.dismiss()
            dialogUnsuccessful.show()
        }
        txtBuyProVer.setOnClickListener {
            super.onBackPressed()
        }


    }

    override fun onClick(v: View?) {
        if (v == constraint2) {
            dialogProVer.show()
        }
        if (v == constraint3) {
            dialogPurchase.show()
        }
        if (v == imgBackToolbarMultiColor) {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}