package com.example.myphamapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphamapp.Adapter.sanphamAdapter
import com.example.myphamapp.Model.Sanpham
import com.example.myphamapp.databinding.ActivityRvSanphamBinding
import com.google.firebase.database.*

class rv_sanpham : AppCompatActivity() {
    private lateinit var binding: ActivityRvSanphamBinding
    private lateinit var sanpham:ArrayList<Sanpham>
    private lateinit var dpRef:DatabaseReference
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRvSanphamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycleview.layoutManager = LinearLayoutManager(this)
            binding.recycleview.setHasFixedSize(true)
        sanpham = arrayListOf<Sanpham>()
        getSanPham()

    }

    private fun getSanPham() {
        dpRef = FirebaseDatabase.getInstance().getReference("sanpham")
        dpRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sanpham.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val spData = empSnap.getValue(Sanpham::class.java)
                        sanpham.add(spData!!)
                    }
                    val madapter = sanphamAdapter(sanpham)
//                    binding.recycleview.adapter = sanphamAdapter(sanpham)
                    binding.recycleview.adapter = madapter

//        binding.rvDemo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.recycleview.layoutManager  = GridLayoutManager(this@rv_sanpham,2, GridLayoutManager.VERTICAL, false)
//                        GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
                    madapter.setOnItemClickListener(object :sanphamAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@rv_sanpham,detail::class.java )
                            intent.putExtra("TenSP", sanpham[position].TenSP)
                            intent.putExtra("GiaSP", sanpham[position].GiaSP)
                            intent.putExtra("SoluongSP", sanpham[position].SoluongSP)
                            intent.putExtra("AnhSP", sanpham[position].AnhSP)
                            intent.putExtra("Id_danhmuc", sanpham[position].Id_danhmuc)
                            intent.putExtra("NhacungcapSP", sanpham[position].NhacungcapSP)
                            intent.putExtra("MotaSP", sanpham[position].MotaSP)
                            intent.putExtra("sl_daban", sanpham[position].sl_daban)
                            intent.putExtra("date", sanpham[position].date)
                            startActivity(intent)

                        }
                    })
                }

    }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
