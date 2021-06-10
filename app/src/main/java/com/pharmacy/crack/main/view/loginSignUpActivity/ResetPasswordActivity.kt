package com.pharmacy.crack.main.view.loginSignUpActivity

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
import com.pharmacy.crack.main.model.LoginDatamodel
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONObject

class ResetPasswordActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityResetPasswordBinding
    lateinit var email:String
    lateinit var pref:PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@ResetPasswordActivity, R.layout.activity_reset_password)
        binding.lifecycleOwner = this

        initAll()

        binding.txtConfirm.setOnClickListener(this)
    }

    private fun initAll() {
        pref = PrefHelper(this)
        email = intent.getStringExtra("email").toString()

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
                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                }else{
                    pref.showProgress(this)
                    CoroutineScope(IO).launch {
                        submitResetPassword()
                    }
                }

            }
        }
    }

    private suspend fun submitResetPassword() {
        val model = LoginDatamodel(email,binding.edtPasswordReset.text.toString())
        val res = RetrofitFactory.api.submitNewPassword(model)

        if(res.isSuccessful){
            res.body()?.let {
                val msg = it.message
                    CoroutineScope(Dispatchers.Main).launch {
                        pref.hideProgress()
                        showToasts(msg)
                        startActivity(Intent(this@ResetPasswordActivity,LoginActivity::class.java))
                        finishAffinity()
                    }
            }
        }else{
            CoroutineScope(Dispatchers.Main).launch {
                pref.hideProgress()
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts(jObjError.getString("message"))
                } catch (e: Exception) {
                    showToasts(e.message.toString())
                } } }
    }
}