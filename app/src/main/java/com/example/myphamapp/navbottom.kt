package com.example.myphamapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.myphamapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class navbottom : AppCompatActivity() {
    private lateinit var view_pager:ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbottom)
        view_pager = findViewById(R.id.view_pager)
        setupViewPager()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.nav_bottom)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Handle Home click
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.cart -> {
                    // Handle Search click
                    Toast.makeText(this, "Giỏ hàng", Toast.LENGTH_SHORT).show()

                    true
                }
                R.id.account -> {
                    // Handle Profile click
                    Toast.makeText(this, "Giỏ hàng", Toast.LENGTH_SHORT).show()

                    true
                }
                else -> false
            }
        }
    }

    private fun setupViewPager() {
        TODO("Not yet implemented")
    }
}