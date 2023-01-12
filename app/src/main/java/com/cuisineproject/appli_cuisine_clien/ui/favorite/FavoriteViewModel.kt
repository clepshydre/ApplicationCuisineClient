package com.cuisineproject.appli_cuisine_clien.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.dto.RecipeRVDTO
import com.cuisineproject.appli_cuisine_clien.model.Recipe
import com.cuisineproject.appli_cuisine_clien.utils.RecipeUtils
import kotlin.concurrent.thread

class FavoriteViewModel : ViewModel() {

    val messageNoRecipe = MutableLiveData(false)
    var dataShow = MutableLiveData<List<RecipeRVDTO>>(ArrayList())
    var runInProgress = MutableLiveData(false)

    fun loadFavoriteListRecipe(){
        runInProgress.postValue(true)
        thread {
            try {
                val recipeList = ArrayList(RecipeUtils.getLikedRecipes())
                println(recipeList.toString())
                runInProgress.postValue(false)
                if(!recipeList.isNullOrEmpty()){
                    dataShow.postValue(recipeList)
                }else{
                    messageNoRecipe.postValue(true)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                runInProgress.postValue(false)
            }
        }
    }

    fun likeRecipe(idRecipe:Int){
        thread {
            try {
                RecipeUtils.likeRecipe(idRecipe)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun unlikeRecipe(idRecipe: Int) {
        thread {
            try {
                RecipeUtils.unlikeRecipe(idRecipe)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}