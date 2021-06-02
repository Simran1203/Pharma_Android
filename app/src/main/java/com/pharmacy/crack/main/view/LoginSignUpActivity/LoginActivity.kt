package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityLoginBinding
import com.pharmacy.crack.main.model.LoginDatamodel
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
   lateinit var pref:PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        binding.lifecycleOwner = this
        pref = PrefHelper(this)

        listner()
    }

    private fun listner() {

        binding.constraintLogin.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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

        if (v === binding.txtForgtPwd) {
            startActivity(Intent(this, ForgetPaswordActivity::class.java))
        } else if (v === binding.txtCreateAccount) {
            if (!isNetworkAvailable(this)) {
                showToast(this, "Please check your internet connection and try again.")

            } else {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        } else if (v === binding.txtLogin) {
            if (binding.editEmailLogin.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                binding.editEmailLogin.text?.clear()
            } else if (!(Patterns.EMAIL_ADDRESS.matcher(
                    binding.editEmailLogin.text.toString().trim()
                ).matches())
            ) {
                Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show()
            } else if (binding.edtPasswordLogin.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                binding.edtPasswordLogin.text?.clear()
            } else if (binding.edtPasswordLogin.text.toString().trim().length < 6) {
                Toast.makeText(
                    this,
                    "Please enter at least 6 chars long password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                hideKeyBoard(this)
                pref.showProgress(this)
                CoroutineScope(IO).launch {
                    submitLogin()
                }
            }
        }
    }

    private suspend fun submitLogin() {
        val model = LoginDatamodel(binding.editEmailLogin.text.toString(),binding.edtPasswordLogin.text.toString())
        val res = RetrofitFactory.api.submitLogin(model)

        if(res.isSuccessful){
              res.body()?.let {
                val msg = it.message
                    if(msg.equals("Logged in Successfully")){

                        CoroutineScope(Dispatchers.Main).launch {
                            pref.hideProgress()
                            showToasts(msg)
                            pref.authToken = it.data.auth_token
                            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                            finishAffinity()
                        }
                    }else{
                        CoroutineScope(Dispatchers.Main).launch {
                            pref.hideProgress()
                            showToasts(msg)
                        }
                    }
                }
        }else{
                CoroutineScope(Dispatchers.Main).launch {
                    pref.hideProgress()
                    try {
                        val jObjError = JSONObject(res.errorBody().toString())
                        showToasts(jObjError.getString("msg"))
                    } catch (e: Exception) {
                        showToasts(e.message.toString())
                    }
                }
        }
    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }
}