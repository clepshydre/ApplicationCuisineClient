package com.cuisineproject.appli_cuisine_clien.ui.createrecipe

import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO
import com.cuisineproject.appli_cuisine_clien.utils.RecipeUtils
import kotlin.concurrent.thread

class CreateRecipeViewModel : ViewModel() {

    fun createRecipe(recipe: RecipeDisplayDTO) {
        thread{
            try{
                RecipeUtils.createRecipe(recipe)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}