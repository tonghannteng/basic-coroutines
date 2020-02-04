package com.tengtonghann.android.basiccoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var count = 0

    companion object {
        const val TAG = "MainActivityLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "Hello: ${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "Hello: ${Thread.currentThread().name}")
        }

        btnCount.setOnClickListener {
            txtCount.text = count++.toString()
        }

        downloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserDataFun()
            }

        }
    }

    private suspend fun downloadUserDataFun() {
        for (i in 1..100000) {
            Log.d(TAG, "Download User: $i ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                txtUserMessage.text = i.toString()
            }
            delay(2000)

        }
    }
}
