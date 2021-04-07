package com.pharmacy.crack.main.ViewModel.LoginSignUpViewModels

import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pharmacy.crack.data.repositories.LoginRepo
import com.pharmacy.crack.main.view.LoginSignUpActivity.LoginActivity
import com.pharmacy.crack.main.view.LoginSignUpActivity.StoryActivity
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var loginRepo : LoginRepo) : ViewModel() {
    var email = ""
    var password = ""

    var bool = MutableLiveData<Boolean>()
    lateinit var bool1 :LiveData<Boolean>


    fun validate(loginActivity: LoginActivity): LiveData<Boolean> {
        if (email.isEmpty()) {
            showToast(loginActivity, "Please Enter the Email")
            bool.value = false
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            showToast(loginActivity, "Email Address is not valid")
            bool.value = false
        } else if (password.isEmpty()) {
            showToast(loginActivity, "Please Enter the Password")
            bool.value = false
        } else if (password.length < 6) {
            showToast(loginActivity, "Password should be at least 6 digits")
            bool.value = false
        } else {
            bool.value = true
        }
        bool1=bool
        return bool1
    }

    fun login(): Boolean {
        return true
    }
}