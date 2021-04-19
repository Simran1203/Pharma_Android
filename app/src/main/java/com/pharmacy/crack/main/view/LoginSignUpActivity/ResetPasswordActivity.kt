package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.AsteriskPasswordTransformationMethod
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.constraintLogin
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class ResetPasswordActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_reset_password)


        initAll()
        constraintReset.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        txtConfirm.setOnClickListener(this)
    }

    private fun initAll() {
        edtPasswordReset.transformationMethod = AsteriskPasswordTransformationMethod()
        edtCnfmPwdReset.transformationMethod = AsteriskPasswordTransformationMethod()
    }

    override fun onClick(v: View?) {
        if(v==txtConfirm){
            if(edtPasswordReset.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                edtPasswordReset.text?.clear()
            } else if (edtPasswordReset.text.toString().trim().length < 6) {
                Toast.makeText(
                    this,
                    "Please enter at least 6 chars long password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (edtCnfmPwdReset.text.toString().trim().isEmpty()) {
                edtCnfmPwdReset.text?.clear()
                Toast.makeText(this, "Please enter Confirm Password.", Toast.LENGTH_SHORT).show()
            } else if (edtCnfmPwdReset.text.toString() != edtPasswordReset.text.toString()
            ) {
                Toast.makeText(this, "Confirm Password should be same as Password.", Toast.LENGTH_SHORT)
                    .show()
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
                finishAffinity()
            }
        }
    }
}