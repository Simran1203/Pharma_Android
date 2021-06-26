package com.pharmacy.crack.main.view.mainActivities

import android.app.Dialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.viewUtils.HardBoldTextView
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import com.pharmacy.crack.utils.viewUtils.SemiBoldTextView
import kotlinx.android.synthetic.main.activity_drug_store.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*

class DrugStoreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dialogUnsuccessful: Dialog
    private lateinit var dialogProStartPack: Dialog
    private lateinit var dialogProVer: Dialog
    private lateinit var dialogOneK: Dialog
    private lateinit var dialog3m6m: Dialog
    private lateinit var dialog25and75: Dialog
    private lateinit var dialogFreeProVer: Dialog
    private lateinit var imgCloseUnsuccess: ImageView
    private lateinit var imgCloseStartPack: ImageView
    private lateinit var imgCloseProVer: ImageView
    private lateinit var imgClose1k: ImageView
    private lateinit var imgClose3m6m: ImageView
    private lateinit var imgClose25and75: ImageView
    private lateinit var imgCloseFreePro: ImageView
    private lateinit var txtBuyStartPack: RegularTextView
    private lateinit var txtBuy1K: RegularTextView
    private lateinit var txtBuy3m6m: RegularTextView
    private lateinit var txtBuyProVer: RegularTextView
    private lateinit var txtBuy25and75: RegularTextView
    private lateinit var txtBuyFreePro: RegularTextView
    private lateinit var txtFreepills: SemiBoldTextView
    private lateinit var txtQuantityPills: HardBoldTextView
    private lateinit var txtPrice25and75: HardBoldTextView
    private lateinit var txtDailyVal: SemiBoldTextView
    private lateinit var txtDuration: SemiBoldTextView
    private lateinit var txtPrice3M6m: HardBoldTextView
    private lateinit var txtFreeprover: RegularTextView
    private var fromSource: String = ""

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_drug_store)

        imgBackToolbarMultiColor.setOnClickListener(this)
        constraint2.setOnClickListener(this)
        constraint3.setOnClickListener(this)
        constraintproStarter.setOnClickListener(this)
        constraint25.setOnClickListener(this)
        constraint75.setOnClickListener(this)
        constrant3M.setOnClickListener(this)
        constrant6M.setOnClickListener(this)
        conFreepro.setOnClickListener(this)

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
        dialogProStartPack = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogProStartPack.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogProStartPack.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogProStartPack.setCancelable(false)
        dialogProStartPack.setContentView(R.layout.dialog_pro_start_pack)
        imgCloseStartPack = dialogProStartPack.findViewById(R.id.imgCloseStartPack)
        txtBuyStartPack = dialogProStartPack.findViewById(R.id.txtBuyStartPack)
        imgCloseStartPack.setOnClickListener {
            dialogProStartPack.dismiss()
        }
        txtBuyStartPack.setOnClickListener {
            dialogProStartPack.dismiss()
        }

        dialogProVer = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogProVer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogProVer.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogProVer.setCancelable(false)
        dialogProVer.setContentView(R.layout.dialog_proversion)
        imgCloseProVer = dialogProVer.findViewById(R.id.imgCloseProVer)
        txtBuyProVer = dialogProVer.findViewById(R.id.txtBuyProVer)
        imgCloseProVer.setOnClickListener {
            dialogProVer.dismiss()
        }
        txtBuyProVer.setOnClickListener {
            dialogProVer.dismiss()
        }

        dialogOneK = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogOneK.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogOneK.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogOneK.setCancelable(false)
        dialogOneK.setContentView(R.layout.dialog_onek_pills)
        imgClose1k = dialogOneK.findViewById(R.id.imgClose1k)
        txtBuy1K = dialogOneK.findViewById(R.id.txtBuy1K)
        imgClose1k.setOnClickListener {
            dialogOneK.dismiss()
        }
        txtBuy1K.setOnClickListener {
            dialogOneK.dismiss()
        }
        dialogFreeProVer = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialogFreeProVer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogFreeProVer.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogFreeProVer.setCancelable(false)
        dialogFreeProVer.setContentView(R.layout.dialog_free_pro_ver)
        imgCloseFreePro = dialogFreeProVer.findViewById(R.id.imgCloseFreePro)
        txtBuyFreePro = dialogFreeProVer.findViewById(R.id.txtBuyFreePro)
        txtFreeprover = dialogFreeProVer.findViewById(R.id.txtFreeprover)
        txtFreeprover.setPaintFlags(txtFreeprover.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        imgCloseFreePro.setOnClickListener {
            dialogFreeProVer.dismiss()
        }
        txtBuyFreePro.setOnClickListener {
            dialogFreeProVer.dismiss()
        }

        dialog3m6m = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialog3m6m.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog3m6m.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialog3m6m.setCancelable(false)
        dialog3m6m.setContentView(R.layout.dialog_3m6m)
        imgClose3m6m = dialog3m6m.findViewById(R.id.imgClose3m6m)
        txtBuy3m6m = dialog3m6m.findViewById(R.id.txtBuy3m6m)
        txtDailyVal = dialog3m6m.findViewById(R.id.txtDailyVal)
        txtDuration = dialog3m6m.findViewById(R.id.txtDuration)
        txtPrice3M6m = dialog3m6m.findViewById(R.id.txtPrice3M6m)
        imgClose3m6m.setOnClickListener {
            dialog3m6m.dismiss()
        }
        txtBuy3m6m.setOnClickListener {
            dialog3m6m.dismiss()
        }

        dialog25and75 = Dialog(this@DrugStoreActivity, android.R.style.Theme_Light)
        dialog25and75.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog25and75.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialog25and75.setCancelable(false)
        dialog25and75.setContentView(R.layout.dialog_25and75_pills)
        imgClose25and75 = dialog25and75.findViewById(R.id.imgClose25and75)
        txtBuy25and75 = dialog25and75.findViewById(R.id.txtBuy25and75)
        txtFreepills = dialog25and75.findViewById(R.id.txtFreepills)
        txtQuantityPills = dialog25and75.findViewById(R.id.txtQuantityPills)
        txtPrice25and75 = dialog25and75.findViewById(R.id.txtPrice25and75)
        imgClose25and75.setOnClickListener {
            dialog25and75.dismiss()
        }
        txtBuy25and75.setOnClickListener {
            dialog25and75.dismiss()
        }
    }

    override fun onClick(v: View?) {
        if (v == constraint2) {
            dialogProVer.show()
        }
        if (v == constraint3) {
            dialogOneK.show()
        }
        if (v == constraint25) {
            txtFreepills.text = "500 FREE PILL\nVALUE"
            txtQuantityPills.text = "2500"
            txtPrice25and75.text = "$1.99"
            dialog25and75.show()
        }
        if (v == constraint75) {
            txtFreepills.text = "2500 FREE PILL\nVALUE"
            txtQuantityPills.text = "7500"
            txtPrice25and75.text = "$4.99"
            dialog25and75.show()
        }
        if (v == constrant3M) {
            txtDailyVal.text = "$1.50 Daily Value X 90 Days\n= $135 Total Value"
            txtDuration.text = "3 Months Subscription"
            txtPrice3M6m.text = "$9.99"
            dialog3m6m.show()
        }
        if (v == constrant6M) {
            txtDailyVal.text = "$10 Daily Value X 180 Days\n= $1800 Total Value"
            txtDuration.text = "6 Months Subscription"
            txtPrice3M6m.text = "$24.99"
            dialog3m6m.show()
        }
        if (v == conFreepro) {
            dialogFreeProVer.show()
        }
        if (v == constraintproStarter) {
            dialogProStartPack.show()
        }
        if (v == imgBackToolbarMultiColor) {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}