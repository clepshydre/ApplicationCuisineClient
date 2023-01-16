package com.cuisineproject.appli_cuisine_clien.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cuisineproject.appli_cuisine_clien.RecipeInfoActivity
import com.cuisineproject.appli_cuisine_clien.adapter.RecipeListAdapter
import com.cuisineproject.appli_cuisine_clien.adapter.RecipeListAdapterListener
import com.cuisineproject.appli_cuisine_clien.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    val model by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvRecipesLike.layoutManager = GridLayoutManager(context, 1)

        model.loadFavoriteListRecipe()

        model.dataShow.observe(viewLifecycleOwner){ recipeList ->
            binding.rvRecipesLike.adapter = RecipeListAdapter(recipeList, object :
                RecipeListAdapterListener {
                override fun displayRecipeOnClick(v: View, position: Int) {
                    val intent = Intent(v.context, RecipeInfoActivity::class.java).putExtra("id",
                        recipeList[position].id)
                    startActivity(intent)
                }

                override fun likeOnClick(v: View, position: Int) {
                    recipeList[position].id?.let { model.likeRecipe(it) }
                }

                override fun unlikeOnClick(v: View, position: Int) {
                    recipeList[position].id?.let { model.unlikeRecipe(it) }
                }
            })
        }
        
        model.messageNoRecipe.observe(viewLifecycleOwner){
            if(it) {
                binding.tvNoLikedRecipes.visibility = View.VISIBLE
            }else{
                binding.tvNoLikedRecipes.visibility = View.GONE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}