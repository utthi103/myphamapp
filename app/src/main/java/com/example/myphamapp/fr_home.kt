package com.example.myphamapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphamapp.Adapter.sanphamAdapter
import com.example.myphamapp.Model.Sanpham
import com.example.myphamapp.databinding.FragmentFrHomeBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fr_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class fr_home : Fragment() {
    private var binding: FragmentFrHomeBinding? = null
    private lateinit var sanpham:ArrayList<Sanpham>
    private lateinit var dpRef:DatabaseReference
    private lateinit var dpRef1:DatabaseReference

    @SuppressLint("SuspiciousIndentation")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFrHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recycleview?.layoutManager = LinearLayoutManager(context)
        binding?.recycleview?.setHasFixedSize(true)
        sanpham = arrayListOf()
        getSanPham()
    }

    private fun getSanPham() {
        dpRef = FirebaseDatabase.getInstance().getReference("sanpham")
        dpRef1 = FirebaseDatabase.getInstance().getReference("danhmuc")

        dpRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sanpham.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val spData = empSnap.getValue(Sanpham::class.java)
                        val id_danhmuc = spData?.id_danhmuc
                        if (id_danhmuc != null) {
                            dpRef1.child(id_danhmuc)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // Retrieve the category name and set it on the product
                                            val categoryName =
                                                dataSnapshot.child("ten_danhmuc").value.toString()
                                            spData?.id_danhmuc = categoryName
                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // Handle errors here
                                    }
                                })
                        }
                        sanpham.add(spData!!)
                    }
                    val madapter = sanphamAdapter(sanpham,requireContext())

                    binding?.recycleview?.adapter = madapter

                    binding?.recycleview?.layoutManager =
                        GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

                    madapter.setOnItemClickListener(object : sanphamAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), detail::class.java)
                            intent.putExtra("idSP", sanpham[position].id_SanPham)
                            intent.putExtra("TenSP", sanpham[position].TenSP)
                            intent.putExtra("GiaSP", sanpham[position].GiaSP)
                            intent.putExtra("SoluongSP", sanpham[position].SoluongSP)
                            intent.putExtra("AnhSP", sanpham[position].AnhSP)
                            intent.putExtra("Id_danhmuc", sanpham[position].id_danhmuc)
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
                // Handle errors here
            }
        })
    }
}