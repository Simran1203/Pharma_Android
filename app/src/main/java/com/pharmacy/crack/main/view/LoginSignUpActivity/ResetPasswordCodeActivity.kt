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
import com.pharmacy.crack.databinding.ActivityResetPasswordBinding
import com.pharmacy.crack.databinding.ActivityResetPasswordCodeBinding
import com.pharmacy.crack.utils.AsteriskPasswordTransformationMethod
import com.pharmacy.crack.utils.XPasswordTransformationMethod
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen

class ResetPasswordCodeActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityResetPasswordCodeBinding
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@ResetPasswordCodeActivity, R.layout.activity_reset_password_code)
        binding.lifecycleOwner = this;
        initAll()

        binding.txtSubmitCode.setOnClickListener(this)
    }

    private fun initAll() {

        binding.edtCode.transformationMethod = XPasswordTransformationMethod()
        binding.constraintReset.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }
    }

    override fun onClick(v: View?) {
        if(v==binding.txtSubmitCode){
            if(binding.edtCode.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Code.", Toast.LENGTH_SHORT).show()
                binding.edtCode.text?.clear()
            } else if (binding.edtCode.text.toString().trim().length !=6) {
                Toast.makeText(
                    this,
                    "Please enter 6 chars long code.",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                startActivity(Intent(this,ResetPasswordActivity::class.java))
                finishAffinity()
            }
        }
    }
}