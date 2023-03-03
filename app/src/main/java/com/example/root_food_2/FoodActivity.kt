package com.example.root_food_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.root_food_2.databinding.ActivityFoodBinding
import com.example.root_food_2.databinding.ActivityHomeScreenBinding

class FoodActivity : AppCompatActivity() {

    lateinit var binding: ActivityFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_food)

        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardView1.setOnClickListener {
            startActivity(Intent(this,FoodSecurity::class.java))
        }

    }
}