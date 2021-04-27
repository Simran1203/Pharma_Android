package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import com.pharmacy.crack.utils.viewUtils.SemiBoldTextView
import kotlinx.android.synthetic.main.activity_forget_pasword.*
import kotlinx.android.synthetic.main.activity_login.*

class ForgetPaswordActivity : AppCompatActivity() ,View.OnClickListener{
    lateinit var dialogForget : Dialog
    lateinit var txtForgotSubmitDialog : RegularTextView
    lateinit var txtEmailDialog : RegularTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_forget_pasword)

        init()
        clickListner()

        constraintForgot.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        })
    }

    private fun init() {
        dialogForget = Dialog(this)
        dialogForget.setContentView(R.layout.dialog_forget)
        dialogForget.setCancelable(false)
        dialogForget.setCanceledOnTouchOutside(false)
        txtForgotSubmitDialog = dialogForget.findViewById(R.id.txtForgotSubmitDialog)
        txtEmailDialog = dialogForget.findViewById(R.id.txtEmailDialog)
    }

    private fun clickListner() {
        txtSubmit.setOnClickListener(this)
        txtForgotSubmitDialog.setOnClickListener(this)
        btnSignUpForgot.setOnClickListener(this)
        imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtSubmit){

            if(edtEmailForget.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                edtEmailForget.text?.clear()
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(edtEmailForget.getText().toString().trim()).matches())){
                Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show()
            }
//           else if(edtEmailForget.getText().toString().startsWith(" ")){
//                Toast.makeText(this, "Please enter Email valid Address.", Toast.LENGTH_SHORT).show()
//            }
            else{
                val emailEnd = edtEmailForget.getText().toString().split("@").toTypedArray()
                val emailStart = edtEmailForget.getText().toString().trim()[0]
                txtEmailDialog.text = emailStart+"...@"+emailEnd[1]
                dialogForget.show()
            }

        }
        else if(v==btnSignUpForgot){
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        else if(v==txtForgotSubmitDialog){
            dialogForget.dismiss()
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }
        else if(v==imgBack){
            onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialogForget
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}