package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityLoginBinding

import com.pharmacy.crack.main.viewModel.loginSignUpViewModels.LoginViewModel
import com.pharmacy.crack.main.model.LoginModel
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {


     private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        binding.lifecycleOwner = this;

        listner()
        }

    private fun listner() {

        binding.constraintLogin.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        }

        binding.edtPasswordLogin.transformationMethod = AsteriskPasswordTransformationMethod()

        binding.txtLogin.setOnClickListener(this)
        binding.txtForgtPwd.setOnClickListener(this)
        binding.txtCreateAccount.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if(v===binding.txtForgtPwd){
            startActivity(Intent(this, ForgetPaswordActivity::class.java))
        }
        else if(v===binding.txtCreateAccount){
            if (!isNetworkAvailable(this)) {
                showToast(this, "Please check your internet connection and try again.")

            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }

        else if(v===binding.txtLogin){
            if(binding.editEmailLogin.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                binding.editEmailLogin.text?.clear()
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(binding.editEmailLogin.getText().toString().trim()).matches())){
                Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show()
            }

            else if(binding.edtPasswordLogin.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                binding.edtPasswordLogin.text?.clear()
            }
            else if(binding.edtPasswordLogin.text.toString().trim().length<6){
                Toast.makeText(
                    this,
                    "Please enter at least 6 chars long password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                hideKeyBoard(this)
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }
}