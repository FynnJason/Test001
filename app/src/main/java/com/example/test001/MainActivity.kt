package com.example.test001

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test001.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnTestSort.setOnClickListener {
            startActivity(Intent(this, SortActivity::class.java))
        }
        binding.btnTestExcel.setOnClickListener {
            startActivity(Intent(this, ExcelActivity::class.java))
        }
    }
}