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

        CoroutineScope(Dispatchers.IO).launch {
            val task1 = async { getTask1() }
            val task2 = async { getTask2() }
            val task3 = async { getTask3() }
            val total = task1.await() + task2.await() + task3.await()
            Log.d(TAG, "TOTAL: $total")
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

    private suspend fun getTask1(): Int {
        delay(6000)
        Log.d(TAG, "Task 1: ${Thread.currentThread().name}")
        return 6000
    }

    private suspend fun getTask2(): Int {
        delay(4000)
        Log.d(TAG, "Task 2: ${Thread.currentThread().name}")
        return 4000
    }

    private suspend fun getTask3(): Int {
        delay(2000)
        Log.d(TAG, "Task 3: ${Thread.currentThread().name}")
        return 2000
    }
}
