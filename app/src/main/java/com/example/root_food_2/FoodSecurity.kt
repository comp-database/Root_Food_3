package com.example.root_food_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.root_food_2.Adapter.GuidelinesAdapter
import com.example.root_food_2.DataClass.guidelinesClass
import com.example.root_food_2.databinding.ActivityFoodSecurityBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FoodSecurity : AppCompatActivity() {
    lateinit var binding : ActivityFoodSecurityBinding
    lateinit var adapterRc : GuidelinesAdapter
    private lateinit var arrayListofGuidelines: ArrayList<guidelinesClass>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_food_security)

        binding = ActivityFoodSecurityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.rvGuidelines
        db = FirebaseFirestore.getInstance()
        recyclerView.layoutManager = LinearLayoutManager(this)
        arrayListofGuidelines = arrayListOf()
        adapterRc = GuidelinesAdapter(arrayListofGuidelines)
        recyclerView.adapter = adapterRc

        EventChangeListener()
//        db.collection("food_security_guidelines").get()
//            .addOnSuccessListener {
//                if(!it.isEmpty ){
//                    for(data in it.documents){
//                        val guidelines : guidelinesClass? = data.toObject(guidelinesClass::class.java)
//                        if (guidelines != null) {
//                            arrayListofGuidelines.add(guidelines)
//                        }
//                    }
//                    recyclerView.adapter = GuidelinesAdapter(arrayListofGuidelines)
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
//            }
    }
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("food security guidelines")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            arrayListofGuidelines.add(dc.document.toObject(guidelinesClass::class.java))
                        }
                    }
                    adapterRc.notifyDataSetChanged()
                }
            })
    }

}