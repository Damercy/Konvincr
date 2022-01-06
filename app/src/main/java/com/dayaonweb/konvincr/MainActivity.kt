package com.dayaonweb.konvincr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dayaonweb.konvincr.R
import com.google.android.material.button.MaterialButton
import lib.dayaonweb.konvincr.dialog.KonvincrDialog

class MainActivity : AppCompatActivity() {
    private lateinit var btn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn_open)
        attachListener()
    }

    private fun attachListener() {
        btn.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val dialog = KonvincrDialog.newInstance(url = VALID_URL)
        dialog.show(supportFragmentManager, null)
    }


    companion object {
        private const val INVALID_URL = ""
        private const val VALID_URL = "https://cdn.videvo.net/videvo_files/video/free/2014-12/large_watermarked/Metal_Wind_Chimes_at_Sunset_preview.mp4"
    }
}