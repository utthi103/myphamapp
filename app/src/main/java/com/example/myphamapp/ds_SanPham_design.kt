package com.example.myphamapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myphamapp.databinding.ActivityDsSanPhamDesignBinding
import com.example.myphamapp.databinding.ActivityMainBinding

class ds_SanPham_design : AppCompatActivity() {
    private lateinit var binding: ActivityDsSanPhamDesignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDsSanPhamDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}