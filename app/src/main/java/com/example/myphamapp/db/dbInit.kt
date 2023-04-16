package com.example.myphamapp.db

import com.google.firebase.database.FirebaseDatabase

class dbInit (private val pathRef: String){
    private lateinit var pathReference: String
    private val database = FirebaseDatabase.getInstance()

}