package com.example.to_do.home.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.to_do.MainActivity
import com.example.to_do.R
import java.util.*

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       Handler(Looper.getMainLooper()).postDelayed(Runnable {
      val intent =Intent (this,MainActivity::class.java)
           startActivity(intent)
           finish()
       },2000)
    }
}