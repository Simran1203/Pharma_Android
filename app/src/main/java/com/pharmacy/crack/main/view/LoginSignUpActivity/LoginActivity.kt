package com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.ViewModel.LoginSignUpViewModels.LoginViewModel
import com.pharmacy.crack.utils.AsteriskPasswordTransformationMethod
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_login)

        listner()

        constraintLogin.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
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
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        else if(v===txtLogin){
            if(editEmailLogin.getText().toString().isEmpty()){
                Toast.makeText(this, "Email Address can't be empty", Toast.LENGTH_SHORT).show()
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(editEmailLogin.getText().toString()).matches())){
                Toast.makeText(this, "Email Address is not valid", Toast.LENGTH_SHORT).show()
            }
           else if(edtPasswordLogin.getText().toString().isEmpty()){
                Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
            }
            else if(edtPasswordLogin.getText().toString().length<6){
                Toast.makeText(this, "Password Should be at least 6 digit", Toast.LENGTH_SHORT).show()
            }
            else
            {
                hideKeyBoard(this)
                startActivity(Intent(this, StoryActivity::class.java))
            }

        }
    }

    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }
}