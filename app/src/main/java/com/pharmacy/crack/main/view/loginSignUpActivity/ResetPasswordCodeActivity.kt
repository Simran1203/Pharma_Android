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
import com.pharmacy.crack.databinding.ActivityResetPasswordCodeBinding
import com.pharmacy.crack.main.model.ResetCodeDataModel
import com.pharmacy.crack.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class ResetPasswordCodeActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityResetPasswordCodeBinding
    lateinit var pref: PrefHelper
    lateinit var email:String
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@ResetPasswordCodeActivity, R.layout.activity_reset_password_code)
        binding.lifecycleOwner = this;
        initAll()

        binding.txtSubmitCode.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
    }

    private fun initAll() {
        pref = PrefHelper(this)
        email = intent.getStringExtra("email").toString()
        binding.edtCode.transformationMethod = XPasswordTransformationMethod()
        binding.constraintReset.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                }else{
                    pref.showProgress(this)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            binding.txtSubmitCode.isClickable = false
                            binding.txtSubmitCode.isFocusable =false
                            submitCode()
                        }catch (e:java.lang.Exception){
                            pref.hideProgress()
                            showToasts(e.message.toString())
                            binding.txtSubmitCode.isClickable = true
                            binding.txtSubmitCode.isFocusable =true
                        }
                    }
                }
            }
        }
        else if(v==  binding.imgBack){
            super.onBackPressed()
        }
    }

    private suspend fun submitCode() {
        val model = ResetCodeDataModel(email,binding.edtCode.text.toString())
        val res = RetrofitFactory.api.submitResetCode(model)

        if(res.isSuccessful){
            res.body()?.let {

                    CoroutineScope(Dispatchers.Main).launch {
                        binding.txtSubmitCode.isClickable = true
                        binding.txtSubmitCode.isFocusable =true
                        pref.hideProgress()
                        startActivity(Intent(this@ResetPasswordCodeActivity,ResetPasswordActivity::class.java)
                            .putExtra("email",email))
                        finishAffinity()
                    }
            }
        }else{
            CoroutineScope(Dispatchers.Main).launch {
                binding.txtSubmitCode.isClickable = true
                binding.txtSubmitCode.isFocusable =true
                pref.hideProgress()
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts(jObjError.getString("message"))
                } catch (e: Exception) {
                    showToasts(e.message.toString())
                }
            }
        }
    }

}