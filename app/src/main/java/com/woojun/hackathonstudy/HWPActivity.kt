package com.woojun.hackathonstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.hackathonstudy.databinding.ActivityHwpactivityBinding

class HWPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHwpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHwpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

        }
    }
}