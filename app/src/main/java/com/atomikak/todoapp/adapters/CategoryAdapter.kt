package com.atomikak.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.atomikak.todoapp.R

class CategoryAdapter(val context: Context, val categoryList: List<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_category_list_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.mcl_categoryName.setText(categoryList[position])
        holder.mcl_categoryCard.setOnClickListener {
            Toast.makeText(context, categoryList[position], Toast.LENGTH_SHORT).show()
        }
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mcl_categoryName: TextView = itemView.findViewById(R.id.mcl_categoryName)
        val mcl_categoryCard: CardView = itemView.findViewById(R.id.mcl_categoryCard)
    }
}