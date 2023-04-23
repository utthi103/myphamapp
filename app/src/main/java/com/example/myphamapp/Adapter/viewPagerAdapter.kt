package com.example.myphamapp.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myphamapp.Model.Sanpham
import com.example.myphamapp.R
import com.example.myphamapp.fr_home
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
class viewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = listOf(fr_home(), fr_home(), fr_home())

    override fun getCount() = fragmentList.size

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}
