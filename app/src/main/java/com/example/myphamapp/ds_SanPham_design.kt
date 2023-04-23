package com.example.myphamapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myphamapp.databinding.ActivityDsSanPhamDesignBinding
import com.example.myphamapp.databinding.ActivityMainBinding
import com.google.firebase.database.*

class ds_SanPham_design : AppCompatActivity() {
    private lateinit var binding: ActivityDsSanPhamDesignBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDsSanPhamDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference("sanpham")

        binding.addGioHang.setOnClickListener {
            Toast.makeText(this, "danh sach san pham", Toast.LENGTH_SHORT).show()

        }

    }
}