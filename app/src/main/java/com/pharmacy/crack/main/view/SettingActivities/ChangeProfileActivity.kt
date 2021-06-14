package com.pharmacy.crack.main.view.SettingActivities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.main.model.ProfileDataModel
import com.pharmacy.crack.utils.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.activity_forget_pasword.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.relMonth
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ChangeProfileActivity : AppCompatActivity(),View.OnClickListener {

    var photoFile: File? = null
    var mCurrentPhotoPath: String? = null
    lateinit var pref: PrefHelper
     var permissionCamera: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_change_profile)

        initAll()

        imgBackToolbar.setOnClickListener(this)
        imgEditProfile.setOnClickListener(this)
        txtSaveProfile.setOnClickListener(this)
        imgProfile.setOnClickListener(this)
    }

    private fun initAll() {
        pref = PrefHelper(this)
        txtToolbar.text = "Change Profile"
        askForPermissioncamera(Manifest.permission.CAMERA, MediaRecorder.VideoSource.CAMERA)

        constarintProfile.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        }

    }

    override fun onClick(v: View?) {
        if(v==imgEditProfile){
            if(permissionCamera){
                showDialogs()
            }
            else{
                askForPermissioncamera(Manifest.permission.CAMERA, MediaRecorder.VideoSource.CAMERA)
            }
        }
        else if(v==imgProfile){
            if(permissionCamera){
                showDialogs()
            }
            else{
                askForPermissioncamera(Manifest.permission.CAMERA, MediaRecorder.VideoSource.CAMERA)
            }
        }
        else if(v==txtSaveProfile){
            if (edtCurrentUserName.text.toString().trim().isEmpty()) {
                showToasts("Please enter current Username.")
            }
            else if (editNewUserName.text.toString().trim().isEmpty()) {
                showToasts("Please enter new Username.")
            }
            else {
                if (!isNetworkAvailable(this)) {
                    showToast(this, "Please check your internet connection and try again.")
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        submitUserName()
                    }
                }

            }
        }
        else if(v==imgBackToolbar){
            super.onBackPressed()
        }
    }

    private suspend fun submitUserName() {
        pref.showProgress(this)
        val model = ProfileDataModel(edtCurrentUserName.text.toString(),editNewUserName.text.toString())
        val  res = RetrofitFactory.api.submitResetUserName(model)

        pref.hideProgress()
        if(res.isSuccessful){
            res.body()?.let {
                showToasts(it.message)
                edtCurrentUserName.text?.clear()
                editNewUserName.text?.clear()
            }
        }
    }

    private fun showDialogs() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAllowRotation(false)
            .setAllowFlipping(false)
            .setOutputCompressQuality(50)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .setAllowRotation(false)
            .setFixAspectRatio(true)
            .start(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode === Activity.RESULT_OK) {
                    val resultUri: Uri? = result.uri
                    if (resultUri?.path != null) {
                      val imagePath : String = resultUri.path!!
//                        Log.i("imagePathGallerySelect",imagePath)

                        Glide.with(applicationContext).load(imagePath)
                            .placeholder(R.drawable.profile_img)
                            .centerCrop()
                            .into(imgProfile)

                    }
                } else if (resultCode === CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                }
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }


    private fun askForPermissioncamera(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionCamera = false
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permission
                )
            ) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        else{
            permissionCamera = true
        }
    }


    private fun scaleBitmap(bitmap: Bitmap): Bitmap? {
        val ei = ExifInterface(photoFile?.absolutePath.toString())
        val orientation: Int = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        var rotatedBitmap: Bitmap? = null
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270)
            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
            else -> rotatedBitmap = bitmap
        }

        return rotatedBitmap
    }

    private fun rotateImage(source: Bitmap, angle: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}