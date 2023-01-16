package com.example.gamesuitbinar

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gamesuitbinar.databinding.ActivitySplashBinding
import com.example.gamesuitbinar.introduction.IntroductionActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpView()
        checkPermission()
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, IntroductionActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun setUpView() {
            Glide.with(this)
                .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
                .placeholder(R.drawable.refresh)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivSplashscreen)
    }

    private fun checkPermission() {
       if (Build.VERSION.SDK_INT >= 29){
           val internetPermissionCheck =
               checkSelfPermission(android.Manifest.permission.INTERNET)

           if (internetPermissionCheck == PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
           }

           if (internetPermissionCheck != PackageManager.PERMISSION_GRANTED
           ) {
               Toast.makeText(this, "Refused", Toast.LENGTH_LONG).show()
               requestPermission()
           }
       }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= 29){
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.INTERNET
                ), REQUEST_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show()
                }

                if (grantResults.contains(PackageManager.PERMISSION_DENIED)) {
                    requestPermission()
                }
            }
            else -> {
                Toast.makeText(this, "Request tidak sesuai", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val REQUEST_PERMISSION_CODE = 201
    }
}