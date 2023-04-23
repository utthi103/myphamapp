package com.example.myphamapp.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myphamapp.Model.Sanpham
import com.example.myphamapp.Model.cart
import com.example.myphamapp.R
import com.example.myphamapp.fr_home
import com.google.firebase.database.*

class sanphamAdapter(private val sanpham:ArrayList<Sanpham>, private val context: Context): RecyclerView.Adapter<sanphamAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener
    private lateinit var db: DatabaseReference


    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
        class ViewHolder(itemView: View, clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
        val image =itemView.findViewById<ImageView>(R.id.imageView)
        val tensanpham =itemView.findViewById<TextView>(R.id.tenSP)
        val danhmuc =itemView.findViewById<TextView>(R.id.danhmucSP)
        //            val danhgia =findViewById<TextView>(R.id.danhgia)
        val giaSp =itemView.findViewById<TextView>(R.id.giaSP)
            val sldaban =itemView.findViewById<TextView>(R.id.sldaban)
            val add =itemView.findViewById<Button>(R.id.addGioHang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        xml->view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_ds_san_pham_design,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val cur = sanpham[position]
        holder.tensanpham.setText(cur.TenSP.toString())
        holder.danhmuc.setText(cur.id_danhmuc.toString())
        holder.giaSp.setText(cur.GiaSP.toString())
        holder.sldaban.setText(cur.sl_daban.toString())
        val bytes=Base64.decode(cur.AnhSP ,Base64.DEFAULT)
        val bitMap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        holder.image.setImageBitmap(bitMap)


        holder.add.setOnClickListener {
            val id_sp = cur.id_SanPham.toString()
            val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            val iduser = sharedPreferences.getString("iduser",null)
//            holder.giaSp.setText(iduser.toString())
            val db = FirebaseDatabase.getInstance().getReference("cart")
            val id_cart = db.push().key!!
            val cart = cart(id_cart,iduser.toString(),id_sp,cur.TenSP.toString(),cur.GiaSP.toString(),cur.AnhSP.toString(),"1")
            var count = 0;
            val query = db.orderByChild("id_user").equalTo(iduser.toString())
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (userSnapshot in dataSnapshot.children) {
                                if (userSnapshot.child("id_SP").equals(id_sp)) {
                                // id_SP tồn tại trong iduser này
                                    count = count+1
                            }
                        }
                        if (count==0){
                            db.child(iduser.toString()).setValue(cart)
                        }
                    } else {
                                    holder.giaSp.setText(iduser.toString())
                        // iduser không tồn tại trong bảng
//                        db.child(id_cart).child(iduser.toString()).setValue(cart)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Xử lý khi có lỗi xảy ra
                }
            })

//            db.child(id_cart).child(iduser.toString()).setValue(cart)

        }

    }


    override fun getItemCount(): Int {
        return sanpham.size
    }
}