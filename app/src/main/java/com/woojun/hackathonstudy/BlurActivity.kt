package com.woojun.hackathonstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.hackathonstudy.databinding.ActivityBlurBinding

class BlurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlurBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

        }
    }
}