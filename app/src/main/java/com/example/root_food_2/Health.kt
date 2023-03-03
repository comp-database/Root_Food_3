package com.example.root_food_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.root_food_2.Adapter.questionAdapter
import com.example.root_food_2.DataClass.questions
import com.example.root_food_2.databinding.ActivityHealthBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Health : AppCompatActivity() {
    lateinit var binding : ActivityHealthBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<questions>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)

        binding = ActivityHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.rvQuestion
        recyclerView.layoutManager = LinearLayoutManager(this)
        arrayList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        db.collection("ques").get()
            .addOnSuccessListener {
                if(!it.isEmpty ){
                    for(data in it.documents){
                        val question : questions? = data.toObject(questions::class.java)
                        if (question != null) {
                            arrayList.add(question)
                        }
                    }
                    recyclerView.adapter = questionAdapter(arrayList)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}