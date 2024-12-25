package com.example.mealsearchapp.presentation.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealsearchapp.databinding.ViewholderSearchListBinding
import com.example.mealsearchapp.domain.model.Meal

class MealSearchAdapter: RecyclerView.Adapter<MealSearchAdapter.MyViewHolder>() {

    class MyViewHolder(val viewHolder: ViewholderSearchListBinding): RecyclerView.ViewHolder(viewHolder.root)

    private var list = mutableListOf<Meal>()
    private var listener: ((Meal) -> Unit)? = null

    fun setContentList(list: MutableList<Meal>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setItemClickListener(l : ((Meal) -> Unit)) {
        listener = l
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealSearchAdapter.MyViewHolder {
        val binding = ViewholderSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealSearchAdapter.MyViewHolder, position: Int) {
        holder.viewHolder.apply {
            Glide.with(mealImage.context).load(list[position].image).into(mealImage)
            mealName.text = list[position].name
            this.root.setOnClickListener {
                listener?.invoke(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}