package com.example.bingo

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.bingo.databinding.ActivityMainActivityBinding
import com.example.bingo.viewmodels.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        mViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        mViewModel.generateNumbers()

        // display called numbers
        val calledNumbers = mViewModel.getCalledNumbers()
        if (calledNumbers.isNotEmpty()) {
            for (number in calledNumbers) {
                displayCalledNumber(number)
            }
            binding.tv1.text = " ${calledNumbers.last()}"
            setBingoBall(calledNumbers.last())
        }


        binding.generateBtn.setOnClickListener {
            val generatedNumber = mViewModel.getNumber()
            // generate sound
            val ball = MediaPlayer.create(
                this,
                resources.getIdentifier("sound${generatedNumber}", "raw", packageName)
            )
            ball.start()

            // generate UI
            setBingoBall(generatedNumber)
            if (mViewModel.getCalledNumbers().isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({ displayCalledNumber(generatedNumber) },
                    1000)
            }
        }
    }

    fun restart(view: View?) {
        val restartIntent = baseContext.packageManager
            .getLaunchIntentForPackage(baseContext.packageName)
        restartIntent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(restartIntent)
    }

    fun setBingoBall(number: Int) {
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.anim)
        val interpolator = MyBounceInterpolator(0.2, 15.0)
        myAnim.interpolator = interpolator

        // set imageView bg and animation
        if (number in 1..15) {
            binding.iv.setImageResource(R.drawable.b)
        } else if (number in 16..30) {
            binding.iv.setImageResource(R.drawable.i)
        } else if (number in 31..45) {
            binding.iv.setImageResource(R.drawable.n)
        } else if (number in 46..60) {
            binding.iv.setImageResource(R.drawable.g)
        } else if (number in 61..75) {
            binding.iv.setImageResource(R.drawable.o)
        }
        binding.iv.startAnimation(myAnim)

        // set textView bg and animation
        binding.tv1.text = " $number"
        binding.tv1.startAnimation(myAnim)
    }

    fun displayCalledNumber(number: Int) {
        when (number) {
            in 1..15 -> generateBallUi(number, binding.b)
            in 16..30 -> generateBallUi(number, binding.i)
            in 31..45 -> generateBallUi(number, binding.n)
            in 46..60 -> generateBallUi(number, binding.g)
            in 61..75 -> generateBallUi(number, binding.o)
        }
    }

    fun generateBallUi(number: Int, layout: LinearLayout) {
        // Create a TextView programmatically.
        val tv = TextView(this)
        tv.id = System.currentTimeMillis().toInt()
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,  // Width of TextView
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ) // Height of TextView

        // Apply the layout parameters to TextView widget
        tv.layoutParams = lp

        // Set text to display in TextView
        tv.text = " $number"
        tv.gravity = Gravity.CENTER
        tv.textSize = 30f
        tv.setTypeface(null, Typeface.BOLD_ITALIC)

        // Set a text color for TextView text
        tv.setTextColor(Color.WHITE)

        // Add newly created TextView to parent view group (RelativeLayout)
        layout.addView(tv)
    }
}
