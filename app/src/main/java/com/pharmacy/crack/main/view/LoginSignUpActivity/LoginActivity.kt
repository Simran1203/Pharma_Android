package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_login)

        listner()

        constraintLogin.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        })

    }


    private fun listner() {
        edtPasswordLogin.setTransformationMethod(AsteriskPasswordTransformationMethod())

        txtForgtPwd.setOnClickListener(this)
        txtCreateAccount.setOnClickListener(this)
        txtLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if(v===txtForgtPwd){
            startActivity(Intent(this, ForgetPaswordActivity::class.java))
        }
        else if(v===txtCreateAccount){
            if (!isNetworkAvailable(this)) {
                showToast(this, "Please check your internet connection and try again.")

            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }

        }
        else if(v===txtLogin){
            if(editEmailLogin.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                editEmailLogin.text?.clear()
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(editEmailLogin.getText().toString().trim()).matches())){
                Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show()
            }
            else if(editEmailLogin.getText().toString().startsWith(" ")){
                Toast.makeText(this, "Please enter Email valid Address.", Toast.LENGTH_SHORT).show()
            }
           else if(edtPasswordLogin.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                edtPasswordLogin.text?.clear()
            }
            else if(edtPasswordLogin.getText().toString().trim().length<6){
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