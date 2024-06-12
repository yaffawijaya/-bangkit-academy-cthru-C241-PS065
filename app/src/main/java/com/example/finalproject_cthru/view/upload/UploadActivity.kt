package com.example.finalproject_cthru.view.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.data.remote.response.Response
import com.example.finalproject_cthru.data.remote.retrofit.ApiConfig
import com.example.finalproject_cthru.databinding.ActivityUploadBinding
import com.example.finalproject_cthru.utils.reduceFileImage
import com.example.finalproject_cthru.utils.uriToFile
import com.example.finalproject_cthru.view.camera.CameraActivity
import com.example.finalproject_cthru.view.home.HomeFragment
import com.example.finalproject_cthru.view.profile.ProfileFragment
import com.example.finalproject_cthru.view.result.ResultActivity
import com.google.android.gms.common.GooglePlayServicesManifestException
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var currentImageUri: Uri? = null
    private val uploadViewModel: UploadViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                currentImageUri = uri
                showImage()
            } else {
                Toast.makeText(this, getString(R.string.no_image_selected), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.d("tes", it.resultCode.toString())
        if (it.resultCode == 200) {
            Log.d("tes","launch camera x")
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
        else{
            Log.d("tes","gagal")
        }
    }

    private var cropImage = registerForActivityResult(
        CropImageContract()
    ) { result: CropImageView.CropResult ->
        if (result.isSuccessful) {
            val crop =
                BitmapFactory.decodeFile(result.getUriFilePath(applicationContext, true))
            binding.uploadImage.setImageBitmap(crop)
            currentImageUri = result.uriContent
            // Now upload the cropped image
            uploadImage()
        } else {
            showToast("getString(R.string.crop_failed")
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        binding = ActivityUploadBinding.inflate(layoutInflater)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { cropAndUploadImage() }
        binding.cameraButton.setOnClickListener { startCameraX() }

        if (currentImageUri != null) {
            Glide.with(this)
                .load(currentImageUri)
                .into(binding.uploadImage)
        }

        setupView()

        binding.backButton.setOnClickListener{
            val resultIntent = Intent()
            // Add any result data if needed
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun cropAndUploadImage() {
        currentImageUri?.let {
            cropImage(it)
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image Classification File", "showImage: ${imageFile.path}")
            showLoading(true)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService2()
                    val successResponse = apiService.uploadImage(multipartBody)
                    with(successResponse.data){
                        showToast(successResponse.message.toString())

                        val bundle = Bundle()
                        bundle.putDouble(
                            ResultActivity.EXTRA_CONFIDENCE_CATARACT,
                            (this?.confidence ?: 0.0) as Double // Provide a default value of 0.0 if cataractConfidence is null
                        )

                        val intent = Intent(this@UploadActivity, ResultActivity::class.java)
                        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
                        intent.putExtra(ResultActivity.EXTRA_PREDICT_CATARACT , this?.detection)
                        intent.putExtras(bundle)
                        startActivityForResult(intent, HomeFragment.EDIT_PROFILE_REQUEST_CODE)
                    }
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, Response::class.java)
                    showToast(errorResponse.message.toString())
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.uploadImage.setImageURI(it)
        }
        binding.imageinfo.visibility = View.GONE
        binding.imageView3.visibility = View.GONE
    }

    private fun cropImage(uri: Uri) {
        cropImage.launch(
            CropImageContractOptions(
                uri = uri,
                cropImageOptions = CropImageOptions(
                    guidelines = CropImageView.Guidelines.ON
                )
            )
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.uploadProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HomeFragment.EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Handle the result from ProfileEditActivity
            // Update UI or refresh data if needed
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
