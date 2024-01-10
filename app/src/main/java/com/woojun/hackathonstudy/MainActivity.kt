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
            ocrButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, OCRActivity::class.java))
            }
        }
    }
}