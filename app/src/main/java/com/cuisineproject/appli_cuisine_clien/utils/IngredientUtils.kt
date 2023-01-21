package com.cuisineproject.appli_cuisine_clien.utils

import com.cuisineproject.appli_cuisine_clien.constant.URL_ALL_INGREDIENTS
import com.cuisineproject.appli_cuisine_clien.model.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object IngredientUtils {

    private val gson = Gson()

    fun getAllIngredients(): List<Ingredient>{

        val json = RequestUtils.sendGet(URL_ALL_INGREDIENTS)

        //Parser le JSON avec le bon bean et GSON
        val listOfIngredient: Type = object : TypeToken<List<Ingredient?>?>() {}.type
        val list:List<Ingredient> = gson.fromJson(json, listOfIngredient)
        println(list[0].name)
        //Retourner la donnée
        return list
    }
}