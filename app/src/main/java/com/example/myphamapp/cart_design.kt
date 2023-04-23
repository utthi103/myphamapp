package com.example.myphamapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myphamapp.databinding.ActivityCartDesignBinding
import com.example.myphamapp.databinding.ActivityMainBinding

class cart_design : AppCompatActivity() {
    private lateinit var binding: ActivityCartDesignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var count = 1

        binding.imgCong.setOnClickListener {
            count += 1
            binding.txtSl.text = count.toString()
        }
        binding.imgTru.setOnClickListener {
            count = count - 1
            binding.txtSl.text = count.toString()
        }
    }
}