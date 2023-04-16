package com.example.myphamapp.Adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myphamapp.Model.Sanpham
import com.example.myphamapp.R

class sanphamAdapter(private val sanpham:ArrayList<Sanpham>): RecyclerView.Adapter<sanphamAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        xml->view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_ds_san_pham_design,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val cur = sanpham[position]
//            cur.tensanpham.text =sanpham[position].TenSP
//            danhmuc.text=sanpham[position].Id_danhmuc
//            giaSp.text=sanpham[position].GiaSP
        holder.tensanpham.setText(cur.TenSP.toString())
        holder.danhmuc.setText(cur.Id_danhmuc.toString())
        holder.giaSp.setText(cur.GiaSP.toString())
        val bytes=Base64.decode(cur.AnhSP ,Base64.DEFAULT)
        val bitMap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        holder.image.setImageBitmap(bitMap)

    }

    override fun getItemCount(): Int {
        return sanpham.size
    }
}