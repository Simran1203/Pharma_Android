package com.pharmacy.crack.main.ViewModel.LoginSignUpViewModels

import androidx.lifecycle.ViewModel
import com.pharmacy.crack.data.repositories.SignupRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModels @Inject constructor(var signupRepo : SignupRepo) : ViewModel() {
}