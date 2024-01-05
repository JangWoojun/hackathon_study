package com.woojun.hackathonstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.hackathonstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.apply {

        }
    }
}