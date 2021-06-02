package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityLoginBinding
import com.pharmacy.crack.databinding.ActivityResetPasswordBinding
import com.pharmacy.crack.utils.AsteriskPasswordTransformationMethod
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.constraintLogin
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class ResetPasswordActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityResetPasswordBinding

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@ResetPasswordActivity, R.layout.activity_reset_password)
        binding.lifecycleOwner = this;

        initAll()

        binding.txtConfirm.setOnClickListener(this)
    }

    private fun initAll() {

        binding.edtPasswordReset.transformationMethod = AsteriskPasswordTransformationMethod()
        binding.edtCnfmPwdReset.transformationMethod = AsteriskPasswordTransformationMethod()

        binding.constraintReset.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }
    }

    override fun onClick(v: View?) {
        if(v==binding.txtConfirm){
            if(binding.edtPasswordReset.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                binding.edtPasswordReset.text?.clear()
            } else if (binding.edtPasswordReset.text.toString().trim().length < 6) {
                Toast.makeText(
                    this,
                    "Please enter at least 6 chars long password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.edtCnfmPwdReset.text.toString().trim().isEmpty()) {
                binding.edtCnfmPwdReset.text?.clear()
                Toast.makeText(this, "Please enter Confirm Password.", Toast.LENGTH_SHORT).show()
            } else if (binding.edtCnfmPwdReset.text.toString() != binding.edtPasswordReset.text.toString()
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