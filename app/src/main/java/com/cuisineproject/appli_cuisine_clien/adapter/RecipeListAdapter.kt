package com.cuisineproject.appli_cuisine_clien.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuisineproject.appli_cuisine_clien.constant.URL_IMAGE
import com.cuisineproject.appli_cuisine_clien.databinding.RowRecipeBinding
import com.cuisineproject.appli_cuisine_clien.dto.RecipeRVDTO
import com.squareup.picasso.Picasso

class RecipeListAdapter(var recipesRVDTO: List<RecipeRVDTO>, var recipesAdapterListener: RecipeListAdapterListener) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {


    class ViewHolder(var binding : RowRecipeBinding) : RecyclerView.ViewHolder(binding.root)


    class Comparator : DiffUtil.ItemCallback<RecipeRVDTO>() {
        override fun areItemsTheSame(oldItem: RecipeRVDTO, newItem: RecipeRVDTO): Boolean =
            oldItem === newItem


        override fun areContentsTheSame(oldItem: RecipeRVDTO, newItem: RecipeRVDTO): Boolean =
            false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RowRecipeBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.rowContainer.context
        val currentItem = recipesRVDTO[position]

        val url = URL_IMAGE + currentItem.image
        Picasso.with(context).load(url).into(holder.binding.ivRecipe)

        holder.binding.tvNameRecipe.text = currentItem.name

        if(currentItem.like != null) {
            if (currentItem.like!!) {
                holder.binding.ivLike.visibility = View.GONE
                holder.binding.ivUnlike.visibility = View.VISIBLE
            } else {
                holder.binding.ivLike.visibility = View.VISIBLE
                holder.binding.ivUnlike.visibility = View.GONE
            }
        }
        holder.binding.ivRecipe.setOnClickListener{
            recipesAdapterListener.displayRecipeOnClick(it,position)
        }

        holder.binding.ivLike.setOnClickListener{
            it.visibility = View.GONE
            holder.binding.ivUnlike.visibility = View.VISIBLE
            recipesAdapterListener.likeOnClick(it,position)
        }

        holder.binding.ivUnlike.setOnClickListener{
            holder.binding.ivLike.visibility = View.VISIBLE
            it.visibility = View.GONE
            recipesAdapterListener.unlikeOnClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return recipesRVDTO.count()
    }
}

interface RecipeListAdapterListener {
    fun displayRecipeOnClick(v: View, position: Int)
    fun likeOnClick(v: View, position: Int)
    fun unlikeOnClick(v: View, position: Int)
}