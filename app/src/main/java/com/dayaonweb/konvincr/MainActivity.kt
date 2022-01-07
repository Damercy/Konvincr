package com.dayaonweb.konvincr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dayaonweb.konvincr.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
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
        private const val INVALID_URL =
            "https://assets.mixkit.co/videos/preview/mixkit-hand-holding-mobile-phone-with-greenscreen-8839-large"
        private const val VALID_URL =
            "https://assets.mixkit.co/videos/preview/mixkit-hand-holding-mobile-phone-with-greenscreen-8839-large.mp4"
    }
}