package com.woojun.hackathonstudy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.woojun.hackathonstudy.databinding.ActivityHwpBinding
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor
import java.io.InputStream

class HWPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHwpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHwpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            selectFileButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"
                startActivityForResult(intent, 1000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val text = readTextFromUri(this@HWPActivity, uri)
                binding.apply {
                    textView.text = text
                    selectFileButton.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun readTextFromUri(context: Context, uri: Uri): String {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            return extractTextFromHWP(inputStream)
        }
        return "파일을 읽을 수 없습니다."
    }


    fun extractTextFromHWP(inputStream: InputStream): String {
        val hwpFile: HWPFile = HWPReader.fromInputStream(inputStream)
       return TextExtractor.extract(hwpFile, TextExtractMethod.InsertControlTextBetweenParagraphText)
    }

}