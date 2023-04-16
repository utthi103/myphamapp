package com.example.myphamapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.telecom.Call
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.myphamapp.Model.Sanpham
import com.example.myphamapp.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Response
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var simage: String? = ""
    var avatar:String? = ""
    private lateinit var dbRef: DatabaseReference
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference("sanpham")
        binding.pickimage.setOnClickListener {
            saveImage()
        }
        binding.btndetail.setOnClickListener {
            val intent  = Intent(this,detail::class.java)
            startActivity(intent)
        }
        binding.btnsave.setOnClickListener {
            insert()
        }

        binding.btninsert.setOnClickListener {
            val intent  = Intent(this,insertsp::class.java)
            startActivity(intent)
        }

        binding.btnds.setOnClickListener {
            Toast.makeText(this, "danh sach san pham", Toast.LENGTH_SHORT).show()

            val intent  = Intent(this,rv_sanpham::class.java)
            startActivity(intent)
        }
        binding.btnnav.setOnClickListener {
            Toast.makeText(this, "danh sach san pham", Toast.LENGTH_SHORT).show()

            val intent  = Intent(this,navbottom::class.java)
            startActivity(intent)
        }

    }

    private fun saveImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val inputStream = contentResolver.openInputStream(filePath!!)
                val bytes = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                    bytes.write(buffer, 0, bytesRead)
                }
                avatar = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT)
//                binding.imageView4.setImageBitmap(base64ToBitmap())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
//
//    private fun base64ToBitmap(base64String: String): Bitmap? {
////        val decodedString = Base64.decode(base64String.split(",")[1], Base64.DEFAULT)
////        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
//        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//    }

//    private fun savedata() {
//        TODO("Not yet implemented")
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insert() {
        val Id_SanPham = dbRef.push().key!!
        val ten = binding.ten.text.toString()
        val gia = binding.gia.text.toString()
        val soluong = binding.soluong.text.toString()
        val danhmuc = binding.idDanhmuc.text.toString()
        val nhacungcap = binding.nhacungcap.text.toString()
        val mota = binding.mota.text.toString()
        binding.soluongdaban.setText("0")
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)
        binding.ngay.setText(formattedDateTime)
        val sanpham = Sanpham(
            Id_SanPham,
            ten,
            gia,
            soluong,
            avatar,
            danhmuc,
            nhacungcap,
            mota,
            "0",
            formattedDateTime
        )

        dbRef.child(Id_SanPham).setValue(sanpham).addOnCompleteListener {
            Toast.makeText(this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show()
//            binding.ten.setText("")
//            binding.gia.setText("")
//            binding.edtEmpSalary.setText("")

        }.addOnFailureListener { err ->
            Toast.makeText(this, "ERR ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun insert_image(view: View) {}

//    private fun saveImage() {
//        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
//        myfileintent.setType("images/*")
//        ActivityResultLauncher.launch(myfileintent)
//    }    private fun saveImage() {
//        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
//        myfileintent.setType("images/*")
//        ActivityResultLauncher.launch(myfileintent)
//    }

//    private var ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        if (result.resultCode == RESULT_OK) {
//            val uri = result.data!!.data
//            try {
//                val inputStream = contentResolver.openInputStream(uri!!)
//                val myBitmap = BitmapFactory.decodeStream(inputStream)
//                val stream = ByteArrayOutputStream()
//                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val bytes = stream.toByteArray()
//                simage = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
//                binding.imageView4.setImageBitmap(myBitmap)
//                inputStream!!.close()
//                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
//
//            } catch (ex: java.lang.Exception) {
//                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


}