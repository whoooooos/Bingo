package com.example.bingo

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 7000
    var startsound: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startsound = MediaPlayer.create(this, R.raw.bgsound)
        startsound!!.start()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        Handler().postDelayed(
            {
                val homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
                finish()
                startsound!!.stop()
            }, SPLASH_TIME_OUT.toLong()
        )
    }
}
