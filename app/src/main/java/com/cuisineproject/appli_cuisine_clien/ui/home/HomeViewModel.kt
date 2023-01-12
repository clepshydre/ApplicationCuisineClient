package com.cuisineproject.appli_cuisine_clien.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.dto.RecipeRVDTO
import com.cuisineproject.appli_cuisine_clien.utils.RecipeUtils
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {

    var dataShow = MutableLiveData<List<RecipeRVDTO>>(ArrayList())
    var runInProgress = MutableLiveData(false)
    var errorMessage = MutableLiveData("")

    fun loadListRecipe(){
        runInProgress.postValue(true)
        thread {
            try {
                val recipeList = ArrayList(RecipeUtils.getAllHomeRecipe())
                if(!recipeList.isNullOrEmpty()){
                    runInProgress.postValue(false)
                    dataShow.postValue(recipeList)
                }else{
                    throw Exception("Une erreur est survenue, veuillez réessayer")
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                runInProgress.postValue(false)
                errorMessage.postValue(e.message)
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