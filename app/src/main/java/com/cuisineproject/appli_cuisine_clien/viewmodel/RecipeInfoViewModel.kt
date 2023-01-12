package com.cuisineproject.appli_cuisine_clien.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO
import com.cuisineproject.appli_cuisine_clien.dto.RecipeRVDTO
import com.cuisineproject.appli_cuisine_clien.utils.RecipeUtils
import com.cuisineproject.appli_cuisine_clien.utils.UserUtils
import kotlin.concurrent.thread

class RecipeInfoViewModel: ViewModel() {
    var dataShow = MutableLiveData<RecipeDisplayDTO>(null)

    fun getRecipe(idRecipe: Int) {
        thread {
            try {
                val recipe = RecipeUtils.getRecipeToDisplay(idRecipe)
                dataShow.postValue(recipe)
                println(recipe)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}