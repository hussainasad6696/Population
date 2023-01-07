package com.test.apitest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.apitest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}