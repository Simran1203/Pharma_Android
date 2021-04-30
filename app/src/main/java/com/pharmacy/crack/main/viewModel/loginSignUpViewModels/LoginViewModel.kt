package com.pharmacy.crack.main.viewModel.loginSignUpViewModels

import android.view.View
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.pharmacy.crack.main.model.LoginModel


class LoginViewModel : ViewModel() {
    var EmailAddress = MutableLiveData<String>()
    var Password = MutableLiveData<String>()

    private var userMutableLiveData: MutableLiveData<LoginModel>? = null
    val user: MutableLiveData<LoginModel>?
        get() {
            if (userMutableLiveData == null) {
                userMutableLiveData = MutableLiveData<LoginModel>()
            }
            return userMutableLiveData
        }

    fun onLoginClick(view: View?) {

        val loginUser = LoginModel(EmailAddress.value.toString().trim(), Password.value.toString().trim())
        userMutableLiveData!!.setValue(loginUser)
    }
}