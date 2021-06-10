package com.pharmacy.crack.main.view.loginSignUpActivity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityForgetPaswordBinding
import com.pharmacy.crack.main.model.ForgetDataModel
import com.pharmacy.crack.utils.*
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.activity_forget_pasword.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONObject

class ForgetPaswordActivity : AppCompatActivity() ,View.OnClickListener{
    private lateinit var dialogForget : Dialog
    lateinit var txtForgotSubmitDialog : RegularTextView
    lateinit var txtEmailDialog : RegularTextView
    lateinit var pref : PrefHelper
    private lateinit var binding: ActivityForgetPaswordBinding
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@ForgetPaswordActivity, R.layout.activity_forget_pasword)
        binding.lifecycleOwner = this;
        pref = PrefHelper(this)

        init()
        clickListner()


    }

    private fun init() {
        dialogForget = Dialog(this)
        dialogForget.setContentView(R.layout.dialog_forget)
        dialogForget.setCancelable(false)
        dialogForget.setCanceledOnTouchOutside(false)
        txtForgotSubmitDialog = dialogForget.findViewById(R.id.txtForgotSubmitDialog)
        txtEmailDialog = dialogForget.findViewById(R.id.txtEmailDialog)
    }

    private fun clickListner() {
        binding.constraintForgot.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        binding.txtSubmit.setOnClickListener(this)
        txtForgotSubmitDialog.setOnClickListener(this)
        binding.btnSignUpForgot.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==binding.txtSubmit){

            if(binding.edtEmailForget.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                binding.edtEmailForget.text?.clear()
            }
            else if(!(Patterns.EMAIL_ADDRESS.matcher(binding.edtEmailForget.text.toString().trim()).matches())){
                Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show()
            }
//           else if(edtEmailForget.getText().toString().startsWith(" ")){
//                Toast.makeText(this, "Please enter Email valid Address.", Toast.LENGTH_SHORT).show()
//            }
            else{
                val emailEnd = binding.edtEmailForget.text.toString().split("@").toTypedArray()
                val emailStart = binding.edtEmailForget.getText().toString().trim()[0]
                txtEmailDialog.text = emailStart+"...@"+emailEnd[1]

                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                }else{
                    pref.showProgress(this)
                    CoroutineScope(IO).launch {
                        submitForgetPass()
                    }
                }
            }
        }
        else if(v==binding.btnSignUpForgot){
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        else if(v==txtForgotSubmitDialog){
            dialogForget.dismiss()
            startActivity(Intent(this,ResetPasswordCodeActivity::class.java)
                .putExtra("email",binding.edtEmailForget.text.toString()))
        }
        else if(v==binding.imgBack){
            onBackPressed()
        }
    }

    private suspend fun submitForgetPass() {
        val model = ForgetDataModel(binding.edtEmailForget.text.toString())
        val res = RetrofitFactory.api.submitForget(model)
        if(res.isSuccessful){
             res.body()?.let {
                 val msg = it.message
                     CoroutineScope(Dispatchers.Main).launch {
                         showToasts(msg)
                         pref.hideProgress()
                         dialogForget.show()
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
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialogForget
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}