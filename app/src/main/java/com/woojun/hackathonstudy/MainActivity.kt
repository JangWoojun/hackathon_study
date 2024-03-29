package com.woojun.hackathonstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.hackathonstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            mapButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, MapActivity::class.java))
            }
            cameraButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, CameraActivity::class.java))
            }
            ocrButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, OCRActivity::class.java))
            }
            blurButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, BlurActivity::class.java))
            }
            hwpButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, HWPActivity::class.java))
            }
        }
    }
}