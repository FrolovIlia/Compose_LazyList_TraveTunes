package io.travel_tunes.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import io.travel_tunes.databinding.ActivitySplashBinding
import io.travel_tunes.ui.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startMain()
    }

    private fun startMain() {
        Handler(Looper.getMainLooper()).postDelayed({ startActivity(MainActivity.getInstance(this)) }, 3_000)
        Handler(Looper.getMainLooper()).postDelayed({ this.finish() }, 3_000)
    }
}