package com.cuisineproject.appli_cuisine_clien.utils

import com.cuisineproject.appli_cuisine_clien.constant.*
import com.cuisineproject.appli_cuisine_clien.dto.RecipeDisplayDTO
import com.cuisineproject.appli_cuisine_clien.dto.RecipeRVDTO
import com.cuisineproject.appli_cuisine_clien.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object RecipeUtils {

    private val gson = Gson()

    fun getAllRecipe(): List<Recipe>{

        val json = RequestUtils.sendGet(URL_ALL_RECIPES)

        //Parser le JSON avec le bon bean et GSON
        val listOfRecipe: Type = object : TypeToken<List<Recipe?>?>() {}.type

        //Retourner la donnée
        return gson.fromJson(json, listOfRecipe)
    }

    fun getAllHomeRecipe(): List<RecipeRVDTO>{

        val json = RequestUtils.sendGet(URL_ALL_HOME_RECIPES)

        //Parser le JSON avec le bon bean et GSON
        val listOfRecipe: Type = object : TypeToken<List<RecipeRVDTO?>?>() {}.type

        //Retourner la donnée
        return gson.fromJson(json, listOfRecipe)
    }

    fun getTrendingRecipes(){
        val json = RequestUtils.sendGet(URL_TRENDING_RECIPES)

        //Parser le JSON avec le bon bean et GSON
        val listOfTrendingRecipe: Type = object : TypeToken<List<Recipe?>?>() {}.type

        //Retourner la donnée
        return gson.fromJson(json, listOfTrendingRecipe)
    }

    fun getRecipeById(idRecipe: Int):Recipe{
        val idJson = gson.toJson(idRecipe)

        val jsonResponse = RequestUtils.sendPost(URL_RECIPE_BY_ID, idJson)

        //Retourner la donnée
        return gson.fromJson(jsonResponse,Recipe::class.java )
    }

    fun getLikedRecipes():List<RecipeRVDTO>{
        val jsonResponse = RequestUtils.sendGet(URL_ALL_FAVORITE_RECIPES)
        val listOfLikedRecipes: Type = object : TypeToken<List<RecipeRVDTO?>?>() {}.type
        return gson.fromJson(jsonResponse,listOfLikedRecipes )
    }

    fun likeRecipe(idRecipe: Int) {
        val idJson = gson.toJson(idRecipe)

        RequestUtils.sendPost(URL_LIKE_RECIPE, idJson)
    }

    fun unlikeRecipe(idRecipe: Int) {
        val idJson = gson.toJson(idRecipe)

        RequestUtils.sendPost(URL_UNLIKE_RECIPE, idJson)
    }

    fun getRecipeToDisplay(idRecipe: Int):RecipeDisplayDTO {
        val idJson = gson.toJson(idRecipe)

        val jsonResponse = RequestUtils.sendPost(URL_RECIPE_TO_DISPLAY, idJson)

        //Retourner la donnée
        return gson.fromJson(jsonResponse,RecipeDisplayDTO::class.java )
    }

    fun createRecipe(recipe: RecipeDisplayDTO) {
        val json = gson.toJson(recipe)
        RequestUtils.sendPost(URL_CREATE_RECIPE, json)
    }
}