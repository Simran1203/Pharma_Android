package com.pharmacy.crack.main.view.loginSignUpActivity

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityLoginBinding
import com.pharmacy.crack.main.model.LoginDatamodel
import com.pharmacy.crack.main.model.SocialLoginData
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.*
import com.pharmacy.crack.utils.viewUtils.RegularEditText
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    lateinit var pref:PrefHelper
    var callbackManager: CallbackManager? = null
    var loginButton: LoginButton? = null
    private val RC_GOOGLE_LOGIN = 101
    var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var dialogEmailSocial: Dialog
    lateinit var edtEmailSocial : RegularEditText
    lateinit var txtSubmitEmailSocial : RegularTextView
    var socialCredid = ""
    var socialName = ""
    var socialType = ""

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        binding.lifecycleOwner = this
        pref = PrefHelper(this)
        loginButton = findViewById(R.id.login_button)
        listner()

        binding.loginButton.setReadPermissions(Arrays.asList("email", "public_profile"))
        callbackManager = CallbackManager.Factory.create()


        val loggedOut = AccessToken.getCurrentAccessToken() == null
        if (!loggedOut) {
            LoginManager.getInstance().logOut()
        }

        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                    getUserProfile(AccessToken.getCurrentAccessToken())
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
                showToast(this@LoginActivity, "Please check your internet connection and try again.")
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("319985004088-hc8ec1iog2etbrmkvtrvctt2prr1n98c.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        dialogEmailSocial = Dialog(this, android.R.style.Theme_Light)
        dialogEmailSocial.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogEmailSocial.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogEmailSocial.setCancelable(false)
        dialogEmailSocial.setContentView(R.layout.dialog_email_social)
        edtEmailSocial = dialogEmailSocial.findViewById(R.id.edtEmailSocial)
        txtSubmitEmailSocial = dialogEmailSocial.findViewById(R.id.txtSubmitEmailSocial)

        txtSubmitEmailSocial.setOnClickListener {
            if (edtEmailSocial.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show()
                edtEmailSocial.text?.clear()
            } else if (!(Patterns.EMAIL_ADDRESS.matcher(edtEmailSocial.getText().toString().trim())
                    .matches())
            ) {
                Toast.makeText(this, "Please enter valid Email Address.", Toast.LENGTH_SHORT).show()
            }else{
                dialogEmailSocial.dismiss()

                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java)
                    .putExtra("name",socialName)
                    .putExtra("email",edtEmailSocial.text.toString())
                    .putExtra("credId",socialCredid)
                    .putExtra("SocialType",socialType))
            }
        }
    }

    // TODO google Implimentation
    fun sign_in_with_gmail() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN)    }

    // TODO google Implimentation override method

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_LOGIN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)
                handleSignInResult(task)
            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // TODO Relate to google login
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                val id = account.id
                var fname = "" + account.givenName
                var lname = "" + account.familyName
                var emal = "" + account.email

                pref.showProgress(this)
                CoroutineScope(IO).launch {
                    try {
                        hitSocialApi(fname+" "+lname,emal,id.toString(),"G")
                    }catch (e:java.lang.Exception){
                        withContext(Main){
                            pref.hideProgress()
                            showToast(this@LoginActivity, e.message.toString())
                        }
                    }
                }

                // if we do not get the picture of user then we will use default profile picture
                val pic_url: String
                pic_url = if (account.photoUrl != null) {
                    account.photoUrl.toString()
                } else {
                    "null"
                }
                if (fname.trim { it <= ' ' } == "" || fname.trim { it <= ' ' } == "null") fname =
                    resources.getString(R.string.app_name)
                if (lname.trim { it <= ' ' } == "" || lname.trim { it <= ' ' } == "null") lname =
                    "User"
                //  Change_Url_to_base64(id, fname, lname, pic_url, "gmail")
            }
        } catch (e: ApiException) {
//            Log.w("Error message", "signInResult:failed code=" + e.statusCode)
        }
    }

    private suspend fun hitSocialApi(name : String,emal: String, id: String, type: String) {
        val model = SocialLoginData(emal,id,type)
        val res = RetrofitFactory.api.submitSocialLogin(model)

        if(res.isSuccessful){
            res.body()?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    pref.hideProgress()
                    pref.storyCount+=1
                    pref.authToken = it.user_Details.auth_token

                    if(it.user_Details.user_data.profile_pic!=null){
                        pref.profilePic = it.user_Details.user_data.profile_pic
                    }
                    pref.userName = it.user_Details.user_data.username
                    pref.fullName = it.user_Details.user_data.fullname


                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finishAffinity()
                }

            }
        }else{
            CoroutineScope(Dispatchers.Main).launch {
                pref.hideProgress()
                try {
                    val jObjError = JSONObject(res.errorBody()?.string())
                    showToasts(jObjError.getString("message"))
                         if(jObjError.getString("message").contains("Required")){
                             socialName = name
                             socialCredid = id
                             socialType = type
                             dialogEmailSocial.show()
                         }
                         else{
                             startActivity(Intent(this@LoginActivity, SignUpActivity::class.java)
                                 .putExtra("name",name)
                                 .putExtra("email",emal)
                                 .putExtra("credId",id)
                                 .putExtra("SocialType",type))
                         }


                } catch (e: Exception) {
                    showToasts(e.message.toString())
                } } }
    }

    private fun getUserProfile(currentAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            currentAccessToken
        ) { `object`, _ ->

            val first_name = `object`.optString("first_name")
            val last_name = `object`.optString("last_name")
            var email = `object`.optString("email")
            val id = `object`.optString("id")

            pref.showProgress(this)
            CoroutineScope(IO).launch {
                try {
                    hitSocialApi(first_name+" "+last_name,email,id,"F")
                }catch (e:java.lang.Exception){
                    withContext(Main){
                        pref.hideProgress()
                        showToast(this@LoginActivity, "Please check your internet connection and try again.")
                    }
                }
            }

            //                        String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
            var image_url: String? = null
            try {
                image_url = JSONObject(`object`.getString("picture")).getJSONObject(
                    "data"
                ).getString("url")
//                Log.d("urlsassd", ""+id)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun listner() {

        binding.constraintLogin.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        binding.edtPasswordLogin.transformationMethod = AsteriskPasswordTransformationMethod()

        binding.txtLogin.setOnClickListener(this)
        binding.imgFacebook.setOnClickListener(this)
        binding.imgGoogle.setOnClickListener(this)
        binding.txtForgtPwd.setOnClickListener(this)
        binding.txtCreateAccount.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (v === binding.txtForgtPwd) {
            startActivity(Intent(this, ForgetPaswordActivity::class.java))
        }
        else if (v === binding.imgFacebook) {
            if (!isNetworkAvailable(this)) {
                showToast(this, "Please check your internet connection and try again.")
            } else {
                binding.loginButton.performClick()
            }

        }
        else if (v === binding.imgGoogle) {
            if (!isNetworkAvailable(this)) {
                showToast(this, "Please check your internet connection and try again.")
            } else {
                sign_in_with_gmail()
            }
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
                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                }else{
                    pref.showProgress(this)
                    CoroutineScope(IO).launch {
                        try {
                            submitLogin()
                        }catch (e:java.lang.Exception){
                            withContext(Main){
                                pref.hideProgress()
                                showToast(this@LoginActivity, e.message.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun submitLogin() {
        val model = LoginDatamodel(binding.editEmailLogin.text.toString(),binding.edtPasswordLogin.text.toString())
        val res = RetrofitFactory.api.submitLogin(model)

        if(res.isSuccessful){
              res.body()?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            pref.hideProgress()
                            pref.authToken = it.user_Details.auth_token
                            if(it.user_Details.user_data.profile_pic!=null){
                                pref.profilePic = it.user_Details.user_data.profile_pic
                            }

                            pref.userName = it.user_Details.user_data.username
                            pref.fullName = it.user_Details.user_data.fullname

                            if(pref.storyCount==1){
                                pref.storyCount+=1
                                startActivity(Intent(this@LoginActivity, StoryActivity::class.java))
                                finishAffinity()
                            }else{
                                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                                finishAffinity()
                            }

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

    override fun onRestart() {
        super.onRestart()
        val loggedOut = AccessToken.getCurrentAccessToken() == null
        if (!loggedOut) {
            LoginManager.getInstance().logOut()
        }
        mGoogleSignInClient?.signOut()
    }
    override fun onBackPressed() {
        hideKeyBoard(this)
        super.onBackPressed()
    }

}