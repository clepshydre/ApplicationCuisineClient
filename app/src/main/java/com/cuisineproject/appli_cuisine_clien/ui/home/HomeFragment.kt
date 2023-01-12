package com.cuisineproject.appli_cuisine_clien.ui.home

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
import com.cuisineproject.appli_cuisine_clien.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val model by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvRecipes.layoutManager = GridLayoutManager(context, 1)

        model.loadListRecipe()

        model.dataShow.observe(viewLifecycleOwner){ recipeList ->
            binding.rvRecipes.adapter = RecipeListAdapter(recipeList, object : RecipeListAdapterListener{
                override fun displayRecipeOnClick(v: View, position: Int) {
                    println("id : ${recipeList[position].id}")
                    val intent = Intent(v.context, RecipeInfoActivity::class.java).putExtra("id",
                        recipeList[position].id.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
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

        model.errorMessage.observe(viewLifecycleOwner){
            if(it.isNotBlank()) {
                binding.tvErrorHome.visibility = View.VISIBLE
            }else{
                binding.tvErrorHome.visibility = View.GONE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}