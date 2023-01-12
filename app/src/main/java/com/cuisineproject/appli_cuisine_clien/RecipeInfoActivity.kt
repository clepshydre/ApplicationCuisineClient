package com.cuisineproject.appli_cuisine_clien

import android.R
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cuisineproject.appli_cuisine_clien.constant.URL_IMAGE
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityRecipeInfoBinding
import com.cuisineproject.appli_cuisine_clien.viewmodel.RecipeInfoViewModel
import com.squareup.picasso.Picasso


class RecipeInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRecipeInfoBinding.inflate(layoutInflater)}
    val model by lazy { ViewModelProvider(this)[RecipeInfoViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var linearLayout: LinearLayout = binding.root

        model.dataShow.observe(this){ recipe ->
            if(recipe != null) {
                binding.tvTitleRecipe.text = recipe.name

                val url = URL_IMAGE + recipe.image
                Picasso.with(this).load(url).into(binding.ivRecipe)

                binding.tvTotalTime.text = recipe.totalTime.toString()

                if (recipe.difficulty == 1) {
                    binding.tvCookingLevel.text = "Facile"
                } else if (recipe.difficulty == 2) {
                    binding.tvCookingLevel.text = "Moyen"
                } else {
                    binding.tvCookingLevel.text = "Difficile"
                }

                if (recipe.cost == 1) {
                    binding.tvBudgetLevel.text = "€"
                } else if (recipe.cost == 2) {
                    binding.tvBudgetLevel.text = "€€"
                } else {
                    binding.tvBudgetLevel.text = "€€"
                }

                val textView = TextView(this)
                val titleTV = TextView(this)

                titleTV.setTextAppearance(com.cuisineproject.appli_cuisine_clien.R.style.title_style)
                titleTV.text = "Ingredients"
                linearLayout.addView(titleTV)
                recipe.listIngredientDTO.forEach { ingredient ->
                    textView.text = "${ingredient.quantity} ${ingredient.unit}: ${ingredient.name} "
                    linearLayout.addView(textView)
                }

                titleTV.text = "Temps"
                linearLayout.addView(titleTV)
                textView.text = "Préparation: ${recipe.preparationTime}min"
                linearLayout.addView(textView)
                textView.text = "Repos: ${recipe.waitingTime}min"
                linearLayout.addView(textView)
                textView.text = "Cuisson: ${recipe.cookingTime}min"
                linearLayout.addView(textView)

                titleTV.text = "Préparation"
                linearLayout.addView(titleTV)
                val step = 1
                recipe.listInstructionDTO.forEach { instruction ->
                    textView.text = "Étape $step:"
                    linearLayout.addView(textView)
                    textView.text = instruction.instruction
                    linearLayout.addView(textView)
                }
            }
        }
        val id = intent.getStringExtra("id")
        if( id != null) {
            println("id not null")
            val id = intent.getStringExtra("id")!!.toInt()
            model.getRecipe(id)
        }
    }
}