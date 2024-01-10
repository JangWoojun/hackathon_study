package com.woojun.hackathonstudy

import android.graphics.BlurMaskFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.woojun.hackathonstudy.databinding.ActivityBlurBinding

class BlurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlurBinding
    private var isBlur = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            blurButton.setOnClickListener {
                setBlurText(isBlur, it)
                isBlur = !isBlur
            }
        }
    }
    private fun setBlurText(isBlur: Boolean, view: View) {
        view as Button
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null).apply {
            if (isBlur) view.paint.maskFilter = BlurMaskFilter(16f, BlurMaskFilter.Blur.NORMAL)
            else view.paint.maskFilter = null
        }
    }

}