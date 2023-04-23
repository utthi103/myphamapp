package com.example.myphamapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myphamapp.Model.danhmucSP
import com.example.myphamapp.databinding.ActivityInsertdanhmucBinding
import com.google.firebase.database.*

class insertdanhmuc : AppCompatActivity() {
    private lateinit var binding:ActivityInsertdanhmucBinding
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertdanhmucBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseDatabase.getInstance().getReference("danhmuc")
        binding.adddanhmuc.setOnClickListener {
            var id_danhmuc = binding.idDanhmuc.text.toString()
            val ten = binding.tendanhmuc.text.toString()
            val mota = binding.mota.text.toString()
            val danhmuc = danhmucSP(id_danhmuc,ten,mota)
            db.child(id_danhmuc).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Node với khóa chính tương ứng đã tồn tại, hiển thị thông báo lỗi và không thêm dữ liệu mới
                        Toast.makeText(this@insertdanhmuc, "ID danh mục đã tồn tại!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Node với khóa chính chưa tồn tại, thêm dữ liệu mới
                        db.child(id_danhmuc).setValue(danhmuc)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xảy ra lỗi trong quá trình đọc dữ liệu từ Firebase Realtime Database, xử lý tùy ý
                }
            })
        }
    }
}