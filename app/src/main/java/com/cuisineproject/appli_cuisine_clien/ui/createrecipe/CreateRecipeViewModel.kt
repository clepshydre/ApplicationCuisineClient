package com.cuisineproject.appli_cuisine_clien.ui.createrecipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuisineproject.appli_cuisine_clien.constant.ERROR_GENERAL_MESSAGE
import com.cuisineproject.appli_cuisine_clien.constant.ERROR_MESSAGE_COMPLETE_ONE
import com.cuisineproject.appli_cuisine_clien.constant.ERROR_MESSAGE_GENERAL_NOT_COMPLETE

import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO
import com.cuisineproject.appli_cuisine_clien.model.Ingredient
import com.cuisineproject.appli_cuisine_clien.model.MyException
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_COOKING_TIME_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_COST_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_DIFFICULTY_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_GENERAL_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_INGREDIENT_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_INSTRUCTION_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_PREPARATION_TIME_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_TITLE_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.MyException.Companion.ERROR_WAITING_TIME_CREATE_RECIPE
import com.cuisineproject.appli_cuisine_clien.model.UnitEntity
import com.cuisineproject.appli_cuisine_clien.utils.IngredientUtils
import com.cuisineproject.appli_cuisine_clien.utils.RecipeUtils
import com.cuisineproject.appli_cuisine_clien.utils.UnitUtils
import kotlin.concurrent.thread

class CreateRecipeViewModel : ViewModel() {

    var sendIngredient = MutableLiveData<List<Ingredient>>(ArrayList())
    var sendUnit = MutableLiveData<List<UnitEntity>>(ArrayList())
    var errorGeneral = MutableLiveData<String>(null)
    var errorReset = MutableLiveData<List<Int>>(null)
    var errorElement = MutableLiveData<List<MyException>>(null)

    private var errorList = arrayListOf(ERROR_TITLE_CREATE_RECIPE,
        ERROR_PREPARATION_TIME_CREATE_RECIPE, ERROR_COOKING_TIME_CREATE_RECIPE,
        ERROR_WAITING_TIME_CREATE_RECIPE, ERROR_DIFFICULTY_CREATE_RECIPE, ERROR_COST_CREATE_RECIPE,
        ERROR_INGREDIENT_CREATE_RECIPE, ERROR_INSTRUCTION_CREATE_RECIPE,ERROR_GENERAL_CREATE_RECIPE)

    fun createRecipe(recipe: RecipeDisplayDTO) {
        resetError()
        val error = verify(recipe)
        if(!error){
            thread {
                try {
                    RecipeUtils.createRecipe(recipe)
                } catch (e: Exception) {
                    errorGeneral.postValue(ERROR_GENERAL_MESSAGE)
                }
            }
        }else{
            errorGeneral.postValue(ERROR_MESSAGE_GENERAL_NOT_COMPLETE)
        }
    }

    private fun resetError() {
        errorReset.postValue(errorList)
    }

    private fun verify(recipe:RecipeDisplayDTO):Boolean{
        val list :ArrayList<MyException> = arrayListOf()
        var error = false
        if(recipe.name.isNullOrBlank()){
            list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_TITLE_CREATE_RECIPE))
            error = true
        }

        if(recipe.preparationTime == null){
            list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_PREPARATION_TIME_CREATE_RECIPE))
            error = true
        }

        if(recipe.cookingTime == null){
            list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_COOKING_TIME_CREATE_RECIPE))
            error = true
        }

        if(recipe.waitingTime == null){
            list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_WAITING_TIME_CREATE_RECIPE))
            error = true
        }

        if(recipe.difficulty == null){
            list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_DIFFICULTY_CREATE_RECIPE))
            error = true
        }

        if(recipe.cost == null){
            list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_COST_CREATE_RECIPE))
            error = true
        }

        recipe.listComposeDTO.forEach{ composeDTO ->
            if(composeDTO.quantity == 0.0 || composeDTO.unitName.isNullOrBlank() || composeDTO.ingredientName.isNullOrBlank()){
                list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_INGREDIENT_CREATE_RECIPE))
                error = true
            }
        }

        recipe.listInstructionDTO.forEach{ instructionDTO ->
            if(instructionDTO.instruction.isNullOrBlank()){
                list.add(MyException(ERROR_MESSAGE_COMPLETE_ONE, ERROR_INSTRUCTION_CREATE_RECIPE))
                error = true
            }
        }
        if(error) {
            errorElement.postValue(list)
        }
        return error
    }

    fun getIngredients(){
        thread{
            try{
                val list = IngredientUtils.getAllIngredients()
                sendIngredient.postValue(list)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getUnits() {
        thread{
            try{
                val list = UnitUtils.getAllUnits()
                sendUnit.postValue(list)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}