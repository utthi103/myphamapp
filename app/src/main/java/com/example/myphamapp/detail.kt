package com.example.myphamapp

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.myphamapp.databinding.ActivityChitietspBinding
import com.example.myphamapp.databinding.ActivityDetailBinding

class detail : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSanPham()
    }

    private fun setSanPham() {
        binding.Tensp.text = intent.getStringExtra("TenSP")
        binding.giasp.text = intent.getStringExtra("GiaSP")
        binding.mota.text = intent.getStringExtra("MotaSP")
        val bytes= Base64.decode(intent.getStringExtra("AnhSP") , Base64.DEFAULT)
        val bitMap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        binding.imageView2.setImageBitmap(bitMap)
    }
}