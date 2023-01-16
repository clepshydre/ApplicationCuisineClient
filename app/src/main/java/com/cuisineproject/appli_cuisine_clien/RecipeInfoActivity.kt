package com.cuisineproject.appli_cuisine_clien

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cuisineproject.appli_cuisine_clien.constant.URL_IMAGE
import com.cuisineproject.appli_cuisine_clien.databinding.ActivityRecipeInfoBinding
import com.cuisineproject.appli_cuisine_clien.dto.IngredientDTO
import com.cuisineproject.appli_cuisine_clien.dto.InstructionDTO
import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO
import com.cuisineproject.appli_cuisine_clien.viewmodel.RecipeInfoViewModel
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class RecipeInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRecipeInfoBinding.inflate(layoutInflater)}
    val model by lazy { ViewModelProvider(this)[RecipeInfoViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        model.dataShow.observe(this){ recipe ->
            if(recipe != null) {
                displayRecipe(recipe)
            }
        }
            model.getRecipe(getId())
}

    private fun displayRecipe(recipe: RecipeDisplayDTO) {
        setTitleRecipe(recipe.name)
        setImage(recipe.image)
        setTotalTime(recipe.totalTime)
        setDifficulty(recipe.difficulty)
        setCost(recipe.cost)
        setIngredients(recipe.listIngredientDTO)
        setTimeSection(recipe)
        setPreparationSection(recipe.listInstructionDTO)

    }

    private fun setTitleRecipe(name:String?){
        binding.tvTitleRecipe.text = name
    }

    private fun setImage(image: String?) {
        val url = URL_IMAGE + image
        Picasso.with(this).load(url).into(binding.ivRecipe)
    }

    private fun setTotalTime(totalTime: Int?) {
        binding.tvTotalTime.text = totalTime.toString()+ "min"
    }

    private fun setDifficulty(difficulty: Int?) {
        when (difficulty) {
            1 -> {
                binding.tvCookingLevel.text = "Facile"
            }
            2 -> {
                binding.tvCookingLevel.text = "Moyen"
            }
            else -> {
                binding.tvCookingLevel.text = "Difficile"
            }
        }
    }
    private fun setCost(cost: Int?) {
        when (cost) {
            1 -> {
                binding.tvBudgetLevel.text = "€"
            }
            2 -> {
                binding.tvBudgetLevel.text = "€€"
            }
            else -> {
                binding.tvBudgetLevel.text = "€€"
            }
        }
    }

    private fun setIngredients(listIngredientDTO: List<IngredientDTO>) {
        addTitleTextView("Ingrédients:")
        listIngredientDTO.forEach { ingredient ->
            addTextView("${removeDecimalZero(ingredient.quantity)} ${ingredient.unit}: ${ingredient.name}")
        }
    }
    private fun removeDecimalZero(quantity: Double?): String? {
        var format = DecimalFormat("0.#")
        return format.format(quantity)
    }
    private fun setTimeSection(recipe: RecipeDisplayDTO) {
        addTitleTextView("Temps:")
        addTextView("Préparation: ${recipe.preparationTime}min")
        addTextView("Repos: ${recipe.waitingTime}min")
        addTextView("Cuisson: ${recipe.cookingTime}min")
    }

    private fun setPreparationSection(listInstructionDTO: List<InstructionDTO>) {
        addTitleTextView("Instructions:")
        var step = 1
        listInstructionDTO.forEach { instruction ->
            addTextView("Étape $step:")
            instruction.instruction?.let { addTextView(it) }
            step++
        }
    }

    private fun addTitleTextView(text: String){
        val titleTV = TextView(this)
        titleTV.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        titleTV.setTextAppearance(R.style.title_style)
        titleTV.text = text
        binding.principalLayout.addView(titleTV)
    }

    private fun addTextView(text: String){
        val textView = TextView(this)
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.text = text
        binding.principalLayout.addView(textView)
    }
    private fun getId(): Int {
        val id = intent.getStringExtra("id")
        return if( id != null) {
            println("id not null")
            intent.getStringExtra("id")!!.toInt()
        }else{
            println("id null")
            0
        }
    }
}

