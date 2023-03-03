package com.example.root_food_2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.root_food_2.R
import com.example.root_food_2.DataClass.questions

class questionAdapter(private var question: ArrayList<questions>) : RecyclerView.Adapter<questionAdapter.MyViewHolder>(){

    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val question: TextView = itemView.findViewById(R.id.tv_questions)
        val expand : LinearLayout = itemView.findViewById(R.id.expand)
        val expandText : TextView = itemView.findViewById(R.id.tv_expand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.question_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return question.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = question[position]
        holder.question.text = question[position].ques

        val isVisible : Boolean = item.visibility
        holder.expandText.visibility = if(isVisible) View.VISIBLE else View.GONE

        holder.expand.setOnClickListener{
            item.visibility = !item.visibility
            notifyItemChanged(position)
        }
    }
}