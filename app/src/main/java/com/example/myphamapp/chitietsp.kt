package com.example.myphamapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myphamapp.databinding.ActivityChitietspBinding
import com.example.myphamapp.databinding.ActivityMainBinding

class chitietsp : AppCompatActivity() {
    private lateinit var binding: ActivityChitietspBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChitietspBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



}