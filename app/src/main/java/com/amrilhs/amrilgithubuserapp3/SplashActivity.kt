package com.amrilhs.amrilgithubuserapp3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    val mIntent = Intent(baseContext, BottomNavigationActivity::class.java)
                    startActivity(mIntent)
                    this@SplashActivity.finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}